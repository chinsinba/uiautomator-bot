package in.BBAT.presenter.DND.listeners;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

public class TestCaseDropListener extends ViewerDropAdapter {

	private static final Logger LOG = BBATLogger.getLogger(TestCaseDropListener.class.getName());
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
					List<TestCaseModel> errorCases = new ArrayList<TestCaseModel>();
					addToTestCaseList((AbstractTreeModel) testObj, tempList, errorCases);
					if(!errorCases.isEmpty()){
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								"Test Case Error", "Errors found in following test cases \n"+ errorCases);
					}
				}
			}
		}


		for (TestRunCaseModel testRunCase : tempList) {
			TestRunExecutionManager.getInstance().addTestRunCase(testRunCase);
		}

		for(DeviceTestRun run : TestRunExecutionManager.getInstance().getSelectedDevices()){
			run.addTestCases(tempList);
		}

		TestRunnerView.refreshView();
		return false;
	}

	private void addToTestCaseList(AbstractTreeModel testObj,List<TestRunCaseModel> tempList,List<TestCaseModel> errorCases) {

		if (testObj instanceof TestCaseModel) {
			if(((TestCaseModel) testObj).hasErrors()){
				errorCases.add((TestCaseModel) testObj);
				return;
			}
			if(testObj.isHelper()){
				return ;
			}

			tempList.add(new TestRunCaseModel((TestCaseModel) testObj));
			return;
		}

		try {
			for (AbstractTreeModel obj : testObj.getChildren()) {
				if(obj instanceof TestSuiteModel){
					if(((TestSuiteModel) obj).isHelper())
						continue;
				}
				addToTestCaseList(obj, tempList,errorCases);
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
