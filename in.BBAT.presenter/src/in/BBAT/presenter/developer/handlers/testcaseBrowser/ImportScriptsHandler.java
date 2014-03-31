package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectManager;
import in.BBAT.presenter.wizards.ImportTestProjectWizard;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class ImportScriptsHandler extends AbstractTestCaseBrowserHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				new ImportTestProjectWizard(TestProjectManager.getInstance()));
		dialog.open();
		return null;
	}

	@Override
	protected boolean canBeEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
