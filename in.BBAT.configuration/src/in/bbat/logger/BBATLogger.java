package in.bbat.logger;


import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * This class is the implementation of the Log4j logging mechanism.
 * As of now the entire application uses this logger for logging.
 * @author syed mehtab
 *
 */
public class BBATLogger {

	static {
		System.setProperty("User.home",System.getProperty("user.dir") + File.separator + "logproperties");
		PropertyConfigurator.configure("logproperties" + File.separator + "log4j.properties");
	}

	/**
	 * Creates a logger based on the namespace provided.
	 * 
	 * It is a wrapper over Logger.getLogger(). It changes the handler to send
	 * output to "stdout" instead of "stderr". Default handler (ConsoleHandler)
	 * sends output to "stderr".
	 * 
	 * @param Namespace
	 *            for the new logger
	 * 
	 * @return Newly created logger
	 */
	public static Logger getLogger(String name)
	{
		Logger logger = Logger.getLogger(name);
		return logger;
	}
}
