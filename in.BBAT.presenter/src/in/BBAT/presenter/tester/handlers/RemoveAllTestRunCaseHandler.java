package in.BBAT.presenter.tester.handlers;

import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class RemoveAllTestRunCaseHandler extends AbstractTestRunnerHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		TestRunExecutionManager.getInstance().clearTestRunCases();
		TestRunExecutionManager.getInstance().clearTestDevices();
		TestRunnerView view  = (TestRunnerView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
		try {
			view.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(TestRunExecutionManager.getInstance().getTestRunCases().isEmpty())
			return false;
		return true;
	}

}
