package in.BBAT.abstrakt.presenter.run.manager;


import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.run.model.TestRunCase;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.testRunner.runner.TestRunner;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;

import java.util.List;

public class TestRunContainer {

	private TestRunModel currentRunningTestRun;

	private TestRunCase currentrunningTestCase;

	private List<TestRunCase> caseList;
	private UiAutoTestCaseJar jar;

	private AndroidDevice testDevice ;

	private TestRunner runer;

	public TestRunContainer(List<TestRunCase> testRunCaseList, AndroidDevice device){
		testDevice =device;
		this.caseList = testRunCaseList;
	}
	/**
	 * This method will prepare the test run for execution.
	 * 
	 */
	public void prepareRun(){
		jar = new UiAutoTestCaseJar("");
		runer = new TestRunner(jar,testDevice.getiDevice());
		currentRunningTestRun = new TestRunModel();
	}

	/**
	 * Runs the test cases in the test run one by one.
	 */
	public void run(){
		for(TestRunCase runCaseObj : caseList){
			currentrunningTestCase = runCaseObj;
			runer.execute(runCaseObj.getTestcase().getName(),new TestCaseExecutionListener(runCaseObj), new DeviceLogListener());
		}
		//for each testcase in the testrun execute 
	}

	private List<TestRunInstanceModel> createTestRunInstances(List<TestRunCase> testRunCases){
		return null;
	}

	public UiAutoTestCaseJar getJar() {
		return jar;
	}

}