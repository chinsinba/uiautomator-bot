package in.BBAT.abstrakt.presenter.run.model;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestRunContainer {

	private List<TestRunCase> testRunCases = new ArrayList<TestRunCase>();
	private Set<AndroidDevice> selectedTestDeviceList = new HashSet<AndroidDevice>();

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

	public Set<AndroidDevice> getSelectedTestDeviceList() {
		return selectedTestDeviceList;
	}

	public void addTestDevice(AndroidDevice selectedTestDevice) {
		selectedTestDeviceList.add(selectedTestDevice);
	}
	
	public void clearDevices(){
		selectedTestDeviceList.clear();
	}
	
	public void removeDevice(AndroidDevice selectedTestDevice){
		selectedTestDeviceList.remove(selectedTestDevice);
	}
}
