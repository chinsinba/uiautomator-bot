package in.BBAT.testRunner.runner;

import in.BBAT.TestRunner.device.ILogListener;

import com.android.ddmlib.testrunner.ITestRunListener;

public interface ITestRunner {

	
	void execute(String testCaseClassName,ITestRunListener listener, ILogListener logListener,IUiAutomatorListener autoListener);

	void abort();
	
}
