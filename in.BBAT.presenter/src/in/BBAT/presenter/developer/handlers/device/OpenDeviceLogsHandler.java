package in.BBAT.presenter.developer.handlers.device;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.bbat.presenter.views.tester.DeviceLogView;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;



public class OpenDeviceLogsHandler extends AbstractDeviceViewHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		DeviceLogView view;
		try {
			view  = (DeviceLogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(DeviceLogView.ID);
			view.setDevice(((AndroidDevice) selectedObjects.get(0)).getiDevice().getMonkeyDevice());
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		// TODO Auto-generated method stub
		return true;
	}

}
