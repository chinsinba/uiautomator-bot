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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class BrowseTestPackagePage extends WizardPage{

	private Composite container;
	private Label nameLabel;
	private Button browseDirButton;
	private Text exportPathText;
	private String labelText;
	protected String selctedProject;

	public BrowseTestPackagePage(String pageName,String labelText, String description) {
		super(pageName);
		this.labelText = labelText;
		setTitle(pageName);
		setDescription(description);
	}

	@Override
	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText(labelText);
		exportPathText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		GridData gdzipFileText = new GridData(GridData.FILL_HORIZONTAL);
		exportPathText.setLayoutData(gdzipFileText);

		browseDirButton = new Button(container, SWT.PUSH);
		GridData gdzipFilePathButton = new GridData(GridData.FILL);
		browseDirButton.setLayoutData(gdzipFilePathButton);
		browseDirButton.setText(" Browse... ");
		addListnerForBrowseButton();
		ModifyListener listner = modifyListner();
		exportPathText.addModifyListener(listner);
		setControl(container);
		setPageComplete(false);

	}

	private void addListnerForBrowseButton() {

		browseDirButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog exportDialog = new FileDialog(getShell(),SWT.SAVE);
				exportDialog.setFilterExtensions(new String[] {"*.dat"});
				String destinationPath = exportDialog.open();
				if (destinationPath != null) {
					exportPathText.setText(destinationPath);
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
					selctedProject = exportPathText.getText();
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		};
		return listner;
	}

	public boolean validateText() {
		if (exportPathText.getText().isEmpty()) {
			setErrorMessage("Folder Path is Empty");
			return false;
		} 
		else {
			setErrorMessage(null);
			return true;
		}
	}

	public String getPath(){
		return selctedProject;
	}
}
