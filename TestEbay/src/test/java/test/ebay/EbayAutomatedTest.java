package test.ebay;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class EbayAutomatedTest {
	
	private WebDriver driver;
	By searchArticles = By.cssSelector("input[placeholder='Buscar artículos']");
	By pumaSelectBox = By.cssSelector("input[aria-label='PUMA']");
	By sizeSelectBox = By.cssSelector("input[aria-label='10']");
	By shopCar = By.cssSelector("a[_sp='p2047675.l1473']");
	By shopAll = By.cssSelector("button[class='call-to-action btn btn--large btn--primary']");
	By price = By.cssSelector("span[itemprop='price']");
	By name = By.cssSelector("h1[id='itemTitle']");
	
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://co.ebay.com/");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws InterruptedException {
		
		driver.findElement(searchArticles).clear();
		driver.findElement(searchArticles).sendKeys("shoes");
		driver.findElement(searchArticles).submit();
		assertEquals("shoes | eBay", driver.getTitle());
		Thread.sleep(2000);
		driver.findElement(pumaSelectBox).click();
		Thread.sleep(2000);
		driver.findElement(sizeSelectBox).click();
		Thread.sleep(2000);
		WebElement resultsSearch = driver.findElement(By.tagName("h1"));
		String numberResults = resultsSearch.getText();
		System.out.println(numberResults);
		Thread.sleep(2000);
		driver.get("https://www.ebay.com/sch/i.html?_from=R40&_nkw=shoes&_sacat=0&_oaa=1&Brand=PUMA&US%2520Shoe%2520Size%2520%2528Men%2527s%2529=10&_dcat=15709&_sop=15");    
		Thread.sleep(2000);
		List inputName = new LinkedList();
		List inputContent = new LinkedList();
		double b[] = new double[4];
		for(int i = 11, f = 0; i <= 15; i++)
		{
			List<WebElement> img = driver.findElements(By.tagName("img"));
			img.get(i).click();
			Thread.sleep(2000);
			WebElement takeName = driver.findElement(name);
			String nameResults = takeName.getText();
			inputName.add(nameResults);
			WebElement takePrice = driver.findElement(price);
			String priceResults = takePrice.getAttribute("content");
			double converterPrice = Double.parseDouble(priceResults);
			b[f] = converterPrice;
			inputContent.add(b[f]);
			System.out.println(nameResults+" "+priceResults);
			driver.findElement(shopCar).click();
			Thread.sleep(3000);
			if (i == 15) {
				WebElement select = driver.findElement(By.id("msku-sel-1"));
				List<WebElement> options = select.findElements(By.id("msku-opt-0"));
				for (WebElement option : options) {
				       if("US4 (220mm)".equals(option.getText()))
				           option.click();
					       driver.findElement(shopCar).click();
					       Thread.sleep(3000);
					       assertEquals("Carro de compras de eBay", driver.getTitle());
					       driver.findElement(shopAll).click();
					       Thread.sleep(4000);
				}
			f++;
			
			}	
			driver.get("https://www.ebay.com/sch/i.html?_from=R40&_nkw=shoes&_sacat=0&_oaa=1&Brand=PUMA&US%2520Shoe%2520Size%2520%2528Men%2527s%2529=10&_dcat=15709&_sop=15");
		}
		System.out.println("\n\n Products by name (ascendant) \n");
		Collections.sort(inputName);
		for( Object nameList : inputName) {
			System.out.println(nameList);
		}
		System.out.println("\n\n Products by price in descendant mode\n");
		Collections.sort(inputContent);
		Collections.reverse(inputContent);
		for( Object contentList : inputContent) {
			System.out.println(contentList);
		}
	}
}
