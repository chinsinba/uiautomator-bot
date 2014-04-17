package in.BBAT.presenter.tester.handlers;

import in.BBAT.presenter.internal.TestRunExecutionManager;
import in.BBAT.presenter.views.tester.TestRunnerView;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

public class StopTestRunHandler extends AbstractTestRunnerHandler {

	private static final Logger LOG = BBATLogger.getLogger(StopTestRunHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		return null;
	}

	@Override
	public Object run(ExecutionEvent event) {
		LOG.info("Stop test run execution");
		boolean openConfirm = MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Stop Test Run", "Do you want to stop ?");
		if(!openConfirm)
			return null;
		TestRunExecutionManager.getInstance().stopRun();
		TestRunnerView.refreshView();
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(!TestRunExecutionManager.getInstance().getTestRunCases().isEmpty()&& !TestRunExecutionManager.getInstance().getSelectedDevices().isEmpty())
			if(TestRunExecutionManager.getInstance().isExecuting())
				return true;
		return false;
	}

}
