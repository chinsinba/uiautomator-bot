package in.BBAT.presenter.tester.handlers;

import in.BBAT.presenter.internal.TestRunExecutionManager;
import in.BBAT.presenter.views.BBATViewPart;
import in.BBAT.presenter.views.tester.AutomatorLogView;
import in.BBAT.presenter.views.tester.ExecutionView;
import in.BBAT.presenter.views.tester.ScreenShotView;
import in.BBAT.presenter.views.tester.TestLogView;
import in.BBAT.presenter.views.tester.TestRunnerView;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

public class ExecuteTestRunHandler extends AbstractTestRunnerHandler {

	private static final Logger LOG = BBATLogger.getLogger(ExecuteTestRunHandler.class.getName());

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		LOG.info("Execute test run ");

		BBATViewPart.hideView(TestLogView.ID);
		BBATViewPart.hideView(AutomatorLogView.ID);
		BBATViewPart.hideView(ScreenShotView.ID);
		BBATViewPart.hideView(ExecutionView.ID);

		TestRunExecutionManager.getInstance().execute();
		TestRunnerView.refreshView();
		return null;

	}


	@Override
	public boolean isEnabled(List<?> object) {

		if(TestRunExecutionManager.getInstance().getTestRunCases().isEmpty() && TestRunExecutionManager.getInstance().getSelectedDevices().isEmpty())
		{
			return false;
		}
		if(TestRunExecutionManager.getInstance().deviceContainsTestCases())
			if(!TestRunExecutionManager.getInstance().isExecuting())
				return true;
		return false;
	}

	private String enabled() {
		if(TestRunExecutionManager.getInstance().getSelectedDevices().isEmpty()){
			return "Please select test devices.";
		}
		if(TestRunExecutionManager.getInstance().getTestRunCases().isEmpty())
		{
			return "Please add test cases";
		}
		if(!TestRunExecutionManager.getInstance().deviceContainsTestCases())
		{
			return "Please add test cases";
		}
		if(TestRunExecutionManager.getInstance().isExecuting())
			return "Test run is currently running";

		return null;
	}
}
