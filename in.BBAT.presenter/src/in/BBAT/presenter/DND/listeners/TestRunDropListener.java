package in.BBAT.presenter.DND.listeners;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunCase;
import in.BBAT.abstrakt.presenter.run.model.TestRunManager;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
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
		AbstractProjectTree testObj=null;
		List<TestRunCase> tempList = new ArrayList<TestRunCase>();
		if(data instanceof TreeSelection){
			/*Multiple selection is allowed
			 */
			Iterator<IStructuredSelection> iterator = ((IStructuredSelection) data).iterator();
			while(iterator.hasNext())
			{
				testObj = (AbstractProjectTree) iterator.next();

				addToTestCaseList(testObj, tempList);
			}
		}
		
		for (TestRunCase testRunCase : tempList) {
			TestRunManager.getInstance().addTestRunCase(testRunCase);
		}

		BBATViewPart testRunView = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
		try {
			testRunView.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void addToTestCaseList(AbstractTreeModel testObj,List<TestRunCase> tempList) {

		if (testObj instanceof TestCaseModel) {
			tempList.add(new TestRunCase((TestCaseModel) testObj));
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
		return true;
	}


}