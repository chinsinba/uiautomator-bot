package in.BBAT.TestRunner.device;

import java.util.Map;

import in.BBAT.testRunner.runner.IUiAutomatorListener;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;

import com.android.chimpchat.core.IChimpDevice;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.testrunner.ITestRunListener;


public interface IAndroidDevice {

	String getModelName();

	String getDeviceId();

	String getStatus();

	void setLogListener(ILogListener listener);

	void startLogging();

	void stopLogging();

	void activate() ;

	void deActivate() ;

	void pushTestJar(UiAutoTestCaseJar jar);

	void executeTestCase(String testCaseName,IUiAutomatorListener uiAutoListener,ITestRunListener... listener);

	String getName();

	RawImage getScreenshot();
	
	IDevice getMonkeyDevice();

	String getSerialNo();

	int getApiLevel();
	
	boolean isUIAutomatorSupported();

	Map<String, String> getProperties();
	
	String getPropertyValue(String property);
	
	IChimpDevice getChimpDevice();

	/**
	 * 
	 * @param apkPath
	 */
	void installPackage(String apkPath)throws Exception;

	void setScreenShotListener(IScreenShotListener listener);
	
	IScreenShotListener getScreenShotListener();
	
	public void removeUIAutomatorJar();

}
