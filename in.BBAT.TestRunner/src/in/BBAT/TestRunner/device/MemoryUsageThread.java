package in.BBAT.TestRunner.device;

public class MemoryUsageThread implements Runnable {

	private TestDevice device;
	private IMemoryUsageListener listener;

	public MemoryUsageThread(TestDevice device, IMemoryUsageListener listener) {
		this.device = device;
		this.listener = listener;
	}

	@Override
	public void run() {

	}

}
