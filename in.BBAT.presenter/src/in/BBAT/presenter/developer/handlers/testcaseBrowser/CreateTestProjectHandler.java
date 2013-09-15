package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.presenter.wizards.CreateTestProjectWizard;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class CreateTestProjectHandler extends AbstractTestCaseBrowserHandler {

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
