package in.BBAT.data.model.Entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class RoleEntity extends AbstractEntity {

	@Id
	@TableGenerator(name = "Role_GEN", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "Role_GEN")
	private int id;
	
	private String roleName;
	
	private String description;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
