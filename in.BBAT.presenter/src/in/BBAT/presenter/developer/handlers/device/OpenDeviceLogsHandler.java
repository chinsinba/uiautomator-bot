package in.BBAT.presenter.developer.handlers.device;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.tester.DeviceLogView;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;



public class OpenDeviceLogsHandler extends AbstractDeviceViewHandler {

	private static final Logger LOG = BBATLogger.getLogger(OpenDeviceLogsHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		LOG.info("Open device logs view");
		DeviceLogView view;
		try {
			view  = (DeviceLogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(DeviceLogView.ID);
			view.setDevice(((AndroidDevice) selectedObjects.get(0)).getiDevice().getMonkeyDevice());
		} catch (PartInitException e) {
			LOG.error(e);
		}
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		// TODO Auto-generated method stub
		return true;
	}

}
