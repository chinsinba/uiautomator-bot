package in.bbat.data.model.Entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class TestRunInfoEntity implements IBBATEntity {
	@Id
	@TableGenerator(name = "TestRunInfo", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "TestRunInfo")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
}
