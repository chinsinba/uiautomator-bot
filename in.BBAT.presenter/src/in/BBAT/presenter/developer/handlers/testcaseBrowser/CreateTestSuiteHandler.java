package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.presenter.wizards.CreateTestSuiteWizard;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class CreateTestSuiteHandler extends AbstractTestCaseBrowserHandler {


	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new CreateTestSuiteWizard((TestProjectModel) selectedObjects.get(0)));
		dialog.setPageSize(300,300);
		dialog.open();
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(!object.isEmpty())
		{
			if(object.size()==1)
			{
				if(object.get(0) instanceof TestProjectModel)
				{
					return true;
				}
			}
		}
		return false;
	}

}
