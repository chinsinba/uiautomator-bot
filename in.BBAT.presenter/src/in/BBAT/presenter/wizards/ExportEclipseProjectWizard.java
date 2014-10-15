package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.BBATProjectUtils;
import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;

public class ExportEclipseProjectWizard extends ExportTestProjectWizard {


	private Logger LOG = BBATLogger.getLogger(ExportEclipseProjectWizard.class.getName());
	public ExportEclipseProjectWizard(List<TestProjectModel> selectedProjects) {
		super(selectedProjects);
	}

	@Override
	public boolean performFinish() {
		for(TestProjectModel model : projects){
			try {
				BBATProjectUtils.project(model, page1.getPath());
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		return true;
	}

}
