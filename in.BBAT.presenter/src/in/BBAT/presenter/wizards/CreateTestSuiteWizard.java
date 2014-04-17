package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.BBAT.dataMine.manager.MineManager;
import in.BBAT.presenter.views.BBATViewPart;
import in.BBAT.presenter.views.developer.TestCaseBrowserView;
import in.BBAT.presenter.wizards.pages.CreateTestSuitePage;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

public class CreateTestSuiteWizard extends Wizard {

	private CreateTestSuitePage suiteCreationPage;

	private TestProjectModel parent;

	private TestSuiteModel suiteModel; 

	private static final Logger LOG = BBATLogger.getLogger(CreateTestSuiteWizard.class.getName());

	public CreateTestSuiteWizard(TestProjectModel testProjectModel) {
		this.parent = testProjectModel;
	}

	public CreateTestSuiteWizard(TestProjectModel testProjectModel, TestSuiteModel model) {
		this(testProjectModel);
		this.suiteModel = model;
	}

	@Override
	public boolean performFinish() {

		LOG.info("Create test suite: " +suiteCreationPage.getName());
		try {
			TestSuiteModel.create(parent, suiteCreationPage.getSuiteName().trim(), suiteCreationPage.getDeskription().trim(),suiteCreationPage.isHelper());
			TestCaseBrowserView.refreshView();
		} catch (Exception e) {
			LOG.error(e);
		}
		return true;
	}

	@Override
	public void addPages() {
		if(suiteModel!=null)
		{
			suiteCreationPage = new CreateTestSuitePage("TestSuite",parent,suiteModel);
		}
		else {
			suiteCreationPage = new CreateTestSuitePage("TestSuite",parent);
		}
		addPage(suiteCreationPage);
	}

}
