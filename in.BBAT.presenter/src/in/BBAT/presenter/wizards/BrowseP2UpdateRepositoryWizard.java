package in.BBAT.presenter.wizards;

import in.BBAT.presenter.wizards.pages.BrowseFilePage;
import in.bbat.logger.BBATLogger;
import in.bbat.p2.rcpupdate.utils.P2Util;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

public class BrowseP2UpdateRepositoryWizard extends Wizard{


	private Logger LOG = BBATLogger.getLogger(ExportTestProjectWizard.class.getName());

	private BrowseFilePage page1;
	@Override
	public boolean performFinish() {

		String path = page1.getPath();
		P2Util.checkForUpdates("file:"+Path.SEPARATOR+Path.SEPARATOR+path);
		return true;
	}

	@Override
	public void addPages() {
		page1 = new BrowseFilePage("Update", "Repository Path" , "Select Local update repository",new String[] {"*.zip"});
		addPage(page1);

	}
}
