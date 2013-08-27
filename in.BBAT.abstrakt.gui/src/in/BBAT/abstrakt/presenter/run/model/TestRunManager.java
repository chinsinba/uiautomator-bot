package in.BBAT.abstrakt.presenter.run.model;

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
