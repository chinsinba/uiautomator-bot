package in.BBAT.presenter.labelProviders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.data.model.Entities.TestRunEntity;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TestRunHistoryLabelProvider extends LabelProvider implements ITableLabelProvider{

	private static final Logger LOG = BBATLogger.getLogger(TestRunHistoryLabelProvider.class.getName());
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
				return ((TestRunModel) element).getLabel();
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
				long timeInmilis =((TestDeviceRunModel) element).getTimeTaken();
				return  String.format("%d : %d : %d ",TimeUnit.MILLISECONDS.toHours(timeInmilis),
						TimeUnit.MILLISECONDS.toMinutes(timeInmilis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeInmilis)),
						TimeUnit.MILLISECONDS.toSeconds(timeInmilis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInmilis))
						);
			}
		}
		return null;
	}
}
