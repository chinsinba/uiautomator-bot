package ${package_name};

import java.io.File;

import android.os.Build;
import android.util.Log;
import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.*;
import static ${utility_import}.*;

/**
${description}

NOTE:1.This class is a utility/helper class for your test cases.
			2.This class cannot have any test cases.<br>
			3.Do not change the class name.
			4.You can create as many methods you want.
		
*/
public class ${testCase_name} {   

	private static String TAG = "${testCase_name}";
	
	/** This method returns a UiDevice instance.
	*/
	public static UiDevice getDevice(){
		return UiDevice.getInstance();
	}
	
	/**
	This is an example method to explain the usage.
	You can call this method from your test case as follows
	${testCase_name}.helper_example_method();
	*/
	public static void helper_example_method(){
		 getDevice().pressHome();
		 takeScreenShot();
	}
	
	/**
	This method can be used to do some setup before the test case execution.
	*/
	public static void preTestCaseRun(){
	//CODE:START 
			
			
	//CODE:END
	}
	
	/**
	This method can be used to do some cleanup after the test case execution.
	*/
	public static void postTestCaseRun(){
	//CODE:START 
			
			
	//CODE:END
	}
}
 
