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
    
    private static final String SEARCH_URL = "https://www.bookfinder.com/search/?author=&title=&lang=en&isbn=%s&new_used=*&destination=by&currency=USD&mode=basic&st=sr&ac=qr";
    private static final String IMAGE_URL = "https://pictures.abebooks.com/isbn/%s-us-300.jpg";
    
    public BookDetails getBookDetailsFromWeb(String isbn) {
        BookDetails bookDetails = new BookDetails();
        try {
            String url = String.format(SEARCH_URL, isbn);
            HtmlPage bookPage = webclient.getPage(url);
            HtmlElement name = (HtmlElement) bookPage.getByXPath("//span[@id='describe-isbn-title']").get(0);
            HtmlElement author = (HtmlElement) bookPage.getByXPath("//span[@itemprop='author']").get(0);
            HtmlElement description = null;
            if (!bookPage.getByXPath("//div[@id='bookSummary']").isEmpty()) {
                description = (HtmlElement) bookPage.getByXPath("//div[@id='bookSummary']").get(0);
                bookDetails.setDescription(description.getTextContent());
            } else {
                bookDetails.setDescription(StringUtils.EMPTY);
            }
            bookDetails.setPicture(String.format(IMAGE_URL, isbn));
            bookDetails.setName(name.getTextContent());
            bookDetails.setAuthor(author.getTextContent());
            return bookDetails;
        } catch (FailingHttpStatusCodeException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}