package in.BBAT.presenter.wizards.pages;


import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ExportTestcaseLogsWizardPage extends WizardPage {

	private Composite container;
	private Text zipFileText;
	private static String zipFilePath;
	private Button zipFilePathButton;
	private Label nameLabel;
	private String filterType;
	private String pathHeading;
	private Button createZipChkboxButton;

	public ExportTestcaseLogsWizardPage(String pageName,String title, String description, 
			String type, String pathHeading) {
		super(pageName);
		setTitle(title);
		setDescription(description);
		this.filterType = type;
		this.pathHeading = pathHeading;
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText(pathHeading);
		zipFileText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		GridData gdzipFileText = new GridData(GridData.FILL_HORIZONTAL);
		zipFileText.setLayoutData(gdzipFileText);

		zipFilePathButton = new Button(container, SWT.PUSH);
		GridData gdzipFilePathButton = new GridData(GridData.FILL);
		zipFilePathButton.setLayoutData(gdzipFilePathButton);
		zipFilePathButton.setText(" Browse... ");
		addListnerForBrowseButton(filterType);
		ModifyListener listner = modifyListner();
		zipFileText.addModifyListener(listner);

		createZipChkboxButton = new Button(container, SWT.CHECK);
		createZipChkboxButton.setText("Create Zip file");

		setControl(container);
		setPageComplete(false);
	}

	private void addListnerForBrowseButton(final String filterExtension) {

		zipFilePathButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog exportDialog = new DirectoryDialog(getShell(),
						SWT.SAVE);
				//					exportDialog.setFileName(defaultName);
				String destinationPath = exportDialog.open();
				if (destinationPath != null) {
					zipFileText.setText(destinationPath);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	private ModifyListener modifyListner() {
		ModifyListener listner = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (validateText()) {
					zipFilePath = zipFileText.getText();
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		};
		return listner;
	}

	public boolean validateText() {
		if (zipFileText.getText().isEmpty()) {
			setErrorMessage("Folder Path is Empty");
			return false;
		} 
		else {
			setErrorMessage(null);
			return true;
		}
	}

	public String getZipFilePath() {
		return zipFilePath;
	}

	public boolean createZip(){
		if(createZipChkboxButton.getSelection())
			return true;
		else
			return false;
	}

}
