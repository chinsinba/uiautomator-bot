package in.bbat.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class DisplayUtils
{
  private static DisplayUtils instance;
  private Display currentDisplay = Display.getCurrent();
  private DisplayClass currentDisplayClass = getDisplayClass(this.currentDisplay);
  private Color macOSBackground = null;

  public static DisplayUtils getInstance()
  {
    if (instance == null)
      instance = new DisplayUtils();
    return instance;
  }

  public int fontSizeForDisplay(int paramInt)
  {
    switch (this.currentDisplayClass.ordinal())
    {
    case 5:
      return paramInt - 2;
    case 4:
      return paramInt - 1;
    case 3:
      return paramInt;
    case 2:
      return paramInt + 1;
    case 1:
      return paramInt + 2;
    }
    return paramInt;
  }

  public int gridSpacingForDisplay(int paramInt)
  {
    switch (this.currentDisplayClass.ordinal())
    {
    case 5:
      return paramInt / 3;
    case 4:
      return paramInt / 2;
    case 3:
      return paramInt;
    case 2:
      return paramInt * 2;
    case 1:
      return (int)(paramInt * 2.5D);
    }
    return paramInt;
  }

  public float getImageScalingFactor()
  {
    switch (this.currentDisplayClass.ordinal())
    {
    case 5:
      return 0.65F;
    case 4:
      return 0.85F;
    case 3:
      return 1.0F;
    case 2:
      return 1.15F;
    case 1:
      return 1.35F;
    }
    return 1.0F;
  }

  public Color getMacOSBackgroundColor()
  {
    if (this.macOSBackground != null)
      return this.macOSBackground;
    String str = SWT.getPlatform();
    if ((str.equals("cocoa")) || (str.equals("carbon")))
      this.macOSBackground = new Color(Display.getCurrent(), 246, 246, 246);
    else
      this.macOSBackground = null;
    return this.macOSBackground;
  }

  private DisplayClass getDisplayClass(Display paramDisplay)
  {
    int i = paramDisplay.getBounds().height;
    if (i <= 768)
      return DisplayClass.DisplayClass_768;
    if (i <= 800)
      return DisplayClass.DisplayClass_800;
    if (i <= 900)
      return DisplayClass.DisplayClass_900;
    if (i <= 1050)
      return DisplayClass.DisplayClass_1050;
    return DisplayClass.DisplayClass_1280;
  }

  private static enum DisplayClass
  {
    DisplayClass_1280, DisplayClass_1050, DisplayClass_900, DisplayClass_800, DisplayClass_768;
  }
}
