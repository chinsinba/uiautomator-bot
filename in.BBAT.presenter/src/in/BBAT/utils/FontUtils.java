package in.BBAT.utils;

import in.BBAT.presenter.Activator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

public class FontUtils
{
	private static Map<String, Font> fontCache = new HashMap();
	private static final int smallFontSize;
	private static final int mediumFontSize;
	private static final int largeFontSize;
	private static final int reallyLargeFontSize;
	private static final String primaryFontName;
	private static final String secondaryFontName;
	private static final String widgetFontName;
	private static float customFontScaleFactor;
	private static boolean initialized = false;

	static
	{
		switch (FileSystem.getOS().ordinal())
		{
		case 1:
			primaryFontName = "Segoe UI";
			secondaryFontName = "Segoe UI";
			widgetFontName = "Segoe UI";
			smallFontSize = 8;
			mediumFontSize = 10;
			largeFontSize = 16;
			reallyLargeFontSize = 26;
			customFontScaleFactor = 0.8F;
			break;
		case 3:
			primaryFontName = "Proxima Nova";
			secondaryFontName = "Proxima Nova";
			widgetFontName = "Proxima Nova";
			smallFontSize = 8;
			mediumFontSize = 10;
			largeFontSize = 16;
			reallyLargeFontSize = 26;
			customFontScaleFactor = 0.8F;
			break;
		case 2:
		default:
			primaryFontName = "Proxima Nova";
			secondaryFontName = "Proxima Nova";
			widgetFontName = "Proxima Nova";
			smallFontSize = 10;
			mediumFontSize = 13;
			largeFontSize = 18;
			reallyLargeFontSize = 28;
			customFontScaleFactor = 1.0F;
		}
	}

	public static Font getPrimaryMediumFont(int paramInt)
	{
		return getFont(primaryFontName, mediumFontSize, paramInt);
	}

	public static Font getSecondaryMediumFont(int paramInt)
	{
		return getFont(secondaryFontName, mediumFontSize, paramInt);
	}

	public static Font getSecondarySmallFont(int paramInt)
	{
		return getFont(secondaryFontName, smallFontSize, paramInt);
	}

	public static Font getSecondaryCustomFont(int paramInt1, int paramInt2)
	{
		return getFont(secondaryFontName, (int)(paramInt1 * customFontScaleFactor), paramInt2);
	}

	public static Font getWidgetMediumFont(int paramInt)
	{
		return getFont(widgetFontName, mediumFontSize, paramInt);
	}

	public static Font getFont(String paramString, int paramInt1, int paramInt2)
	{
		if (!initialized)
			initialize();
		paramInt1 = DisplayUtils.getInstance().fontSizeForDisplay(paramInt1);
		if (paramInt1 <= 7)
			paramInt1 = 7;
		String str = paramString + paramInt1 + paramInt2;
		Font localFont = (Font)fontCache.get(str);
		if ((localFont == null) || (localFont.isDisposed()))
		{
			localFont = new Font(Display.getDefault(), paramString, paramInt1, paramInt2);
			fontCache.put(str, localFont);
		}
		return localFont;
	}

	public static String getPrimaryFontName()
	{
		return primaryFontName;
	}

	public static String getSecondaryFontName()
	{
		return secondaryFontName;
	}

	public static String getWidgetFontName()
	{
		return widgetFontName;
	}

	public static int getSmallFontSize()
	{
		return smallFontSize;
	}

	public static int getMediumFontSize()
	{
		return mediumFontSize;
	}

	public static int getLargeFontSize()
	{
		return largeFontSize;
	}

	public static int getReallyLargeFontSize()
	{
		return reallyLargeFontSize;
	}

	private static void initialize()
	{
		initialized = true;
		if (FileSystem.getOS() != FileSystem.OS.OSX)
			return;
		try
		{
			URL localURL = Activator.getDefault().getBundle().getResource("/fonts/ProximaNova-Bold.otf");
			localURL = FileLocator.toFileURL(localURL);
			String str = new File(localURL.getFile()).getAbsolutePath();
			boolean bool = Display.getDefault().loadFont(str);
			if (!bool)
				System.err.println("Unable to load regular font file. Using default fonts!");
			localURL = Activator.getDefault().getBundle().getResource("/fonts/ProximaNova-Reg.otf");
			localURL = FileLocator.toFileURL(localURL);
			str = new File(localURL.getFile()).getAbsolutePath();
			bool = Display.getDefault().loadFont(str);
			if (!bool)
				System.err.println("Unable to load bold font file. Using default fonts!");
		}
		catch (IOException localIOException)
		{
		}
	}
}
