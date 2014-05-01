package in.BBAT.data.model.Entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@Entity
@NamedQueries({
	@NamedQuery(name="TestSuiteEntity.findAll",query="SELECT testcase FROM TestSuiteEntity testcase ORDER BY testcase.id")
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"testCases"
})
@XmlRootElement(name = "TestSuiteEntity")
public class TestSuiteEntity extends AbstractEntity  {

	@Id
	@TableGenerator(name = "Suite_GEN", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "Suite_GEN")
	@XmlTransient
	private int id;

	@XmlAttribute(required = true)
	private String name;

	@XmlAttribute(required = true)
	private String description;

	@OneToOne
	@XmlTransient
	private UserEntity createdBy;

	@XmlTransient
	@Version
	private Timestamp lastModified;

	@XmlTransient
	private Timestamp createdOn;

	@ManyToOne
	@XmlTransient
	private TestProjectEntity testProject;

	@XmlElement(name="TestCaseEntity", required= true)
	@OneToMany(mappedBy="suite", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@OrderColumn
	private List<TestCaseEntity> testCases;

	@XmlAttribute(required = true)
	private boolean library;

	public TestSuiteEntity(TestProjectEntity testProject, String name, boolean isHelper) {

		this.name = name;
		this.testProject = testProject;
		this.library = isHelper;
	}

	public TestSuiteEntity() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public List<? extends AbstractEntity> getChildren() {
		return getTestCases();
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

	public List<TestCaseEntity> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<TestCaseEntity> testCases) {
		this.testCases = testCases;
	}

	public void addTestCase(TestCaseEntity testCase)
	{
		this.testCases.add(testCase);
	}

	public void removeTestCase(TestCaseEntity testCase)
	{
		this.testCases.remove(testCase);
	}

	public TestProjectEntity getTestProject() {
		return testProject;
	}

	public void setTestProject(TestProjectEntity testProject) {
		this.testProject = testProject;
	}

	public boolean isLibrary() {
		return library;
	}

	public void setLibrary(boolean library) {
		this.library = library;
	}

}
