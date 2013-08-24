package in.BBAT.dataMine.manager;

import in.BBAT.data.model.Entities.TestProjectEntity;

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

}
