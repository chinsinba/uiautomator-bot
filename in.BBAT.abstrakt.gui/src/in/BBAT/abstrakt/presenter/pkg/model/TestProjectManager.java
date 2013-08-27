package in.BBAT.abstrakt.presenter.pkg.model;

import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.data.model.Entities.TestProjectEntity;
import in.BBAT.data.model.Entities.TestRunEntity;
import in.BBAT.dataMine.manager.ProjectMineManager;
import in.BBAT.dataMine.manager.RunMineManager;

import java.util.ArrayList;
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
		List<TestProjectModel> models = new ArrayList<TestProjectModel>();
		for(TestProjectEntity entity: ProjectMineManager.getAllTesPackages()){
			TestProjectModel model = new TestProjectModel(entity);
			models.add(model);
		}
		return models;
	}
}
