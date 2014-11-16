package in.BBAT.presenter;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.bbat.logger.BBATLogger;

public class SettingsHandler extends BBATHandler {

	private static final Logger LOG = BBATLogger.getLogger(SettingsHandler.class.getName());
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
		LOG.info("open settings info");
		SettingsWindow window = new SettingsWindow(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		window.open();
		return null;
	}

}
