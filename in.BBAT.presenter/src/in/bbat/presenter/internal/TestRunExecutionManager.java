package in.bbat.presenter.internal;

import in.BBAT.abstrakt.presenter.run.model.TestRunCase;

import java.util.List;
import java.util.Set;


public class TestRunExecutionManager {


	private static TestRunExecutionManager instance;
	private TestRunContainer runContainer;
	private boolean executing = false;


	private TestRunExecutionManager()
	{
		runContainer = new TestRunContainer();
	}

	public static TestRunExecutionManager getInstance(){
		if(instance == null)
		{
			instance= new TestRunExecutionManager();
		}
		return instance;
	}


	public List<TestRunCase> getTestRunCases(){
		return runContainer.getTestRunCases();
	}

	public void addTestRunCase(TestRunCase runCase){
		runContainer.addTestRunCase(runCase);	
	}

	public void removeTestRunCase(TestRunCase runCase){
		runContainer.removeTestRunCase(runCase);
	}

	public void clearTestRunCases(){
		runContainer.clearTestRunCases();
	}

	public void clearTestDevices() {
		runContainer.clearDeviceRuns();		
	}

	public void addTestDevice(DeviceTestRun selectedDevice){
		runContainer.addTestDevice(selectedDevice);
	}

	public void removeDevice(DeviceTestRun selectedTestDevice){
		runContainer.removeDeviceRun(selectedTestDevice);
	}

	public Set<DeviceTestRun> getSelectedDevices(){
		return runContainer.getdeviceTestRuns();
	}

	public void stopRun(){
		setExecuting(false);
		runContainer.stopRuns();
	}

	public void execute() {
		setExecuting(true);
		TestRunExecutor executor = new TestRunExecutor(getSelectedDevices());
		executor.run();		
	}

	public boolean isExecuting() {
		return executing;
	}

	public void setExecuting(boolean executing) {
		this.executing = executing;
	}

}
