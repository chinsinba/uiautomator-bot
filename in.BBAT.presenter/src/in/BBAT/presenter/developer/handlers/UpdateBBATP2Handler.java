package in.BBAT.presenter.developer.handlers;

import in.BBAT.presenter.wizards.BrowseP2UpdateRepositoryWizard;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class UpdateBBATP2Handler extends BBATHandler {

	private static final Logger LOG = BBATLogger.getLogger(UpdateBBATP2Handler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		return null;
	}

	@Override
	public Object run(ExecutionEvent event) {
		
		LOG.info("Update Uiautomator-bot");
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
