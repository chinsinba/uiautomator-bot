package in.bbat.license;

import in.bbat.logger.BBATLogger;
import in.bbat.web.WebServiceException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.FileLock;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.json.JSONException;
import org.json.JSONObject;

public class LicenseManager
{
	private static String licenseDateFormat = "yyyy.MM.dd HH:mm:ss z";
	private static boolean serverRechable = false;
	private static FileLock lock;
	private static File licenseLockFile;
	private static RandomAccessFile randomAccessFileObject;
	private static final Logger LOG = BBATLogger.getLogger(LicenseManager.class.getName());

	public static boolean registerActivationCode(String activationCode)   throws ActivationCodeValidationException
	{
		activationCode = activationCode.trim();
		try
		{
			JSONObject response = LicenseClient.getInstance().registerActivationCode(activationCode);
			String status = response.getString("status");
			if (!status.equals("success"))
				throw new ActivationCodeValidationException(response.getString("message"));
			storeLicenseInfo(response);
			LicenseInfo localLicenseInfo = getCurrentLicense();
			if (!localLicenseInfo.isCheckedOut())
				throw new ActivationCodeValidationException("The license code could not be checked out! " + localLicenseInfo.getCheckedOutError());
		}
		catch (WebServiceException localWebServiceException)
		{
			LOG.error(localWebServiceException);
			throw new ActivationCodeValidationException(localWebServiceException.getServerResponse(), localWebServiceException);
		}
		catch (Exception localException)
		{
			throw new ActivationCodeValidationException("Internal Error:" + localException.getMessage(), localException);
		}
		return true;
	}

	public static void storeLicenseFromString(String paramString)
	{
		try
		{
			JSONObject localJSONObject = new JSONObject();
			localJSONObject.put("license", new JSONObject(paramString));
			storeLicenseInfo(localJSONObject);
		}
		catch (Exception localException)
		{
			LOG.error("Tried to set a license string, but it was not valid. License was not updated.The License was (" + paramString + ")" + "The underlying error was:" + localException.getMessage(), localException);
		}
	}

	private static void storeLicenseInfo(JSONObject registerResponse)throws JSONException, ActivationCodeValidationException, UnsupportedEncodingException
	{
		JSONObject localJSONObject = registerResponse.getJSONObject("license");
		localJSONObject.put("instanceId", getInstanceId());
		String license = localJSONObject.toString();
		String encodedLicense = new String(Base64.encode(license.getBytes("UTF-8")));
		System.setProperty("license.activation.enc", EncryptionManager.encrypt(encodedLicense));
	}

	private static LicenseInfo parseLicenseJson(JSONObject jsonObj)throws JSONException
	{
		LicenseInfo licInfo = new LicenseInfo();
		licInfo.setOriginalJsonString(jsonObj);
		try
		{
			licInfo.setActivationCode(jsonObj.getString("activationCode"));
			licInfo.setLicenseVersion(jsonObj.getString("version"));
			licInfo.setInstanceId(getInstanceId());
			licInfo.setIsTrial(jsonObj.getString("licenseType").toUpperCase().contains("TRIAL"));
			licInfo.setIsCommunity(jsonObj.getString("licenseType").toUpperCase().contains("COMMUNITY"));
			licInfo.setUserEmail(jsonObj.getString("userEmail"));
			licInfo.setLicenseType(jsonObj.getString("licenseType"));
			licInfo.setCheckedOut(jsonObj.getString("checkedOut"));
			if (jsonObj.has("licenseDisplayName"))
				licInfo.setLicenseDisplayName(jsonObj.getString("licenseDisplayName"));
			if (jsonObj.has("enableUsageTracking"))
				licInfo.setUsageAnalyticsEnabled(jsonObj.getBoolean("enableUsageTracking"));
			try
			{
				licInfo.setExpiryDate(new SimpleDateFormat(licenseDateFormat).parse(jsonObj.getString("licenseBasisValue")));
			}
			catch (ParseException localParseException2)
			{
				licInfo.setExpiryDate(new SimpleDateFormat(getLogDateFormat()).parse(jsonObj.getString("licenseBasisValue")));
			}
		}
		catch (ParseException localParseException1)
		{
			throw new JSONException(localParseException1);
		}
		catch (NumberFormatException localNumberFormatException)
		{
			throw new JSONException(localNumberFormatException);
		}
		return licInfo;
	}

