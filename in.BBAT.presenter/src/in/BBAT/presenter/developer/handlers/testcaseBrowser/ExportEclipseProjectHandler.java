package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import java.util.List;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.BBAT.presenter.wizards.ExportEclipseProjectWizard;
import in.BBAT.presenter.wizards.ExportTestProjectWizard;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class ExportEclipseProjectHandler extends AbstractTestCaseBrowserHandler {

	private static final Logger LOG = BBATLogger.getLogger(ExportEclipseProjectHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		LOG.info("Export scripts ");
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				new ExportEclipseProjectWizard((List<TestProjectModel>) selectedObjects));
		dialog.open();
		return null;
	}

	@Override
	protected boolean canBeEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(object==null){
			return false;
		}

		if(object.isEmpty()){
			return false;
		}

		for(Object obj : object){
			if(! (obj instanceof TestProjectModel))
			{
				return false;
			}
		}

		return true;
	}


}
