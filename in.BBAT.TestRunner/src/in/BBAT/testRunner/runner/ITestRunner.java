package in.BBAT.testRunner.runner;

import in.BBAT.TestRunner.Listener.ICpuUsageListener;
import in.BBAT.TestRunner.Listener.ILogListener;
import in.BBAT.TestRunner.Listener.IMemoryUsageListener;
import in.BBAT.TestRunner.Listener.IScreenShotListener;

import com.android.ddmlib.testrunner.ITestRunListener;

public interface ITestRunner {

	
	void execute(String testCaseClassName,ITestRunListener listener, ILogListener logListener,IUiAutomatorListener autoListener,IScreenShotListener shotListener,IMemoryUsageListener memListner,ICpuUsageListener cpuListener);

	void abort();

}