	public static boolean isLicenseValid(String paramString)
	{
		try
		{
			parseLicenseJson(new JSONObject(paramString));
		}
		catch (JSONException localJSONException)
		{
			return false;
		}
		return true;
	}

	private static LicenseInfo parseLicenseBase64(String paramString)
	{
		if ((paramString == null) || (paramString.trim().isEmpty()))
			return null;
		try
		{
			byte[] arrayOfByte = Base64.decode(paramString.getBytes("UTF-8"));
			String str = new String(arrayOfByte, "UTF-8");
			if ((str != null) && (!str.trim().isEmpty()))
			{
				LicenseInfo localLicenseInfo = parseLicenseJson(new JSONObject(str));
				return localLicenseInfo;
			}
		}
		catch (Exception localException)
		{
		}
		return null;
	}

	private static LicenseInfo parseLicenseEncrypted(String encryptedLic)
	{
		if ((encryptedLic == null) || (encryptedLic.trim().isEmpty()))
			return null;
		String decryptedLic = EncryptionManager.decrypt(encryptedLic);
		if ((decryptedLic == null) || (decryptedLic.isEmpty()))
			return null;
		try
		{
			byte[] arrayOfByte = Base64.decode(decryptedLic.getBytes("UTF-8"));
			String decodedLic = new String(arrayOfByte, "UTF-8");
			if ((decodedLic == null) || (decodedLic.isEmpty()))
				return null;
			return parseLicenseJson(new JSONObject(decodedLic));
		}
		catch (JSONException localJSONException)
		{
			LOG.error("Error parsing the license string to JSON:(" + decryptedLic + ")" + localJSONException.getMessage(), localJSONException);
			return null;
		}
		catch (Throwable localThrowable)
		{
			LOG.error( "Invalid License file found:" + localThrowable.getMessage(), localThrowable);
		}
		return null;
	}

