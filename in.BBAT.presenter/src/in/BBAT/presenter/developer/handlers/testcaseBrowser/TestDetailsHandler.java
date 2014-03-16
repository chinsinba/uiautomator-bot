package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.BBAT.presenter.wizards.CreateTestCaseWizard;
import in.BBAT.presenter.wizards.CreateTestProjectWizard;
import in.BBAT.presenter.wizards.CreateTestSuiteWizard;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class TestDetailsHandler extends AbstractTestCaseBrowserHandler {

	private static final Logger LOG = BBATLogger.getLogger(TestDetailsHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		LOG.info("view details");
		WizardDialog dialog = null;
		if(selectedObjects.get(0) instanceof TestCaseModel){
			dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new CreateTestCaseWizard((TestSuiteModel) ((TestCaseModel) selectedObjects.get(0)).getParent(),(TestCaseModel) selectedObjects.get(0)));
		}
		else if(selectedObjects.get(0) instanceof TestSuiteModel){
			dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new CreateTestSuiteWizard((TestProjectModel) ((TestSuiteModel) selectedObjects.get(0)).getParent(),(TestSuiteModel) selectedObjects.get(0)));
		}
		else if(selectedObjects.get(0) instanceof TestProjectModel){
			dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new CreateTestProjectWizard((TestProjectModel) selectedObjects.get(0)));
		}
		dialog.setPageSize(300,300);
		dialog.open();
		if(!selectedObjects.isEmpty())
		{

		}
		LOG.info("done viewing");
		return null;
	}

	@Override
	protected boolean canBeEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(!object.isEmpty())
		{
			if(object.size()==1)
			{
				if(object.get(0) instanceof AbstractProjectTree)
				{
					return true;
				}
			}
		}
		return false;
	}

}
