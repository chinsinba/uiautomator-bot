package in.BBAT.abstrakt.presenter.run.model;


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
}
