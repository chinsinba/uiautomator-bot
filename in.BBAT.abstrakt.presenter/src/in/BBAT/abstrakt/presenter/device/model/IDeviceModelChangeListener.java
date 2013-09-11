package in.BBAT.abstrakt.presenter.device.model;


public interface IDeviceModelChangeListener {

	void deviceAdded(AndroidDevice device);
	
	void deviceRemoved(AndroidDevice device);
	
}
