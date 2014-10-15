package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.presenter.wizards.pages.BrowseDirectoryPage;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;

public class ExportTestProjectWizard extends Wizard {

	protected List<TestProjectModel> projects;
	private Logger LOG = BBATLogger.getLogger(ExportTestProjectWizard.class.getName());
	protected BrowseDirectoryPage page1;
	
	public ExportTestProjectWizard(List<TestProjectModel> selectedProjects) {
		projects = selectedProjects;
	}

	@Override
	public boolean performFinish() {

		for(TestProjectModel model : projects){
			try {
				model.export(page1.getPath());
			} catch (Exception e) {
			LOG.error(e);
			
			}
		}
		return true;
	}

	@Override
	public void addPages() {
		 page1 = new BrowseDirectoryPage("Export", "Export Path: " , "Select directory");
		addPage(page1);

	}

}
