package in.BBAT.TestRunner.Listener;

import java.util.Map;

import in.BBAT.testRunner.runner.IUiAutomatorListener;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;

import com.android.chimpchat.core.IChimpDevice;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.testrunner.ITestRunListener;

/**
 */
public interface IAndroidDevice {

	/**
	 * 
	 * @return
	 */
	String getModelName();

	/**
	 * 
	 * @return
	 */
	String getDeviceId();

	/**
	 * 
	 * @return
	 */
	String getStatus();

	/**
	 * 
	 * @param listener
	 */
	void setLogListener(ILogListener listener);

	/**
	 * 
	 */
	void startLogging();

	/**
	 * 
	 */
	void stopLogging();

	/**
	 * 
	 */
	void activate() ;

	/**
	 * 
	 */
	void deActivate() ;

	/**
	 * 
	 * @param jar
	 */
	void pushTestJar(UiAutoTestCaseJar jar);

	/**
	 * 
	 * @param testCaseName
	 * @param uiAutoListener
	 * @param memoryListener
	 * @param cpuListener
	 * @param listener
	 */
	void executeTestCase(String testCaseName,IUiAutomatorListener uiAutoListener,IMemoryUsageListener memoryListener,ICpuUsageListener cpuListener,ITestRunListener... listener);

	/**
	 * returns device name
	 * @return
	 */
	String getName();

	/**
	 * 
	 * @return screenshot
	 */
	RawImage getScreenshot();

	/**
	 * 
	 * @return
	 */
	IDevice getMonkeyDevice();

	/**
	 * 
	 * @return
	 */
	String getSerialNo();
	/**
	 * 
	 * @return
	 */
	int getApiLevel();

	/**
	 * 
	 * @return
	 */
	boolean isUIAutomatorSupported();

	/**
	 * 
	 * @return
	 */
	Map<String, String> getProperties();

	/**
	 * 
	 * @param property
	 * @return
	 */
	String getPropertyValue(String property);

	/**
	 * 
	 * @return
	 */
	IChimpDevice getChimpDevice();

	/**
	 * 
	 * @param apkPath
	 */
	void installPackage(String apkPath)throws Exception;

	/**
	 * 
	 * @param listener
	 */
	void setScreenShotListener(IScreenShotListener listener);

	/**
	 * 
	 * @return
	 */
	IScreenShotListener getScreenShotListener();

	/**
	 * 
	 */
	public void removeUIAutomatorJar();

	/**
	 * 
	 */
	public void startMemoryUsageThread();

	/**
	 * 
	 */
	public void startCpuUsageThread();
}
