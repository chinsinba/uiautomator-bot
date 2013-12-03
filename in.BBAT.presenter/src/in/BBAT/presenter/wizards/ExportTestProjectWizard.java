package in.BBAT.presenter.wizards;

import java.util.List;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;

import org.eclipse.jface.wizard.Wizard;

public class ExportTestProjectWizard extends Wizard{

	private List<TestProjectModel> projects;
	public ExportTestProjectWizard(List<TestProjectModel> projectList ) {
		this.projects = projectList;
	}
	
	@Override
	public void addPages() {
		super.addPages();
	}

	@Override
	public boolean performFinish() {
		return false;
	}

}
