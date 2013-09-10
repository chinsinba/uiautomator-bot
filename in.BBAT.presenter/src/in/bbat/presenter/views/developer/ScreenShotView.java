package in.bbat.presenter.views.developer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.android.uiautomator.UiAutomatorViewer;

public class ScreenShotView extends ViewPart {

	public static final String ID ="in.BBAT.presenter.developer.screenShotView";

	public ScreenShotView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		UiAutomatorViewer viewer = new UiAutomatorViewer();
		viewer.createContents(parent);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
