package in.BBAT.abstrakt.presenter.run.model;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.data.model.Entities.TestRunEntity;
import in.BBAT.dataMine.manager.RunMineManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class TestRunManager {


	private static TestRunManager instance;
	private TestRunContainer runContainer;

	private TestRunManager()
	{
		runContainer = new TestRunContainer();
	}

	public static TestRunManager getInstance(){
		if(instance == null)
		{
			instance= new TestRunManager();
		}
		return instance;
	}


	public List<TestRunModel> getTestRuns(){
		List<TestRunModel> models = new ArrayList<TestRunModel>();
		for(TestRunEntity entity: RunMineManager.getAllTestRuns()){
			TestRunModel model = new TestRunModel(entity);
			models.add(model);
		}
		return models;
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
}
