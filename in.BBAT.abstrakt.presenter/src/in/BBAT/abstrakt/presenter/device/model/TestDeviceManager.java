package in.BBAT.abstrakt.presenter.device.model;

import in.BBAT.TestRunner.device.IAndroidDevice;
import in.BBAT.TestRunner.device.IDeviceConnectionListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestDeviceManager  implements IDeviceConnectionListener{


	private static TestDeviceManager instance;
	
	private Map<IAndroidDevice, AndroidDevice> iDeviceMap = new HashMap<IAndroidDevice, AndroidDevice>();

	private TestDeviceManager()
	{
	}

	public static TestDeviceManager getInstance(){
		if(instance == null)
		{
			instance= new TestDeviceManager();
		}
		return instance;
	}

	public List<AndroidDevice> getDevices(){
		return (List<AndroidDevice>) iDeviceMap.values();
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

}
