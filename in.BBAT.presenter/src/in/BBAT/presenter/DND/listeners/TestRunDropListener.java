package in.BBAT.presenter.DND.listeners;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
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

public class TestRunDropListener extends ViewerDropAdapter{

	public TestRunDropListener(Viewer viewer) {
		super(viewer);
	}

	@Override
	public boolean performDrop(Object data) {
		Object testObj=null;
		List<TestCaseModel> tempList = new ArrayList<TestCaseModel>();


		if(data instanceof ISelection){
			/*Multiple selection is allowed
			 */
			Iterator<IStructuredSelection> iterator = ((IStructuredSelection) data).iterator();
			while(iterator.hasNext())
			{
				testObj = iterator.next();
				if (testObj instanceof AndroidDevice) {
					TestRunExecutionManager.getInstance().addTestDevice(new DeviceTestRun((AndroidDevice) testObj));
				}
				if(testObj instanceof AbstractProjectTree){
					addToTestCaseList((AbstractTreeModel) testObj, tempList);
				}
			}
		}


		for (TestCaseModel testRunCase : tempList) {
			TestRunExecutionManager.getInstance().addTestRunCase(testRunCase);
		}

		BBATViewPart testRunView = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
		try {
			testRunView.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void addToTestCaseList(AbstractTreeModel testObj,List<TestCaseModel> tempList) {

		if (testObj instanceof TestCaseModel) {
			tempList.add((TestCaseModel) testObj);
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
