package in.BBAT.presenter.developer.handlers.device;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

public class DeviceDetailsHandler extends AbstractDeviceViewHandler{

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Device Details", "Here you will see device details" +
				"");
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
