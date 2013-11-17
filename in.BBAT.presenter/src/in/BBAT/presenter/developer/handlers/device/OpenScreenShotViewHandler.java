package in.BBAT.presenter.developer.handlers.device;

import java.util.List;

import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.developer.ScreenShotView;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.android.monkeyrunner.recorder.MonkeyRecorder;

public class OpenScreenShotViewHandler extends BBATHandler {

	private static final Logger LOG = BBATLogger.getLogger(OpenScreenShotViewHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event) {

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ScreenShotView.ID);
		} catch (PartInitException e) {
			LOG.error(e);
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
