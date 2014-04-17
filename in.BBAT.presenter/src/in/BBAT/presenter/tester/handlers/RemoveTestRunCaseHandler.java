package in.BBAT.presenter.tester.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;
import in.BBAT.presenter.internal.DeviceTestRun;
import in.BBAT.presenter.internal.TestRunExecutionManager;
import in.BBAT.presenter.views.tester.TestRunnerView;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class RemoveTestRunCaseHandler extends AbstractTestRunnerHandler {

	private static final Logger LOG = BBATLogger.getLogger(RemoveTestRunCaseHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		LOG.info("Remove test run case ");
		for (Object runCase : selectedObjects) {
			for(DeviceTestRun run: TestRunExecutionManager.getInstance().getSelectedDevices()){
				run.removeCase((TestRunCaseModel) runCase);
			}
			if(runCase instanceof TestRunCaseModel){
				TestRunExecutionManager.getInstance().removeTestRunCase((TestRunCaseModel) runCase);
			}
		}

		TestRunnerView view  = (TestRunnerView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
		try {
			view.refresh();
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(TestRunExecutionManager.getInstance().isExecuting())
			return false;

		if(object.isEmpty())
			return false;

		for (Object runCase : object) {
			if(runCase instanceof TestRunCaseModel){
				return true;
			}			
		}

		return false;
	}

}
