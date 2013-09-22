package in.BBAT.presenter.tester.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunCase;
import in.BBAT.abstrakt.presenter.run.model.TestRunManager;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class RemoveTestRunCaseHandler extends AbstractTestRunnerHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		for (Object runCase : selectedObjects) {
			if(runCase instanceof TestRunCase){
				TestRunManager.getInstance().removeTestRunCase((TestRunCase) runCase);
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
			if(runCase instanceof TestRunCase){
				return true;
			}			
		}

		return false;
	}

}
