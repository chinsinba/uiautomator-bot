package in.bbat.data.model.Entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

}
