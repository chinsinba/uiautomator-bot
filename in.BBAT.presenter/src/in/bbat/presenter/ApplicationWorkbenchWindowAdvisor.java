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

import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.android.SdkConstants;

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
		BusyIndicator.showWhile(Display.getCurrent(), new Runnable() {

			@Override
			public void run() {
				initialize();				
			}
		});

	}

	private void initialize() {
		try {
			LOG.info("Started....");
			ApplicationHelper.initializeDb();
			TestDeviceManager.init(BBATConfigXml.getInstance().getAndroid_AdbPath());
		} catch (UnknownHostException e) {
			LOG.error(e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Unable to connect to DB");

		}catch (DeviceException e) {
			LOG.error(e);
		}
		catch (Exception e) {
			LOG.error(e);
		}
	}

	public static final String[] IGNORE_PERSPECTIVES = new String[] {
		"org.eclipse.debug.ui.DebugPerspective",
		"org.eclipse.jdt.ui.JavaPerspective", "org.eclipse.jdt.ui.JavaHierarchyPerspective",
		"org.eclipse.jdt.ui.JavaBrowsingPerspective", "org.eclipse.mylyn.tasks.ui.perspectives.planning",
		"org.eclipse.pde.ui.PDEPerspective", "org.eclipse.team.cvs.ui.cvsPerspective",
		"org.eclipse.ui.resourcePerspective", }; 

	@Override
	public void postWindowCreate() {

		removeUnWantedPerspectives();

		String errorMessage = validateAndroidSdkLocation(BBATConfigXml.getInstance().getAndroid_SdkPath());

		if(errorMessage!=null){
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"SDK Error", errorMessage);
		}
	}

	public static String validateAndroidSdkLocation(String osSdkLocation) {
		
		if (osSdkLocation == null || osSdkLocation.trim().length() == 0) {
			return "Location of the Android SDK has not been setup in the preferences.";
		}

		if (!osSdkLocation.endsWith(File.separator)) {
			osSdkLocation = osSdkLocation + File.separator;
		}

		File osSdkFolder = new File(osSdkLocation);
		if (osSdkFolder.isDirectory() == false) {
			return "Could not find SDK at: "+osSdkLocation;
		}

		String osTools = osSdkLocation + SdkConstants.OS_SDK_TOOLS_FOLDER;
		File toolsFolder = new File(osTools);
		if (toolsFolder.isDirectory() == false) {
			return "Could not find folder"+ SdkConstants.FD_TOOLS +"inside SDK "+osSdkLocation;

		}

		// check that we have both the tools component and the platform-tools component.
		String platformTools = osSdkLocation + SdkConstants.OS_SDK_PLATFORM_TOOLS_FOLDER;
		if (checkFolder(platformTools) == false) {
			return 	"SDK Platform Tools component is missing!\n" +
					"Please use the SDK Manager to install it.";
		}

		String tools = osSdkLocation + SdkConstants.OS_SDK_TOOLS_FOLDER;
		if (checkFolder(tools) == false) {
			return 	"SDK Tools component is missing!\n" +
					"Please use the SDK Manager to install it.";
		}

		// check the path to various tools we use to make sure nothing is missing. This is
		// not meant to be exhaustive.
		String[] filesToCheck = new String[] {
				osSdkLocation + getOsRelativeAdb(),
				osSdkLocation + getOsRelativeEmulator()
		};
		for (String file : filesToCheck) {
			if (checkFile(file) == false) {
				return "Could not find file :"+ file;
			}
		}
		return null;
	}

	public static String getOsRelativeAdb() {
		return SdkConstants.OS_SDK_PLATFORM_TOOLS_FOLDER + SdkConstants.FN_ADB;
	}

	/** Returns the zipalign path relative to the sdk folder */
	public static String getOsRelativeZipAlign() {
		return SdkConstants.OS_SDK_TOOLS_FOLDER + SdkConstants.FN_ZIPALIGN;
	}

	/** Returns the emulator path relative to the sdk folder */
	public static String getOsRelativeEmulator() {
		return SdkConstants.OS_SDK_TOOLS_FOLDER + SdkConstants.FN_EMULATOR;
	}
	/**
	 * Checks if a path reference a valid existing file.
	 * @param osPath the os path to check.
	 * @return true if the file exists and is, in fact, a file.
	 */
	private static boolean checkFile(String osPath) {
		File file = new File(osPath);
		if (file.isFile() == false) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if a path reference a valid existing folder.
	 * @param osPath the os path to check.
	 * @return true if the folder exists and is, in fact, a folder.
	 */
	private static boolean checkFolder(String osPath) {
		File file = new File(osPath);
		if (file.isDirectory() == false) {
			return false;
		}

		return true;
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
