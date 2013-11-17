package in.bbat.presenter.perstpectives;

import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class HistoryPerspective implements IPerspectiveFactory {

	public static final String ID = "in.BBAT.presenter.perspective.history";
	private static final Logger LOG = BBATLogger.getLogger(HistoryPerspective.class.getName());
	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
	}

}
