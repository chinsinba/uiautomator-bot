package in.BBAT.presenter.wizards;

import java.io.IOException;
import java.util.List;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.presenter.wizards.pages.ExportTestcaseLogsWizardPage;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;

public class ExportLogsWizard  extends Wizard{
	private List<TestRunInstanceModel> testRunInstances;
	private ExportTestcaseLogsWizardPage page;
	private static Logger LOG = BBATLogger.getLogger(ExportLogsWizard.class.getName()); 
	public ExportLogsWizard(List<TestRunInstanceModel> testRunInstances) {
		this.testRunInstances = testRunInstances;
	}

	@Override
	public void addPages() {
		page = new ExportTestcaseLogsWizardPage("Export Logs", "Export Logs", "Export device and uiAutomator logs", "*.zip*", "Export Path");
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
			@Override
			public void run() {
				try {
					for (TestRunInstanceModel model : testRunInstances) {
						model.exportLogs(page.getZipFilePath(), page.createZip());
					}

				} catch (IOException e) {
					LOG.error(e);
				}
			}
		});
		return true;
	}

}

