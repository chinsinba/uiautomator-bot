package in.BBAT.dataMine.manager;

import in.BBAT.data.model.Entities.TestDeviceEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class DeviceMineManager {


	public static TestDeviceEntity find(String serialNo) {
		EntityManager em =  MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		try{
			Query query = em.createNamedQuery("TestDeviceEntity.findByID");
			query.setParameter(1, serialNo);
			Object obj=null;
			try{
				 obj = query.getSingleResult();
			}catch(Exception e){
				return null;
			}
			return (TestDeviceEntity) obj;
		}finally{
			em.close();
		}
	};
}
