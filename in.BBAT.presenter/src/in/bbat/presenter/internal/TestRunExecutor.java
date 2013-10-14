package in.bbat.presenter.internal;


import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TestRunExecutor {

	private Set<DeviceTestRun> deviceTestRuns;
	private TestRunModel testRun;

	public TestRunExecutor(Set<DeviceTestRun> deviceTestRuns) {
		this.deviceTestRuns = deviceTestRuns;
		testRun = new TestRunModel();
		testRun.setStartTime(new Timestamp(System.currentTimeMillis()));
		testRun.save();
	}

	public void run() {

		UiAutoTestCaseJar jar = new UiAutoTestCaseJar(getTestScriptPaths());
		DeviceRunListener listener = new DeviceRunListener(deviceTestRuns.size());

		for (DeviceTestRun deviceRun : deviceTestRuns) {
			deviceRun.setTestRun(testRun);
			deviceRun.setTestRunCases(TestRunExecutionManager.getInstance().getTestRunCases());
			deviceRun.createTab();
			deviceRun.addListener(listener);
			deviceRun.execute(jar);
		}

	}

	public List<String> getTestScriptPaths() {
		List<String> testScriptPaths = new ArrayList<String>();
		for (TestCaseModel testRunCase : TestRunExecutionManager.getInstance().getTestRunCases()) {
			if(!testScriptPaths.contains(testRunCase.getTestScriptPath()))
				testScriptPaths.add(testRunCase.getTestScriptPath());
		}
		return testScriptPaths;
	}

	class DeviceRunListener implements IDeviceRunExecutionlistener
	{
		int executingDevRuns = 0;

		public DeviceRunListener(int execCount) {
			executingDevRuns =execCount;
		}

		@Override
		public void deviceRunExecutionStarted(DeviceTestRun deviceRun) {

		}

		@Override
		public void deviceRunExecutionCompleted(DeviceTestRun deviceRun) {
			executingDevRuns-=1;
			if(executingDevRuns ==0)
			{
				testRun.setEndTime(new Timestamp(System.currentTimeMillis()));
				testRun.update();
				TestRunExecutionManager.getInstance().setExecuting(false);
			}
		}

	}
}
