package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.presenter.views.developer.TestCaseBrowserView;
import in.BBAT.presenter.wizards.pages.CreateTestProjectPage;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;

public class CreateTestProjectWizard extends Wizard {

	private CreateTestProjectPage projCreationPage;
	private TestProjectModel prject;

	private static final Logger LOG = BBATLogger.getLogger(CreateTestProjectWizard.class.getName());

	public CreateTestProjectWizard(TestProjectModel model) {
		this.prject = model;
	}

	public CreateTestProjectWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean performFinish() {
		LOG.info("Creating test Project : " + projCreationPage.getName());
		TestProjectModel newTestProject = null;
		try {
			newTestProject = TestProjectModel.create(projCreationPage.getProjName().trim(), projCreationPage.getDeskription().trim(), Integer.parseInt(projCreationPage.getApiLevel()), projCreationPage.getPackageName(),true); 
		} catch (Exception e) {
			LOG.error(e);
		}
		TestCaseBrowserView.refreshView();
		return true;
	}

	@Override
	public void addPages() {
		if(prject!=null){
			projCreationPage = new CreateTestProjectPage("TestProject",prject);
		}
		else{
			projCreationPage = new CreateTestProjectPage("TestProject");
		}
		addPage(projCreationPage);
	}


}
