package in.BBAT.presenter.developer.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

public class RestartHandler extends BBATHandler implements IHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object run(ExecutionEvent event) {
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
