package in.BBAT.presenter.wizards;

import java.io.File;
import java.io.IOException;

import in.BBAT.presenter.wizards.pages.BrowseFilePage;
import in.bbat.logger.BBATLogger;
import in.bbat.p2.rcpupdate.utils.P2Util;
import in.bbat.utility.FileUtils;
import in.bbat.utility.ZipFiles;

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


		File folder = new File("temp_update");
		if(folder.exists()){
			try {
				FileUtils.delete(folder);
			} catch (IOException e) {
				LOG.error(e);
			}
		}
		folder.mkdir();
		try{

			ZipFiles.unZipIt(path,folder.getAbsolutePath());
			File f = new File(folder,"repository");
			P2Util.checkForUpdates(f.toURI());
			return true;
		}
		finally{

		}
	}

	@Override
	public void addPages() {
		page1 = new BrowseFilePage("Update", "Repository Path" , "Select Local update repository",new String[] {"*.zip"});
		addPage(page1);

	}
}
