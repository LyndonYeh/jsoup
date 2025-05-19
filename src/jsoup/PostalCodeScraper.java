package jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostalCodeScraper {
    public static void main(String[] args) {
    	// 設定 ChromeDriver 的路徑
        //System.setProperty("webdriver.chrome.driver", "C:/Users/gga99/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
    	WebDriverManager.chromedriver().clearDriverCache();
    	WebDriverManager.chromedriver().setup(); // 自動下載並設定對應的 chromedriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");// 無頭模式
        ChromeDriver driver = new ChromeDriver(options);

        try {
            // 開啟目標網站
            driver.get("https://www.sinyi.com.tw/tradeinfo/list");

            // 等待內容載入（可視需求調整時間）
            Thread.sleep(5000);

            // 抓取包含 __NEXT_DATA__ 的 <script>
            WebElement scriptTag = driver.findElement(By.xpath("//script[contains(text(), '__NEXT_DATA__')]"));
            String jsonContent = scriptTag.getAttribute("innerHTML");

            // 印出內容
//            System.out.println("這是原始內容" + jsonContent);
            try {
            	
            	 // 移除 "__NEXT_DATA__ = " 的前置部分
                jsonContent = jsonContent.replaceFirst("__NEXT_DATA__ = ", "").trim();
                
                // 使用 Jackson 解析 JSON
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonContent);

                // 定位到 zipCodeTW 節點
                JsonNode zipCodeTW = rootNode.at("/props/pageProps/query/apiCache/zipCodeTW");
                if (zipCodeTW.isArray()) {
                    for (JsonNode city : zipCodeTW) {
                        String cityName = city.get("cityName").asText();
                        JsonNode zips = city.get("zips");
                        if (zips.isArray()) {
                            for (JsonNode zip : zips) {
                                String zipCode = zip.get("zipCode").asText();
                                String zipName = zip.get("zipName").asText();
                                // 分別列印郵遞區號與地區
                                System.out.println("城市: " + cityName + ", 地區: " + zipName + ", 郵遞區號: " + zipCode);
                            }
                        }
                    }
                } else {
                    System.out.println("未找到 zipCodeTW 資料");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
