package webscada.utils.communication;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Component
public class WebScraper {

    private WebClient webclient = WebClientProvider.getDefaultWebClient();
    
    private static final String SEARCH_URL = "currency.com";
    private static final String IMAGE_URL = "https://pictures.abebooks.com/isbn/%s-us-300.jpg";
    
    public Float getValueFromWeb(String str) {
        Float webVal = null;
        try {
            String url = String.format(SEARCH_URL, str);
            HtmlPage webPage = webclient.getPage(url);
            HtmlElement htmlValue = (HtmlElement) webPage.getByXPath("//span[@id='describe-isbn-title']").get(0);
            //HtmlElement author = (HtmlElement) webPage.getByXPath("//span[@itemprop='author']").get(0);
            //HtmlElement description = null;
//            if (!webPage.getByXPath("//div[@id='bookSummary']").isEmpty()) {
//                description = (HtmlElement) bookPage.getByXPath("//div[@id='bookSummary']").get(0);
//                bookDetails.setDescription(description.getTextContent());
//            } else {
//                bookDetails.setDescription(StringUtils.EMPTY);
//            }
//            bookDetails.setPicture(String.format(IMAGE_URL, isbn));
//            bookDetails.setName(name.getTextContent());
//            bookDetails.setAuthor(author.getTextContent());
            String retStr= htmlValue.getTextContent();
                       
            return 55386.89F;
        } catch (FailingHttpStatusCodeException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}