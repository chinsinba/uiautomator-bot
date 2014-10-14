package in.BBAT.presenter;
import in.bbat.configuration.BBATProperties;
import in.bbat.utility.IBBATConstants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.prefs.Preferences;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class WorkspaceSelectionDialog extends TitleAreaDialog {

	private CCombo directoryCombo;

	private Preferences node = Preferences.userRoot().node(this.getClass().getName());

	private String workspace;

	public String getWorkspace() {
		return workspace;
	}

	public WorkspaceSelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Select a workspace");
		setMessage("All of your source will be saved in this workspace.", IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(2, false);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(layout);

		createWorkspaceInput(container);

		return area;
	}

	private void createWorkspaceInput(Composite container) {
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);


		GridData dataFirstName = new GridData();
		dataFirstName.grabExcessHorizontalSpace = true;
		dataFirstName.horizontalAlignment = GridData.FILL;

		directoryCombo = new CCombo(container, SWT.BORDER);
		directoryCombo.setLayoutData(dataFirstName);
		String[] workspaces =  node.get(IBBATConstants.USER_WORKSPACES, System.getProperty("user.dir")).split(",");
		directoryCombo.setItems(workspaces);
		directoryCombo.setText(node.get(IBBATConstants.RECENT_SELECTED_WORKSPACE, System.getProperty("user.dir")));
		directoryCombo.setEditable(false);
		directoryCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save(directoryCombo.getText());
			}
		});
		Button browseButton = new Button(container, SWT.PUSH);
		browseButton.setText("Browse");

		addListnerForBrowseButton(browseButton, directoryCombo);

	}

	@Override
	protected boolean isResizable() {
		return false;
	}

	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes
	private void saveInput() {
		workspace = directoryCombo.getText();
		save(directoryCombo.getText());
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	private void addListnerForBrowseButton(final Button button,final CCombo updateText) {
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog importDialog = new DirectoryDialog(getShell(),SWT.OPEN);
				String text = updateText.getText();
				if(text==null||text.isEmpty() )	
					importDialog.setFilterPath(System.getProperty(IBBATConstants.USER_HOME_PROPERTY));
				else
					importDialog.setFilterPath(text.trim());
				String selectedFile = importDialog.open();
				if (selectedFile != null) {
					updateText.setText(selectedFile);
				}
				save(selectedFile);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}
	
	@Override
	protected void cancelPressed() {
		PlatformUI.getWorkbench().close();
		super.cancelPressed();
	}

	public void save(String directory){

		node.put(IBBATConstants.RECENT_SELECTED_WORKSPACE,directory);
		BBATProperties.getInstance().setWkspc_UiAutomator(directory);
		try {
			BBATProperties.getInstance().save();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] workspaces =  node.get(IBBATConstants.USER_WORKSPACES, System.getProperty(IBBATConstants.USER_HOME_PROPERTY)).split(",");
		for (String workspace : workspaces) {
			System.out.println(workspace);
			if(workspace.equalsIgnoreCase(directory))
			{
				return;
			}
		}

		String commaSeperatedList = node.get(IBBATConstants.USER_WORKSPACES, System.getProperty(IBBATConstants.USER_HOME_PROPERTY));
		commaSeperatedList = commaSeperatedList +","+directory;

		node.put(IBBATConstants.USER_WORKSPACES, commaSeperatedList);
	}

} 