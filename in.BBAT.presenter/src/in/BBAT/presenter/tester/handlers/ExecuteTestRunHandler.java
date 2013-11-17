package in.BBAT.presenter.tester.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.tester.AutomatorLogView;
import in.bbat.presenter.views.tester.TestLogView;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

import com.android.ddmlib.logcat.LogCatMessage;

public class ExecuteTestRunHandler extends AbstractTestRunnerHandler {

	private static final Logger LOG = BBATLogger.getLogger(ExecuteTestRunHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		IViewPart autoLogView =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(AutomatorLogView.ID);
		if(autoLogView!=null)
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(autoLogView);
		TestLogView view  = (TestLogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestLogView.ID);
		if(view!=null)
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);

		TestRunExecutionManager.getInstance().execute();
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

}
