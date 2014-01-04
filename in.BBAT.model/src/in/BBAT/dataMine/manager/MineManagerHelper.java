package in.BBAT.dataMine.manager;

import in.BBAT.dataMine.Activator;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
/**
 * 
 * @author Syed Mehtab
 *
 */
public class MineManagerHelper {

	private static MineManagerHelper emHelp = null;
	private EntityManagerFactory emFactory = null;

	public EntityManagerFactory getEmFactory() {
		return emFactory;
	}

	/*private  void getPropertiesCreateTable(Map<String, Object> properties) {
		properties.put(PersistenceUnitProperties.DDL_GENERATION,
				PersistenceUnitProperties.CREATE_OR_EXTEND);
		properties.put(PersistenceUnitProperties.DDL_GENERATION_MODE,
				PersistenceUnitProperties.DDL_DATABASE_GENERATION);
		properties.put(PersistenceUnitProperties.CLASSLOADER,
				Activator.getClassLoader());
	}*/

	private  void getPropertiesExistingTable(
			Map<String, Object> properties) {
		properties.put(PersistenceUnitProperties.CLASSLOADER,
				Activator.getClassLoader());


		//		properties.put(PersistenceUnitProperties.TARGET_DATABASE, "DERBY");
	}

	private MineManagerHelper(String persistenceUnitName,
			boolean createTable, boolean networkDb, String dbPath,String dbUserName,String dbPassword)throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();

		getPropertiesExistingTable(properties);
		String url;
		if(networkDb)
		url = "jdbc:derby://" + dbPath + ";create=true";
		else
			url = "jdbc:derby:" + dbPath + ";create=true";
		properties.put(PersistenceUnitProperties.JDBC_URL, url);
		/*if (createTable) {
			getPropertiesCreateTable(properties);
		}*/

		/*	if (networkDb) {
			DerbyMineProperties.getPropertiesClient(properties, dbPath,dbUserName,dbPassword);
		} else {
			DerbyMineProperties.getPropertiesEmbedded(properties, dbPath,dbUserName,dbPassword);
		}*/

		emFactory = new PersistenceProvider().createEntityManagerFactory(persistenceUnitName, properties);
	}

	public static MineManagerHelper getInstance() 
	{
		if(emHelp == null){
			/*"The Entitymanagerhelper should be initialised at least once before accessing this method." +
			"Call init method before calling this"*/
			return null;
		}

		return emHelp;
	}

	public static MineManagerHelper createEntityManager (
			String persistenceUnitName, boolean createTable, boolean networkDb,
			String dbPath,String dbUserName,String dbPassword) throws Exception{
		if (emHelp == null) {
			emHelp = new MineManagerHelper(persistenceUnitName,
					createTable, networkDb, dbPath,dbUserName,dbPassword);
		}
		return emHelp;
	}

	/**
	 * THis method should be called only once before using this singleton class
	 * @param persistenceUnitName
	 * @param createTable
	 * @param networkDb
	 * @param dbPath
	 * @param dbUserName
	 * @param dbPassword
	 * @return
	 * @throws Exception
	 */
	public static MineManagerHelper init (
			String persistenceUnitName, boolean createTable, boolean networkDb,
			String dbPath,String dbUserName,String dbPassword) throws Exception{

		if (emHelp != null) {
			throw new IllegalStateException("already initialized. This can be called only once in the life time of this class object");
		}
		if (emHelp == null) {
			emHelp = new MineManagerHelper(persistenceUnitName,
					createTable, networkDb, dbPath,dbUserName,dbPassword);
		}
		return emHelp;
	}

	public void closeEntityManagerHelper() {
		if (emHelp != null) {
			emFactory.close();
			emHelp = null;
		}
	}
	
	public Connection getConnection(){
		
		EntityManager createEntityManager = emFactory.createEntityManager();
		createEntityManager.getTransaction().begin();
		Connection con = createEntityManager.unwrap(java.sql.Connection.class);
		createEntityManager.getTransaction().commit();
		return con;
				
	}
}

