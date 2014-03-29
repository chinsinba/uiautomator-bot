package ${package_name};

import java.io.File;

import android.os.Build;
import android.util.Log;
import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.*;
import static ${utility_import}.*;

/**
${description}

<br><b>NOTE:1.This class can contain only one test case.<br>
		2.Do not change the class name.</b>
*/
public class ${testCase_name} extends UiAutomatorTestCase {   

	private static String TAG = "${testCase_name}";
	
	/**
		@generated 
		All your test code will go here
		NOTE: Do not change the method signature
	*/
	public void test() throws UiObjectNotFoundException {  
			System.out.println("testcase execution started");
			//takeScreenShot();
			//CODE:START 
			
			
			//CODE:END
			System.out.println("testcase execution completed");
        }
    
}
 
