package in.BBAT.presenter.tester.handlers;

import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.BBAT.abstrakt.presenter.run.manager.DeviceLogListener;
import in.BBAT.abstrakt.presenter.run.model.TestRunCase;
import in.BBAT.abstrakt.presenter.run.model.TestRunManager;
import in.BBAT.testRunner.runner.TestRunner;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.bbat.presenter.TestCaseExecutionListener;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class ExecuteTestRunHandler extends AbstractTestRunnerHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {


		Job testRunJob = new Job("Execute") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				List<String> testScriptPaths = new ArrayList<String>();
				for (TestRunCase testRunCase : TestRunManager.getInstance().getTestRunCases()) {
					if(!testScriptPaths.contains(testRunCase.getTestcase().getTestScriptPath()))
						testScriptPaths.add(testRunCase.getTestcase().getTestScriptPath());
				}

				TestRunner runner = new TestRunner(new UiAutoTestCaseJar(testScriptPaths),TestDeviceManager.getInstance().getDevices().get(0).getiDevice());

				for (TestRunCase testRunCase : TestRunManager.getInstance().getTestRunCases()) {
					runner.execute(testRunCase.getTestcase().getName(), new TestCaseExecutionListener(testRunCase, TestDeviceManager.getInstance().getDevices().get(0)), new DeviceLogListener(testRunCase));
				}
				return Status.OK_STATUS;
			}
		};

		testRunJob.schedule();
		return null;

	}


	@Override
	public boolean isEnabled(List<?> object) {
		if(TestRunManager.getInstance().getTestRunCases().isEmpty())
			return false;
		return true;
	}

}
