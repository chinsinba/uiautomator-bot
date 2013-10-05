package in.bbat.presenter.internal;


import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.dataMine.manager.MineManager;

import java.sql.Timestamp;
import java.util.Set;

public class TestRunExecutor {

	private Set<DeviceTestRun> deviceTestRuns;

	public TestRunExecutor(Set<DeviceTestRun> deviceTestRuns) {
		this.deviceTestRuns = deviceTestRuns;
	}

	public void run() {
		MineManager.getInstance().beginTransaction();
		TestRunModel testRun = new TestRunModel();
		testRun.setStartTime(new Timestamp(System.currentTimeMillis()));
		testRun.save();
		MineManager.getInstance().commitTransaction();

		for (DeviceTestRun device : deviceTestRuns) {
			device.setTestRun(testRun);
			device.setTestRunCases(TestRunExecutionManager.getInstance().getTestRunCases());
			device.createTab();
			device.excute();
		}

		MineManager.getInstance().beginTransaction();
		testRun.setEndtiTime(new Timestamp(System.currentTimeMillis()));
		testRun.update();
		MineManager.getInstance().commitTransaction();
	}
}
