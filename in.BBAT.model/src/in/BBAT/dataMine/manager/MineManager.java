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

	private static boolean transactionStarted=false;
	private static NetworkServerControl server;

	private static final String UNIT_NAME ="BBATDATA";

	private static final String DB_ADDRESS ="127.0.0.1"+":"+"1527";

	private static MineManager instance_;
	private MineManager(){

	}

	public static MineManager getInstance()
	{
		if(instance_ == null)
			instance_ = new MineManager();
		return instance_;
	}

	public  void createDb(String dbName) throws Exception {
		MineManagerHelper.init(UNIT_NAME, true, true, DB_ADDRESS + Path.SEPARATOR + dbName,"app","app");
	}

	public  void connectDB(String dbName) throws Exception {
		MineManagerHelper.init(UNIT_NAME, false, true, DB_ADDRESS + Path.SEPARATOR + dbName,"app","app");
	}

	public  void abortTransaction()
	{
		if(!transactionStarted){

		}
		EntityManager em = MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		em.close();
		transactionStarted =false;
	}


	public  void persist(Object object)
	{
		EntityManager em = MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		if(em==null)
		{	

		}
		em.getTransaction().begin();
		em.persist(object);
		em.getTransaction().commit();
	}

	public  Object merge(Object object)
	{
		EntityManager em = MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		if(em==null)
		{	

		}
		em.getTransaction().begin();
		Object obj =em.merge(object);
		em.getTransaction().commit();
		return obj;
	}

	public  void remove(Object object)
	{
		EntityManager em = MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		if(em==null)
		{	

		}
		em.getTransaction().begin();
		em.remove(em.merge(object));
		em.getTransaction().commit();
	}


	public  void startDBServer() throws UnknownHostException, Exception {

		System.setProperty("derby.drda.startNetworkServer", "true");
		server = new NetworkServerControl(InetAddress.getByName("127.0.0.1"), 
				1527, "app","app");
		java.io.PrintWriter consoleWriter = new java.io.PrintWriter(System.out, true);
		server.start(consoleWriter);
	}



	public  void stopDBServer() throws UnknownHostException, Exception {
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
			MineManager.getInstance().startDBServer();
			MineManager.getInstance().createDb("");
			TestProjectEntity proj = new TestProjectEntity("gygy");
			//			MineManager.getInstance().beginTransaction();
			proj.save();
			//			MineManager.getInstance().commitTransaction();
			ProjectMineManager.getAllTesPackages();
			RunMineManager.getAllTestRuns();
			SuiteMineManager.getAllTestSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
