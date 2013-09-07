package in.BBAT.TestRunner.device;


public interface IAndroidDevice {

	public String getModelName();

	public String getDeviceId();

	public String getStatus();

	public void addLogListener(ILogListener listener);
	
	public void startLogging();
	public void stopLogging();
	public void activate() ;
	public void deActivate() ;

}
