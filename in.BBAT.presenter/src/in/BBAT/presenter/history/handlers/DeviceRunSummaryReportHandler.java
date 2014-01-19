package in.BBAT.presenter.history.handlers;

import in.bbat.reporter.poc.DeviceRunWindow;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class DeviceRunSummaryReportHandler extends AbstractTestRunInfoHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		DeviceRunWindow w = new DeviceRunWindow(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		w.open();
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
