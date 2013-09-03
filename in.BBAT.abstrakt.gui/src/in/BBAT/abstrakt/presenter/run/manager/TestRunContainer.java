package in.BBAT.abstrakt.presenter.run.manager;


import java.util.List;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.testRunner.runner.TestRunner;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.bbat.testrunner.TestDevice;

public class TestRunContainer {

	private TestRunModel currentRunningTestRun;

	private TestCaseModel currentrunningTestCase;

	private UiAutoTestCaseJar jar;
	
	AndroidDevice testDevice ;

	public TestRunContainer(List<TestCaseModel> testCaseModels, AndroidDevice device){
testDevice =device;
	}
	/**
	 * This method will prepare the test run for execution.
	 * 
	 */
	public void prepareRun(){
		jar = new UiAutoTestCaseJar("");

	}

	/**
	 * Runs the test cases in the test run one by one.
	 */
	public void run(){
		
		TestRunner runer = new TestRunner(jar,testDevice.getiDevice());

		//for each testcase in the testrun execute 
	}

	private List<TestRunInstanceModel> createTestRunInstances(List<TestCaseModel> testCaseModels){
		return null;
	}
	
	public UiAutoTestCaseJar getJar() {
		return jar;
	}
	

}
