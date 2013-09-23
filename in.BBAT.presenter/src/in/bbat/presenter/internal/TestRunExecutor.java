package in.bbat.presenter.internal;

import in.BBAT.TestRunner.device.IAndroidDevice;
import in.BBAT.TestRunner.device.TestDevice;
import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.run.manager.DeviceLogListener;
import in.BBAT.abstrakt.presenter.run.model.TestRunCase;
import in.BBAT.testRunner.runner.TestRunner;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.bbat.presenter.TestCaseExecutionListener;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class TestRunExecutor {

	private List<AndroidDevice> devices;
	private List<TestRunCase> testCases;
	private List<String> testScriptPaths ;

	public TestRunExecutor(List<AndroidDevice> deviceList, List<TestRunCase> testRunCases) {
		this.devices = deviceList;
		this.testCases = testRunCases;
		this.testScriptPaths = getTestScriptPaths();
	}

	public void run() {

		for (AndroidDevice device : devices) {
			excute(device);
		}
		

	}

	private void excute(final AndroidDevice device) {
		Job testRunJob = new Job("Execute") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				TestRunner runner = new TestRunner(new UiAutoTestCaseJar(testScriptPaths),device.getiDevice());
				for (TestRunCase testRunCase : testCases) {
					runner.execute(testRunCase.getTestcase().getName(), new TestCaseExecutionListener(testRunCase, device), new DeviceLogListener(testRunCase));
				}
				return Status.OK_STATUS;
			}
		};
		testRunJob.schedule();
	}

	private List<String> getTestScriptPaths() {
		List<String> testScriptPaths = new ArrayList<String>();
		for (TestRunCase testRunCase : testCases) {
			if(!testScriptPaths.contains(testRunCase.getTestcase().getTestScriptPath()))
				testScriptPaths.add(testRunCase.getTestcase().getTestScriptPath());
		}
		return testScriptPaths;
	}

}
