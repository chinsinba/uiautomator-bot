package in.bbat.presenter;

import in.BBAT.TestRunner.device.DeviceException;
import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.bbat.abstrakt.gui.ApplicationHelper;
import in.bbat.configuration.BBATConfigXml;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.perstpectives.DeveloperPerspective;
import in.bbat.presenter.perstpectives.HistoryPerspective;
import in.bbat.presenter.perstpectives.ReporterPerspective;
import in.bbat.presenter.perstpectives.TesterPerspective;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	private static final Logger LOG = BBATLogger.getLogger(ApplicationWorkbenchWindowAdvisor.class.getName());
	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		IPreferenceStore prefStore = PlatformUI.getPreferenceStore();

		prefStore.setValue(IWorkbenchPreferenceConstants.PERSPECTIVE_BAR_EXTRAS,DeveloperPerspective.ID+","+TesterPerspective.ID+","+HistoryPerspective.ID+","+ReporterPerspective.ID);

		//		configurer.setInitialSize(new Point(800, 600));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setTitle("BBAT");
		configurer.setShowPerspectiveBar(true);
		configurer.setShowProgressIndicator(true);
	}

	public static final String[] IGNORE_PERSPECTIVES = new String[] {
		"org.eclipse.debug.ui.DebugPerspective",
		"org.eclipse.jdt.ui.JavaPerspective", "org.eclipse.jdt.ui.JavaHierarchyPerspective",
		"org.eclipse.jdt.ui.JavaBrowsingPerspective", "org.eclipse.mylyn.tasks.ui.perspectives.planning",
		"org.eclipse.pde.ui.PDEPerspective", "org.eclipse.team.cvs.ui.cvsPerspective",
		"org.eclipse.ui.resourcePerspective", }; 

	@Override
	public void postWindowCreate() {
		try {
			LOG.info("Started....");
			ApplicationHelper.initializeDb();
			TestDeviceManager.init(BBATConfigXml.getInstance().getAndroid_AdbPath());
		} catch (UnknownHostException e) {
			LOG.error(e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Unable to connect to DB");

		}catch (DeviceException e) {
			LOG.error(e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Please check the android path");
		}
		catch (Exception e) {
			LOG.error(e);
		}

		removeUnWantedPerspectives();
	}



	/**
	 * Removes the unwanted perspectives from your RCP application
	 */
	private void removeUnWantedPerspectives() {
		IPerspectiveRegistry perspectiveRegistry = PlatformUI.getWorkbench().getPerspectiveRegistry();
		IPerspectiveDescriptor[] perspectiveDescriptors = perspectiveRegistry.getPerspectives();
		List ignoredPerspectives = Arrays.asList(IGNORE_PERSPECTIVES);
		List removePerspectiveDesc = new ArrayList();

		// Add the perspective descriptors with the matching perspective ids to the list
		for (IPerspectiveDescriptor perspectiveDescriptor : perspectiveDescriptors) {
			if(ignoredPerspectives.contains(perspectiveDescriptor.getId())) {
				removePerspectiveDesc.add(perspectiveDescriptor);
			}
		}

		// If the list is non-empty then remove all such perspectives from the IExtensionChangeHandler
		if(perspectiveRegistry instanceof IExtensionChangeHandler && !removePerspectiveDesc.isEmpty()) {
			IExtensionChangeHandler extChgHandler = (IExtensionChangeHandler) perspectiveRegistry;
			extChgHandler.removeExtension(null, removePerspectiveDesc.toArray());
		}
	}

}
