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
	
	/**
		Test method.
	
		NOTE: Do not change the method signature
	*/
	public void test() throws UiObjectNotFoundException {  
			Log.i(TAG,"testcase execution started");
			
			preRun();
			
			//CODE:START 
			
			
			//CODE:END
			
			postRun();
			
			Log.i(TAG,"testcase execution completed");
        }
    
    /**
    	Should be called for setting up the test execution environment. 
    */
    private void preRun(){
    
    }
    
    /**
        Should be called to clean up after testcase execution. 
    */
    private void postRun(){
    
    }
    
     /**
        Creates a screen shot this method should not be changed
        @generated 
    */
    public void takeShot(){
		if(Build.VERSION.SDK_INT<17)
			return;
		getUiDevice().takeScreenshot(new File("/data/local/tmp/"+System.currentTimeMillis()+".png"));	
		Log.i(TAG,"screen shot taken");
	}
}
 
