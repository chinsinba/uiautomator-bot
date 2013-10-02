package in.BBAT.data.model.Entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.TableGenerator;

/**
 * 
 * @author Syed Mehtab
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="TestRunInfoEntity.findAll",query="SELECT testcase FROM TestRunInfoEntity testcase ORDER BY testcase.id")
})
public class TestRunInfoEntity extends AbstractEntity {
	
	public TestRunInfoEntity(TestCaseEntity testCase,String verdict) {
		this.testCase = testCase;
		this.verdict = verdict;
	}
	
	public TestRunInfoEntity() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@TableGenerator(name = "TestRunInfo", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "TestRunInfo")
	private int id;

	@OneToOne
	private TestCaseEntity testCase;
	
	
	private String verdict;
	
	private Timestamp startTime;
	
	private Timestamp endTime;
	
	@ManyToOne
	@OrderColumn
	private TestRunEntity testRun;
	
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public TestRunEntity getTestRun() {
		return testRun;
	}

	public void setTestRun(TestRunEntity testRun) {
		this.testRun = testRun;
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public AbstractEntity getParent() {
		return getTestRun();
	}

	public TestCaseEntity getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCaseEntity testCase) {
		this.testCase = testCase;
	}

	public String getVerdict() {
		return verdict;
	}

	public void setVerdict(String verdict) {
		this.verdict = verdict;
	}
}
