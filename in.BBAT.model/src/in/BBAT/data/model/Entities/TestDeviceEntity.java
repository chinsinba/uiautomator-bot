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
	
	private String apiLevel;

	private String modelName;

	private String manufacturer;

	private String build_board;

	private String build_brand;
	private String build_cpu_abi;
	private String build_cpu_abi2;
	private String build_device;
	private String build_display;
	private String build_fingerprint;
	private String build_hardware;
	private String build_host;
	private String build_id;
	private String build_manufacturer;
	private String build_model;
	private String build_product;
	private String build_radio;
	private String build_user;
	private String build_tags;
	private String build_type;
	private String version_codename;
	private String version_incremental;
	private String version_release;
	private String version_sdk;

	private String deviceId;

	private String buildId;

	private String osName;

	public String getBuild_board() {
		return build_board;
	}

	public void setBuild_board(String build_board) {
		this.build_board = build_board;
	}

	public String getBuild_brand() {
		return build_brand;
	}

	public void setBuild_brand(String build_brand) {
		this.build_brand = build_brand;
	}

	public String getBuild_cpu_abi() {
		return build_cpu_abi;
	}

	public void setBuild_cpu_abi(String build_cpu_abi) {
		this.build_cpu_abi = build_cpu_abi;
	}

	public String getBuild_cpu_abi2() {
		return build_cpu_abi2;
	}

	public void setBuild_cpu_abi2(String build_cpu_abi2) {
		this.build_cpu_abi2 = build_cpu_abi2;
	}

	public String getBuild_device() {
		return build_device;
	}

	public void setBuild_device(String build_device) {
		this.build_device = build_device;
	}

	public String getBuild_display() {
		return build_display;
	}

	public void setBuild_display(String build_display) {
		this.build_display = build_display;
	}

	public String getBuild_fingerprint() {
		return build_fingerprint;
	}

	public void setBuild_fingerprint(String build_fingerprint) {
		this.build_fingerprint = build_fingerprint;
	}

	public String getBuild_hardware() {
		return build_hardware;
	}

	public void setBuild_hardware(String build_hardware) {
		this.build_hardware = build_hardware;
	}

	public String getBuild_host() {
		return build_host;
	}

	public void setBuild_host(String build_host) {
		this.build_host = build_host;
	}

	public String getBuild_id() {
		return build_id;
	}

	public void setBuild_id(String build_id) {
		this.build_id = build_id;
	}

	public String getBuild_manufacturer() {
		return build_manufacturer;
	}

	public void setBuild_manufacturer(String build_manufacturer) {
		this.build_manufacturer = build_manufacturer;
	}

	public String getBuild_model() {
		return build_model;
	}

	public void setBuild_model(String build_model) {
		this.build_model = build_model;
	}

	public String getBuild_product() {
		return build_product;
	}

	public void setBuild_product(String build_product) {
		this.build_product = build_product;
	}

	public String getBuild_radio() {
		return build_radio;
	}

	public void setBuild_radio(String build_radio) {
		this.build_radio = build_radio;
	}

	public String getBuild_user() {
		return build_user;
	}

	public void setBuild_user(String build_user) {
		this.build_user = build_user;
	}

	public String getBuild_tags() {
		return build_tags;
	}

	public void setBuild_tags(String build_tags) {
		this.build_tags = build_tags;
	}

	public String getBuild_type() {
		return build_type;
	}

	public void setBuild_type(String build_type) {
		this.build_type = build_type;
	}

	public String getVersion_codename() {
		return version_codename;
	}

	public void setVersion_codename(String version_codename) {
		this.version_codename = version_codename;
	}

	public String getVersion_incremental() {
		return version_incremental;
	}

	public void setVersion_incremental(String version_incremental) {
		this.version_incremental = version_incremental;
	}

	public String getVersion_release() {
		return version_release;
	}

	public void setVersion_release(String version_release) {
		this.version_release = version_release;
	}

	public String getVersion_sdk() {
		return version_sdk;
	}

	public void setVersion_sdk(String version_sdk) {
		this.version_sdk = version_sdk;
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
