package in.bbat.web;

public class WebServiceException extends Exception
{
  private static final long serialVersionUID = -2091508491941879587L;
  private String serverResponse;

  public WebServiceException(String paramString, Exception paramException)
  {
    super(paramString, paramException);
  }

  public void setServerResponse(String paramString)
  {
    this.serverResponse = paramString;
  }

  public String getServerResponse()
  {
    return this.serverResponse;
  }
}