package in.BBAT.presenter.labelProviders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.data.model.Entities.TestRunEntity;

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
				String s= "RUN_"+((TestRunEntity)((TestRunModel) element).getEntity()).getId();

				return s/*+" ["+ ((TestRunModel) element).getStartTime() +"]"*/;
			}			
		}
		if(element instanceof TestDeviceRunModel)
		{
			switch (columnIndex) {
			case 0:
				return ((TestDeviceRunModel) element).getDeviceName();
			case 1:
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date(((TestDeviceRunModel) element).getStartTime().getTime());
				return dateFormat.format(date);
			case 2:
				return ((TestDeviceRunModel) element).getStatus();

			case 3:
				return  String.valueOf(((TestDeviceRunModel) element).getTimeTaken()/1000f);
			}
		}
		return null;
	}
}
