package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.BBAT.presenter.wizards.pages.CreateTestCasePage;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class CreateTestCaseWizard extends Wizard {

	private static final Logger LOG = BBATLogger.getLogger(CreateTestCaseWizard.class.getName());

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
		LOG.info("Creating test case : "+caseCreationPage.getName());
		try {
			TestCaseModel newTestCase = new TestCaseModel(parent, caseCreationPage.getName());
			newTestCase.setDescription(caseCreationPage.getDescription());
			newTestCase.save();
			BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
			try {
				view.refresh();
			} catch (Exception e) {
				LOG.error(e);
			}

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
