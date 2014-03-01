package in.bbat.abstrakt.gui;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * This class will keep all the images needed in a {@link Map}.
 * For every new image an Image Id has to be created and the Image must be added to the {@link Map} 
 * along with Image Id as Key. 
 * @author syed Mehtab
 */

public class BBATImageManager 
{

	private static BBATImageManager imageManager_;
	private final String imageLocation ="icons"; 
	private Map<String, Image> imageMap;

	//Image IDs or Keys
	public static final String PROJECT_GIF_16="project.gif";
	public static final String TESTSUITE_GIF_16="suite.gif";
	public static final String TESTCASE_GIF_16="testcase.gif";
	public static final String ANDROID_DEVICE ="Android_device.png";
	public static final String PASS ="pass.png";
	public static final String FAIL ="fail.png";
	public static final String ERROR ="error.png";
	public static final String EXECUTING ="execute.gif";
	public static final String TIME ="time.png";
	public static final String TESTCASE_ERROR_GIF_8 ="error_co.gif";


	/**
	 * Every new image should be added in to the
	 *  <Variable>imageMap</Variable> via the add method.
	 */
	private BBATImageManager()
	{
		imageMap = new HashMap<String, Image>();
		addImage(PROJECT_GIF_16, "project.gif");
		addImage(TESTSUITE_GIF_16, "suite.gif");
		addImage(TESTCASE_GIF_16, "testcase.gif");
		addImage(ANDROID_DEVICE, ANDROID_DEVICE);
		addImage(PASS, PASS);
		addImage(FAIL, FAIL);
		addImage(ERROR, ERROR);
		addImage(EXECUTING, EXECUTING);
		addImage(TIME, TIME);
		addImage(TESTCASE_ERROR_GIF_8, TESTCASE_ERROR_GIF_8);
	}

	/**
	 * Singleton acces to {@link BBATImageManager} class
	 * @return
	 */
	public static BBATImageManager getInstance()
	{
		if(imageManager_==null)
			imageManager_=new BBATImageManager();
		return imageManager_;

	}

	/**
	 * 
	 * @param imageId This will go as the key for the image
	 * 			cannot be <code>null</code>
	 * @param imageName Image name
	 * 			cannot be <code>null</code>
	 */
	private void addImage(String imageId, String imageName)
	{
		imageMap.put(imageId,Activator.getImageDescriptor(imageLocation+Path.SEPARATOR+imageName).createImage());
	}

	public Image getImage(String imageId)
	{
		return imageMap.get(imageId);
	}

	public Image createImage(String absoluteImagePath) throws Exception{
		return ImageDescriptor.createFromURL(new URL(absoluteImagePath)).createImage();
	}
}
