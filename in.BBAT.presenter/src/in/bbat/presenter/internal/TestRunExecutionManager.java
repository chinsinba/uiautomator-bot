package in.bbat.presenter.internal;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.BBAT.abstrakt.presenter.run.model.TestRunCase;

import java.util.List;
import java.util.Set;


public class TestRunExecutionManager {


	private static TestRunExecutionManager instance;
	private TestRunContainer runContainer;

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
		runContainer.clearDevices();		
	}

	public void addTestDevice(AndroidDevice selectedDevice){
		runContainer.addTestDevice(selectedDevice);
	}

	public void removeDevice(AndroidDevice selectedTestDevice){
		runContainer.removeDevice(selectedTestDevice);
	}

	public Set<AndroidDevice> getSelectedDevices(){
		return runContainer.getSelectedTestDeviceList();
	}

	public void execute() {
		TestRunExecutor executor = new TestRunExecutor(TestDeviceManager.getInstance().getDevices(),TestRunExecutionManager.getInstance().getTestRunCases());
		executor.run();		
	}
}
