package in.BBAT.dataMine.manager;

import in.BBAT.data.model.Entities.TestRunEntity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class RunMineManager {

	public static List<TestRunEntity> getAllTestRuns()
	{
		EntityManager em =  MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		try{

			Query query = em.createNamedQuery("TestRunEntity.findAll");
			return query.getResultList();
		}finally{
			em.close();
		}
	}
}
