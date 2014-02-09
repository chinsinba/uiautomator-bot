package in.BBAT.dataMine.manager;

import in.BBAT.data.model.Entities.TestCaseEntity;
import in.BBAT.data.model.Entities.TestRunEntity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class TestCaseMineManager {

	public static List<TestRunEntity> getAllRunsContainingTestCase(TestCaseEntity testCase)
	{
		EntityManager em =  MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		try{

			Query query = em.createQuery("SELECT TR FROM TestRunEntity TR, TestRunInfoEntity TRI , TestDeviceRunEntity TDR " +
					"WHERE TDR.testRun.id =TR.id " +
					"and TDR.id=TRI.testDeviceRun.id  " +
					"and TRI.testCase = ?1  group by TR");
			query.setParameter(1, testCase);
			return query.getResultList();
		}finally{
			em.close();
		}
	}
}
