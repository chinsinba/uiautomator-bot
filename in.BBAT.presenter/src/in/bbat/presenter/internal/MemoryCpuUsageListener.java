package in.bbat.presenter.internal;

import in.BBAT.TestRunner.device.ICpuUsageListener;
import in.BBAT.TestRunner.device.IMemoryUsageListener;
import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;

public class MemoryCpuUsageListener implements IMemoryUsageListener,ICpuUsageListener {

	private TestRunInstanceModel model;
	private AndroidDevice device ;


	public MemoryCpuUsageListener(TestRunInstanceModel model, AndroidDevice device ) {
		this.model=model;
		this.device=device;
	}

	@Override
	public void cpuUsage(int percentUsage, long time) {

	}

	@Override
	public void memoryUsage(int percentUsage, long time) {

	}

}
