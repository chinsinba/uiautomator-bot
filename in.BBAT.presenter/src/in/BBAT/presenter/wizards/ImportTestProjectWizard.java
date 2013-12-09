package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectManager;
import in.BBAT.presenter.wizards.pages.BrowseTestPackagePage;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;

public class ImportTestProjectWizard extends Wizard {

	private TestProjectManager projMgr;
	private BrowseTestPackagePage page0;

	private Logger LOG = BBATLogger.getLogger(ImportTestProjectWizard.class.getName());

	public ImportTestProjectWizard(TestProjectManager projMgr) {
		this.projMgr = projMgr;
	}

	@Override
	public boolean performFinish() {
		try {
			projMgr.importProject(page0.getPath());
		} catch (Exception e) {
			LOG.error(e);
		}
		return true;
	}

	@Override
	public void addPages() {
		page0 = new BrowseTestPackagePage("Import", "Test Project", "Select Test Project to import");
		addPage(page0);
	}

}
