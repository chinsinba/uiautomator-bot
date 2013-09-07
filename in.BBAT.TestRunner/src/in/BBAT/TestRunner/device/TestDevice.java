package in.BBAT.TestRunner.device;

import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.BBAT.testRunner.runner.internal.UIAutomatorRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.testrunner.ITestRunListener;


public class TestDevice implements IAndroidDevice {

	private boolean active;
	private IDevice monkeyDevice;

	private List<ILogListener> listeners = new ArrayList<ILogListener>();
	private LogLineReciever logReciever;
	
	public final static String UIAUTOMATOR_JAR_PATH = "/data/local/tmp/";

	public TestDevice(IDevice device) {
		this.monkeyDevice = device;
		logReciever =new LogLineReciever();
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
			e.printStackTrace();
		} catch (AdbCommandRejectedException e) {
			e.printStackTrace();
		} catch (ShellCommandUnresponsiveException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startLogging() {
		clearLogs();
		try {
			monkeyDevice.executeShellCommand("logcat -v time", logReciever);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (AdbCommandRejectedException e) {
			e.printStackTrace();
		} catch (ShellCommandUnresponsiveException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

	@Override
	public void stopLogging() {
		logReciever.setStop(true);
	}

	private class LogLineReciever extends MultiLineReceiver
	{
		private boolean stop;

		@Override
		public boolean isCancelled() {
			return isStop();
		}

		@Override
		public void processNewLines(String[] logs) {
			for(int i=0;i<logs.length;i++){
				for(ILogListener logListener : listeners){
					logListener.processLogLine(logs[i]);
				}
			}
		}

		public boolean isStop() {
			return stop;
		}

		public void setStop(boolean stop) {
			this.stop = stop;
		}
	}

	@Override
	public void pushTestJar(UiAutoTestCaseJar jar) {
		try {
			monkeyDevice.pushFile(jar.getJarPath(), UIAUTOMATOR_JAR_PATH);
		} catch (SyncException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AdbCommandRejectedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void executeTestCase(String testCaseName, ITestRunListener listener) {
		UIAutomatorRunner runner = new UIAutomatorRunner(testCaseName, monkeyDevice);
		try {
			runner.run(listener);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (AdbCommandRejectedException e) {
			e.printStackTrace();
		} catch (ShellCommandUnresponsiveException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}