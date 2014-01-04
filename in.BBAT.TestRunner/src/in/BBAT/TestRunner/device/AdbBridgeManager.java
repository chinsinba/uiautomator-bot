package in.BBAT.TestRunner.device;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private Map<IDevice, IAndroidDevice> iDeviceMap = new HashMap<IDevice, IAndroidDevice>();

	private List<IDeviceConnectionListener> connectionListeners = new ArrayList<IDeviceConnectionListener>();

	private AdbBackend adbBackend;

	private AdbBridgeManager(String adbLocation) throws DeviceException {
		if (!(new File(adbLocation).canExecute())) {
			throw new DeviceException("Could not find the adb");
		}

		adbBackend = new AdbBackend(adbLocation,false);
		bridge = AndroidDebugBridge.getBridge();
		if (bridge != null) {
			AndroidDebugBridge.addDeviceChangeListener(new AdbDeviceChangeListener());
		} else {
		}
	}

	public static void init(String adbLocation) throws DeviceException{
		if (manager == null) {
			manager = new AdbBridgeManager(adbLocation);
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
	public static AdbBridgeManager getInstance() {
		return manager;
	}



	private class AdbDeviceChangeListener implements IDeviceChangeListener {
		//private AdbBridgeManager adbManager = AdbBridgeManager.this;

		public AdbDeviceChangeListener() {

		}

		@Override
		public void deviceDisconnected(IDevice device) {
			IAndroidDevice dev = iDeviceMap.get(device);
			iDeviceMap.remove(device);
			for(IDeviceConnectionListener listener :connectionListeners){
				listener.deviceDisconnected(dev);
			}
		}

		@Override
		public void deviceConnected(IDevice device) {
			IAndroidDevice dev = new TestDevice(device);
			iDeviceMap.put(device, dev);
			for(IDeviceConnectionListener listener :connectionListeners){
				listener.deviceConnected(dev);
			}
		}

		@Override
		public void deviceChanged(IDevice device, int changeMask) {

			// See if these need to be used or not.
			@SuppressWarnings("unused")
			boolean changeState = ((changeMask & IDevice.CHANGE_STATE) != 0);
			@SuppressWarnings("unused")
			boolean changeClientList = ((changeMask & IDevice.CHANGE_CLIENT_LIST) != 0);
			@SuppressWarnings("unused")
			boolean changeBuild = ((changeMask & IDevice.CHANGE_BUILD_INFO) != 0);
//			System.out.println(device.getState());
			IAndroidDevice iAndroidDevice = iDeviceMap.get(device);
			for(IDeviceConnectionListener listener :connectionListeners){
				listener.deviceChanged(iAndroidDevice, changeMask);
			}
		}
	}

	public List<IDeviceConnectionListener> getConnectionListeners() {
		return connectionListeners;
	}

	public void addConnectionListeners(IDeviceConnectionListener connectionListener) {
		this.connectionListeners.add(connectionListener);
	}

	public List<IAndroidDevice> getDevices() {
		List<IAndroidDevice> devices = new ArrayList<IAndroidDevice>();
		for(Object devObj : iDeviceMap.values()){
			devices.add((IAndroidDevice) devObj);
		}
		return devices;
	}

	public AdbBackend getAdbBackend() {
		return adbBackend;
	}

	public void setAdbBackend(AdbBackend adbBackend) {
		this.adbBackend = adbBackend;
	}

}
