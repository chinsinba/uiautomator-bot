package in.BBAT.data.model.Entities;



import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.TableGenerator;
import javax.persistence.Version;


@Entity
@NamedQueries({
	@NamedQuery(name="TestCaseEntity.findAll",query="SELECT testcase FROM TestCaseEntity testcase ORDER BY testcase.id")
})
public class TestCaseEntity extends AbstractEntity {

	@Id
	@TableGenerator(name = "Case_GEN", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "Case_GEN")
	private int id;

	private String name;

	private String description;
	
	@Version
	private Timestamp lastModified;

	private Timestamp createdOn;

	@OneToOne
	private UserEntity createdBy;

	@ManyToMany
	@JoinColumn(name = "suiteID")
	@OrderColumn
	private List<TestSuiteEntity> suite;

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

	public List<TestSuiteEntity> getSuite() {
		return suite;
	}

	public void setSuite(List<TestSuiteEntity> suite) {
		this.suite = suite;
	}

	@Override
	public List<AbstractEntity> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractEntity getParent() {
		// TODO Auto-generated method stub
		return null;
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

}
