package in.BBAT.TestRunner.device;

import java.util.List;

import com.android.ddmlib.logcat.LogCatMessage;

public interface ILogListener {
	
	public void processLogLine(List<LogCatMessage> logMessages);

}
