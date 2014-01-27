package in.bbat.presenter.dialogs;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class IPAdressAndPortDialog extends Dialog {

	private Text ipAddressText;
	private String ipAddress;
	private Text portText;
	private String port;
	
	public IPAdressAndPortDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);

		Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("IPAddress");
		nameLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));

		ipAddressText = new Text(composite, SWT.BORDER);
		ipAddressText.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, false));

		ipAddressText.setTextLimit(15);
		ipAddressText.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (('a' <= chars[i] && chars[i] <= 'z')
							|| ('A' <= chars[i] && chars[i] <= 'Z')) {
						e.doit = false;
						return;
					}
				}
				e.doit = true;
			}
		});

		Label portLabel = new Label(composite, SWT.NONE);
		portLabel.setText("Port");
		portLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));

		portText = new Text(composite, SWT.BORDER);
		portText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true,
				false));
		portText.setTextLimit(5);
		portText.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
//				e.doit = TextValidator.validatePositiveInteger(e);
			}

		});

		return composite;
	}
	
	/**
	 * Validate textBox for port such that it allows to enter only positive
	 * integers
	 * 
	 * @param e
	 * @return
	 */

	private boolean validatePositiveInteger(Event e) {
		String string = e.text;
		char[] chars = new char[string.length()];
		string.getChars(0, chars.length, chars, 0);
		for (int i = 0; i < chars.length; i++) {
			if (!('0' <= chars[i] && chars[i] <= '9')) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected void okPressed() {
		ipAddress = ipAddressText.getText().trim();
		port = portText.getText().trim();

		if (ipAddress.equals("") || (port.equals(""))) {
			MessageDialog.openError(getShell(), "Invalid field",
					"Cannot be empty");
			return;
		}

		if (!isIPAddress(ipAddress)) {
			MessageDialog.openError(getShell(), "Invalid IpAddress",
					"Invalid IP address.");
			return;
		}

		if (!validatePort(port)) {
			MessageDialog.openError(getShell(), "Invalid port",
					"Invalid port number.");
			return;
		}
		super.okPressed();
	}

	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * validate ip address text
	 * 
	 * @param str
	 * @return
	 */

	private boolean isIPAddress(String str) {
		Pattern ipPattern = Pattern
				.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
		return ipPattern.matcher(str).matches();
	}

	/**
	 * port number validation if port number varies from 0 to 65536.
	 * 
	 * @param port
	 * @return
	 */
	public boolean validatePort(String port) {
		int portNum = Integer.parseInt(port);
		if (portNum >= 0 && portNum <= 65536)
			return true;
		return false;
	}

	public String getPort() {

		return port;
	}

}
