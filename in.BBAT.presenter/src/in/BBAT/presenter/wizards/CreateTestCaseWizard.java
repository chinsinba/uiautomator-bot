package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.BBAT.dataMine.manager.MineManager;
import in.BBAT.presenter.wizards.pages.CreateTestCasePage;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

public class CreateTestCaseWizard extends Wizard {


	private CreateTestCasePage caseCreationPage;

	private TestSuiteModel parent;

	public CreateTestCaseWizard(TestSuiteModel suiteModel) {
		this.parent = suiteModel;
	}

	@Override
	public void addPages() {
		caseCreationPage = new CreateTestCasePage("TestCase",parent);
		addPage(caseCreationPage);
	}
	@Override
	public boolean performFinish() {

		try {
			TestCaseModel newTestCase = new TestCaseModel(parent, caseCreationPage.getName());
			newTestCase.setDescription(caseCreationPage.getDescription());
//			MineManager.getInstance().beginTransaction();
			newTestCase.save();
//			MineManager.getInstance().commitTransaction();
			BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
			try {
				view.refresh();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

}
