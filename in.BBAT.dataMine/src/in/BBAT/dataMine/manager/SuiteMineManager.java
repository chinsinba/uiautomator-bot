package in.BBAT.dataMine.manager;

import in.BBAT.data.model.Entities.TestCaseEntity;
import in.BBAT.data.model.Entities.TestRunEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class SuiteMineManager {

	public static List<TestSuiteEntity> getAllTestSuite()
	{
		EntityManager em =  MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		try{

			Query query = em.createNamedQuery("TestSuiteEntity.findAll");
			return query.getResultList();
		}finally{
			em.close();
		}
	}

	public static List<TestRunEntity> getAllRunsContainingTestSuite(TestSuiteEntity testSuite)
	{
		EntityManager em =  MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		try{

			Query query = em.createQuery("SELECT TR FROM TestRunEntity TR, TestRunInfoEntity TRI , TestDeviceRunEntity TDR " +
					"WHERE TDR.testRun.id =TR.id " +
					"and TDR.id=TRI.testDeviceRun.id  " +
					"and TRI.testCase.suite = ?1  group by TR");
			query.setParameter(1, testSuite);
			return query.getResultList();
		}finally{
			em.close();
		}
	}
}
