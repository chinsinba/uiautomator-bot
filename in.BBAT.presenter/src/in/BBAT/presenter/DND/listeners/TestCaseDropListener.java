package in.BBAT.presenter.DND.listeners;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;
import in.bbat.presenter.internal.DeviceTestRun;
import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.PlatformUI;

public class TestCaseDropListener extends ViewerDropAdapter {

	public TestCaseDropListener(Viewer viewer) {
		super(viewer);
		// TODO Auto-generated constructor stub
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
			/*	if (testObj instanceof AndroidDevice) {
					TestRunExecutionManager.getInstance().addTestDevice(new DeviceTestRun((AndroidDevice) testObj,TestRunExecutionManager.getInstance().getTestRunCases()));
				}*/
				if(testObj instanceof AbstractProjectTree){
					addToTestCaseList((AbstractTreeModel) testObj, tempList);
				}
			}
		}


		for (TestRunCaseModel testRunCase : tempList) {
			TestRunExecutionManager.getInstance().addTestRunCase(testRunCase);
		}
		
		for(DeviceTestRun run : TestRunExecutionManager.getInstance().getSelectedDevices()){
			run.addTestCases(tempList);
		}

		BBATViewPart testRunView = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
		try {
			testRunView.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void addToTestCaseList(AbstractTreeModel testObj,List<TestRunCaseModel> tempList) {

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
