package in.BBAT.presenter.perstpectives;

import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ReporterPerspective implements IPerspectiveFactory {

	private static final Logger LOG = BBATLogger.getLogger(ReporterPerspective.class.getName());
	public static final String ID = "in.BBAT.presenter.perspective.reporter";
	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
	}

}
