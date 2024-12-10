package jsoup;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.Iterator;


//靜態網頁
public class JsoupExample {
    public static void main(String[] args) {
        // 定義目標網頁 URL
        String url = "https://www.housefeel.com.tw/price-all/"; 

        try {
            // 獲取網頁文件
//          Document document = Jsoup.connect(url).get();
        	Document document = Jsoup.connect(url)
        		    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
        		    .get();



            // 選取外層容器
        	Elements containerName = document.select("span.name");
            Elements containerPrice = document.select("span.median");

            // Step 3: 分析並選取特定欄位的 DOM 結構
            for (int i = 0; i < containerName.size(); i++) {
             String text1 = containerName.get(i).text();
             String text2 = i < containerPrice.size() ? containerPrice.get(i).text() : "--";
             System.out.println(text1 + ": " + text2);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

