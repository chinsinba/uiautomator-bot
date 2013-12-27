package in.BBAT.abstrakt.presenter.device.model;

import in.BBAT.TestRunner.device.IAndroidDevice;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.data.model.Entities.TestDeviceEntity;
import in.BBAT.dataMine.manager.DeviceMineManager;
import in.bbat.abstrakt.gui.BBATImageManager;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Image;

import com.android.ddmlib.RawImage;

public class AndroidDevice implements IGUITreeNode{

	private IAndroidDevice iDevice;
	private boolean isAddedToRun =false;

	private TestDeviceEntity deviceEntity;

	public AndroidDevice(IAndroidDevice dev){
		this.setiDevice(dev);
		initializeEntity(dev);
	}

	private void initializeEntity(IAndroidDevice dev) {
		TestDeviceEntity entity = DeviceMineManager.find(dev.getSerialNo());
		if(entity==null)
		{
			entity = new TestDeviceEntity();
			entity.setDeviceId(dev.getSerialNo());
			entity.setBuild_board(dev.getPropertyValue(DeviceDetails.BUILD_BOARD));
			entity.setBuild_brand(dev.getPropertyValue(DeviceDetails.BUILD_BRAND));
			entity.setBuild_cpu_abi(dev.getPropertyValue(DeviceDetails.BUILD_CPU_ABI));
			entity.setBuild_cpu_abi2(dev.getPropertyValue(DeviceDetails.BUILD_CPU_ABI2));
			entity.setBuild_device(dev.getPropertyValue(DeviceDetails.BUILD_DEVICE));
			entity.setBuild_display(dev.getPropertyValue(DeviceDetails.BUILD_DISPLAY));
			entity.setBuild_fingerprint(dev.getPropertyValue(DeviceDetails.BUILD_FINGERPRINT));
			entity.setBuild_hardware(dev.getPropertyValue(DeviceDetails.BUILD_HARDWARE));
			entity.setBuild_host(dev.getPropertyValue(DeviceDetails.BUILD_HOST));
			entity.setBuild_id(dev.getPropertyValue(DeviceDetails.BUILD_ID));
			entity.setBuild_manufacturer(dev.getPropertyValue(DeviceDetails.BUILD_MANUFACTURER));
			entity.setBuild_model(dev.getPropertyValue(DeviceDetails.BUILD_MODEL));
			entity.setBuild_product(dev.getPropertyValue(DeviceDetails.BUILD_PRODUCT));
			entity.setBuild_radio(dev.getPropertyValue(DeviceDetails.BUILD_RADIO));
			entity.setBuild_tags(dev.getPropertyValue(DeviceDetails.BUILD_TAGS));
			entity.setBuild_type(dev.getPropertyValue(DeviceDetails.BUILD_TYPE));
			entity.setBuild_user(dev.getPropertyValue(DeviceDetails.BUILD_USER));
			entity.setVersion_codename(dev.getPropertyValue(DeviceDetails.VERSION_CODENAME));
			entity.setVersion_incremental(dev.getPropertyValue(DeviceDetails.VERSION_INCREMENTAL));
			entity.setVersion_release(dev.getPropertyValue(DeviceDetails.VERSION_RELEASE));
			entity.setVersion_sdk(dev.getPropertyValue(DeviceDetails.VERSION_SDK));
			entity.save();
		}
		deviceEntity = entity;
	}

	@Override
	public String getLabel() {
		return iDevice.getName();
	}

	@Override
	public Image getImage() {
		return BBATImageManager.getInstance().getImage(BBATImageManager.ANDROID_DEVICE);
	}

	@Override
	public List<IGUITreeNode> getChildren() {
		return null;
	}

	@Override
	public IGUITreeNode getParent() {
		return null;
	}

	public IAndroidDevice getiDevice() {
		return iDevice;
	}

	public void setiDevice(IAndroidDevice iDevice) {
		this.iDevice = iDevice;
	}

	@Override
	public void addChild(IGUITreeNode childNode) {

	}

	public String getName() {
		return iDevice.getName();
	}

	public RawImage getScreenshot() {
		return iDevice.getScreenshot();
	}

	public String getApiLevel(){
		return "";
	}

	public boolean isAddedToRun() {
		return isAddedToRun;
	}

	public void setAddedToRun(boolean isAddedToRun) {
		this.isAddedToRun = isAddedToRun;
	}

	public TestDeviceEntity getDeviceEntity(){
		return deviceEntity;
	}

	@Override
	public void setParent(IGUITreeNode parent) {
		// TODO Auto-generated method stub

	}

	public boolean isUIAutomatorSupported(){
		return iDevice.isUIAutomatorSupported();
	}

	public void installApk(String apkPath) throws Exception {
		iDevice.installPackage(apkPath);		
	}

	public Map<String, String> getProperties() {
		return iDevice.getProperties();
	}
}
