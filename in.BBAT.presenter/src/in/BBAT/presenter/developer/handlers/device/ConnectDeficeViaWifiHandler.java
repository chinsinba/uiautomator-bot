package in.BBAT.presenter.developer.handlers.device;

import in.bbat.configuration.BBATProperties;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.dialogs.IPAdressAndPortDialog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.PlatformUI;

public class ConnectDeficeViaWifiHandler extends AbstractDeviceViewHandler {

	private static final Logger LOG = BBATLogger.getLogger(AbstractDeviceViewHandler.class.getName());
	private String deviceIPAddress;
	private String devicePort;
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		LOG.info("Connect device over Wifi");

		IPAdressAndPortDialog connectDeviceDialog = new IPAdressAndPortDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		int returnedCode = connectDeviceDialog.open();
		if(returnedCode == Window.OK){
			deviceIPAddress = connectDeviceDialog.getIpAddress();
			devicePort = connectDeviceDialog.getPort();
			BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), new Runnable() {

				@Override
				public void run() {
					if(connectDeviceOverWifi(deviceIPAddress, devicePort)){
						LOG.info("Successfully connected device with IP " + deviceIPAddress + 
								"and Port " + devicePort + "over Wifi");
					}
					else{
						LOG.error("Unable to connect device over Wifi");
					}
				}
			});
		}
		return null;
	}

	private boolean connectDeviceOverWifi(String ipAddress, String port) {
		boolean connectedOverWifi = false;
		try {
			final Process process = Runtime.getRuntime().exec(BBATProperties.getInstance().getAndroid_AdbPath() + 
					" connect " + ipAddress + ":" + port);

			Thread killProcess = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(10000); //This is done because sometimes the connect command doesn't give response for long. 
						LOG.info("Before destroying the connect process");
						process.destroy();
						LOG.info("Destroyed the connect process");
					} catch (InterruptedException e) {
						LOG.error("Thread interrupted", e);
					}
				}
			});
			killProcess.start();

			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;

			if((line = in.readLine()) != null) 
			{ 
				if(line.toLowerCase().contains("connected to".toLowerCase()))
				{
					connectedOverWifi = true;
				}
			}
		} catch (Exception e) {
			LOG.error("Problem in connecting device over wifi", e);
		}
		return connectedOverWifi;
	}
	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
