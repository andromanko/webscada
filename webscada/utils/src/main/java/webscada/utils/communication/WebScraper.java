package webscada.utils.communication;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import webscada.api.dto.ValueReal;
import webscada.api.mappers.ValueRealMapper;
import webscada.api.utils.IWebScrapper;
import webscada.entity.Dev;
import webscada.entity.Value;

@Component
public class WebScraper implements IWebScrapper {

	private WebClient webclient = WebClientProvider.getDefaultWebClient();

//	private static final String SEARCH_URL = "currency.com";
//	private static final String IMAGE_URL = "https://pictures.abebooks.com/isbn/%s-us-300.jpg";

	@Override
	public Map<Long, ValueReal> start(Dev dev, List<Value> values) {
		String url = dev.getIp();// пока url - определенный без подстановки конкретного запроса

		try {

			HtmlPage webPage = webclient.getPage(url);
			Map<Long, ValueReal> data = new HashMap<>();
			for (Value value : values) {
				HtmlElement htmlValue = (HtmlElement) webPage.getByXPath(value.getAddr()).get(0);
				String retStr = htmlValue.getTextContent();
				String retStr1 = retStr.substring(1, 3);
				String retStr2 = retStr.substring(4, 7);
				int i = Integer.parseInt(retStr1 + retStr2);
				ValueReal valueReal = ValueRealMapper.mapValueReal(value);
				valueReal.setNumber(i);
				data.put(valueReal.getId(), valueReal);
			}
			return data;
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}