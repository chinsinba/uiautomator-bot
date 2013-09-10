package in.bbat.abstrakt.gui;

import java.net.UnknownHostException;

import in.BBAT.dataMine.manager.MineManager;

public class ApplicationHelper {


	public static void initializeDb() throws UnknownHostException, Exception{
		MineManager.getInstance().startDBServer();
		MineManager.getInstance().createDb();
	}
}
