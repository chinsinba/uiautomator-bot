package in.bbat.presenter.internal;

import in.BBAT.abstrakt.presenter.run.model.TestRunCase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestRunContainer {

	private List<TestRunCase> testRunCases = new ArrayList<TestRunCase>();
	private Set<DeviceTestRun> deviceTestRuns = new HashSet<DeviceTestRun>();

	public List<TestRunCase> getTestRunCases(){
		return testRunCases;
	}

	public void addTestRunCase(TestRunCase runCase){
		testRunCases.add(runCase);	
	}

	public void removeTestRunCase(TestRunCase runCase){
		testRunCases.remove(runCase);
	}

	public void clearTestRunCases(){
		testRunCases.clear();
	}

	public Set<DeviceTestRun> getdeviceTestRuns() {
		return deviceTestRuns;
	}

	public void addTestDevice(DeviceTestRun deviceTestRun) {
		deviceTestRuns.add(deviceTestRun);
	}

	public void clearDeviceRuns(){
		for(DeviceTestRun run : deviceTestRuns){
			run.clear();
		}
		deviceTestRuns.clear();
	}

	public void removeDeviceRun(DeviceTestRun selectedTestDevice){
		deviceTestRuns.remove(selectedTestDevice);
	}
}
