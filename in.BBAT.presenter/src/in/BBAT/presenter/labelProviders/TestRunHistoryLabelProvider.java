package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TestRunHistoryLabelProvider extends LabelProvider implements ITableLabelProvider{

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if(element instanceof TestDeviceRunModel)
		{
			switch (columnIndex) {
			case 0:
				return ((TestDeviceRunModel) element).getImage();
			}
		}
		return null;
	}
	@Override
	public String getColumnText(Object element, int columnIndex) {

		if(element instanceof TestRunModel){
			switch (columnIndex) {
			case 0:
				return	((TestRunModel) element).getStartTime().toString();
			}			
		}

		if(element instanceof TestDeviceRunModel)
		{
			switch (columnIndex) {
			case 0:
				return ((TestDeviceRunModel) element).getDeviceName();
			case 1:
				return ((TestDeviceRunModel) element).getStatus();
			}
		}
		return null;
	}
}
