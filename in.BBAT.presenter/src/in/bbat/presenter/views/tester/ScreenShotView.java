package in.bbat.presenter.views.tester;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryGroupRenderer;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryItemRenderer;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.presenter.dialogs.FrameInspectionWindow;
import in.bbat.presenter.views.BBATViewPart;

public class ScreenShotView extends BBATViewPart {

	public static final String ID = "in.BBAT.presenter.tester.screenShots";
	private TestRunInstanceModel runCase;
	private Label imageLabel;
	public static TestRunInstanceModel testCase = null;

	public static void selectedTestRuncase(TestRunInstanceModel runCase){
		testCase = runCase;
	}

	@Override
	public void refresh() throws Exception {

	}

	@Override
	public ISelection getSelectedElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createPartControl(Composite parent) {

		if(testCase!=null)
		{
			File scrShotDir = new File(testCase.getScreenShotDir());
			if(scrShotDir.exists())
			{
				File[] listFiles = scrShotDir.listFiles();
				Arrays.sort(listFiles);
				Gallery gallery = new Gallery(parent, SWT.V_SCROLL | SWT.MULTI);
				gallery.addMouseListener(new MouseListener() {
					public void mouseUp(MouseEvent e) {
					}
					public void mouseDown(MouseEvent e) {
					}

					public void mouseDoubleClick(MouseEvent e) {
						GalleryItem[] selection = ((Gallery)e.getSource()).getSelection();
						if(selection==null || selection.length==0){
							return;
						}
						Image image = selection[0].getImage();
						if(image == null){
							return;
						}
						FrameInspectionWindow window = new FrameInspectionWindow(image); 			
						window.open();
						window.loadImage();	
					}
				});
				// Renderers
				DefaultGalleryGroupRenderer gr = new DefaultGalleryGroupRenderer();
				gr.setMinMargin(2);
				gr.setItemHeight(300);
				gr.setItemWidth(200);
				gr.setAutoMargin(true);
				gallery.setGroupRenderer(gr);

				DefaultGalleryItemRenderer ir = new DefaultGalleryItemRenderer();
				gallery.setItemRenderer(ir);

				GalleryItem group = new GalleryItem(gallery, SWT.NONE);
				group.setText("Screen Shots "); //$NON-NLS-1$
				group.setExpanded(true);
				for (File file : listFiles) {

					ImageData image = new ImageData(file.getAbsolutePath());
					Image im = new Image(Display.getDefault(), image);

					GalleryItem item = new GalleryItem(group, SWT.NONE);
					if (im != null) {
						item.setImage(im);
					}
					item.setText(file.getName() ); //$NON-NLS-1$
				}

			}
		}
	}

	private void createScreenShot() throws Exception {
		if(runCase!=null)
		{
			File scrShotDir = new File(runCase.getScreenShotDir());
			if(scrShotDir.exists()){
				File[] listFiles = scrShotDir.listFiles();
				ImageData image = new ImageData(listFiles[0].getAbsolutePath()).scaledTo(200, 300);
				imageLabel.setImage(new Image(Display.getDefault(), image));
				imageLabel.setSize( imageLabel.computeSize( SWT.DEFAULT, SWT.DEFAULT ));
			}

		}
	}

	@Override
	public void setFocus() {
		if(testCase !=null)
			setPartName("Screenshots: "+testCase.getTestCaseEntity().getName());
	}

	public void setInput(TestRunInstanceModel runInstance){

	}

}
