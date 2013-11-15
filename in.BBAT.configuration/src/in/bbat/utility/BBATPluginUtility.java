package in.bbat.utility;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * 
 * This utility class will provide services to the entire application.
 * 
 * @author syed Mehtab
 *
 */
public class BBATPluginUtility {

	private static BBATPluginUtility instance_;

	private BBATPluginUtility() {
	}

	public static BBATPluginUtility getInstance()
	{
		if(instance_==null)
			instance_ = new BBATPluginUtility();
		return instance_;
	}

	/**
	 *  Given the <var>pluginId</var>, this method will return the 
	 *  location of the plugin.
	 * @param pluginId 
	 * @return The absolute location of the plugin. 
	 */
	public  String getPluginDir(String pluginId)
	{
		/* get bundle with the specified id */
		Bundle bundle = Platform.getBundle(pluginId);
		if( bundle == null )
			throw new RuntimeException("Could not resolve plugin: " + pluginId + "\r\n" +
					"Probably the plugin has not been correctly installed.\r\n" +
					"Running eclipse from shell with -clean option may rectify installation.");

		/* resolve Bundle::getEntry to local URL */
		URL pluginURL = null;
		try {
			pluginURL = Platform.resolve(bundle.getEntry("/"));
		} catch (IOException e) {
//			LOG.error("Could not get installation directory of the plugin: ",e);
			throw new RuntimeException("Could not get installation directory of the plugin: " + pluginId);
		}
		String pluginInstallDir = pluginURL.getPath().trim();
		if( pluginInstallDir.length() == 0 )
			throw new RuntimeException("Could not get installation directory of the plugin: " + pluginId);

		/* since path returned by URL::getPath starts with a forward slash, that
		 * is not suitable to run commandlines on Windows-OS, but for Unix-based
		 * OSes it is needed. So strip one character for windows. There seems
		 * to be no other clean way of doing this. */
		if( Platform.getOS().compareTo(Platform.OS_WIN32) == 0 )
			pluginInstallDir = pluginInstallDir.substring(1);
		return pluginInstallDir;
	}

}
