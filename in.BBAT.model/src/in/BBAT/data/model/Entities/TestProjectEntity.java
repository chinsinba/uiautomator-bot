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
import javax.persistence.OrderColumn;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@Entity
@NamedQueries({
	@NamedQuery(name="TestProjectEntity.findAll",query="SELECT testProj FROM TestProjectEntity testProj ORDER BY testProj.id")
	//@NamedQuery(name="TestProjectEntity.findTestCase",query="SELECT testcase FROM TestProjectEntity testcase ORDER BY testcase.id")
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"testSuites"
})
@XmlRootElement(name = "TestProjectEntity")
public class TestProjectEntity extends AbstractEntity {

	@Id
	@TableGenerator(name = "TestProject", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "TestProject")
	@XmlTransient
	private int id;

	@XmlAttribute(required = true)
	private String description;

	@OneToOne
	@XmlTransient
	private UserEntity createdBy;

	@XmlAttribute(required = true)
	private String name;

	@XmlAttribute(required = true)
	private int apiLevel;
	
	@XmlElement(name="TestSuiteEntity",required=true)
	@OneToMany(mappedBy="testProject",fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@OrderColumn
	private List<TestSuiteEntity> testSuites;

	public TestProjectEntity(String projectName,int apiLevel) {
		this.name = projectName;
		this.apiLevel = apiLevel;
	}

	public TestProjectEntity() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public List<? extends AbstractEntity> getChildren() {

		return getTestSuites();
	}


	@Override
	public AbstractEntity getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChild(IBBATEntity childEntity) {
		addTestSuite((TestSuiteEntity) childEntity);
	}

	@Override
	public void removeChild(IBBATEntity childEntity) {
		removeTestSuite((TestSuiteEntity) childEntity);
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

	public List<TestSuiteEntity> getTestSuites() {
		return testSuites;
	}

	public void setTestSuites(List<TestSuiteEntity> testSuites) {
		this.testSuites = testSuites;
	}

	public void addTestSuite(TestSuiteEntity testSuite){
		this.testSuites.add(testSuite);
	}

	public void removeTestSuite(TestSuiteEntity testSuite){
		this.testSuites.remove(testSuite);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getApiLevel() {
		return apiLevel;
	}

	public void setApiLevel(int apiLevel) {
		this.apiLevel = apiLevel;
	}
}
