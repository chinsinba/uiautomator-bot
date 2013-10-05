package in.BBAT.abstrakt.presenter.run.model;

import in.BBAT.data.model.Entities.TestDeviceLogEntity;
import in.BBAT.data.model.Entities.TestRunInfoEntity;

import com.android.ddmlib.logcat.LogCatMessage;

public class DeviceLog {

	private  String mLogLevel;
	private  String mPid;
	private  String mTid;
	private  String mAppName;
	private  String mTag;
	private  String mTime;
	private  String mMessage;

	private TestDeviceLogEntity deviceLog ;

	public DeviceLog(TestDeviceLogEntity log) {
		deviceLog =log;
	}

	public DeviceLog(TestRunInstanceModel runInfo,LogCatMessage message) {
		deviceLog = new TestDeviceLogEntity((TestRunInfoEntity) runInfo.getEntity());
		deviceLog.setmLogLevel(message.getLogLevel().getStringValue());
		deviceLog.setmPid(message.getPid());
		deviceLog.setmTid(message.getTid());
		deviceLog.setmAppName(message.getAppName());
		deviceLog.setmTag(message.getTag());
		deviceLog.setmTime(message.getTime());
		deviceLog.setmMessage(message.getMessage());
	}

	public TestDeviceLogEntity getDeviceLog() {
		return deviceLog;
	}

	public void setDeviceLog(TestDeviceLogEntity deviceLog) {
		this.deviceLog = deviceLog;
	}

	public void save(){
		deviceLog.save();
	}

}
