package in.BBAT.data.model.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class TestDeviceLogEntity  extends AbstractEntity{

	@Id
	@TableGenerator(name = "DeviceLog", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "DeviceLog")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TestDeviceLogEntity() {
		// TODO Auto-generated constructor stub
	}

	public TestDeviceLogEntity( TestRunInfoEntity testRunInfo) {
		this.testRunInfo =testRunInfo;
	}

	private  String mLogLevel;
	private  String mPid;
	private  String mTid;
	private  String mAppName;
	private  String mTag;
	private  String mTime;
	@Lob
	private  String mMessage;

	@ManyToOne
	private TestRunInfoEntity testRunInfo;

	public String getmLogLevel() {
		return mLogLevel;
	}

	public String getmPid() {
		return mPid;
	}

	public String getmTid() {
		return mTid;
	}

	public String getmAppName() {
		return mAppName;
	}

	public String getmTag() {
		return mTag;
	}

	public String getmTime() {
		return mTime;
	}

	public String getmMessage() {
		return mMessage;
	}

	public TestRunInfoEntity getTestRunInfo() {
		return testRunInfo;
	}

	public void setTestRunInfo(TestRunInfoEntity testRunInfo) {
		this.testRunInfo = testRunInfo;
	}
	public void setmLogLevel(String mLogLevel) {
		this.mLogLevel = mLogLevel;
	}

	public void setmPid(String mPid) {
		this.mPid = mPid;
	}

	public void setmTid(String mTid) {
		this.mTid = mTid;
	}

	public void setmAppName(String mAppName) {
		this.mAppName = mAppName;
	}

	public void setmTag(String mTag) {
		this.mTag = mTag;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public void setmMessage(String mMessage) {
		this.mMessage = mMessage;
	}


}
