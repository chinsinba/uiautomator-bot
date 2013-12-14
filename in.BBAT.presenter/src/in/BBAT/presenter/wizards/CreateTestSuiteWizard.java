package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.BBAT.dataMine.manager.MineManager;
import in.BBAT.presenter.wizards.pages.CreateTestSuitePage;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

public class CreateTestSuiteWizard extends Wizard {

	private CreateTestSuitePage suiteCreationPage;

	private TestProjectModel parent;

	private static final Logger LOG = BBATLogger.getLogger(CreateTestSuiteWizard.class.getName());
	
	public CreateTestSuiteWizard(TestProjectModel testProjectModel) {
		this.parent = testProjectModel;
	}

	@Override
	public boolean performFinish() {
		
		LOG.info("Create test suite: " +suiteCreationPage.getName());
		try {
			TestSuiteModel newTestSuite = TestSuiteModel.create(parent, suiteCreationPage.getName(), suiteCreationPage.getDescription());
			BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
			try {
				view.refresh();
			} catch (Exception e) {
				LOG.error(e);
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return true;
	}

	@Override
	public void addPages() {
		suiteCreationPage = new CreateTestSuitePage("TestSuite",parent);
		addPage(suiteCreationPage);
	}

}
