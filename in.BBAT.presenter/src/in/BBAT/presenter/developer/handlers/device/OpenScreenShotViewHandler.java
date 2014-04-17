package in.BBAT.presenter.developer.handlers.device;

import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.BBAT.presenter.views.developer.ScreenShotView;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.android.uiautomator.UiAutomatorViewer;


public class OpenScreenShotViewHandler extends BBATHandler {

	private static final Logger LOG = BBATLogger.getLogger(OpenScreenShotViewHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event) {

		/*ActivationCodeDialog d = new ActivationCodeDialog(new Shell());
		d.open();*/
		LOG.info("Open screen shot ui automator view");
//		try {
//			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ScreenShotView.ID);
//		} catch (PartInitException e) {
//			LOG.error(e);
//		}
		
		UiAutomatorViewer window = new UiAutomatorViewer(); 			
		window.open(); 
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
