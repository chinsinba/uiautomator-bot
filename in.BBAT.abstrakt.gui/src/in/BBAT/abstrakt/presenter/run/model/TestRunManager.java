package in.BBAT.abstrakt.presenter.run.model;

import in.BBAT.abstrakt.presenter.suite.model.TestSuiteModel;

import java.util.List;


public class TestRunManager {


	private static TestRunManager instance;

	private TestRunManager()
	{

	}

	public static TestRunManager getInstance(){
		if(instance == null)
		{
			instance= new TestRunManager();
		}
		return instance;
	}
	
	public List<TestRunModel> getTestRuns(){
		return null;
	}
}
