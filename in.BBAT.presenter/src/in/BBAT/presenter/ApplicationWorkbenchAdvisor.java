package in.BBAT.presenter;

import in.BBAT.presenter.perstpectives.DeveloperPerspective;
import in.bbat.logger.BBATLogger;
import in.bbat.p2.rcpupdate.utils.P2Util;

import org.apache.log4j.Logger;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final Logger LOG = BBATLogger.getLogger(ApplicationWorkbenchAdvisor.class.getName());
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		PlatformUI.getPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
		PlatformUI.getPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_MEMORY_MONITOR, true);
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public String getInitialWindowPerspectiveId() {
		return DeveloperPerspective.ID;
	}

	@Override
	public void preStartup() {
//		P2Util.checkForUpdates();
	}
}
