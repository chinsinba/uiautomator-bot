package in.bbat.presenter;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

import in.BBAT.presenter.developer.handlers.BBATHandler;

public class SettingsHandler extends BBATHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		return null;
	}


	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}


	@Override
	public Object run(ExecutionEvent event) {
		SettingsWindow window = new SettingsWindow(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		window.open();
		return null;
	}

}
