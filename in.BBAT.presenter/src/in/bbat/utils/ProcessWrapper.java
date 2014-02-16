package in.bbat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class ProcessWrapper
{
	ProcessBuilder builder;

	public ProcessWrapper(ProcessBuilder paramProcessBuilder)
	{
		this.builder = paramProcessBuilder;
	}

	public boolean run() throws IOException
	{
		BufferedReader localBufferedReader = null;
		try
		{
			String str2 = "";

			builder.redirectErrorStream(true);
			Process process = builder.start();
			localBufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((str2 = localBufferedReader.readLine()) != null){

			}
			process.waitFor();
			return true;
		}
		catch (InterruptedException localInterruptedException)
		{
			//			LOG.E("ProcessWrapper", "Interrupted executing command " + str1, localInterruptedException);
			return false;
		}
		finally
		{
			if (localBufferedReader != null)
				localBufferedReader.close();
		}
	}
}
