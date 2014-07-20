package in.BBAT.presenter.developer.handlers;

import in.BBAT.presenter.wizards.BrowseP2UpdateRepositoryWizard;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class UpdateBBATP2Handler extends BBATHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		return null;
	}

	@Override
	public Object run(ExecutionEvent event) {
		
		
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				new BrowseP2UpdateRepositoryWizard());
		dialog.open();
		
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}
}
