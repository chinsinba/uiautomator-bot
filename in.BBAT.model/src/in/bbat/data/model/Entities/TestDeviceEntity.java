package in.bbat.data.model.Entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class TestDeviceEntity implements IBBATEntity {
	@Id
	@TableGenerator(name = "Dev_GEN", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize=1)
	@GeneratedValue(generator = "Dev_GEN")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String deviceId;

	private String buildId;

	private String osName;


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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

}
