package in.BBAT.dataMine.manager;

import java.util.Map;

import org.eclipse.persistence.config.PersistenceUnitProperties;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class DerbyMineProperties {

	public static void getPropertiesEmbedded(
			Map<String, Object> properties, String dbDir,String dbUserName,String dbPassword) {
		properties.put(PersistenceUnitProperties.JDBC_DRIVER,
				"org.apache.derby.jdbc.EmbeddedDriver");

		String url = "jdbc:derby:" + dbDir + ";create=true";
		properties.put(PersistenceUnitProperties.JDBC_URL, url);
		properties.put(PersistenceUnitProperties.JDBC_USER, dbUserName);
		properties.put(PersistenceUnitProperties.JDBC_PASSWORD,dbPassword);
	}

	public static void getPropertiesClient(Map<String, Object> properties, String dbName,String dbUserName,String dbPassword) {
		properties.put(PersistenceUnitProperties.JDBC_DRIVER,"org.apache.derby.jdbc.ClientDriver");
		
		String url = "jdbc:derby://" + dbName + ";create=true";
		properties.put(PersistenceUnitProperties.JDBC_URL, url);
		properties.put(PersistenceUnitProperties.JDBC_USER, dbUserName);
		properties.put(PersistenceUnitProperties.JDBC_PASSWORD,dbPassword);
	}
}
