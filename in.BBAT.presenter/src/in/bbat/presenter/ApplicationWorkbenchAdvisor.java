package in.bbat.presenter;

import in.bbat.logger.BBATLogger;
import in.bbat.presenter.perstpectives.DeveloperPerspective;

import org.apache.log4j.Logger;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final Logger LOG = BBATLogger.getLogger(ApplicationWorkbenchAdvisor.class.getName());
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public String getInitialWindowPerspectiveId() {
		return DeveloperPerspective.ID;
	}

}
