package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.BBAT.presenter.wizards.pages.CreateTestCasePage;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PartInitException;

public class CreateTestCaseWizard extends Wizard {

	private static final Logger LOG = BBATLogger.getLogger(CreateTestCaseWizard.class.getName());

	private CreateTestCasePage caseCreationPage;

	private TestSuiteModel parent;
	private TestCaseModel caseModel;

	public CreateTestCaseWizard(TestSuiteModel suiteModel) {
		this.parent = suiteModel;
	}
	public CreateTestCaseWizard(TestSuiteModel suiteModel,TestCaseModel model) {
		this(suiteModel);
		this.caseModel = model;


	}

	@Override
	public void addPages() {
		if(caseModel!=null)
			caseCreationPage = new CreateTestCasePage("TestCase",parent,caseModel);
		else
			caseCreationPage = new CreateTestCasePage("TestCase",parent);
		addPage(caseCreationPage);
	}

	@Override
	public boolean performFinish() {
		LOG.info("Creating test case : "+caseCreationPage.getName());
		try {
			TestCaseModel newTestCase = TestCaseModel.create(parent, caseCreationPage.getCaseName().trim(),caseCreationPage.getDeskription().trim()); 
			TestCaseBrowserView.refreshView();

			try
			{
				newTestCase.openEditor();
			} catch (PartInitException e)
			{
				LOG.error(e);
			}
		} catch (Exception e) {
			LOG.error(e);
		}

		return true;
	}

	private void createContents() {

	}

}
