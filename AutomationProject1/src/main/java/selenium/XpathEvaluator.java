package selenium;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XpathEvaluator {

	public static Object executeScript(WebDriver driver, String script) {
		return ((JavascriptExecutor) driver).executeScript(script);
	}

	public static void XpathEvaluate(String url,String filename,String chromedriverloc,String TagnameContainingXpathExprssn) throws InterruptedException, ParserConfigurationException, SAXException, IOException {
		System.setProperty("webdriver.chrome.driver","driverloc");
		WebDriver driver=new ChromeDriver();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File(filename));
		driver.get(url);
		Thread.sleep(5000);
		try {
			
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;

					
					
					String Xpath = elem.getElementsByTagName(TagnameContainingXpathExprssn)
							.item(0).getChildNodes().item(0).getNodeValue();
					String script = "return document.evaluate('count("+Xpath.replaceAll("'", "\"")+")', document, null, XPathResult.STRING_TYPE, null ).stringValue;";
					
			
			Object o = executeScript(driver,script);
			if(String.valueOf(o).matches("0"))
			{
				System.out.println(Xpath+" is not present ");
			}
			else
			{
				
				System.out.println(Xpath+" is present and  number of nodes = "+String.valueOf(o));
			}
			
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.quit();

	}
	
	
}
