package in.BBAT.TestRunner.device;

import in.BBAT.testRunner.runner.IUiAutomatorListener;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.BBAT.testRunner.runner.internal.UIAutomatorRunner;

import java.io.IOException;
import java.util.List;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.NullOutputReceiver;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.logcat.LogCatListener;
import com.android.ddmlib.logcat.LogCatMessage;
import com.android.ddmlib.logcat.LogCatReceiverTask;
import com.android.ddmlib.testrunner.ITestRunListener;


public class TestDevice implements IAndroidDevice {

	private boolean active;
	private IDevice monkeyDevice;

	private ILogListener listener;
	private LogCatReceiverTask logReciever;

	public final static String UIAUTOMATOR_JAR_PATH = "/data/local/tmp/BBAT.jar";

	public TestDevice(IDevice device) {
		this.monkeyDevice = device;
	}

	@Override
	public String getModelName() {
		return null;
	}

	@Override
	public String getDeviceId() {
		return monkeyDevice.getSerialNumber();
	}

	@Override
	public String getStatus() {
		return null;
	}

	@Override
	public void setLogListener(ILogListener listener) {
		this.listener = listener;
	}


	private void clearLogs()
	{
		final String cmd ="logcat -c";
		try {
			monkeyDevice.executeShellCommand(cmd, new NullOutputReceiver(),0);
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

		Thread logger = new Thread(new Runnable() {

			@Override
			public void run() {
				clearLogs();
				listener.startLogging(TestDevice.this.getMonkeyDevice());
				logReciever =new LogCatReceiverTask(monkeyDevice);
				logReciever.addLogCatListener(new LogLineReciever());
				logReciever.run();
			}
		});

		logger.start();
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
		logReciever.stop();
	}

	private class LogLineReciever implements LogCatListener
	{
		@Override
		public void log(List<LogCatMessage> logMessages) {
			listener.processLogLine(logMessages);
		}
	}

	@Override
	public void pushTestJar(UiAutoTestCaseJar jar) {
		try {
			monkeyDevice.pushFile(jar.getJarPath(),UIAUTOMATOR_JAR_PATH);
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
	public void executeTestCase(String testCaseName,IUiAutomatorListener uiAutoListener,ITestRunListener... listener) {
		UIAutomatorRunner runner = new UIAutomatorRunner(testCaseName, monkeyDevice,uiAutoListener);
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

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return monkeyDevice.getName();
	}

	@Override
	public RawImage getScreenshot() {
		// TODO Auto-generated method stub
		try {
			return monkeyDevice.getScreenshot();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (AdbCommandRejectedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IDevice getMonkeyDevice() {
		return monkeyDevice;
	}
}