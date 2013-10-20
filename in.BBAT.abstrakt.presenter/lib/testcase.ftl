package ${package_name};

import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.*;

/**
${description}

NOTE:This class can contain only one test case.
*/
public class ${testCase_name} extends UiAutomatorTestCase {   

	/**
	
	*/
	public void test${testCase_name}() throws UiObjectNotFoundException {  
	
			preRun();
			
			//Go to home
			getUiDevice().pressHome();
			
			//CODE:START 
			
			
			//CODE:END
			
			postRun();

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
}
 
