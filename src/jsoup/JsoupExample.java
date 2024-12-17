package jsoup;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


//靜態網頁
public class JsoupExample {
    public static void main(String[] args) {
        // 定義目標網頁 URL
        String url = "https://www.housefeel.com.tw/price-all/"; 
        
        
        // 獲取當前時間戳
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // 固定目錄和檔名
        String directory = "C:/jsoupOutput/";
        String fileName = "output_" + timestamp + ".csv";
        String filePath = directory + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 獲取網頁文件
//          Document document = Jsoup.connect(url).get();
        	Document document = Jsoup.connect(url)
        		    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
        		    .get();



            // 選取外層容器
        	Elements containerName = document.select("span.name");
            Elements containerPrice = document.select("span.median");

            	// 輸出到 CSV 檔案
                // 寫入 CSV 標頭
                writer.write("District,Price");
                writer.newLine();

                // 寫入內容
                for (int i = 0; i < containerName.size(); i++) {
                    String district = containerName.get(i).text();
                    String price = i < containerPrice.size() ? containerPrice.get(i).text() : "--";
                    writer.write(district + "," + price);
                    writer.newLine();
                }

                System.out.println("CSV 檔案已儲存至：" + filePath);
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

