package in.BBAT.data.model.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class AutomatorLogEntity {

	@Id
	@TableGenerator(name = "AutomatorLog", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "AutomatorLog")
	private int id;

	@Lob
	private String message;

	@ManyToOne
	private TestRunInfoEntity testRunInfo;


	public AutomatorLogEntity() {
	}

	public AutomatorLogEntity(TestRunInfoEntity runEntity, String message) {
		this.message = message;
		this.testRunInfo = runEntity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TestRunInfoEntity getTestRunInfo() {
		return testRunInfo;
	}

	public void setTestRunInfo(TestRunInfoEntity testRunInfo) {
		this.testRunInfo = testRunInfo;
	}
}
