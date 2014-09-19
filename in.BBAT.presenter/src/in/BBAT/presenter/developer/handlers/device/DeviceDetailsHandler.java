package in.BBAT.presenter.developer.handlers.device;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.presenter.developer.handlers.FeedBackHandler;
import in.bbat.logger.BBATLogger;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class DeviceDetailsHandler extends AbstractDeviceViewHandler{

	private static final Logger LOG = BBATLogger.getLogger(DeviceDetailsHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		LOG.info("Device details");
		AndroidDevice device = (AndroidDevice) selectedObjects.get(0);
		Map<String, String> properties = device.getProperties();

		DeviceDetailsWindow win = new DeviceDetailsWindow(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), properties.entrySet());
		win.open();

		return null;
	}


	@Override
	public boolean isEnabled(List<?> object) {
		if(object.isEmpty()){
			return false;
		}
		return true;
	}

}
