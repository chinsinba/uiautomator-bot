package in.BBAT.dataMine.manager;

import java.net.InetAddress;
import java.net.UnknownHostException;

import in.BBAT.data.model.Entities.TestProjectEntity;

import javax.persistence.EntityManager;

import org.apache.derby.drda.NetworkServerControl;
import org.eclipse.core.runtime.Path;

/**
 * @author Syed Mehtab
 */
public class MineManager {

	private static EntityManager em = null;
	private static boolean transactionStarted=false;
	private static NetworkServerControl server;

	public static void createDb() throws Exception {
		String path ="127.0.0.1"+":"+"1527";
		MineManagerHelper.init("BBATDATA", true, true, path + Path.SEPARATOR + "sample","app","app");
	}

	public static void connectDB() throws Exception {
		String path ="127.0.0.1"+":"+"1527";
		MineManagerHelper.init("BBATDATA", false, true, path + Path.SEPARATOR + "sample","app","app");
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


	public static void persist(Object object)
	{
		if(em==null)
		{	

		}
		em.persist(object);
	}
	
	public static Object merge(Object object)
	{
		if(em==null)
		{	

		}
		return em.merge(object);
	}
	
	public static void remove(Object object)
	{
		if(em==null)
		{	

		}
		em.remove(object);
	}

	
	public static void startDBServer() throws UnknownHostException, Exception {

		System.setProperty("derby.drda.startNetworkServer", "true");
		server = new NetworkServerControl(InetAddress.getByName("127.0.0.1"), 
				1527, "app","app");
		java.io.PrintWriter consoleWriter = new java.io.PrintWriter(System.out, true);
		server.start(consoleWriter);
	}

	

	public static void stopDBServer() throws UnknownHostException, Exception {
		if(server!=null)
		{
			try{
				server.shutdown();
			}catch (Exception e) {
			}
		}
	}

	public static void main(String[] args) {
		try {
			startDBServer();
			createDb();
			TestProjectEntity proj = new TestProjectEntity();
			beginTransaction();
			proj.save();
			commitTransaction();
			ProjectMineManager.getAllTesPackages();
			RunMineManager.getAllTestRuns();
			SuiteMineManager.getAllTestSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
