package in.bbat.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
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
