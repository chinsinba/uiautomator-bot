package in.bbat.presenter.perstpectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DeveloperPerspective implements IPerspectiveFactory {

	public static final String ID = "in.BBAT.presenter.perspective.developer";
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
//		layout.setFixed(true);
		
	}

}
