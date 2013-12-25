package in.BBAT.presenter.developer.handlers.device;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;

import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class DeviceDetailsHandler extends AbstractDeviceViewHandler{

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		AndroidDevice device = (AndroidDevice) selectedObjects.get(0);
		Map<String, String> properties = device.getProperties();

		DeviceDetailsWindow win = new DeviceDetailsWindow(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), properties.entrySet());
		win.open();

		return null;
	}


	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
