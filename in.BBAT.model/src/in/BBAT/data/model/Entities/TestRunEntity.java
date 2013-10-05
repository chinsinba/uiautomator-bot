package in.BBAT.data.model.Entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
	@NamedQuery(name="TestRunEntity.findAll",query="SELECT testcase FROM TestRunEntity testcase ORDER BY testcase.id")
})
public class TestRunEntity extends AbstractEntity{


	@Id
	@TableGenerator(name = "TestRun_GEN", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "TestRun_GEN")
	private int id;

	private String name;

	private String description;

	private String verdict;

	@OneToOne
	private UserEntity createdBy;

	@OneToMany(mappedBy="testRun", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	@OrderColumn
	private List<TestDeviceRunEntity> testDeviceRuns;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private Timestamp startTime;

	private Timestamp endtiTime;

	@Override
	public List<? extends AbstractEntity> getChildren() {
		return getTestDeviceRuns();
	}

	@Override
	public void addChild(IBBATEntity childEntity) {
		addTestRunInfo((TestDeviceRunEntity) childEntity);
	}

	@Override
	public void removeChild(IBBATEntity childEntity) {
		removeTestRunInfo((TestRunInfoEntity) childEntity);
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndtiTime() {
		return endtiTime;
	}

	public void setEndtiTime(Timestamp endtiTime) {
		this.endtiTime = endtiTime;
	}



	public UserEntity getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserEntity createdBy) {
		this.createdBy = createdBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVerdict() {
		return verdict;
	}

	public void setVerdict(String verdict) {
		this.verdict = verdict;
	}


	public void addTestRunInfo(TestDeviceRunEntity entity)
	{
		this.getTestDeviceRuns().add(entity);
	}

	public void removeTestRunInfo(TestRunInfoEntity entity)
	{
		this.getTestDeviceRuns().remove(entity);
	}

	public List<TestDeviceRunEntity> getTestDeviceRuns() {
		return testDeviceRuns;
	}

	public void setTestDeviceRuns(List<TestDeviceRunEntity> testDeviceRuns) {
		this.testDeviceRuns = testDeviceRuns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
