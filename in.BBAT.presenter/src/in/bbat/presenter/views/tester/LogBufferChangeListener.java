package in.bbat.presenter.views.tester;

import java.util.List;

import com.android.ddmlib.logcat.LogCatMessage;
import com.android.ddmuilib.logcat.ILogCatBufferChangeListener;

public class LogBufferChangeListener implements ILogCatBufferChangeListener {
	LogCatPanel panel;

	public LogBufferChangeListener(LogCatPanel panel) {
		this.panel =panel;
	}

	@Override
	public void bufferChanged(List<LogCatMessage> addedMessages, List<LogCatMessage> deletedMessages) {/*

		panel.updateUnreadCount(addedMessages);
		panel.refreshFiltersTable();

		synchronized (mLogBuffer) {
			addedMessages = applyCurrentFilters(addedMessages);
			deletedMessages = applyCurrentFilters(deletedMessages);

			mLogBuffer.addAll(addedMessages);
			mDeletedLogCount += deletedMessages.size();
		}
		panel.refreshLogCatTable();
	*/}

}
