package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class DeviceViewLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if(element instanceof AndroidDevice)
			return ((AndroidDevice) element).getLabel();
		return element.toString();
	}

	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return  PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_ELEMENT);
	}
}
