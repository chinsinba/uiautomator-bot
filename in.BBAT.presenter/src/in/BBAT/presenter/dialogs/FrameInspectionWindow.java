package in.BBAT.presenter.dialogs;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager; 
import org.eclipse.jface.action.StatusLineManager; 
import org.eclipse.jface.action.ToolBarManager; 
import org.eclipse.jface.window.ApplicationWindow; 
import org.eclipse.swt.SWT; 
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point; 
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite; 
import org.eclipse.swt.widgets.Control; 
import org.eclipse.swt.widgets.Display; 
import org.eclipse.swt.widgets.Shell;

public class FrameInspectionWindow extends ApplicationWindow {	
	private Action zoomInAction;
	private Action zoomOutAction;
	private SWTImageCanvas canvas;
	private Image image;
	private Action fitToScreenAction;
	private Action actualSizeAction;
	/** 	 
	 * Create the application window. 	 
	 * @param imageFilePath 
	 */ 	
	public FrameInspectionWindow(Image imageFilePath) { 		
		super(new Shell());
		this.image = imageFilePath;
		createActions(); 		
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
		canvas = new SWTImageCanvas(container,true);

		return container; 	
	}

	public void loadImage(){

		Display.getDefault().timerExec(100, new Runnable() {

			@Override
			public void run() {
				canvas.loadImage(image);
				canvas.redraw();
				canvas.update();
			}
		});


	}

	/** 	 
	 * Create the actions. 	 
	 */ 	
	private void createActions() { 		
		// Create the actions 

		zoomInAction = new Action("Zoom-in") { 			
			@Override 			

			public void run() { 	

				canvas.zoomIn();				
			} 		
		}; 	


		zoomOutAction = new Action("Zoom-out") { 			
			@Override 			
			public void run() { 
				canvas.zoomOut();			
			} 		
		};

		fitToScreenAction = new Action("Fit-To-Screen") { 			
			@Override 			
			public void run() { 
				canvas.fitCanvas();			
			} 		
		}; 	


		actualSizeAction = new Action("100%") { 			
			@Override 			
			public void run() { 
				canvas.showOriginal();			
			} 		
		}; 	
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
	 * Create the toolbar manager. 	 
	 * @return the toolbar manager 	 
	 */ 	
	@Override 	
	protected ToolBarManager createToolBarManager(int style) { 		
		ToolBarManager toolBarManager = new ToolBarManager(style); 	
		// Create toolbars here	
		toolBarManager.add(zoomInAction);
		toolBarManager.add(zoomOutAction);
		toolBarManager.add(fitToScreenAction);
		toolBarManager.add(actualSizeAction);
		return toolBarManager; 	
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

		FrameInspectionWindow window = new FrameInspectionWindow(null); 			
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
