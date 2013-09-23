package in.BBAT.presenter.tester.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunManager;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class RemoveAllTestRunCaseHandler extends AbstractTestRunnerHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		TestRunManager.getInstance().clearTestRunCases();
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
		if(TestRunManager.getInstance().getTestRunCases().isEmpty())
			return false;
		return true;
	}

}
