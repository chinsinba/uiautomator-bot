package in.bbat.presenter.internal;


import in.BBAT.abstrakt.presenter.run.model.TestRunModel;

import java.sql.Timestamp;
import java.util.Set;

public class TestRunExecutor {

	private Set<DeviceTestRun> deviceTestRuns;

	public TestRunExecutor(Set<DeviceTestRun> deviceTestRuns) {
		this.deviceTestRuns = deviceTestRuns;
	}

	public void run() {
		TestRunModel testRun = new TestRunModel();
		testRun.setStartTime(new Timestamp(System.currentTimeMillis()));
		testRun.save();

		for (DeviceTestRun device : deviceTestRuns) {
			device.setTestRun(testRun);
			device.setTestRunCases(TestRunExecutionManager.getInstance().getTestRunCases());
			device.createTab();
			device.excute();
		}

		testRun.setEndtiTime(new Timestamp(System.currentTimeMillis()));
		testRun.update();
	}
}
