package in.BBAT.presenter.tester.handlers;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class RemoveTestRunCaseHandler extends AbstractTestRunnerHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		for (Object runCase : selectedObjects) {
			if(runCase instanceof TestCaseModel){
				TestRunExecutionManager.getInstance().removeTestRunCase((TestCaseModel) runCase);
			}
		}

		TestRunnerView view  = (TestRunnerView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
		try {
			view.refresh();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(object.isEmpty())
			return false;

		for (Object runCase : object) {
			if(runCase instanceof TestCaseModel){
				return true;
			}			
		}

		return false;
	}

}
