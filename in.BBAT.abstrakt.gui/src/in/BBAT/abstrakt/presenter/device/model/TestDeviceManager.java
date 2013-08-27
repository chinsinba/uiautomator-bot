package in.BBAT.abstrakt.presenter.device.model;

import java.util.List;


public class TestDeviceManager {


	private static TestDeviceManager instance;

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
		return null;
	}
}
