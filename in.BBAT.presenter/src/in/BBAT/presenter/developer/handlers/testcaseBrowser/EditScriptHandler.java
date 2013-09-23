package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class EditScriptHandler extends AbstractTestCaseBrowserHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		IEditorInput input=null;
		input = new FileEditorInput(((TestCaseModel) selectedObjects.get(0)).getIFile());
		try
		{
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, "org.eclipse.jdt.ui.CompilationUnitEditor");
		} catch (PartInitException e)
		{
		}
		return null;
	}


	@Override
	public boolean isEnabled(List<?> object) {
		if(!object.isEmpty())
		{
			if(object.size()==1)
			{
				if(object.get(0) instanceof TestCaseModel)
				{
					return true;
				}
			}
		}
		return false;
	}
}
