package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.BBAT.presenter.wizards.pages.CreateTestCasePage;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

public class CreateTestCaseWizard extends Wizard {


	private CreateTestCasePage caseCreationPage;

	private TestSuiteModel parent;

	public CreateTestCaseWizard(TestSuiteModel suiteModel) {
		this.parent = suiteModel;
	}

	@Override
	public void addPages() {
		caseCreationPage = new CreateTestCasePage("TestCase",parent);
		addPage(caseCreationPage);
	}
	@Override
	public boolean performFinish() {

		try {
			TestCaseModel newTestCase = new TestCaseModel(parent, caseCreationPage.getName());
			newTestCase.setDescription(caseCreationPage.getDescription());
			newTestCase.save();
			newTestCase.createContents();
			BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
			try {
				view.refresh();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			IEditorInput input=null;
			input = new FileEditorInput(newTestCase.getIFile());
			try
			{
//				IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), newTestCase.getIFile());
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, "org.eclipse.jdt.ui.CompilationUnitEditor");
			} catch (PartInitException e)
			{
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	
	private void createContents() {
		
	}

}
