package in.BBAT.dataMine.manager;

import in.BBAT.dataMine.Activator;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
/**
 * 
 * @author Syed Mehtab
 *
 */
public class MineManagerHelper {

	private static MineManagerHelper emHelper = null;
	private EntityManager entityManager = null;
	private EntityManagerFactory emFactory = null;

	public EntityManagerFactory getEmFactory() {
		return emFactory;
	}

	private  void getPropertiesCreateTable(Map<String, Object> properties) {
		properties.put(PersistenceUnitProperties.DDL_GENERATION,
				PersistenceUnitProperties.CREATE_OR_EXTEND);
		properties.put(PersistenceUnitProperties.DDL_GENERATION_MODE,
				PersistenceUnitProperties.DDL_DATABASE_GENERATION);
		properties.put(PersistenceUnitProperties.CLASSLOADER,
				Activator.getClassLoader());
	}

	private  void getPropertiesExistingTable(
			Map<String, Object> properties) {
		properties.put(PersistenceUnitProperties.CLASSLOADER,
				Activator.getClassLoader());

		properties.put(PersistenceUnitProperties.TARGET_DATABASE, "DERBY");
	}

	private MineManagerHelper(String persistenceUnitName,
			boolean createTable, boolean networkDb, String dbPath,String dbUserName,String dbPassword)throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();

		getPropertiesExistingTable(properties);

		if (createTable) {
			getPropertiesCreateTable(properties);
		}

		if (networkDb) {
			DerbyMineProperties.getPropertiesClient(properties, dbPath,dbUserName,dbPassword);
		} else {
			DerbyMineProperties.getPropertiesEmbedded(properties, dbPath,dbUserName,dbPassword);
		}

		emFactory = new PersistenceProvider().createEntityManagerFactory(
				persistenceUnitName, properties);
		try
		{
			createEntityMgr();
		}
		catch (Exception e)
		{
		}
	}

	private void createEntityMgr() {
		if(entityManager==null || !entityManager.isOpen())
			entityManager = emFactory.createEntityManager();
	}

	public void closeEntityMgr(){
		if(entityManager!=null && entityManager.isOpen()){
			entityManager.close();

		}
	}

	public static MineManagerHelper getInstance() 
	{
		if(emHelper == null){
			/*"The Entitymanagerhelper should be initialised at least once before accessing this method." +
			"Call init method before calling this"*/
			return null;
		}
		else
		{
			if(!emHelper.getEm().isOpen())
			{
				emHelper.createEntityMgr();
			}
			return emHelper;

		}
	}

	public static MineManagerHelper createEntityManager (
			String persistenceUnitName, boolean createTable, boolean networkDb,
			String dbPath,String dbUserName,String dbPassword) throws Exception{
		if (emHelper == null) {
			emHelper = new MineManagerHelper(persistenceUnitName,
					createTable, networkDb, dbPath,dbUserName,dbPassword);
		}
		return emHelper;
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

		if (emHelper != null) {
			throw new IllegalStateException("already initialized. This can be called only once in the life time of this class object");
		}
		if (emHelper == null) {
			emHelper = new MineManagerHelper(persistenceUnitName,
					createTable, networkDb, dbPath,dbUserName,dbPassword);
		}
		return emHelper;
	}

	public void closeEntityManagerHelper() {
		if (emHelper != null) {
			entityManager.close();
			emFactory.close();
			emHelper = null;
		}
	}

	public EntityManager getEm() {
		return entityManager;
	}

}
