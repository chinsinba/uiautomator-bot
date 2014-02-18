package in.BBAT.TestRunner.Listener;

public interface IMemoryUsageListener {
	void memoryUsage(int percentUsage,long time);
	/**
	 * 
	 * @return Apk package name for which the memory usage has to be tracked
	 */
	String getPackageName();
}
