package in.BBAT.presenter.history.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.presenter.wizards.ExportLogsWizard;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;


public class ExportLogsHandler extends AbstractTestRunInfoHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {


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
