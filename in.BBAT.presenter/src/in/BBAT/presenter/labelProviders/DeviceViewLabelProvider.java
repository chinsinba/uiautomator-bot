package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.presenter.internal.DeviceTestRun;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class DeviceViewLabelProvider extends LabelProvider implements ITableLabelProvider {

	private static final Logger LOG = BBATLogger.getLogger(DeviceViewLabelProvider.class.getName());
	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(element instanceof AndroidDevice){
			switch (columnIndex) {
			case 0:
				return ((AndroidDevice) element).getImage();
			case 1:
				return null;
			default:
				break;
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if(element instanceof AndroidDevice){
			switch (columnIndex) {
			case 0:
				return ((AndroidDevice) element).getLabel();
			case 1:
				return ((AndroidDevice) element).getiDevice().getMonkeyDevice().getState().toString();
			default:
				break;
			}
		}
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
