package in.BBAT.presenter.tester.handlers;

import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.BBAT.abstrakt.presenter.run.model.TestRunManager;
import in.bbat.presenter.internal.TestRunExecutor;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;

public class ExecuteTestRunHandler extends AbstractTestRunnerHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {


		TestRunExecutor executor = new TestRunExecutor(TestDeviceManager.getInstance().getDevices(),TestRunManager.getInstance().getTestRunCases());
		executor.run();
		return null;

	}


	@Override
	public boolean isEnabled(List<?> object) {
		if(!TestRunManager.getInstance().getTestRunCases().isEmpty()&& !TestRunManager.getInstance().getSelectedDevices().isEmpty())
			return true;
		return false;
	}

}
