package in.bbat.presenter.perstpectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class HistoryPerspective implements IPerspectiveFactory {

	public static final String ID = "in.BBAT.presenter.perspective.history";
	
	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
	}

}
