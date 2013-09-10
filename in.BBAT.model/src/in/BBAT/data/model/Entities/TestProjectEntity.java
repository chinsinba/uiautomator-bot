package in.BBAT.data.model.Entities;

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
import javax.persistence.TableGenerator;

@Entity
@NamedQueries({
	@NamedQuery(name="TestProjectEntity.findAll",query="SELECT testProj FROM TestProjectEntity testProj ORDER BY testProj.id")
	//@NamedQuery(name="TestProjectEntity.findTestCase",query="SELECT testcase FROM TestProjectEntity testcase ORDER BY testcase.id")
})
public class TestProjectEntity extends AbstractEntity {

	@Id
	@TableGenerator(name = "TestProject", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "TestProject")
	private int id;

	private String description;

	@OneToOne
	private UserEntity createdBy;
	
	private String name;

	@OneToMany(mappedBy="testProject",fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<TestCaseEntity> testcases;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public List<? extends AbstractEntity> getChildren() {

		return getTestcases();
	}


	@Override
	public AbstractEntity getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChild(IBBATEntity childEntity) {
		addTestCase((TestCaseEntity) childEntity);
	}

	@Override
	public void removeChild(IBBATEntity childEntity) {
		removeTestCase((TestCaseEntity) childEntity);
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

	public List<TestCaseEntity> getTestcases() {
		return testcases;
	}

	public void setTestcases(List<TestCaseEntity> testcases) {
		this.testcases = testcases;
	}

	public void addTestCase(TestCaseEntity testCase){
		this.testcases.add(testCase);
	}

	public void removeTestCase(TestCaseEntity testCase){
		this.testcases.remove(testCase);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
