package in.BBAT.abstrakt.presenter.suite.model;

import in.BBAT.data.model.Entities.TestSuiteEntity;
import in.BBAT.dataMine.manager.SuiteMineManager;

import java.util.ArrayList;
import java.util.List;

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

	public List<TestSuiteModel> getTestSuites(){
		List<TestSuiteModel> models = new ArrayList<TestSuiteModel>();
		for(TestSuiteEntity entity: SuiteMineManager.getAllTestSuite()){
			TestSuiteModel model = new TestSuiteModel(entity);
			models.add(model);
		}
		return models;
	}
}
