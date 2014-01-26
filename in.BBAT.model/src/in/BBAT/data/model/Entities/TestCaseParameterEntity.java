package in.BBAT.data.model.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "TestCaseParameterEntity")
public class TestCaseParameterEntity extends AbstractEntity {

	@Id
	@TableGenerator(name = "param_GEN", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "param_GEN")
	@XmlTransient
	private int id;
	
	private String name;
	
	private String value;
	
	@ManyToOne
	private TestCaseEntity cases;
	
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TestCaseEntity getCases() {
		return cases;
	}

	public void setCases(TestCaseEntity cases) {
		this.cases = cases;
	}

}
