package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.presenter.internal.DeviceTestRun;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class DeviceViewLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

	/*	switch (columnIndex) {
		case 0:
			return  BBATImageManager.getInstance().getImage(BBATImageManager.ANDROID_DEVICE);
		}*/
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if(element instanceof AndroidDevice)
			return ((AndroidDevice) element).getLabel();
		if(element  instanceof DeviceTestRun){
			switch (columnIndex) {
			case 0:
				return ((DeviceTestRun) element).getDevice().getLabel();
			case 1:
				return ((DeviceTestRun) element).getStatus().getStatus();
			default:
				break;
			}
		}

		return element.toString();
	}
}
