package com.android.uiautomator;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import com.android.uiautomator.tree.BasicTreeNode;



public class ScreenShot {

	private static final int IMG_BORDER = 2;

	// The screenshot area is made of a stack layout of two components: screenshot canvas and
	// a "specify screenshot" button. If a screenshot is already available, then that is displayed
	// on the canvas. If it is not availble, then the "specify screenshot" button is displayed.
	private Composite mScreenshotComposite;
	private StackLayout mStackLayout;
	private Composite mSetScreenshotComposite;
	private Canvas mScreenshotCanvas;


	private float mScale = 1.0f;
	private int mDx, mDy;

	private UiAutomatorModel mModel;
	public File mModelFile;
	public Image mScreenshot;

	private XMLArea xmlArea;

	public ScreenShot(Composite baseSash){
		createScreenShotArea(baseSash);
	}

	private void createScreenShotArea(Composite baseSash) {
		mScreenshotComposite = new Composite(baseSash, SWT.BORDER);
		mStackLayout = new StackLayout();
		mScreenshotComposite.setLayout(mStackLayout);

		// draw the canvas with border, so the divider area for sash form can be highlighted
		mScreenshotCanvas = new Canvas(mScreenshotComposite, SWT.BORDER);
		mStackLayout.topControl = mScreenshotCanvas;
		mScreenshotComposite.layout();

		mScreenshotCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (getmModel() != null) {
					getmModel().toggleExploreMode();
					redrawScreenshot();
				}
			}
		});
		mScreenshotCanvas.setBackground(
				baseSash.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		mScreenshotCanvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				if (mScreenshot != null) {
					updateScreenshotTransformation();
					// shifting the image here, so that there's a border around screen shot
					// this makes highlighting red rectangles on the screen shot edges more visible
					Transform t = new Transform(e.gc.getDevice());
					t.translate(mDx, mDy);
					t.scale(mScale, mScale);
					e.gc.setTransform(t);
					e.gc.drawImage(mScreenshot, 0, 0);
					// this resets the transformation to identity transform, i.e. no change
					// we don't use transformation here because it will cause the line pattern
					// and line width of highlight rect to be scaled, causing to appear to be blurry
					e.gc.setTransform(null);
					if (getmModel().shouldShowNafNodes()) {
						// highlight the "Not Accessibility Friendly" nodes
						e.gc.setForeground(e.gc.getDevice().getSystemColor(SWT.COLOR_YELLOW));
						e.gc.setBackground(e.gc.getDevice().getSystemColor(SWT.COLOR_YELLOW));
						for (Rectangle r : getmModel().getNafNodes()) {
							e.gc.setAlpha(50);
							e.gc.fillRectangle(mDx + getScaledSize(r.x), mDy + getScaledSize(r.y),
									getScaledSize(r.width), getScaledSize(r.height));
							e.gc.setAlpha(255);
							e.gc.setLineStyle(SWT.LINE_SOLID);
							e.gc.setLineWidth(2);
							e.gc.drawRectangle(mDx + getScaledSize(r.x), mDy + getScaledSize(r.y),
									getScaledSize(r.width), getScaledSize(r.height));
						}
					}
					// draw the mouseover rects
					Rectangle rect = getmModel().getCurrentDrawingRect();
					if (rect != null) {
						e.gc.setForeground(e.gc.getDevice().getSystemColor(SWT.COLOR_RED));
						if (getmModel().isExploreMode()) {
							// when we highlight nodes dynamically on mouse move,
							// use dashed borders
							e.gc.setLineStyle(SWT.LINE_DASH);
							e.gc.setLineWidth(1);
						} else {
							// when highlighting nodes on tree node selection,
							// use solid borders
							e.gc.setLineStyle(SWT.LINE_SOLID);
							e.gc.setLineWidth(2);
						}
						e.gc.drawRectangle(mDx + getScaledSize(rect.x), mDy + getScaledSize(rect.y),
								getScaledSize(rect.width), getScaledSize(rect.height));
					}
				}
			}
		});
		mScreenshotCanvas.addMouseMoveListener(new MouseMoveListener(){
			@Override
			public void mouseMove(MouseEvent e) {
				if (getmModel() != null && getmModel().isExploreMode()) {
					BasicTreeNode node = getmModel().updateSelectionForCoordinates(
							getInverseScaledSize(e.x - mDx),
							getInverseScaledSize(e.y - mDy));
					if (node != null) {
						updateTreeSelection(node);
					}
				}
			}
		});

		mSetScreenshotComposite = new Composite(mScreenshotComposite, SWT.NONE);
		mSetScreenshotComposite.setLayout(new GridLayout());

		final Button setScreenshotButton = new Button(mSetScreenshotComposite, SWT.PUSH);
		setScreenshotButton.setText("Specify Screenshot...");
		setScreenshotButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(setScreenshotButton.getShell());
				fd.setFilterExtensions(new String[] { "*.png" });
				if (mModelFile != null) {
					fd.setFilterPath(mModelFile.getParent());
				}
				String screenshotPath = fd.open();
				if (screenshotPath == null) {
					return;
				}

				ImageData[] data;
				try {
					data = new ImageLoader().load(screenshotPath);
				} catch (Exception e) {
					return;
				}

				// "data" is an array, probably used to handle images that has multiple frames
				// i.e. gifs or icons, we just care if it has at least one here
				if (data.length < 1) {
					return;
				}

				mScreenshot = new Image(Display.getDefault(), data[0]);
				redrawScreenshot();
			}
		});
	}


	private int getScaledSize(int size) {
		if (mScale == 1.0f) {
			return size;
		} else {
			return new Double(Math.floor((size * mScale))).intValue();
		}
	}

	private int getInverseScaledSize(int size) {
		if (mScale == 1.0f) {
			return size;
		} else {
			return new Double(Math.floor((size / mScale))).intValue();
		}
	}

	private void updateScreenshotTransformation() {
		Rectangle canvas = mScreenshotCanvas.getBounds();
		Rectangle image = mScreenshot.getBounds();
		float scaleX = (canvas.width - 2 * IMG_BORDER - 1) / (float)image.width;
		float scaleY = (canvas.height - 2 * IMG_BORDER - 1) / (float)image.height;
		// use the smaller scale here so that we can fit the entire screenshot
		mScale = Math.min(scaleX, scaleY);
		// calculate translation values to center the image on the canvas
		mDx = (canvas.width - getScaledSize(image.width) - IMG_BORDER * 2) / 2 + IMG_BORDER;
		mDy = (canvas.height - getScaledSize(image.height) - IMG_BORDER * 2) / 2 + IMG_BORDER;
	}

	/**
	 * Causes a redraw of the canvas.
	 *
	 * The drawing code of canvas will handle highlighted nodes and etc based on data
	 * retrieved from Model
	 */
	public void redrawScreenshot() {
		if (mScreenshot == null) {
			mStackLayout.topControl = mSetScreenshotComposite;
		} else {
			mStackLayout.topControl = mScreenshotCanvas;
		}
		mScreenshotComposite.layout();

		mScreenshotCanvas.redraw();
	}

	public void updateTreeSelection(BasicTreeNode node) {
		getXmlArea().updateTreeSelection(node);
	}

	public UiAutomatorModel getmModel() {
		return mModel;
	}

	public void setmModel(UiAutomatorModel mModel) {
		this.mModel = mModel;
	}

	public XMLArea getXmlArea() {
		return xmlArea;
	}

	public void setXmlArea(XMLArea xmlArea) {
		this.xmlArea = xmlArea;
	}

	public void setNewScreenShot(Image screenshot) {
		if (mScreenshot != null) {
			mScreenshot.dispose();
		}
		mScreenshot = screenshot;

		redrawScreenshot();
	}

}