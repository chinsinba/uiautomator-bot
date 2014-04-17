package in.BBAT.presenter.internal;

public interface IDeviceRunExecutionlistener {

	void deviceRunExecutionStarted(DeviceTestRun deviceRun);
	void deviceRunExecutionCompleted(DeviceTestRun deviceRun);
}
