package in.BBAT.abstrakt.presenter.run.manager;


import java.util.List;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;

public class TestRunContainer {

	private TestRunModel currentRunningTestRun;

	private TestCaseModel currentrunningTestCase;

	public TestRunContainer(List<TestCaseModel> testCaseModels){

	}
	/**
	 * This method will prepare the test run for execution.
	 * 
	 */
	public void prepareRun(){

	}

	/**
	 * Runs the test cases in the test run one by one.
	 */
	public void run(){

	}

	private List<TestRunInstanceModel> createTestRunInstances(List<TestCaseModel> testCaseModels){
		return null;
	}

}
