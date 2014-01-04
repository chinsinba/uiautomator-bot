package in.bbat.reporter.poc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Panel;
import java.util.Map;

import javax.swing.JRootPane;

import net.sf.jasperreports.view.JRViewer;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractReportWindow extends ApplicationWindow {

	public AbstractReportWindow(Shell parentShell) {
		super(parentShell);
	}

	private void configureShell(Composite parent) {
		Rectangle localRectangle = Display.getCurrent().getBounds();
		getShell().setText(getWindowTitle());
		getShell().setMaximized(true);
		parent.setSize(new Point(localRectangle.width-200, localRectangle.height));
	}
	abstract protected String getWindowTitle();


	@Override
	protected Control createContents(Composite parent) {

		configureShell(parent);

		JRViewer jasperviewer = new JRViewer(ReportGen.generateReport(getReportName(),getParameters()));
		//add the SWT_AWT compposite for SWING contents of GUI              
		final Composite swtAwtComposite = new Composite(parent, SWT.EMBEDDED);
		swtAwtComposite.setBounds(10, 0, 767, 600);

		Frame frame = SWT_AWT.new_Frame(swtAwtComposite);

		Panel panel = new Panel();
		frame.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JRootPane rootPane = new JRootPane();
		rootPane.setSize(767, 600);
		panel.add(rootPane);

		//Define a container yourself
		Container c = rootPane.getContentPane();

		//Add the JRViewer object onto the container to render in GUI
		c.add(jasperviewer);
		return parent;

	}

	protected abstract Map getParameters();

	protected abstract String getReportName();
}
