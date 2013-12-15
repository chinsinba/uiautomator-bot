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

public class BrowseApkPage extends WizardPage {


	private Composite container;
	private Label nameLabel;
	private Button browseDirButton;
	private Text apkPathText;
	private String labelText;
	protected String selctedApk;
	
	public  BrowseApkPage(String pageName,String labelText, String description) {
		super(pageName);
		setTitle(pageName);
		setDescription(description);
		this.labelText = labelText;
	}

	@Override
	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText(labelText);
		apkPathText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		GridData gdzipFileText = new GridData(GridData.FILL_HORIZONTAL);
		apkPathText.setLayoutData(gdzipFileText);

		browseDirButton = new Button(container, SWT.PUSH);
		GridData gdzipFilePathButton = new GridData(GridData.FILL);
		browseDirButton.setLayoutData(gdzipFilePathButton);
		browseDirButton.setText(" Browse... ");
		addListnerForBrowseButton();
		ModifyListener listner = modifyListner();
		apkPathText.addModifyListener(listner);
		setControl(container);
		setPageComplete(false);

	}

	private void addListnerForBrowseButton() {

		browseDirButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog exportDialog = new FileDialog(getShell(),SWT.SAVE);
				exportDialog.setFilterExtensions(new String[] {"*.apk"});
				String destinationPath = exportDialog.open();
				if (destinationPath != null) {
					apkPathText.setText(destinationPath);
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
					selctedApk = apkPathText.getText();
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

		};
		return listner;
	}

	public boolean validateText() {
		if (apkPathText.getText().isEmpty()) {
			setErrorMessage("Folder Path is Empty");
			return false;
		} 
		else {
			setErrorMessage(null);
			return true;
		}
	}

	public String getPath(){
		return selctedApk;
	}

}
