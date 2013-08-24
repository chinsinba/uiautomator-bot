package in.BBAT.dataMine.manager;

import in.BBAT.data.model.Entities.TestProjectEntity;

import javax.persistence.EntityManager;

import org.eclipse.core.runtime.Path;

/**
 * @author Syed Mehtab
 */
public class MineManager {

	private static EntityManager em = null;
	private static boolean transactionStarted=false;
	
	public static void createDb() throws Exception {
		String path ="127.0.0.1"+":"+"1527";
		MineManagerHelper.init("BBATDATA", true, true, path + Path.SEPARATOR + "sample","app","app");
	}


	public static void beginTransaction()
	{
		if(transactionStarted){

		}
		em = MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		em.getTransaction().begin();
		transactionStarted = true;
	}

	public static void commitTransaction()
	{
		if(!transactionStarted){

		}
		em.getTransaction().commit();
		em.close();
		transactionStarted =false;
	}

	public static void abortTransaction()
	{
		if(!transactionStarted){

		}
		em.close();
		transactionStarted =false;
	}
	
	public static void main(String[] args) {
		try {
			createDb();
			
			TestProjectEntity proj = new TestProjectEntity();
			beginTransaction();
			em.persist(proj);
			commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
