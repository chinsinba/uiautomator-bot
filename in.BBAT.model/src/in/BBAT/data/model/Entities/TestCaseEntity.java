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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@Entity
@NamedQueries({
	@NamedQuery(name="TestCaseEntity.findAll",query="SELECT testcase FROM TestCaseEntity testcase ORDER BY testcase.id")
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "TestCaseEntity")
public class TestCaseEntity extends AbstractEntity {

	@Id
	@TableGenerator(name = "Case_GEN", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "Case_GEN")
	@XmlTransient
	private int id;

	@XmlAttribute(required = true)
	private String name;

	@XmlAttribute(required = true)
	private String description;

	@XmlTransient
	@Version
	private Timestamp lastModified;

	@XmlTransient
	private Timestamp createdOn;

	@ManyToOne
	@XmlTransient
	private TestSuiteEntity suite;

	@OneToOne
	@XmlTransient
	private UserEntity createdBy;


	@OneToMany(mappedBy="cases", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@OrderColumn
	private List<TestCaseParameterEntity> testCaseParams;
	
	public TestCaseEntity(TestSuiteEntity testSuiteEntity, String testCaseName) {
		this.name = testCaseName;
		this.suite = testSuiteEntity;
	}

	TestCaseEntity(){

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
	public AbstractEntity getParent() {
		return getSuite();
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

	public TestSuiteEntity getSuite() {
		return suite;
	}

	public void setSuite(TestSuiteEntity suite) {
		this.suite = suite;
	}

	public List<TestCaseParameterEntity> getTestCaseParams() {
		return testCaseParams;
	}

	public void setTestCaseParams(List<TestCaseParameterEntity> testCaseParams) {
		this.testCaseParams = testCaseParams;
	}
}
