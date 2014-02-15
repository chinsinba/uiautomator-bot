package in.BBAT.TestRunner.Listener;

import java.util.List;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.logcat.LogCatMessage;
import com.android.ddmlib.logcat.LogCatReceiverTask;
import com.android.ddmuilib.logcat.LogCatReceiver;

public interface ILogListener {
	
	public void processLogLine(List<LogCatMessage> logMessages);
	
	public void startLogging(IDevice iDevice);

}
