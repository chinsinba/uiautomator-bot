package in.BBAT.abstrakt.gui;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;

import in.BBAT.dataMine.manager.MineManager;
import in.bbat.configuration.BBATInternalProperties;
import in.bbat.configuration.BBATProperties;
import in.bbat.logger.BBATLogger;
import in.bbat.utility.DefaultValueSetter;

public class ApplicationHelper {
	private static final Logger LOG = BBATLogger.getLogger(ApplicationHelper.class.getName());

	public static void initializeDb(String workspace) throws UnknownHostException, Exception{

		LOG.info("Initializing database");
		MineManager.getInstance().createDb(DefaultValueSetter.initializeDataBasePath(workspace),BBATProperties.getInstance().getDatabase_IpAddress(),BBATProperties.getInstance().getDatabase_Port(),BBATProperties.getInstance().getDatabase_UserName(),BBATProperties.getInstance().getDatabase_Pwd(),
				false,BBATInternalProperties.getInstance().getDatabaseCreate());

		LOG.info("Initialized database");
	}
}
