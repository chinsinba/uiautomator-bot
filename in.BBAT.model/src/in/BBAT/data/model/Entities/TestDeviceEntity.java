package in.BBAT.data.model.Entities;

import java.util.List;

import in.BBAT.dataMine.manager.MineManagerHelper;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.TableGenerator;

/**
 * 
 * @author syed Mehtab
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="TestDeviceEntity.findAll",query="SELECT device FROM TestDeviceEntity device ORDER BY device.id"),
	@NamedQuery(name="TestDeviceEntity.findByID",query="SELECT device FROM TestDeviceEntity device WHERE device.deviceId = ?1")
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

	private String apiLevel;

	private String modelName;
	
	private String manufacturer;

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

	public String getApiLevel() {
		return apiLevel;
	}

	public void setApiLevel(String apiLevel) {
		this.apiLevel = apiLevel;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}
