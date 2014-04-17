package in.BBAT.presenter.internal;

import in.BBAT.TestRunner.Listener.ILogListener;
import in.BBAT.TestRunner.Listener.IScreenShotListener;
import in.BBAT.abstrakt.presenter.run.model.DeviceLogModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.presenter.views.tester.TestLogView;
import in.bbat.logger.BBATLogger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.logcat.LogCatMessage;

public class DeviceLogListener implements ILogListener, IScreenShotListener{

	private static final Logger LOG = BBATLogger.getLogger(DeviceLogListener.class.getName());
	private TestRunInstanceModel testRunCase;

	private int no =0;

	public DeviceLogListener(TestRunInstanceModel runCaseObj) {
		this.testRunCase = runCaseObj;
	}

	@Override
	public void processLogLine(final List<LogCatMessage> logMessages) {
		for(LogCatMessage message : logMessages){
			testRunCase.saveDeviceLog(message);
			/*DeviceLogModel log  = new DeviceLogModel(testRunCase,message);
			log.save();*/
//			testRunCase.addDeviceLog(log);
		}
	}

	@Override
	public void startLogging(IDevice iDevice) {
	}

	@Override
	public void processScreenshot(RawImage rawImage) {

		/*PaletteData palette = new PaletteData(rawImage.getRedMask(),rawImage.getGreenMask(),rawImage.getBlueMask());
		ImageData imageData = new ImageData(rawImage.width, rawImage.height,rawImage.bpp, palette, 1, rawImage.data);
		imageData =imageData.scaledTo(250, 350);
		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { imageData };
		File f = new File("/home/syed/Desktop/accc/"+testRunCase.getName()+testRunCase.getId());
		f.mkdirs();
		loader.save(f.getAbsolutePath()+"/screen"+no+".png", SWT.IMAGE_PNG);
		no++;*/
	}

}
