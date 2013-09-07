package in.BBAT.data.model.Entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;

/**
 * 
 * @author syed Mehtab
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="TestDeviceEntity.findAll",query="SELECT testcase FROM TestDeviceEntity testcase ORDER BY testcase.id")
})
public class TestDeviceEntity extends AbstractEntity {
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
