package in.BBAT.presenter.developer.handlers.device;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;

public class InstallApkHandler extends AbstractDeviceViewHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
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
