package in.BBAT.dataMine.manager;

import in.BBAT.data.model.Entities.TestProjectEntity;
import in.BBAT.data.model.Entities.TestRunEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ProjectMineManager {

	public static List<TestProjectEntity> getAllTesPackages()
	{
		EntityManager em =  MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		try{

			Query query = em.createNamedQuery("TestProjectEntity.findAll");
			return query.getResultList();
		}finally{
			em.close();
		}
	}

	public static List<TestRunEntity> getAllRunsContainingTestProject(TestProjectEntity testProj)
	{
		EntityManager em =  MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		try{

			Query query = em.createQuery("SELECT TR FROM TestRunEntity TR, TestRunInfoEntity TRI , TestDeviceRunEntity TDR " +
					"WHERE TDR.testRun.id =TR.id " +
					"and TDR.id=TRI.testDeviceRun.id  " +
					"and TRI.testCase.suite.testProject = ?1  "
					+ " group by TR");
			query.setParameter(1, testProj);
			return query.getResultList();
		}finally{
			em.close();
		}
	}

}
