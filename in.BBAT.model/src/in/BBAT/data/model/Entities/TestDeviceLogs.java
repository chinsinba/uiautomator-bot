package in.BBAT.data.model.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class TestDeviceLogs  extends AbstractEntity{
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

	private  String mLogLevel;
	private  String mPid;
	private  String mTid;
	private  String mAppName;
	private  String mTag;
	private  String mTime;
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

}
