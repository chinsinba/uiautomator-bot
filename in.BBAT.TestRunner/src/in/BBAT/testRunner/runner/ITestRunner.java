package in.BBAT.testRunner.runner;

import in.BBAT.TestRunner.device.ICpuUsageListener;
import in.BBAT.TestRunner.device.ILogListener;
import in.BBAT.TestRunner.device.IMemoryUsageListener;
import in.BBAT.TestRunner.device.IScreenShotListener;

import com.android.ddmlib.testrunner.ITestRunListener;

public interface ITestRunner {

	
	void execute(String testCaseClassName,ITestRunListener listener, ILogListener logListener,IUiAutomatorListener autoListener,IScreenShotListener shotListener,IMemoryUsageListener memListner,ICpuUsageListener cpuListener);

	void abort();

}
