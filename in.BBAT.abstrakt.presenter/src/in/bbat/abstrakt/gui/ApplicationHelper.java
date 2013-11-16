package in.bbat.abstrakt.gui;

import java.net.UnknownHostException;

import in.BBAT.dataMine.manager.MineManager;
import in.bbat.configuration.ConfigXml;

public class ApplicationHelper {


	public static void initializeDb() throws UnknownHostException, Exception{
		MineManager.getInstance().startDBServer(ConfigXml.getInstance().getDatabase_IpAddress(),ConfigXml.getInstance().getDatabase_Port(),ConfigXml.getInstance().getDatabase_UserName(),ConfigXml.getInstance().getDatabase_Pwd());
		MineManager.getInstance().createDb(ConfigXml.getInstance().getDatabase_Name(),ConfigXml.getInstance().getDatabase_IpAddress(),ConfigXml.getInstance().getDatabase_Port(),ConfigXml.getInstance().getDatabase_UserName(),ConfigXml.getInstance().getDatabase_Pwd());
	}
}
