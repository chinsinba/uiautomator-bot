package in.BBAT.TestRunner.device;

import in.BBAT.TestRunner.Listener.IMemoryUsageListener;

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

	public void stop(){

	}
}
