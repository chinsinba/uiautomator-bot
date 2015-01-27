package in.BBAT.presenter.history.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.presenter.wizards.ExportTestRunReportWizard;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class ExportReportHandler extends AbstractTestRunBrowserHandler {

	private static final Logger LOG = BBATLogger.getLogger(ExportReportHandler.class.getName());

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		
		LOG.info("Export report");
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new ExportTestRunReportWizard((List<TestRunModel>) selectedObjects));
		dialog.open();

		return null;
	}

	@Override
	public boolean isEnabled(List<?> selectedObjects) {
		if(!selectedObjects.isEmpty())
		{
			/*if(selectedObjects.size()>1){
				return false;
			}*/
			Object sample = selectedObjects.get(0);
			for (Object object : selectedObjects) {
				if(!(sample.getClass() == object.getClass()))
				{
					return false;
				}

				if(sample instanceof TestDeviceRunModel){
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
