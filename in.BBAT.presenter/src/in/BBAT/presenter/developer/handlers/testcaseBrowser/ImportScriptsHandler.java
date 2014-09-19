package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectManager;
import in.BBAT.presenter.developer.handlers.device.DeviceDetailsHandler;
import in.BBAT.presenter.wizards.ImportTestProjectWizard;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class ImportScriptsHandler extends AbstractTestCaseBrowserHandler {

	private static final Logger LOG = BBATLogger.getLogger(ImportScriptsHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		LOG.info("Import scripts");
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				new ImportTestProjectWizard(TestProjectManager.getInstance()));
		dialog.open();
		return null;
	}

	@Override
	protected boolean canBeEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
