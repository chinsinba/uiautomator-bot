package in.BBAT.data.model.Entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class TestRunEntity implements IBBATEntity {

	
	@Id
	@TableGenerator(name = "TestRun_GEN", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "TestRun_GEN")
	private int id;

	private String description;
	
	private String verdict;
	
	@OneToOne
	private UserEntity createdBy;
	
	@OneToMany(mappedBy="testRun", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	@OrderColumn
	private List<TestRunInfoEntity> testRunInfoList;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	private Timestamp startTime;
	
	private Timestamp endtiTime;
	
	@Override
	public List<IBBATEntity> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public IBBATEntity getParent() {
		// TODO Auto-generated method stub
		return null;
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

	public List<TestRunInfoEntity> getTestRunInfoList() {
		return testRunInfoList;
	}

	public void setTestRunInfoList(List<TestRunInfoEntity> testRunInfoList) {
		this.testRunInfoList = testRunInfoList;
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

}
