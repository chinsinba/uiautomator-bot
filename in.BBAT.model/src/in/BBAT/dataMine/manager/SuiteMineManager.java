package in.BBAT.dataMine.manager;

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
}
