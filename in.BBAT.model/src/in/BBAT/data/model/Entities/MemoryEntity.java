package in.BBAT.data.model.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class MemoryEntity extends AbstractEntity{

	@Id
	@TableGenerator(name = "mem_GEN", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "mem_GEN")
	private int id;
	
	private int percent;
	
	private long time;
	
	@ManyToOne
	private TestRunInfoEntity testRunInfo;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TestRunInfoEntity getTestRunInfo() {
		return testRunInfo;
	}

	public void setTestRunInfo(TestRunInfoEntity testRunInfo) {
		this.testRunInfo = testRunInfo;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
