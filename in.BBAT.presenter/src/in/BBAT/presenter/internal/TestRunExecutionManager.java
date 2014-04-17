package in.BBAT.presenter.internal;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;
import in.bbat.logger.BBATLogger;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;


public class TestRunExecutionManager {

	private static final Logger LOG = BBATLogger.getLogger(TestRunExecutionManager.class.getName());
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


	public List<TestRunCaseModel> getTestRunCases(){
		return runContainer.getTestRunCases();
	}

	public void addTestRunCase(TestRunCaseModel runCase){
		runContainer.addTestRunCase(runCase);	
	}

	public void removeTestRunCase(TestRunCaseModel runCase){
		runContainer.removeTestRunCase(runCase);
	}

	public void clearTestRunCases(){
		runContainer.clearTestRunCases();
	}

	public void clearTestDevices() {
		runContainer.clearDeviceRuns();		
	}

	public void addTestDevice(DeviceTestRun selectedDevice){
		boolean present =false;
		for(DeviceTestRun run : runContainer.getdeviceTestRuns()){
			if(run.getDevice().getiDevice().getDeviceId().equals(selectedDevice.getDevice().getiDevice().getDeviceId())){
				present =true;
				break;
			}
		}
		if(!present)
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
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				setExecuting(true);
				TestRunExecutor executor = new TestRunExecutor(getSelectedDevices());
				executor.run();					
			}
		});
	}

	public boolean isExecuting() {
		return executing;
	}

	public void setExecuting(boolean executing) {
		this.executing = executing;
	}

	public boolean deviceContainsTestCases(){

		for(DeviceTestRun run :runContainer.getdeviceTestRuns()){
			if(!run.getCases().isEmpty()){
				return true;
			}
		}

		return false;
	}

	public void deviceRemoved(AndroidDevice device) {
		runContainer.deviceRemoved(device);		
	}

}
