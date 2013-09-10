package in.BBAT.abstrakt.presenter.device.model;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.android.ddmlib.CollectingOutputReceiver;
import com.android.ddmlib.RawImage;

import in.BBAT.TestRunner.device.IAndroidDevice;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;

public class AndroidDevice implements IGUITreeNode{

	private IAndroidDevice iDevice;

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
		// TODO Auto-generated method stub
		
	}
	public String getName() {
		// TODO Auto-generated method stub
		return iDevice.getName();
	}
	public RawImage getScreenshot() {
		// TODO Auto-generated method stub
		return iDevice.getScreenshot();
	}
	public void executeShellCommand(String command,
			CollectingOutputReceiver collectingOutputReceiver, int i) {
		// TODO Auto-generated method stub
		
	}

}
