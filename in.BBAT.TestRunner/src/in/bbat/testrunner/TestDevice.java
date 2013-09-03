package in.bbat.testrunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.BBAT.testRunner.runner.ILogListener;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;


public class TestDevice implements IAndroidDevice {

	private boolean active;
	private IDevice monkeyDevice;

	private List<ILogListener> listeners = new ArrayList<ILogListener>();

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

	@Override
	public void addLogListener(ILogListener listener) {
		listeners.add(listener);
	}


	private void clearLogs()
	{
		final String cmd ="logcat -c";
		try {
			monkeyDevice.executeShellCommand(cmd, new IShellOutputReceiver() {
				@Override
				public boolean isCancelled()
				{
					return false;
				}

				@Override
				public void flush()
				{
				}

				@Override
				public void addOutput(byte[] arg0, int arg1, int arg2)
				{
				}
			}, 0);

		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdbCommandRejectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellCommandUnresponsiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void startLogging() {
		try {
			monkeyDevice.executeShellCommand("logcat -v time", new MultiLineReceiver() {

				@Override
				public boolean isCancelled() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void processNewLines(String[] arg0) {
					// TODO Auto-generated method stub

				}
			});
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdbCommandRejectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellCommandUnresponsiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public void activate() {
		this.active = true;
	}

	@Override
	public void deActivate() {
		this.active = false;		
	}
}