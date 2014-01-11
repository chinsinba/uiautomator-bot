package in.bbat.license;

import in.bbat.logger.BBATLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

public class EncryptionManager
{
	static String password = "@#uio[plmzmapsdiojcv";
	private static final Logger LOG = BBATLogger.getLogger(EncryptionManager.class.getName());

	public static String encrypt(String paramString)
	{
		try
		{
			DESKeySpec localDESKeySpec = new DESKeySpec(password.getBytes("UTF8"));
			SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey localSecretKey = localSecretKeyFactory.generateSecret(localDESKeySpec);
			byte[] arrayOfByte = paramString.getBytes("UTF8");
			Cipher localCipher = Cipher.getInstance("DES");
			localCipher.init(1, localSecretKey);
			String str = new String(Base64.encode(localCipher.doFinal(arrayOfByte)), "UTF-8");
			return str;
		}
		catch (Exception localException)
		{
			LOG.error(localException);
		}
		return null;
	}

	public static String decrypt(String paramString)
	{
		try
		{
			DESKeySpec localDESKeySpec = new DESKeySpec(password.getBytes("UTF8"));
			SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey localSecretKey = localSecretKeyFactory.generateSecret(localDESKeySpec);
			byte[] arrayOfByte1 = Base64.decode(paramString);
			Cipher localCipher = Cipher.getInstance("DES");
			localCipher.init(2, localSecretKey);
			byte[] arrayOfByte2 = localCipher.doFinal(arrayOfByte1);
			return new String(arrayOfByte2, "UTF-8");
		}
		catch (Exception localException)
		{
			LOG.error(localException);
		}
		return null;
	}

	private static String parseLicenseEncrypted(String paramString)
	{
		if ((paramString == null) || (paramString.trim().isEmpty()))
			return null;
		String str1 = decrypt(paramString);
		if ((str1 == null) || (str1.isEmpty()))
			return null;
		try
		{
			byte[] arrayOfByte = Base64.decode(str1.getBytes("UTF-8"));
			String str2 = new String(arrayOfByte, "UTF-8");
			if ((str2 == null) || (str2.isEmpty()))
				return null;
			return str2;
		}
		catch (Throwable localThrowable)
		{
			LOG.error(localThrowable);
		}
		return null;
	}
	public static void main(String[] args) {

		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new File("/home/syed/.littleeye/workspace/.metadata/.plugins/com.littleeyelabs.utils/littleeye.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String d = p.getProperty("license.activation.enc");
		String de = parseLicenseEncrypted(d);
		System.out.println(de);
	}
}