	public static LicenseInfo getCurrentLicense()
	{
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new File("/home/syed/.littleeye/workspace/.metadata/.plugins/com.littleeyelabs.utils/littleeye.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str = p.getProperty("license.activation.enc");
		LicenseInfo localLicenseInfo = parseLicenseEncrypted(str);
		if (localLicenseInfo != null)
			return localLicenseInfo;
		str = System.getProperty("license.activation");
		localLicenseInfo = parseLicenseBase64(str);
		if (localLicenseInfo != null)
			return localLicenseInfo;
		localLicenseInfo = parseLicenseEncrypted(str);
		if (localLicenseInfo != null)
			return localLicenseInfo;
		return null;
	}

	public static String getUserId()
	{
		String str = System.getProperty("user.id");
		if (str == null)
		{
			str = UUID.randomUUID().toString();
			System.setProperty("user.id", str);
		}
		return str;
	}

	public static String getInstanceId()
	{
		String str = getUserId();
		return str + "/" + ManagementFactory.getRuntimeMXBean().getName();
	}

	public static String getMacId()
	{
		try
		{
			InetAddress localInetAddress = InetAddress.getLocalHost();
			NetworkInterface localNetworkInterface = NetworkInterface.getByInetAddress(localInetAddress);
			byte[] arrayOfByte = localNetworkInterface.getHardwareAddress();
			StringBuilder localStringBuilder = new StringBuilder();
			for (int i = 0; i < arrayOfByte.length; i++)
				localStringBuilder.append(String.format("%02X%s", new Object[] { Byte.valueOf(arrayOfByte[i]), i < arrayOfByte.length - 1 ? "-" : "" }));
			return localStringBuilder.toString();
		}
		catch (Exception localException)
		{
		}
		return "";
	}

	public static boolean isUsageAnalyticsTrackingEnabled()
	{
		if (getCurrentLicense() == null)
			return true;
		return getCurrentLicense().isUsageAnalyticsTrackingEnabled();
	}

	public static boolean registerNewTrial(String email, String userName, String compName, String designation)	throws ActivationCodeValidationException
	{
		try
		{
			JSONObject response = LicenseClient.getInstance().registerTrial(email, userName, compName, designation);
			String status = response.getString("status");
			if (!status.equals("success"))
				throw new ActivationCodeValidationException("There was an error while registering for a new trial license: " + response.getString("message"));
			storeLicenseInfo(response);
		}
		catch (WebServiceException localWebServiceException)
		{
			LOG.error( "Could not send email for trial activation code:" + localWebServiceException.getMessage(), localWebServiceException);
			throw new ActivationCodeValidationException(localWebServiceException.getServerResponse(), localWebServiceException);
		}
		catch (Exception localException)
		{
			throw new ActivationCodeValidationException("Internal Error:" + localException.getMessage(), localException);
		}
		return true;
	}

	public static JSONObject getLocallyGeneratedLicense(String paramString)
	{
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(new Date());
		localCalendar.add(1, 99);
		Date localDate = localCalendar.getTime();
		JSONObject localJSONObject = new JSONObject();
		try
		{
			localJSONObject.put("licenseType", "LittleEye");
			localJSONObject.put("licenseDisplayName", "LittleEye");
			localJSONObject.put("licenseBasis", "TIME_BOUND");
			localJSONObject.put("licenseBasisValue", new SimpleDateFormat(getLogDateFormat()).format(localDate));
			localJSONObject.put("created", new SimpleDateFormat(getLogDateFormat()).format(new Date()));
			localJSONObject.put("bindType", "USER_EMAIL");
			localJSONObject.put("bindAgent", paramString);
			localJSONObject.put("userEmail", paramString);
			localJSONObject.put("numLicenses", 1);
			localJSONObject.put("activationCode", "AAAAA-AAAAA-AAAAA-AAAAA");
			localJSONObject.put("verified", "true");
			localJSONObject.put("version", "1.0");
			localJSONObject.put("checkedOut", "success");
			return localJSONObject;
		}
		catch (Exception localException)
		{
			LOG.error("Error constructing a local license object", localException);
		}
		return null;
	}

	public static LicenseInfo getUnlicensedLicenseInfo()
	{
		LicenseInfo localLicenseInfo = new LicenseInfo();
		localLicenseInfo.setExpiryDate(new Date());
		localLicenseInfo.setActivationCode("none");
		localLicenseInfo.setLicenseType("unlicensed");
		localLicenseInfo.setLicenseVersion("");
		localLicenseInfo.setUserEmail("none");
		return localLicenseInfo;
	}

	public static LicenseInfo checkoutLicense(LicenseInfo paramLicenseInfo)
	{
		try
		{
			if (paramLicenseInfo == null)
				return null;
			JSONObject localJSONObject = LicenseClient.getInstance().checkoutLicense(paramLicenseInfo);
			if (localJSONObject.getString("status").equals("success"))
			{
				storeLicenseInfo(localJSONObject);
				createLicenseLockFile();
				serverRechable = true;
				return parseLicenseJson(localJSONObject.getJSONObject("license"));
			}
		}
		catch (Exception localException)
		{
			serverRechable = false;
			LOG.error( "Error while trying to checkout a license: " + localException.getMessage());
		}
		return null;
	}

	private static void createLicenseLockFile()
	{
		if (lock != null)
			return;
		File localFile = new File("");/*InternalProperties.getLicenseLockFileDirectory()*/;
		try
		{
			licenseLockFile = File.createTempFile("littleeye", ".license.lock", localFile);
			FileUtils.write(licenseLockFile, getInstanceId());
			try
			{
				randomAccessFileObject = new RandomAccessFile(licenseLockFile, "rw");
				lock = randomAccessFileObject.getChannel().tryLock();
			}
			catch (Exception localException)
			{
				LOG.error( "Could not create a license lock file. Continuing without a license lock file", localException);
			}
		}
		catch (IOException localIOException)
		{
			LOG.error( "Could not create a license lock file. Continuing without a license lock file", localIOException);
		}
	}

	public static void cleanupLockFiles()
	{
		if (lock != null)
			try
		{
				lock.release();
		}
		catch (IOException localIOException1)
		{
		}
		if (randomAccessFileObject != null)
			try
		{
				randomAccessFileObject.close();
		}
		catch (IOException localIOException2)
		{
		}
		if ((licenseLockFile != null) && (licenseLockFile.exists()))
			licenseLockFile.delete();
	}

	public static void logStartProfile(String paramString)
	{
		LicenseInfo localLicenseInfo = getCurrentLicense();
		try
		{
			LicenseClient.getInstance().logProfileStart(localLicenseInfo.getActivationCode(), paramString);
		}
		catch (WebServiceException localWebServiceException)
		{
			LOG.error("Error while logging profileStart to the server:" + localWebServiceException.getMessage(), localWebServiceException);
		}
	}

	public static void returnLicense(LicenseInfo paramLicenseInfo)
	{
		if (paramLicenseInfo == null)
			return;
		try
		{
			LicenseClient.getInstance().returnLicense(paramLicenseInfo.getActivationCode(), paramLicenseInfo.getInstanceId());
		}
		catch (WebServiceException localWebServiceException)
		{
			LOG.error("Error while returning the license back to the server:" + localWebServiceException.getMessage(), localWebServiceException);
		}
	}

	public static String getLittleEyeVersion()
	{
		return "";
	}

	public static void returnOlderLicense(String paramString)
	{
		LicenseInfo localLicenseInfo = getCurrentLicense();
		localLicenseInfo.setInstanceId(paramString);
		returnLicense(localLicenseInfo);
	}

	public static void cleanupOlderLicenseFiles()
	{
		File localFile1 = new File("");
		File[] arrayOfFile1 = localFile1.listFiles(new FilenameFilter()
		{
			public boolean accept(File paramAnonymousFile, String paramAnonymousString)
			{
				return paramAnonymousString.endsWith(".license.lock");
			}
		});
		for (File localFile2 : arrayOfFile1)
		{
			FileLock localFileLock = null;
			RandomAccessFile localRandomAccessFile = null;
			try
			{
				localRandomAccessFile = new RandomAccessFile(localFile2, "rw");
				localFileLock = localRandomAccessFile.getChannel().tryLock();
			}
			catch (Exception localException)
			{
				localFileLock = null;
			}
			if (localFileLock != null)
			{
				try
				{
					localFileLock.release();
					localRandomAccessFile.close();
				}
				catch (IOException localIOException1)
				{
					LOG.error( "Error releasing an acquired lock. No problem, continuing!");
				}
				String str = null;
				try
				{
					str = FileUtils.readFileToString(localFile2);
					returnOlderLicense(str);
					localFile2.delete();
				}
				catch (IOException localIOException2)
				{
					LOG.error( "Error returning an older license that is no longer valid. No problem, continuing!");
				}
			}
		}
	}

	public static String getLogDateFormat()
	{
		return "EEE MMM dd HH:mm:ss z yyyy";
	}

	public static LicenseInfo redeemPromoCode(String paramString)
			throws ActivationCodeValidationException
			{
		try
		{
			LicenseInfo localLicenseInfo = getCurrentLicense();
			if (localLicenseInfo == null)
				return null;
			JSONObject localJSONObject = LicenseClient.getInstance().redeemPromoCode(localLicenseInfo.getActivationCode(), paramString);
			if (localJSONObject.getString("status").equals("success"))
			{
				storeLicenseInfo(localJSONObject);
				return parseLicenseJson(localJSONObject.getJSONObject("license"));
			}
			throw new ActivationCodeValidationException(localJSONObject.getString("message"));
		}
		catch (WebServiceException localWebServiceException)
		{
			LOG.error( "Error while trying to redeem a promo code: " + localWebServiceException.getMessage());
			throw new ActivationCodeValidationException(localWebServiceException.getServerResponse(), localWebServiceException);
		}
		catch (Exception localException)
		{
			LOG.error( "Unexpected Error while trying to redeem a promo code: " + localException.getMessage());
			throw new ActivationCodeValidationException(localException.getMessage(), localException);
		}
			}

	public static boolean isLicenseServerReachable()
	{
		return serverRechable;
	}
}

