package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectManager;
import in.BBAT.presenter.views.BBATViewPart;
import in.BBAT.presenter.views.developer.TestCaseBrowserView;
import in.BBAT.presenter.wizards.pages.BrowseTestPackagePage;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

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
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Import Error", e.getMessage());
			LOG.error(e);
			return false;
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
		page0 = new BrowseTestPackagePage("Import", "Test Project", "Select Test Project to import");
		addPage(page0);
	}

}
