package in.BBAT.presenter.tester.handlers;

import in.bbat.logger.BBATLogger;
import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.tester.AutomatorLogView;
import in.bbat.presenter.views.tester.ScreenShotView;
import in.bbat.presenter.views.tester.TestLogView;
import in.bbat.presenter.views.tester.TestRunnerView;

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

		IViewPart autoLogView =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(AutomatorLogView.ID);
		if(autoLogView!=null)
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(autoLogView);

		TestLogView view  = (TestLogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestLogView.ID);
		if(view!=null)
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);


		ScreenShotView shotView  = (ScreenShotView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ScreenShotView.ID);
		if(shotView!=null)
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(shotView);

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
