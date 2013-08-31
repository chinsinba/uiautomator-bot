package in.bbat.testrunner;

import com.android.ddmlib.IDevice;


public class TestDevice implements IAndroidDevice {

	IDevice monkeyDevice;
	public TestDevice(IDevice device) {
		this.monkeyDevice = device;
	}

	@Override
	public String getModelName() {
		return null;
	}

	@Override
	public String getDeviceId() {
		return null;
	}

	@Override
	public String getStatus() {
		return null;
	}

}
