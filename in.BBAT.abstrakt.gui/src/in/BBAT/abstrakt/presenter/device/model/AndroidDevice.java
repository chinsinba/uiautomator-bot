package in.BBAT.abstrakt.presenter.device.model;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.bbat.testrunner.IAndroidDevice;

public class AndroidDevice implements IGUITreeNode{

	private IAndroidDevice device;

	public AndroidDevice(IAndroidDevice dev){
		this.device = dev;
	}
	@Override
	public String getLabel() {
		return null;
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

}
