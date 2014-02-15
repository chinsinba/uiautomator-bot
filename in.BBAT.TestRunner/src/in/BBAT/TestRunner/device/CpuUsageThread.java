package in.BBAT.TestRunner.device;

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

}
