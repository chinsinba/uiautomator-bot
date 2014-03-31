package in.BBAT.dataMine.manager;

import in.bbat.configuration.BBATProperties;
import in.bbat.configuration.BBATProperties;
import in.bbat.logger.BBATLogger;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


import javax.persistence.EntityManager;

import org.apache.derby.drda.NetworkServerControl;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;

/**
 * @author Syed Mehtab
 */
public class MineManager {

	private static boolean transactionStarted=false;
	private static NetworkServerControl server;
	private static final String UNIT_NAME ="BBATDATA";
	private static MineManager instance_;
	private static final Logger LOG = BBATLogger.getLogger(MineManager.class.getName());
	private MineManager(){

	}

	public static MineManager getInstance()
	{
		if(instance_ == null)
			instance_ = new MineManager();
		return instance_;
	}

	public  void createDb(String dbName,String ipAddress, int port, String userName, String password,boolean networkDB) throws Exception {
		if(networkDB){
			LOG.info("Starting database");
			startDBServer(BBATProperties.getInstance().getDatabase_IpAddress(),BBATProperties.getInstance().getDatabase_Port(),BBATProperties.getInstance().getDatabase_UserName(),BBATProperties.getInstance().getDatabase_Pwd());
			LOG.info("Database started");
		}
		if(isDBPresent())
			return;
		String DB_ADDRESS =ipAddress+":"+port;
		if(networkDB)
			MineManagerHelper.init(UNIT_NAME, false, networkDB, DB_ADDRESS + Path.SEPARATOR + dbName,userName,password);
		else {
			MineManagerHelper.init(UNIT_NAME, false, networkDB, dbName,userName,password);
		}
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
		try{
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
		}finally
		{
			if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			em.close();
		}

	}

	public  Object merge(Object object)
	{
		EntityManager em = MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		Object obj = null;
		try{
			em.getTransaction().begin();
			obj =em.merge(object);
			em.getTransaction().commit();
		}finally
		{

			if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			em.close();
		}
		return obj;
	}

	public  void remove(Object object)
	{
		EntityManager em = MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		try{
			em.getTransaction().begin();
			em.remove(em.merge(object));
			em.getTransaction().commit();
		}finally
		{

			if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			em.close();
		}
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

	public void persistAll(List<Object> objectList) {

		EntityManager em = MineManagerHelper.getInstance().getEmFactory().createEntityManager();
		try{
			em.getTransaction().begin();
			for (Object object : objectList) {
				em.persist(object);
			}
			em.getTransaction().commit();
		}finally
		{
			if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			em.close();
		}


	}
}
