package in.BBAT.presenter.developer.handlers.device;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.presenter.wizards.ImportInstallApkWizard;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class InstallApkHandler extends AbstractDeviceViewHandler {

	private static final Logger LOG = BBATLogger.getLogger(InstallApkHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		
		LOG.info("Inatall APk");
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				new ImportInstallApkWizard((List<AndroidDevice>) selectedObjects));
		dialog.open();
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(object.isEmpty()){
			return false;
		}
		return true;
	}

}
