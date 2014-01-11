package in.bbat.license;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

public class LicenseInfo
{
  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  private String activationCode;
  private String instanceId;
  private String userId;
  private String licenseVersion;
  private Date expiryDate;
  private boolean isTrial;
  private boolean isCommunity;
  private String userEmail;
  private String licenseType;
  private boolean isCheckedOut;
  private String checkedOutError;
  private String licenseString;
  private boolean usageAnalyticsEnabled = true;
  private String licenseDisplayName;

  public boolean isCheckedOut()
  {
    return this.isCheckedOut;
  }

  public void setCheckedOut(String paramString)
  {
    this.isCheckedOut = "success".equals(paramString);
    if (this.isCheckedOut)
      this.checkedOutError = null;
    else
      this.checkedOutError = paramString;
  }

  public String getCheckedOutError()
  {
    return this.checkedOutError;
  }

  public String getActivationCode()
  {
    return this.activationCode;
  }

  public void setActivationCode(String paramString)
  {
    this.activationCode = paramString;
  }

  public String getInstanceId()
  {
    if (this.instanceId == null)
      this.instanceId = LicenseManager.getInstanceId();
    return this.instanceId;
  }

  public String getUserId()
  {
    if (this.userId == null)
      this.userId = LicenseManager.getUserId();
    return this.userId;
  }

  public void setInstanceId(String paramString)
  {
    this.instanceId = paramString;
  }

  public String getLicenseVersion()
  {
    return this.licenseVersion;
  }

  public void setLicenseVersion(String paramString)
  {
    this.licenseVersion = paramString;
  }

  public void setExpiryDate(Date paramDate)
  {
    this.expiryDate = paramDate;
  }

  public Date getExpiryDate()
  {
    return this.expiryDate;
  }

  public String getExpiryDateNice()
  {
    return sdf.format(getExpiryDate());
  }

  public boolean isExpired()
  {
    Date localDate = new Date();
    return localDate.after(this.expiryDate);
  }

  public void setIsTrial(boolean paramBoolean)
  {
    this.isTrial = paramBoolean;
  }

  public boolean isTrial()
  {
    return this.isTrial;
  }

  public void setIsCommunity(boolean paramBoolean)
  {
    this.isCommunity = paramBoolean;
  }

  public boolean isCommunity()
  {
    return this.isCommunity;
  }

  public boolean isValid()
  {
    return !isExpired();
  }

  public String getUserEmail()
  {
    return this.userEmail;
  }

  public void setUserEmail(String paramString)
  {
    this.userEmail = paramString;
  }

  public String getLicenseType()
  {
    return this.licenseType;
  }

  public void setLicenseType(String paramString)
  {
    this.licenseType = paramString;
  }

  public String getUserName()
  {
    return "User@" + getHostname() + "." + System.getProperty("os.name");
  }

  private String getHostname()
  {
    String str = null;
    try
    {
      str = InetAddress.getLocalHost().getHostName();
    }
    catch (UnknownHostException localUnknownHostException)
    {
    }
    if (str == null)
      str = "<unknown>";
    return str;
  }

  private static String getLoggedInUser()
  {
    String str = System.getProperty("user.name");
    if (str == null)
      str = "<unknown user>";
    return str;
  }

  public String getFirstName()
  {
    return "User";
  }

  public String getLastName()
  {
    return getHostname() + "." + System.getProperty("os.name");
  }

  public String getCreatedDate()
  {
    return System.getProperty("user.created.on");
  }

  public String getInitialVersion()
  {
    return System.getProperty("user.initial.version");
  }

  public void setOriginalJsonString(JSONObject paramJSONObject)
  {
    this.licenseString = paramJSONObject.toString();
  }

  public void setUsageAnalyticsEnabled(boolean paramBoolean)
  {
    this.usageAnalyticsEnabled = paramBoolean;
  }

  public boolean isUsageAnalyticsTrackingEnabled()
  {
    return this.usageAnalyticsEnabled;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LicenseInfo))
      return false;
    LicenseInfo localLicenseInfo = (LicenseInfo)paramObject;
    if ((localLicenseInfo.licenseString == null) && (this.licenseString == null))
      return true;
    if ((localLicenseInfo.licenseString == null) || (this.licenseString == null))
      return false;
    return ((LicenseInfo)paramObject).licenseString.equals(this.licenseString);
  }

  public boolean isVerificationPending()
  {
    return "VERIFICATION_PENDING".equals(getLicenseType());
  }

  public void setLicenseDisplayName(String paramString)
  {
    this.licenseDisplayName = paramString;
  }

  public String getLicenseDisplayName()
  {
    if ((this.licenseDisplayName == null) || (this.licenseDisplayName.trim().isEmpty()))
      return getLicenseType();
    return this.licenseDisplayName;
  }
}

