package in.bbat.presenter;

import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.bbat.abstrakt.gui.ApplicationHelper;
import in.bbat.configuration.ConfigXml;
import in.bbat.logger.BBATLogger;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	private static final Logger LOG = BBATLogger.getLogger(Application.class.getName());
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) {
		Display display = PlatformUI.createDisplay();
		try {
			try {
				LOG.info("Initialize Db");
				ApplicationHelper.initializeDb();
				TestDeviceManager.init(ConfigXml.getInstance().getAndroid_AdbPath());
			} catch (UnknownHostException e) {
				LOG.error(e);
				
			} catch (Exception e) {
				LOG.error(e);
			}
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());

			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			return IApplication.EXIT_OK;
		} finally {
			display.dispose();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning())
			return;
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
