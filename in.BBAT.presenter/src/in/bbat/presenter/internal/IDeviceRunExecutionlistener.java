package in.bbat.presenter.internal;

public interface IDeviceRunExecutionlistener {

	void deviceRunExecutionStarted(DeviceTestRun deviceRun);
	void deviceRunExecutionCompleted(DeviceTestRun deviceRun);
}
