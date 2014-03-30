package in.BBAT.presenter.wizards;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import in.BBAT.presenter.wizards.pages.BrowseTestPackagePage;
import in.BBAT.presenter.wizards.pages.ConnectWiFiWizardPage;
import in.bbat.configuration.BBATProperties;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.PlatformUI;

public class WifiConnectingWizard extends Wizard {

	private Logger LOG = BBATLogger.getLogger(WifiConnectingWizard.class.getName());
	private ConnectWiFiWizardPage page0;
	private String deviceIPAddress;
	private String devicePort;

	@Override
	public boolean performFinish() {

		deviceIPAddress = page0.getIpAddress();
		devicePort = page0.getPort();
		
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
		return false;
	}

	@Override
	public void addPages() {
		page0 = new ConnectWiFiWizardPage("Connect Device");
		addPage(page0);

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
}
