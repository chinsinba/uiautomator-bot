package in.BBAT.abstrakt.presenter.device.model;

import in.BBAT.TestRunner.device.IAndroidDevice;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.data.model.Entities.TestDeviceEntity;
import in.BBAT.dataMine.manager.DeviceMineManager;

import java.util.List;

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
			entity.save();
		}
		deviceEntity = entity;
	}

	@Override
	public String getLabel() {
		return iDevice.getDeviceId();
	}

	@Override
	public Image getImage() {
		return null;
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
}
