package in.BBAT.presenter.developer.handlers.device;

import java.util.List;

import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.bbat.presenter.views.developer.ScreenShotView;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.android.monkeyrunner.recorder.MonkeyRecorder;

public class OpenScreenShotViewHandler extends BBATHandler {

	@Override
	public Object run(ExecutionEvent event) {

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ScreenShotView.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean isEnabled(List<?> object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
