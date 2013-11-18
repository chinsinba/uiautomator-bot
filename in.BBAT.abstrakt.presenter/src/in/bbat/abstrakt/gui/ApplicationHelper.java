package in.bbat.abstrakt.gui;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import in.BBAT.dataMine.manager.MineManager;
import in.bbat.configuration.ConfigXml;
import in.bbat.logger.BBATLogger;

public class ApplicationHelper {
	private static final Logger LOG = BBATLogger.getLogger(ApplicationHelper.class.getName());

	public static void initializeDb() throws UnknownHostException, Exception{
		LOG.info("Starting database");
		MineManager.getInstance().startDBServer(ConfigXml.getInstance().getDatabase_IpAddress(),ConfigXml.getInstance().getDatabase_Port(),ConfigXml.getInstance().getDatabase_UserName(),ConfigXml.getInstance().getDatabase_Pwd());
		LOG.info("Database started");
		
		LOG.info("Initializing database");
		MineManager.getInstance().createDb(ConfigXml.getInstance().getDatabase_Name(),ConfigXml.getInstance().getDatabase_IpAddress(),ConfigXml.getInstance().getDatabase_Port(),ConfigXml.getInstance().getDatabase_UserName(),ConfigXml.getInstance().getDatabase_Pwd());
		LOG.info("Initialized database");
	}
}
