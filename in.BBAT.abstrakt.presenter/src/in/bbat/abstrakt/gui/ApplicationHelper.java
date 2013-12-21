package in.bbat.abstrakt.gui;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;

import in.BBAT.dataMine.manager.MineManager;
import in.bbat.configuration.BBATConfigXml;
import in.bbat.logger.BBATLogger;

public class ApplicationHelper {
	private static final Logger LOG = BBATLogger.getLogger(ApplicationHelper.class.getName());

	public static void initializeDb() throws UnknownHostException, Exception{
		LOG.info("Starting database");
		MineManager.getInstance().startDBServer(BBATConfigXml.getInstance().getDatabase_IpAddress(),BBATConfigXml.getInstance().getDatabase_Port(),BBATConfigXml.getInstance().getDatabase_UserName(),BBATConfigXml.getInstance().getDatabase_Pwd());
		LOG.info("Database started");
		
		LOG.info("Initializing database");
		MineManager.getInstance().createDb(String.valueOf(System.getProperty("user.home"))+Path.SEPARATOR+"BBAT_Data"+Path.SEPARATOR+BBATConfigXml.getInstance().getDatabase_Name(),BBATConfigXml.getInstance().getDatabase_IpAddress(),BBATConfigXml.getInstance().getDatabase_Port(),BBATConfigXml.getInstance().getDatabase_UserName(),BBATConfigXml.getInstance().getDatabase_Pwd());
		
		LOG.info("Initialized database");
	}
}
