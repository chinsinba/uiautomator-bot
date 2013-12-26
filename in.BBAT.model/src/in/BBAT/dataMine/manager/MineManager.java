package in.BBAT.dataMine.manager;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;


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
	private static MineManager instance_;

	private MineManager(){

	}

	public static MineManager getInstance()
	{
		if(instance_ == null)
			instance_ = new MineManager();
		return instance_;
	}

	public  void createDb(String dbName,String ipAddress, int port, String userName, String password) throws Exception {
		if(isDBPresent())
			return;
		String DB_ADDRESS =ipAddress+":"+port;
		MineManagerHelper.init(UNIT_NAME, false, true, DB_ADDRESS + Path.SEPARATOR + dbName,userName,password);
	}

	private boolean isDBPresent() {
		return false;
	}

	public  void connectDB(String dbName) throws Exception {
//		String DB_ADDRESS =ConfigXml.getInstance().getDatabase_IpAddress()+":"+ConfigXml.getInstance().getDatabase_Port();
//		MineManagerHelper.init(UNIT_NAME, false, true, DB_ADDRESS + Path.SEPARATOR + dbName,"app","app");
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


	public void startDBServer(String ipAddress, int port, String userName, String password) throws UnknownHostException, Exception {
		System.setProperty("derby.drda.startNetworkServer", "true");
		server = new NetworkServerControl(InetAddress.getByName(ipAddress),port,userName,password);
		PrintWriter consoleWriter = new PrintWriter(System.out, true);
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
}
