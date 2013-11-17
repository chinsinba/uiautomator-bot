package in.bbat.presenter.views.developer;

import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.android.uiautomator.UiAutomatorViewer;

public class ScreenShotView extends ViewPart {

	public static final String ID ="in.BBAT.presenter.developer.screenShotView";
	private static final Logger LOG = BBATLogger.getLogger(ScreenShotView.class.getName());
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
