package in.BBAT.presenter.wizards;

import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.presenter.wizards.pages.BrowseDirectoryPage;
import in.BBAT.utils.ExcelReportGenerator;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;

public class ExportTestRunReportWizard extends Wizard {


	protected List<TestRunModel> testRunModelList;
	private Logger LOG = BBATLogger.getLogger(ExportTestRunReportWizard.class.getName());
	protected BrowseDirectoryPage page1;

	public ExportTestRunReportWizard(List<TestRunModel> testRun) {
		testRunModelList = testRun;
	}

	@Override
	public boolean performFinish() {

		for(TestRunModel testRun: testRunModelList){
			ExcelReportGenerator reportGenerator = new ExcelReportGenerator(testRun,page1.getPath()+Path.SEPARATOR+testRun.getName()+".xls");
			try {
				reportGenerator.write();
			} catch (Exception e) {
				MessageDialog.openError(getShell(), "Export Error", "Failled to export report.");
				LOG.error(e);
				return false;
			}
		} 
		return true;
	}

	@Override
	public void addPages() {
		page1 = new BrowseDirectoryPage("Export Test Run Report", "Export path: " , "Select directory");
		addPage(page1);

	}

}
