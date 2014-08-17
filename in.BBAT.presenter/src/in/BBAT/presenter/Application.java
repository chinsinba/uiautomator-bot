package in.BBAT.presenter;

import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	private static final Logger LOG = BBATLogger.getLogger(Application.class.getName());
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) {

		Display display = PlatformUI.createDisplay();
		Location instanceLocation = null;
		try
		{
			instanceLocation = Platform.getInstanceLocation();
			instanceLocation.getURL();

			if (!instanceLocation.lock()) 
			{
				MessageDialog.openError(new Shell(display), "Error",
						"Another instance of uiautomator-bot is currently running.");
				return IApplication.EXIT_OK;
			} else {

				if (PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor())==PlatformUI.RETURN_RESTART)
					return IApplication.EXIT_RESTART;
				else
					return IApplication.EXIT_OK;
			}
		}
		catch (Exception e) 
		{
			LOG.error("Unable to start the application",e);

		}
		finally
		{
			if(instanceLocation!=null ){
				instanceLocation.release();
			}
			if(display!=null)
			{
				display.dispose();
			}
		}
		return IApplication.EXIT_OK;

	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning())
			return;
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
