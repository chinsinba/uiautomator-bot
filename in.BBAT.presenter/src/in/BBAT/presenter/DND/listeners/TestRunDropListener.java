package in.BBAT.presenter.DND.listeners;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.internal.DeviceTestRun;
import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class TestRunDropListener extends ViewerDropAdapter{

	private static final Logger LOG = BBATLogger.getLogger(TestRunDropListener.class.getName());
	public TestRunDropListener(Viewer viewer) {
		super(viewer);
	}

	@Override
	public boolean performDrop(Object data) {
		Object testObj=null;
		List<TestRunCaseModel> tempList = new ArrayList<TestRunCaseModel>();

		if(data instanceof ISelection){
			/*Multiple selection is allowed
			 */
			Iterator<IStructuredSelection> iterator = ((IStructuredSelection) data).iterator();
			while(iterator.hasNext())
			{
				testObj = iterator.next();
				if (testObj instanceof AndroidDevice) {
					if(!((AndroidDevice) testObj).isUIAutomatorSupported()){
						Display.getDefault().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() ,"Info", "The device does not support UiAutomator."
										+ "");
								
							}
						});
						return true;
					}
					TestRunExecutionManager.getInstance().addTestDevice(new DeviceTestRun((AndroidDevice) testObj,TestRunExecutionManager.getInstance().getTestRunCases()));
				}
				if(testObj instanceof AbstractProjectTree){
					addToTestCaseList((AbstractTreeModel) testObj, tempList);
				}
			}
		}

		Object deviceRun = getCurrentTarget();
		if(deviceRun instanceof DeviceTestRun){
			((DeviceTestRun) deviceRun).addTestCases(tempList);
		}

		TestRunnerView.refreshView();
		return true;
	}

	protected void addToTestCaseList(AbstractTreeModel testObj,List<TestRunCaseModel> tempList) {

		if (testObj instanceof TestCaseModel) {
			tempList.add(new TestRunCaseModel((TestCaseModel) testObj));
			return;
		}

		try {
			for (AbstractTreeModel obj : testObj.getChildren()) {
				addToTestCaseList(obj, tempList);
			}
		} catch (Exception e) {
		}

	}

	@Override
	public boolean validateDrop(Object target, int operation,
			TransferData transferType) {
		if(TestRunExecutionManager.getInstance().isExecuting())
			return  false;
		return true;
	}


}
