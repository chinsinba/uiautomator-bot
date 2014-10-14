package in.BBAT.utils;

import in.bbat.utility.IBBATConstants;

import java.io.File;

public class FileSystem
{
  public static final String[] osnames = { "win", "osx", "linux", "non-exist" };
  private static OS os = OS.WINDOWS;
  private static boolean inited = false;
  public static String QUOTE = "";

  public static OS getOS()
  {
    if (inited)
      return os;
    String str = System.getProperty("os.name").toLowerCase();
    if (str.indexOf("win") >= 0)
    {
      os = OS.WINDOWS;
      QUOTE = "\"";
    }
    else if (str.indexOf("mac") >= 0)
    {
      os = OS.OSX;
    }
    else if (str.indexOf("linux") >= 0)
    {
      os = OS.LINUX;
    }
    else
    {
      os = OS.OTHER;
    }
    return os;
  }

  public static String getHomeDirectory()
  {
    boolean bool = true;
    File homeDir = null;
    String str = null;
    switch (getOS().ordinal())
    {
    case 2:
    case 3:
      str = System.getProperty(IBBATConstants.USER_HOME_PROPERTY);
      homeDir = new File(str);
      bool = homeDir.exists();
      break;
    case 1:
      str = System.getProperty(IBBATConstants.USER_HOME_PROPERTY);
      homeDir = new File(str);
      bool = homeDir.exists();
      break;
    default:
      bool = false;
    }
    if (bool)
      return homeDir.getAbsolutePath();
    return null;
  }

  public static String getOSSafeFileName(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int j = 0;
    int k = paramString.length();
    while (j < k)
    {
      char c = paramString.charAt(j);
      if (Character.isLetterOrDigit(c))
      {
        localStringBuffer.append(c);
        i = 0;
      }
      else if (c == '_')
      {
        localStringBuffer.append(c);
      }
      else if (i == 0)
      {
        localStringBuffer.append('_');
        i = 1;
      }
      j++;
    }
    if (i != 0)
      localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
    paramString = localStringBuffer.toString();
    return paramString;
  }

  public static enum OS
  {
    WINDOWS, OSX, LINUX, OTHER;
  }
}
