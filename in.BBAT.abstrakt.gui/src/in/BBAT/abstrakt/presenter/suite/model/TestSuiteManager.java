package in.BBAT.abstrakt.presenter.suite.model;

public class TestSuiteManager {


	private static TestSuiteManager instance;

	private TestSuiteManager()
	{

	}

	public static TestSuiteManager getInstance(){
		if(instance == null)
		{
			instance= new TestSuiteManager();
		}
		return instance;
	}
}
