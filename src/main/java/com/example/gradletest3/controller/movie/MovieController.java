package com.example.gradletest3.controller.movie;

import com.example.gradletest3.crwaler.MegaBoxCrawler;
import com.example.gradletest3.dao.movie.image.MovieImageDTO;
import com.example.gradletest3.dao.movie.paylist.MoviePayListDTO;
import com.example.gradletest3.service.movie.image.MovieImageService;
import com.example.gradletest3.service.movie.paylist.MoviePayListService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/movie")
@Slf4j
public class MovieController {
    MegaBoxCrawler mega = new MegaBoxCrawler();
    private final MovieImageService movieImageService;
    private final MoviePayListService moviePayListService;

    private WebDriver driver;
    private static final String url = "https://www.megabox.co.kr/movie";

    public MovieController(MovieImageService movieImageService, MoviePayListService moviePayListService) {
        this.movieImageService = movieImageService;
        this.moviePayListService = moviePayListService;
    }

    @GetMapping("/mv_list")
//    @ResponseBody
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

        return;


    }

    //크롤링 실행
    private void getDataList() throws InterruptedException, IOException {
        MovieImageDTO movieImageDTO = new MovieImageDTO();
        List<String> list = new ArrayList<>();
        int mv_Cnt = 0;
        int cnt = 0;

        LocalDate seoul = LocalDate.now(ZoneId.of("Asia/Seoul"));

        String today = String.valueOf(seoul);

        String full_path = "C:\\Josef_study\\Project\\intellij\\gradleTest3\\Crawling\\Mv_img\\";
        File downloadsFolder = new File(full_path + today);


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
        for (int i = 0; i < mv_Cnt / 20; i++) {
            Actions actions = new Actions(driver);
            actions.moveToElement(bnt).click().build().perform();
            Thread.sleep(500);

        }

        List<WebElement> img = driver.findElements(By.cssSelector("div.movie-list ol li.no-img .movie-list-info img.poster"));
        List<WebElement> poster = driver.findElements(By.cssSelector("div.movie-list ol li.no-img"));

        for (WebElement element1 : img) {
            // 포스터 이미지 가져오기
//            element1 = driver.findElement(By.cssSelector(".movie-list-info img.poster"));
            String ImgUrl = element1.getAttribute("src").replaceAll("-", "");
            String imgName = element1.getAttribute("alt").replaceAll("/", "").replaceAll(":", "").trim().toString();

            log.info("영화명 : " + imgName);

            //포스터 이름 가져오기
//            element1 = driver.findElement(By.cssSelector("div.tit-area p.tit"));
//            String name = element1.getAttribute("title").toString();
//
//            log.info("영화명 : " + name);


//            System.out.println(ImgUrl);
//            String ImgName = element1.getAttribute("alt");
//            log.info("영화명 : " + ImgName);
            String ImgFormat = "jpg";
            String ImgFilePath = downloadsFolder + "\\" + cnt + "_" + imgName + "." + ImgFormat;

            log.info("imgFilePath = " + ImgFilePath);

            URL url = new URL(ImgUrl);
            cnt++;
            try {

                //이미지 가져오기
                BufferedImage image = ImageIO.read(new URL(ImgUrl));

                // 이미지 저장할 파일
                File imgFile = new File(ImgFilePath);
                int filesize = (int) imgFile.length();

//                log.info("filesize ="+String.valueOf(filesize));

                movieImageDTO.setMv_size(filesize);
                movieImageDTO.setMv_extension(ImgFormat);
                movieImageDTO.setOrg_file_name(ImgFilePath);

//                log.info("dto : "+String.valueOf(movieImageDTO.getMv_size()));

                int result = movieImageService.addImage(movieImageDTO);

                if (result == 1) {
                    //이미지 저장
                    log.info(cnt + "번째 이미지 저장");
                    ImageIO.write(image, ImgFormat, imgFile);
                } else {
                    log.info("이미지 저장 실패 !");
                }


            } catch (Exception e) {
                e.printStackTrace();
                log.info("실패 ? Excetion이유");
            }


        }
        return;

    }

    //아임포트 카페 결제
    @GetMapping("payment")
    public String movie_payment() {
        return "movie/payment";
    }

    //영화 리스트
    @GetMapping("list")
    @ResponseBody
    public String getMovieList() {
        LocalDate now = LocalDate.now();
        LocalDate seoul = LocalDate.now(ZoneId.of("Asia/Seoul"));

        log.info(String.valueOf(seoul));

        return null;
    }

    @PostMapping("/payment/complete")
    public String pay_complete(@RequestParam Map<String, Object> payInfo) {

        String name = (String) payInfo.get("name");
        String price = (String) payInfo.get("price");

        log.info(name.getClass().getName());
        log.info(price.getClass().getName());

        log.info(String.valueOf(payInfo.get("name")));
        log.info((String) payInfo.get("price"));

//        String name = (String) payInfo.get("name");
//        String price = (String) payInfo.get("price");
        MoviePayListDTO result = new MoviePayListDTO();
        result.setMp_name(name);
        result.setMp_price(price);


        int pay_result = moviePayListService.addPaylist(result);

        if (pay_result == 1) {
            log.info("구매 성공");
        } else {
            log.info("구매 실패 !");
        }

        return "redirect:/movie/payment";

    }
}

