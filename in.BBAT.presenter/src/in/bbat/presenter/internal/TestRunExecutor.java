package in.bbat.presenter.internal;


import java.util.Set;

public class TestRunExecutor {

	private Set<DeviceTestRun> deviceTestRuns;

	public TestRunExecutor(Set<DeviceTestRun> deviceTestRuns) {
		this.deviceTestRuns = deviceTestRuns;
	}

	public void run() {

		for (DeviceTestRun device : deviceTestRuns) {
			device.setTestRunCases(TestRunExecutionManager.getInstance().getTestRunCases());
			device.createTab();
			device.excute();
		}
	}
}
