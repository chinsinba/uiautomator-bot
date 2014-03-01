package in.BBAT.TestRunner.device;

import in.BBAT.TestRunner.Listener.IAndroidDevice;
import in.BBAT.TestRunner.Listener.ICpuUsageListener;
import in.BBAT.TestRunner.Listener.ILogListener;
import in.BBAT.TestRunner.Listener.IMemoryUsageListener;
import in.BBAT.TestRunner.Listener.IScreenShotListener;
import in.BBAT.TestRunner.Listener.IUiAutomatorListener;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.BBAT.testRunner.runner.internal.UIAutomatorRunner;
import in.bbat.configuration.BBATProperties;
import in.bbat.logger.BBATLogger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.android.chimpchat.core.IChimpDevice;
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

	private static final Logger LOG = BBATLogger.getLogger(TestDevice.class.getName());
	public static final int UIAUTOMATOR_MIN_API_LEVEL = 16;
	public static final String SCREENSHOT_DIR = "/data/local/tmp/";
	private boolean active;
	private IDevice monkeyDevice;

	private ILogListener listener;

	private LogCatReceiverTask logReciever;

	private IChimpDevice chimpDevice;

	private IScreenShotListener screenShotListener;

	private IMemoryUsageListener memoryListener;

	private ICpuUsageListener cpuListener;
	private CpuUsageThread cpuUsageThread;
	private MemoryUsageThread memoryUsageThread;

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
			LOG.error(e);
		} catch (AdbCommandRejectedException e) {
			LOG.error(e);
		} catch (ShellCommandUnresponsiveException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
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
			monkeyDevice.pushFile(jar.getJarPath(),UiAutoTestCaseJar.UIAUTOMATOR_JAR_PATH);
		} catch (SyncException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		} catch (AdbCommandRejectedException e) {
			LOG.error(e);
		} catch (TimeoutException e) {
			LOG.error(e);
		}
	}

	@Override
	public void executeTestCase(String testCaseName,IUiAutomatorListener uiAutoListener,ITestRunListener... listener) {

		//		ScreenShotThread screenShotThread = new ScreenShotThread();

		startCpuUsageThread();
		startMemoryUsageThread();
		UIAutomatorRunner runner = new UIAutomatorRunner(testCaseName, monkeyDevice,uiAutoListener);
		try {
			//			screenShotThread.start();
			runner.run(listener);
		} catch (TimeoutException e) {
			LOG.error(e);
		} catch (AdbCommandRejectedException e) {
			LOG.error(e);
		} catch (ShellCommandUnresponsiveException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
		//		screenShotThread.stopThread();

		stopCpuUsageThread();
		stopMemoryUsageThread();
	}

	class ScreenShotThread extends Thread
	{
		boolean stop = true;
		public void stopThread(){
			stop = false;
		}
		@Override
		public void run() {
			while(stop){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				RawImage image = getScreenshot();
				if(screenShotListener !=null)
					screenShotListener.processScreenshot(image);

			}
		}

	}
	@Override
	public String getName() {
		return monkeyDevice.getName();
	}

	@Override
	public RawImage getScreenshot() {
		try {
			return monkeyDevice.getScreenshot();
		} catch (TimeoutException e) {
			LOG.error(e);
		} catch (AdbCommandRejectedException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
		return null;
	}

	@Override
	public IDevice getMonkeyDevice() {
		return monkeyDevice;
	}

	@Override
	public String getSerialNo() {
		return monkeyDevice.getSerialNumber();
	}

	@Override
	public int getApiLevel() {
		String apiLevelString = getMonkeyDevice().getProperty("ro.build.version.sdk");

		int apiLevel;
		try {
			apiLevel = Integer.parseInt(apiLevelString);
		} catch (NumberFormatException e) {
			apiLevel = UIAUTOMATOR_MIN_API_LEVEL;
		}
		return apiLevel;
	}

	@Override
	public boolean isUIAutomatorSupported() {
		return getApiLevel() >= UIAUTOMATOR_MIN_API_LEVEL;
	}

	public Map<String, String> getProperties(){
		return getMonkeyDevice().getProperties();
	}

	public IChimpDevice getChimpDevice() {
		return chimpDevice;
	}

	@Override
	public void installPackage(String apkPath) throws Exception {
		monkeyDevice.installPackage(apkPath, true);
	}

	public IScreenShotListener getScreenShotListener() {
		return screenShotListener;
	}

	public void setScreenShotListener(IScreenShotListener screenShotListener) {
		this.screenShotListener = screenShotListener;
	}

	@Override
	public String getPropertyValue(String property) {
		return getMonkeyDevice().getProperty(property);
	}

	@Override
	public void removeUIAutomatorJar() {
		final String cmd ="rm -r "+UiAutoTestCaseJar.UIAUTOMATOR_JAR_PATH;
		try {
			monkeyDevice.executeShellCommand(cmd, new NullOutputReceiver(),0);
		} catch (TimeoutException e) {
			LOG.error(e);
		} catch (AdbCommandRejectedException e) {
			LOG.error(e);
		} catch (ShellCommandUnresponsiveException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
	}

	@Override
	public void startMemoryUsageThread() {
		memoryUsageThread = new MemoryUsageThread(this, memoryListener);
		Thread t = new Thread(memoryUsageThread);
		t.start();
	}

	@Override
	public void startCpuUsageThread() {

		cpuUsageThread = new CpuUsageThread(this, cpuListener);
		Thread t = new Thread(cpuUsageThread);
		t.start();
	}


	public void stopCpuUsageThread(){
		if(cpuUsageThread!=null)
			cpuUsageThread.stop();
	}

	public void stopMemoryUsageThread(){
		if(memoryUsageThread!=null)
			memoryUsageThread.stop();
	}

	public IMemoryUsageListener getMemoryListener() {
		return memoryListener;
	}

	@Override
	public void setMemoryListener(IMemoryUsageListener memoryListener) {
		this.memoryListener = memoryListener;
	}

	public ICpuUsageListener getCpuListener() {
		return cpuListener;
	}

	@Override
	public void setCpuListener(ICpuUsageListener cpuListener) {
		this.cpuListener = cpuListener;
	}

	@Override
	public void pullScreenShots(String destinationDir, String sourceDirInDevice , boolean delete) {

		try {
			Process exec = Runtime.getRuntime().exec(BBATProperties.getInstance().getAndroid_AdbPath()+" pull "+ SCREENSHOT_DIR+"/"+sourceDirInDevice + " "+ destinationDir);
			exec.waitFor();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(delete)
			deleteFolder(SCREENSHOT_DIR+"/"+sourceDirInDevice);
	}

	public void deleteFolder(String sourceDirectory) {
		final String cmd ="rm -Rf "+sourceDirectory;
		try {
			monkeyDevice.executeShellCommand(cmd, new NullOutputReceiver(),0);
		} catch (TimeoutException e) {
			LOG.error(e);
		} catch (AdbCommandRejectedException e) {
			LOG.error(e);
		} catch (ShellCommandUnresponsiveException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
	}
}