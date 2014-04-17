package in.BBAT.presenter.perstpectives;

import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class TesterPerspective implements IPerspectiveFactory {

	private static final Logger LOG = BBATLogger.getLogger(TesterPerspective.class.getName());
	public static final String ID ="in.BBAT.presenter.perspective.tester";
	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
	}

}
