package webscada.utils.communication;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

import lombok.experimental.UtilityClass;


@UtilityClass
public class WebClientProvider {

	 public static WebClient getDefaultWebClient() {
		 return setWebClientSettings(BrowserVersion.CHROME);
	 }
	 
	 private static WebClient setWebClientSettings(final BrowserVersion browserVersion) {
		 WebClient webClient = new WebClient(browserVersion);
		 setWebClientDefaultOptions(webClient);
		 return webClient;
	 }
	 
	 private static void setWebClientDefaultOptions(WebClient webClient) {
	        if (webClient != null) {
	            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	            webClient.getOptions().setThrowExceptionOnScriptError(false);
	            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	            webClient.getOptions().setRedirectEnabled(true);
	            webClient.getOptions().setJavaScriptEnabled(true);
	            webClient.getOptions().setCssEnabled(false);
	            webClient.getOptions().setPrintContentOnFailingStatusCode(false);
	            webClient.waitForBackgroundJavaScript(10000);
	        }
	    }
	}
