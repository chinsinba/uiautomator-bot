package in.BBAT.abstrakt.presenter.pkg.model;

import java.util.List;


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

	public List<TestProjectModel> getTestProjects(){ 
		return null;
	}
}
