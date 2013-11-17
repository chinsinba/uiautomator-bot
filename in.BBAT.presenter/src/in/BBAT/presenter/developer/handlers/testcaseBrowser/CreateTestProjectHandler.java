package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.presenter.wizards.CreateTestProjectWizard;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class CreateTestProjectHandler extends AbstractTestCaseBrowserHandler {

	private static final Logger LOG = BBATLogger.getLogger(CreateTestProjectHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new CreateTestProjectWizard());
		dialog.setPageSize(300,300);
		dialog.open();

		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
