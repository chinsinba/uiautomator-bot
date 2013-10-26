package in.bbat.presenter.internal;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import in.BBAT.abstrakt.presenter.run.model.AutomatorLogModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.testRunner.runner.IUiAutomatorListener;
import in.bbat.presenter.views.tester.AutomatorLogView;

public class UIAutomatorOutputListener implements IUiAutomatorListener {

	private TestRunInstanceModel runInstance;

	public UIAutomatorOutputListener(TestRunInstanceModel runInstance) {
		this.runInstance = runInstance;
	}

	@Override
	public void processLine(String line) {
		AutomatorLogModel log = new AutomatorLogModel(runInstance,line);
		log.save();
		runInstance.addAutoLog(log);


	/*	Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				IViewPart autoLogView;
				try {
					autoLogView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AutomatorLogView.ID);
					if(autoLogView!= null){
						((AutomatorLogView)autoLogView).refresh();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});*/

	}

}
