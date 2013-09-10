package in.BBAT.abstrakt.presenter.device.model;

import in.BBAT.TestRunner.device.AdbBridgeManager;
import in.BBAT.TestRunner.device.IAndroidDevice;
import in.BBAT.TestRunner.device.IDeviceConnectionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestDeviceManager  implements IDeviceConnectionListener{


	private static TestDeviceManager instance;

	private Map<IAndroidDevice, AndroidDevice> iDeviceMap = new HashMap<IAndroidDevice, AndroidDevice>();

	private TestDeviceManager()
	{
		AdbBridgeManager.getInstance().addConnectionListeners(this);
	}

	public static TestDeviceManager getInstance(){
		if(instance == null)
		{
			instance= new TestDeviceManager();
		}
		return instance;
	}

	public List<AndroidDevice> getDevices(){
		List<AndroidDevice> devices = new ArrayList<AndroidDevice>();
		for(IAndroidDevice devObj : AdbBridgeManager.getInstance().getDevices()){
			if(iDeviceMap.get(devObj)==null){
				AndroidDevice dev = new AndroidDevice( devObj);
				devices.add(dev);
			}
		}
		return devices;
	}

	@Override
	public void deviceDisconnected(IAndroidDevice device) {
		AndroidDevice dev = new AndroidDevice(device);
		iDeviceMap.put(device, dev);
	}

	@Override
	public void deviceConnected(IAndroidDevice device) {
		iDeviceMap.get(device);
	}

	@Override
	public void deviceChanged(IAndroidDevice device, int changeMask) {
	}

	public static void init(String adbLocation) {
		AdbBridgeManager.init(adbLocation);		
	}

}
