package in.bbat.testrunner;

import java.io.File;

import org.eclipse.core.runtime.ListenerList;

import com.android.chimpchat.adb.AdbBackend;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.AndroidDebugBridge.IDeviceChangeListener;
import com.android.ddmlib.IDevice;

/**
 * Class that implements IDeviceManager for ADB/Monkeyrunner debug mechanism.
 * This is the only mechanism supported as of now.
 * 
 * 
 */
public class AdbBridgeManager {


	private AndroidDebugBridge bridge = null;

	private static AdbBridgeManager manager = null;

	private ListenerList deviceListChangeListener = new ListenerList();

	private AdbDeviceChangeListener adbDeviceChangeListener = new AdbDeviceChangeListener();

	private AdbBackend adbBackend;

	private AdbBridgeManager(String adbLocation) {
		if (!(new File(adbLocation).canExecute())) {
		}

		adbBackend = new AdbBackend(adbLocation);
		bridge = AndroidDebugBridge.getBridge();
		if (bridge != null) {
			AndroidDebugBridge.addDeviceChangeListener(adbDeviceChangeListener);
		} else {
		}
	}

	/**
	 * Get manager instance. Adb/monkeyrunner implementation allows only one
	 * AdbBackend instance. Hence this class also needs to be a singleton class.
	 * 
	 * @param adbLocation
	 *            Path to adb executable, it must be a valid path.
	 * 
	 * @return New or existing manager instance.
	 * 
	 * @throws TmgDeviceManagerCreationException
	 *             When adbLocation is not a valid adb executable.
	 */
	public static AdbBridgeManager getInstance(String adbLocation) {
		if (manager == null) {
			manager = new AdbBridgeManager(adbLocation);
		}
		return manager;
	}



	private class AdbDeviceChangeListener implements IDeviceChangeListener {
		private AdbBridgeManager adbManager = AdbBridgeManager.this;

		public AdbDeviceChangeListener() {

		}

		@Override
		public void deviceDisconnected(IDevice arg0) {
		}


		@Override
		public void deviceConnected(IDevice arg0) {}

		@Override
		public void deviceChanged(IDevice arg0, int changeMask) {}
	}

	
	public static void main(String[] args) {
	AdbBridgeManager.getInstance("/home/syed/Documents/android-sdk-linux/platform-tools/adb");	
	}
}
