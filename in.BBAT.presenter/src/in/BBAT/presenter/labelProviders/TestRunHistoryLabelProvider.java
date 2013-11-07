package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.data.model.Entities.TestRunEntity;
import in.bbat.abstrakt.gui.BBATImageManager;

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
				return	"Run_"+((TestRunEntity)((TestRunModel) element).getEntity()).getId();
			}			
		}

		if(element instanceof TestDeviceRunModel)
		{
			switch (columnIndex) {
			case 0:
				return ((TestDeviceRunModel) element).getDeviceName();
			case 1:
				return ((TestDeviceRunModel) element).getStatus();
				
			case 2:
				return  String.valueOf(((TestDeviceRunModel) element).getTimeTaken()/1000f);
			}
		}
		return null;
	}
}
