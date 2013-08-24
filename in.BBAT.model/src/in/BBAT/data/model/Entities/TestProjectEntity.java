package in.BBAT.data.model.Entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

@Entity
public class TestProjectEntity extends AbstractEntity {

	@Id
	@TableGenerator(name = "TestProject", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "TestProject")
	private int id;

	private String description;
	
	@OneToOne
	private UserEntity createdBy;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
