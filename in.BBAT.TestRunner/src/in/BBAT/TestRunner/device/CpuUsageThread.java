package in.BBAT.TestRunner.device;

import in.BBAT.TestRunner.Listener.ICpuUsageListener;

public class CpuUsageThread implements Runnable {

	
	private TestDevice device;
	private ICpuUsageListener listener;

	public CpuUsageThread(TestDevice device, ICpuUsageListener listener) {
		this.device = device;
		this.listener = listener;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public void stop(){
		
	}

}
