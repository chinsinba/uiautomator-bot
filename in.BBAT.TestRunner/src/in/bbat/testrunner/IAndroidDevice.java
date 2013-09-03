package in.bbat.testrunner;

import in.BBAT.testRunner.runner.ILogListener;

public interface IAndroidDevice {

	public String getModelName();

	public String getDeviceId();

	public String getStatus();

	public void addLogListener(ILogListener listener);
	
	public void startLogging();
	public void activate() ;
	public void deActivate() ;

}
