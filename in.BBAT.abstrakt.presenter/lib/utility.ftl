package ${package_name};

import java.io.File;

import android.os.Build;
import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.*;

/**
  
  ${description}

*/
public class ${testCase_name} {   

	private static String TAG = "${testCase_name}";
	
	//@generated Do not change this path
	private static String SCREENSHOTPATH = "/data/local/tmp/"+TAG+"/";

	public static void takeScreenShot(){
		if(Build.VERSION.SDK_INT<17)
		{
			System.out.println("Screen shot is not available");
			return;
		}

		File dir = new File(SCREENSHOTPATH);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		UiDevice.getInstance().takeScreenshot(new File(dir,System.currentTimeMillis()+".png"));	
		System.out.println("Screen shot taken");
	}
	
}
 
