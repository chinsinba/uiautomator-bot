package in.bbat.reporter.poc;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;

public class DeviceRunWindow extends AbstractReportWindow {

	public DeviceRunWindow(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Map getParameters() {
		// TODO Auto-generated method stub
		return new HashMap<String, String>();
	}

	@Override
	protected String getReportName() {
		return "DeviceRunSummary.jasper";
	}

	@Override
	protected String getWindowTitle() {
		// TODO Auto-generated method stub
		return "Device Run Summary";
	}

}
