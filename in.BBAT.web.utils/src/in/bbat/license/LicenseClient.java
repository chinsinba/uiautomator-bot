package in.bbat.license;

import in.bbat.web.WebServiceException;
import in.bbat.web.WebServiceHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class LicenseClient
{
  private static LicenseClient instance;

  public static LicenseClient getInstance()
  {
    if (instance == null)
      instance = new LicenseClient();
    return instance;
  }

  private String getURL(String paramString)
  {
    return "https://licensing.bbat.in" + paramString;
  }

  private void addStandardProperties(JSONObject paramJSONObject)
    throws JSONException
  {
    paramJSONObject.put("instanceId", LicenseManager.getInstanceId());
    paramJSONObject.put("macId", LicenseManager.getMacId());
    paramJSONObject.put("bbatEyeVersion", LicenseManager.getLittleEyeVersion());
  }

  public JSONObject registerActivationCode(String licActivationCode)
    throws WebServiceException
  {
    JSONObject licActJson = new JSONObject();
    try
    {
      licActJson.put("activationCode", licActivationCode);
      addStandardProperties(licActJson);
    }
    catch (JSONException localJSONException)
    {
      throw new WebServiceException("Error building JSON:" + localJSONException.getMessage(), localJSONException);
    }
    JSONObject localJSONObject2 = WebServiceHelper.makeRequest(getURL("/activation/register"), licActJson.toString());
    return localJSONObject2;
  }

  public JSONObject registerBetaTrial(String paramString1, String paramString2, String paramString3, String paramString4)
    throws WebServiceException
  {
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      localJSONObject1.put("email", paramString1);
      localJSONObject1.put("userName", paramString2);
      localJSONObject1.put("company", paramString3);
      localJSONObject1.put("designation", paramString4);
      addStandardProperties(localJSONObject1);
    }
    catch (JSONException localJSONException)
    {
      throw new WebServiceException("Error building JSON:" + localJSONException.getMessage(), localJSONException);
    }
    JSONObject localJSONObject2 = WebServiceHelper.makeRequest(getURL("/activation/beta/trial/new"), localJSONObject1.toString());
    return localJSONObject2;
  }

  public JSONObject registerTrial(String email, String userName, String company, String designation)
    throws WebServiceException
  {
    JSONObject regTrialJason = new JSONObject();
    try
    {
      regTrialJason.put("email", email);
      regTrialJason.put("userName", userName);
      regTrialJason.put("company", company);
      regTrialJason.put("designation", designation);
      addStandardProperties(regTrialJason);
    }
    catch (JSONException localJSONException)
    {
      throw new WebServiceException("Error building JSON:" + localJSONException.getMessage(), localJSONException);
    }
    JSONObject reponseJson = WebServiceHelper.makeRequest(getURL("/activation/trial/new"), regTrialJason.toString());
    return reponseJson;
  }

  public JSONObject renewLicense(String paramString)
    throws WebServiceException
  {
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      localJSONObject1.put("activationCode", paramString);
      addStandardProperties(localJSONObject1);
    }
    catch (JSONException localJSONException)
    {
      throw new WebServiceException("Error building JSON:" + localJSONException.getMessage(), localJSONException);
    }
    JSONObject localJSONObject2 = WebServiceHelper.makeRequest(getURL("/activation/license/renew"), localJSONObject1.toString());
    return localJSONObject2;
  }

  public JSONObject checkoutBetaLicense(LicenseInfo paramLicenseInfo)
    throws WebServiceException
  {
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      localJSONObject1.put("activationCode", paramLicenseInfo.getActivationCode());
      localJSONObject1.put("email", paramLicenseInfo.getUserEmail());
      addStandardProperties(localJSONObject1);
    }
    catch (JSONException localJSONException)
    {
      throw new WebServiceException("Error building JSON:" + localJSONException.getMessage(), localJSONException);
    }
    JSONObject localJSONObject2 = WebServiceHelper.makeRequest(getURL("/activation/beta/license/checkout"), localJSONObject1.toString());
    return localJSONObject2;
  }

  public JSONObject checkoutLicense(LicenseInfo paramLicenseInfo) throws WebServiceException
  {
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      localJSONObject1.put("activationCode", paramLicenseInfo.getActivationCode());
      addStandardProperties(localJSONObject1);
    }
    catch (JSONException localJSONException)
    {
      throw new WebServiceException("Error building JSON:" + localJSONException.getMessage(), localJSONException);
    }
    JSONObject localJSONObject2 = WebServiceHelper.makeRequest(getURL("/activation/license/checkout"), localJSONObject1.toString());
    return localJSONObject2;
  }

  public JSONObject returnLicense(String paramString1, String paramString2)
    throws WebServiceException
  {
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      addStandardProperties(localJSONObject1);
      localJSONObject1.put("activationCode", paramString1);
      localJSONObject1.put("instanceId", paramString2);
    }
    catch (JSONException localJSONException)
    {
      throw new WebServiceException("Error building JSON:" + localJSONException.getMessage(), localJSONException);
    }
    JSONObject localJSONObject2 = WebServiceHelper.makeRequest(getURL("/activation/license/return"), localJSONObject1.toString());
    return localJSONObject2;
  }

  public JSONObject logProfileStart(String paramString1, String paramString2)
    throws WebServiceException
  {
    if ((paramString2 == null) || (paramString2.trim().isEmpty()))
      paramString2 = new SimpleDateFormat(LicenseManager.getLogDateFormat(), Locale.ROOT).format(new Date());
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      localJSONObject1.put("activationCode", paramString1);
      localJSONObject1.put("timestamp", paramString2);
      addStandardProperties(localJSONObject1);
    }
    catch (JSONException localJSONException)
    {
      throw new WebServiceException("Error building JSON:" + localJSONException.getMessage(), localJSONException);
    }
    JSONObject localJSONObject2 = WebServiceHelper.makeRequest(getURL("/activation/license/profilestart"), localJSONObject1.toString());
    return localJSONObject2;
  }

  public JSONObject redeemPromoCode(String paramString1, String paramString2)
    throws WebServiceException
  {
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      localJSONObject1.put("activationCode", paramString1);
      localJSONObject1.put("promoCode", paramString2);
      addStandardProperties(localJSONObject1);
    }
    catch (JSONException localJSONException)
    {
      throw new WebServiceException("Error building JSON:" + localJSONException.getMessage(), localJSONException);
    }
    JSONObject localJSONObject2 = WebServiceHelper.makeRequest(getURL("/activation/promo/redeem"), localJSONObject1.toString());
    return localJSONObject2;
  }
}

