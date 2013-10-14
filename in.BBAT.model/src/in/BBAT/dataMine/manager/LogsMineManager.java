package in.BBAT.dataMine.manager;

import in.BBAT.data.model.Entities.AutomatorLogEntity;
import in.BBAT.data.model.Entities.TestDeviceLogEntity;
import in.BBAT.data.model.Entities.TestRunInfoEntity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class LogsMineManager {
	
	public static List<AutomatorLogEntity> getAutoLogs(TestRunInfoEntity runinfo)
	{
		EntityManager em =  MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		try{

			Query query = em.createNamedQuery("AutomatorLogEntity.findLogsForCase");
			query.setParameter(1, runinfo);
			return query.getResultList();
		}finally{
			em.close();
		}
	}
	
	public static List<TestDeviceLogEntity> getDeviceLogs(TestRunInfoEntity runinfo)
	{
		EntityManager em =  MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		try{

			Query query = em.createNamedQuery("TestDeviceLogEntity.findLogsForCase");
			query.setParameter(1, runinfo);
			return query.getResultList();
		}finally{
			em.close();
		}
	}

}
