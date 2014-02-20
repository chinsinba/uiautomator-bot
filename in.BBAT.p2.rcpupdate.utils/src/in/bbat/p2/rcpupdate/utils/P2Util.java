package in.bbat.p2.rcpupdate.utils;

import in.bbat.logger.BBATLogger;
import in.bbat.p2.rcpupdate.utils.plugin.Activator;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.operations.ProvisioningJob;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.Update;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


/**
 * @see http://wiki.eclipse.org/Equinox/p2/Adding_Self-Update_to_an_RCP_Application
 * @see http://bugs.eclipse.org/281226
 */
public class P2Util {

	private static final Logger LOG = BBATLogger.getLogger(P2Util.class.getName());
	public static void checkForUpdates() {
		try {
			ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(null);
			progressDialog.run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					execute(new NullProgressMonitor());
				}

			});
		} catch (InvocationTargetException e) {
			Activator.log(e);
		} catch (InterruptedException e) {
			Activator.log(e);
		}

	}

	private static void doCheckForUpdates(IProgressMonitor monitor) {
		BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference reference = bundleContext.getServiceReference(IProvisioningAgent.SERVICE_NAME);
		if (reference == null) {
			Activator.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"No provisioning agent found.  This application is not set up for updates."));
			return;
		}

		final IProvisioningAgent agent = (IProvisioningAgent) bundleContext.getService(reference);
		try {
			IStatus updateStatus = P2Util.checkForUpdates(agent, monitor);
			Activator.log(updateStatus);
			if (updateStatus.getCode() == UpdateOperation.STATUS_NOTHING_TO_UPDATE) {
				return;
			}
			if (updateStatus.getSeverity() != IStatus.ERROR)
				PlatformUI.getWorkbench().restart();
		} finally {
			bundleContext.ungetService(reference);
		}
	}

	static IStatus checkForUpdates(IProvisioningAgent agent, IProgressMonitor monitor)
			throws OperationCanceledException {
		ProvisioningSession session = new ProvisioningSession(agent);
		// the default update operation looks for updates to the currently
		// running profile, using the default profile root marker. To change
		// which installable units are being updated, use the more detailed
		// constructors.
		UpdateOperation operation = new UpdateOperation(session);
		SubMonitor sub = SubMonitor.convert(monitor, "Checking for application updates...", 200);
		IStatus status = operation.resolveModal(sub.newChild(100));
		if (status.getCode() == UpdateOperation.STATUS_NOTHING_TO_UPDATE) {
			return status;
		}
		if (status.getSeverity() == IStatus.CANCEL)
			throw new OperationCanceledException();

		if (status.getSeverity() != IStatus.ERROR) {
			// More complex status handling might include showing the user what
			// updates are available if there are multiples, differentiating
			// patches vs. updates, etc. In this example, we simply update as
			// suggested by the operation.
			ProvisioningJob job = operation.getProvisioningJob(monitor);
			if (job == null) {
				return new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						"ProvisioningJob could not be created - does this application support p2 software installation?");
			}
			status = job.runModal(sub.newChild(100));
			if (status.getSeverity() == IStatus.CANCEL)
				throw new OperationCanceledException();
		}
		return status;
	}

	public static void execute( IProgressMonitor monitor) {

		
		Job j = new Job("Update Job") {
			private boolean doInstall = false;

			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				
				BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
				ServiceReference reference = bundleContext.getServiceReference(IProvisioningAgent.SERVICE_NAME);
				if (reference == null) {
					Activator.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							"No provisioning agent found.  This application is not set up for updates."));
					return Status.CANCEL_STATUS;
				}

				final IProvisioningAgent agent = (IProvisioningAgent) bundleContext.getService(reference);
				final String REPOSITORY_LOC = 
						System.getProperty("UpdateHandler.Repo", 
								"file:///home/syed/Desktop/Backup_Desktop/BLACKANDRO/repository/");

				/* 1. Prepare update plumbing */

				final ProvisioningSession session = new ProvisioningSession(agent);
				final UpdateOperation operation = new UpdateOperation(session);

				// create uri
				URI uri = null;
				try {
					uri = new URI(REPOSITORY_LOC);
				} catch (final URISyntaxException e) {
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							MessageDialog.openError(new Shell(), "URI invalid", e.getMessage());
						}
					});
					return Status.CANCEL_STATUS;
				}

				// set location of artifact and metadata repo
				operation.getProvisioningContext().setArtifactRepositories(new URI[] { uri });
				operation.getProvisioningContext().setMetadataRepositories(new URI[] { uri });

				/* 2. check for updates */

				// run update checks causing I/O
				final IStatus status = operation.resolveModal(monitor);

				// failed to find updates (inform user and exit)
				if (status.getCode() == UpdateOperation.STATUS_NOTHING_TO_UPDATE) {
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							/*MessageDialog
							.openWarning(new Shell(), "No update",
									"No updates for the current installation have been found");*/
						}
					});
					return Status.CANCEL_STATUS;
				}

				/* 3. Ask if updates should be installed and run installation */

				// found updates, ask user if to install?
				if (status.isOK() && status.getSeverity() != IStatus.ERROR) {
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							String updates = "";
							Update[] possibleUpdates = operation
									.getPossibleUpdates();
							for (Update update : possibleUpdates) {
								updates += update + "\n";
							}
							doInstall = MessageDialog.openQuestion(new Shell(),
									"Really install updates?","Do you want to install available updates ?");
						}
					});
				}

				// start installation
				if (doInstall) {
					//This is done because in windows if report server is started tool will not be allowed to update.

					final ProvisioningJob provisioningJob = operation
							.getProvisioningJob(monitor);
					// updates cannot run from within Eclipse IDE!!!
					if (provisioningJob == null) {
						LOG.error("Running update from within Eclipse IDE? This won't work!!!");
						throw new NullPointerException();
					}

					// register a job change listener to track
					// installation progress and notify user upon success
					provisioningJob
					.addJobChangeListener(new JobChangeAdapter() {
						@Override
						public void done(IJobChangeEvent event) {
							if (event.getResult().isOK()) {
								Display.getDefault().syncExec(new Runnable() {

									@Override
									public void run() {
										boolean restart = MessageDialog.openQuestion(new Shell(),
														"Updates installed, restart?",
														"Updates have been installed successfully, You will need to restart the tool for the updates to take effect.");
										if (restart) {
											PlatformUI.getWorkbench().restart();
										}
									}
								});

							}
							super.done(event);
						}
					});

					provisioningJob.schedule();
				}
				return Status.OK_STATUS;
			}
		};
		j.schedule();
	}
}
