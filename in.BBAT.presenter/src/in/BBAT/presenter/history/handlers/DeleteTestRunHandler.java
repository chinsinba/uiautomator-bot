package in.BBAT.presenter.history.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.history.TestRunHistoryView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

public class DeleteTestRunHandler extends AbstractTestRunBrowserHandler {

	private static final Logger LOG = BBATLogger.getLogger(DeleteTestRunHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event,final  List<?> selectedObjects) {

		LOG.info("Delete test run ");
		if(!MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Delete","Do you want to delete ?"))
			return null;
		try {
			ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(null);
			progressDialog.run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						monitor.beginTask("Deleting ...", selectedObjects.size());
						for(Object pkgObj :selectedObjects){
							if(monitor.isCanceled()){
								break;
							}
							monitor.setTaskName("Deleting "+((TestRunModel)pkgObj).getLabel() );
							((TestRunModel)pkgObj).delete();
							TestRunHistoryView.refreshView();
							monitor.worked(1);
						}
						monitor.done();
					} catch (Exception e) {
						LOG.error(e);
					}			
				}

			});
		} catch (InvocationTargetException e) {
			LOG.error(e);
		} catch (InterruptedException e) {
			LOG.error(e);
		}

		
		return null;
	}

	@Override
	public boolean isEnabled(List<?> selectedObjects) {

		if(!selectedObjects.isEmpty())
		{
			Object sample = selectedObjects.get(0);
			for (Object object : selectedObjects) {
				if(!(sample.getClass() == object.getClass()))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}



}
