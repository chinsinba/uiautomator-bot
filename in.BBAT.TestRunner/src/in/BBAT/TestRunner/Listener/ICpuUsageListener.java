package in.BBAT.TestRunner.Listener;

public interface ICpuUsageListener {

	void cpuUsage(int percentUsage,long time);
	
	/**
	 * 
	 * @return Apk package name for which the CPU usage has to be tracked
	 */
	String getPackageName();
}
