package in.BBAT.presenter.history.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.presenter.developer.handlers.device.DeviceDetailsHandler;
import in.BBAT.presenter.wizards.ExportLogsWizard;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;


public class ExportLogsHandler extends AbstractTestRunInfoHandler {

	private static final Logger LOG = BBATLogger.getLogger(ExportLogsHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		LOG.info("Export logs");

		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new ExportLogsWizard((List<TestRunInstanceModel>) selectedObjects));
		dialog.open();
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(object.isEmpty())
			return false;
		return true;
	}
}
