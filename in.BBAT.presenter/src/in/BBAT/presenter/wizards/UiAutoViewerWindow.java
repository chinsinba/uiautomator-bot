package in.BBAT.presenter.wizards;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.android.uiautomator.UiAutomatorViewer;

public class UiAutoViewerWindow extends ApplicationWindow {	

	/** 	 
	 * Create the application window. 	 
	 * @param imageFilePath 
	 */ 	
	public UiAutoViewerWindow() { 		
		super(new Shell());
		addToolBar(SWT.FLAT | SWT.WRAP); 		
		addMenuBar(); 		
		//		addStatusLine(); 	
	}	

	/** 	 
	 * Create contents of the application window. 	 
	 * 
	 * @param parent 	 
	 */ 	
	@Override 	
	protected Control createContents(Composite parent) { 		
		Composite container = new Composite(parent, SWT.NONE);		
		// create widgets that fill main window area
		container.setLayout(new FillLayout());
		UiAutomatorViewer viewer = new UiAutomatorViewer();
		viewer.createContents(container);
		return container; 	
	}


	/** 	 
	 * Create the menu manager. 	 
	 * 
	 * @return the menu manager 	 
	 */ 	
	@Override 	
	protected MenuManager createMenuManager() { 		
		MenuManager menuManager = new MenuManager("menu"); 		
		// create menus here
		return menuManager;

	}

	/** 	 
	 * Create the status line manager. 	 
	 * @return the status line manager 	 
	 */ 	
	@Override 	
	protected StatusLineManager createStatusLineManager() { 		
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager; 	
	}

	public static void main(String[] args) {
		final Display  display = new Display ();
		final Shell shell = new Shell ();

		UiAutomatorViewer window = new UiAutomatorViewer(); 			
		window.setBlockOnOpen(true); 			
		window.open(); 

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep ();
			}
		}
		display.dispose ();
	}


	/** 	 
	 * Configure the shell. 	 
	 * @param newShell 	 
	 */ 	
	@Override 	
	protected void configureShell(Shell newShell) { 		
		super.configureShell(newShell); 		
		newShell.setText("Inspect Screenshot"); 	
		newShell.setSize(500,Display.getDefault().getBounds().height);
	}	

}
