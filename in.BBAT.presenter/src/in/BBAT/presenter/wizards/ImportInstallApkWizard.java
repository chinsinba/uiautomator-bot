package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;

public class ImportInstallApkWizard extends Wizard {


	private List<AndroidDevice> devices;
	private Logger LOG = BBATLogger.getLogger(ImportInstallApkWizard.class.getName());

	public ImportInstallApkWizard(List<AndroidDevice> selectedDevices) {
		devices = selectedDevices;
	}
	@Override
	public boolean performFinish() {
		for(AndroidDevice device  : devices){
			try
			{
				device.installApk();
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		return false;
	}

	@Override
	public void addPages() {
		super.addPages();
	}
}
