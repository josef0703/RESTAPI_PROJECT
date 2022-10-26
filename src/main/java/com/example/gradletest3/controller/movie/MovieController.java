package com.example.gradletest3.controller.movie;

import com.example.gradletest3.crwaler.MegaBoxCrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {
    MegaBoxCrawler mega = new MegaBoxCrawler();
    private WebDriver driver;
    private static final String url = "https://www.megabox.co.kr/movie";

    @GetMapping("list")
    @ResponseBody
    public void MovieList() {

//        mega.Crawling("https://www.megabox.co.kr/");
        System.setProperty("webdriver.chrome.driver", "C:\\Josef_study\\Project\\intellij\\gradleTest3\\Crawling\\chromedriver_win32\\chromedriver.exe");


        driver = new ChromeDriver();

        try {
            getDataList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driver.close();
        driver.quit();


//        for (int i = 1; i < 5; i++) {
//            mega.Crawling("https://puradakchicken.com/menu/product.asp?page="+Integer.toString(i)+"&sermode=&sermode2=&serdiv=");
//        }


    }

    //크롤링 실행
    private void getDataList() throws InterruptedException, IOException {
        List<String> list = new ArrayList<>();
        int mv_Cnt = 0;
        int cnt=0;

        File downloadsFolder = new File("C:\\Josef_study\\Project\\intellij\\gradleTest3\\Crawling\\Mv_img");


        // start
        if (!downloadsFolder.exists()) {
            downloadsFolder.mkdir();
            System.out.println("폴더가 생성되었습니다");
        } else {
            System.out.println("이미 폴더가 생성되어 있습니다");

        }

        driver.get(url);
        Thread.sleep(1000);


        WebElement total = driver.findElement(By.xpath("//*[@id=\"totCnt\"]"));
        WebElement bnt = driver.findElement(By.cssSelector("div.btn-more.v1 .btn"));
        mv_Cnt = Integer.parseInt(total.getText());
        for (int i = 0; i < mv_Cnt/20; i++) {
            Actions actions = new Actions(driver);
            actions.moveToElement(bnt).click().build().perform();
            Thread.sleep(500);

        }

        List<WebElement> img = driver.findElements(By.cssSelector("div.movie-list ol li.no-img .movie-list-info img.poster"));

        for (WebElement element1 : img) {
            String ImgUrl = element1.getAttribute("src").replaceAll("-", "");
//            System.out.println(ImgUrl);
            String ImgName = element1.getAttribute("alt");
            String ImgFilePath = downloadsFolder + "\\"+"_"+cnt + ".jpg";
            String ImgFormat = "jpg";
            URL url = new URL(ImgUrl);
            cnt++;
            try {

                //이미지 가져오기
                BufferedImage image = ImageIO.read(new URL(ImgUrl));

                // 이미지 저장할 파일
                File imgFile = new File(ImgFilePath);

                //이미지 저장
                ImageIO.write(image, ImgFormat, imgFile);


            } catch (Exception e) {

            }


        }

    }
    //아임포트 카페 결제
    @GetMapping("payment")
    public String movie_payment() {
        return "movie/payment";
    }

}

