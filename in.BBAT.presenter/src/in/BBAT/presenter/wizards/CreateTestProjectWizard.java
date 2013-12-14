package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.presenter.wizards.pages.CreateTestProjectPage;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

public class CreateTestProjectWizard extends Wizard {

	private CreateTestProjectPage projCreationPage;

	private static final Logger LOG = BBATLogger.getLogger(CreateTestProjectWizard.class.getName());
	@Override
	public boolean performFinish() {
		LOG.info("Creating test Project : " + projCreationPage.getName());
		TestProjectModel newTestProject = null;
		try {
			newTestProject = TestProjectModel.create(projCreationPage.getName(), projCreationPage.getDescription()); 
		} catch (Exception e) {
			LOG.error(e);
		}
		BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
		try {
			view.refresh();
		} catch (Exception e) {
			LOG.error(e);
		}
		return true;
	}
	
	@Override
	public void addPages() {
		projCreationPage = new CreateTestProjectPage("TestProject");
		addPage(projCreationPage);
	}
	

}
