package ${package_name};

import java.io.File;

import android.os.Build;
import android.util.Log;
import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.*;

/**
${description}

<br><b>NOTE:1.This class can contain only one test case.<br>
		2.Do not change the class name.</b>
*/
public class ${testCase_name} extends UiAutomatorTestCase {   

	private static String TAG = "${testCase_name}";
	
	//@generated Do not change this path
	private static String SCREENSHOTPATH = "/data/local/tmp/"+${testCase_name}.class.getName()+"/";
	
	/**
		@generated 
		All your test code will go here
		NOTE: Do not change the method signature
	*/
	public void test() throws UiObjectNotFoundException {  
			Log.i(TAG,"testcase execution started");
			
			takeShot();
			
			//CODE:START 
			
			
			//CODE:END
			
			Log.i(TAG,"testcase execution completed");
        }
    
       
     /**
        @generated 
        Creates a screen shot this method should not be changed
	 */
	public void takeShot(){
		if(Build.VERSION.SDK_INT<17)
		{
			Log.i(TAG,"Screen shot is not available");
			return;
		}

		File dir = new File(SCREENSHOTPATH);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		getUiDevice().takeScreenshot(new File(dir,System.currentTimeMillis()+".png"));	
		Log.i(TAG,"Screen shot taken");
	}
}
 
