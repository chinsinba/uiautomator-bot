package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;

public class ImportInstallApkWizard extends Wizard {

	
	private List<AndroidDevice> devices;

	public ImportInstallApkWizard(List<AndroidDevice> selectedDevices) {
		devices = selectedDevices;
	}
	@Override
	public boolean performFinish() {
		
		for(AndroidDevice device  : devices){
			device.installApk();
		}
		return false;
	}

	@Override
	public void addPages() {
		super.addPages();
	}
}
