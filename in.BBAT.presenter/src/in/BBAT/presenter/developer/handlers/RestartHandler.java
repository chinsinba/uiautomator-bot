package in.BBAT.presenter.developer.handlers;

import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

public class RestartHandler extends BBATHandler implements IHandler {

	private static final Logger LOG = BBATLogger.getLogger(RestartHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object run(ExecutionEvent event) {
		LOG.info("Restart tool");
		if(MessageDialog.openConfirm(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), "Restart",
				"Do you want to restart ?")){
			PlatformUI.getWorkbench().restart();
		}
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		// TODO Auto-generated method stub
		return true;
	}

}
