package jsoup;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

//動態網頁
public class WaitForElement {
    public static void main(String[] args) {
        // 設定 ChromeDriver 路徑
        //System.setProperty("webdriver.chrome.driver", "resource/chromedriver-win64/chromedriver-win64/chromedriver.exe");
    	WebDriverManager.chromedriver().clearDriverCache();
    	WebDriverManager.chromedriver().setup(); // 自動下載並設定對應的 chromedriver
        // 初始化 WebDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        options.addArguments("--start-maximized");
//        options.addArguments("user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1");

        options.addArguments("--headless");// 無頭模式
        options.addArguments("--incognito"); //	無痕模式
        options.addArguments("--disable-gpu"); // 避免 GPU 渲染問題
//        options.addArguments("--no-sandbox"); // 避免沙箱限制
//        options.addArguments("--disable-dev-shm-usage"); // 解決 /dev/shm 共享內存不足問題(Linux)
        
//        此參數禁用 Chrome 中的 Blink 特性 AutomationControlled，用於讓瀏覽器更加接近於普通用戶的操作行為，避免被檢測出是自動化腳本。
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        WebDriver driver = new ChromeDriver(options);


        try {
            // 定義基礎 URL
            String baseUrl = "https://www.sinyi.com.tw/tradeinfo/list/Miaoli-county/{zipCode}-zip/6month-dealtime/datatime-desc/index";

          
            for (int i = 350; i <= 350; i++) {
                // 替換 URL 中的 {zipCode} 部分
                String dynamicUrl = baseUrl.replace("{zipCode}", String.valueOf(i));
                driver.get(dynamicUrl);

            try { 
                
            // 1. 建立一個等待器，設定最長等待時間為 20 秒
            // (時間長短可依據網站載入速度調整)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            System.out.println("開始等待元素出現...");

            // 2. 使用 wait.until 來智慧等待
            // ExpectedConditions.presenceOfElementLocated 意思是「直到這個元素出現在 DOM 結構中」
            WebElement trendTitleDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tradeTitleWeb")));
//          WebElement trendTitleDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".trade_trade-list-map-title__TksA3")));
//          WebElement trendChartDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("trade_trend-chart-avg-mobile__tAc1u")));

            // 3. 程式執行到這裡，表示元素已經成功找到並指派給 tradeTitleDiv 了
            System.out.println("成功找到元素！");

            String trendTitle = trendTitleDiv.getText();
//            String avgPriceHtml = trendTitleDiv.findElement(By.xpath(".//span[@style='font-size: 1.75rem; color: rgb(221, 37, 37);']")).getAttribute("innerHTML");
            String transCountHtml = trendTitleDiv.findElement(By.xpath(".//span[@style='color: rgb(221, 37, 37);']")).getAttribute("innerHTML");

            System.out.println(i + trendTitle);
//            System.out.println("平均單價: " + avgPriceHtml + " 萬/坪");
            System.out.println("成交件數: " + transCountHtml + " 件");
                
            } catch (TimeoutException e) {
                System.out.println("頁面不存在或加載失敗，跳過 zipCode: " + i);
                e.printStackTrace();
                continue; 
            } catch (NoSuchElementException e) {
                System.out.println("頁面加載成功，但未找到指定元素，跳過 zipCode: " + i);
                continue; 
            } 
            
          }
        } catch (Exception e) {
			e.printStackTrace();
		}
            finally {
            // 關閉瀏覽器
            driver.quit();
        }
    }
}



