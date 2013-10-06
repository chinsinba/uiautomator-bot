package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.bbat.presenter.internal.DeviceTestRun;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class DeviceViewLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return  PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJS_INFO_TSK);
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if(element instanceof AndroidDevice)
			return ((AndroidDevice) element).getLabel();
		if(element  instanceof DeviceTestRun)
			return ((DeviceTestRun) element).getDevice().getLabel();
		return element.toString();
	}
}
