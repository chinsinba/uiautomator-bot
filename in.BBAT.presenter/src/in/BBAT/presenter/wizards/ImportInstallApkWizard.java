package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.presenter.wizards.pages.BrowseFilePage;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class ImportInstallApkWizard extends Wizard {


	private List<AndroidDevice> devices;
	private Logger LOG = BBATLogger.getLogger(ImportInstallApkWizard.class.getName());
	private BrowseFilePage page;

	public ImportInstallApkWizard(List<AndroidDevice> selectedDevices) {
		devices = selectedDevices;
	}
	@Override
	public boolean performFinish() {
		for(final AndroidDevice device  : devices){

			new Thread(new Runnable() {
				@Override
				public void run() {
					try
					{
						device.installApk(page.getPath());
					} catch (Exception e) {
						LOG.error(e);
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Install Failled", "Failled to install APK " +
										"on device : "+device.getName());								
							}
						});
					}					
				}
			}).start();

		}
		return true;
	}

	@Override
	public void addPages() {

		page = new BrowseFilePage("Install APK", "Install APK", "Select APK to install",new String[] {"*.apk"});
		addPage(page);
	}
}
