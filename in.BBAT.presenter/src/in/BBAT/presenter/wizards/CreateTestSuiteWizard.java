package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.BBAT.dataMine.manager.MineManager;
import in.BBAT.presenter.wizards.pages.CreateTestSuitePage;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

public class CreateTestSuiteWizard extends Wizard {

	private CreateTestSuitePage suiteCreationPage;

	private TestProjectModel parent;

	public CreateTestSuiteWizard(TestProjectModel testProjectModel) {
		this.parent = testProjectModel;
	}

	@Override
	public boolean performFinish() {
		try {
			TestSuiteModel newTestSuite = new TestSuiteModel(parent, suiteCreationPage.getName());
			newTestSuite.setDescription(suiteCreationPage.getDescription());
			MineManager.getInstance().beginTransaction();
			newTestSuite.save();
			MineManager.getInstance().commitTransaction();
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

	@Override
	public void addPages() {
		suiteCreationPage = new CreateTestSuitePage("TestCase",parent);
		addPage(suiteCreationPage);
	}

}
