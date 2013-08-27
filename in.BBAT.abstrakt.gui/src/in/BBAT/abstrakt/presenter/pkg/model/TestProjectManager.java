package in.BBAT.abstrakt.presenter.pkg.model;

import in.BBAT.abstrakt.presenter.run.model.TestRunManager;

public class TestProjectManager {


	private static TestProjectManager instance;

	private TestProjectManager()
	{

	}

	public static TestProjectManager getInstance(){
		if(instance == null)
		{
			instance= new TestProjectManager();
		}
		return instance;
	}
}
