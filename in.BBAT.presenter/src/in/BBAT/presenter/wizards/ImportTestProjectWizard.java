package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectManager;

import org.eclipse.jface.wizard.Wizard;

public class ImportTestProjectWizard extends Wizard {

	private TestProjectManager projMgr;

	public ImportTestProjectWizard(TestProjectManager projMgr) {
		this.projMgr = projMgr;
	}

	@Override
	public boolean performFinish() {
		return true;
	}

	@Override
	public void addPages() {
		super.addPages();
	}

}
