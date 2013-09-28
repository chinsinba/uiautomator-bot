package in.BBAT.abstrakt.presenter.device.model;

import in.BBAT.TestRunner.device.IAndroidDevice;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.android.ddmlib.RawImage;

public class AndroidDevice implements IGUITreeNode{

	private IAndroidDevice iDevice;
	private boolean isAddedToRun =false;

	public AndroidDevice(IAndroidDevice dev){
		this.setiDevice(dev);
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

}
