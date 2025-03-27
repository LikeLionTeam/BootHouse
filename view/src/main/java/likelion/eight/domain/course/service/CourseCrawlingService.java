package likelion.eight.domain.course.service;

import org.springframework.stereotype.Service;

package likelion.eight.domain.course.service;

import jakarta.annotation.PostConstruct;
import likelion.eight.course.ParticipationTime;
import likelion.eight.domain.course.model.CrawledCourses;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CourseCrawlingService {
    private WebDriver webDriver;

    @Value("<span class="math-inline">\{webdriver\.id\}"\)
private String WEB\_DRIVER\_ID;
@Value\("</span>{webdriver.path}")
    private String WEB_DRIVER_PATH;

    @Value("<span class="math-inline">\{url\.boottent\-course\-web\}"\)
private String webUrl;
@Value\("</span>{url.boottent-course-mobile}")
    private String mobileUrl;
    @Value("<span class="math-inline">\{url\.boottent\-course\-data\}"\)
private String dataUrl;
@Value\("</span>{url.boottent-course-infra}")
    private String infraUrl;
    @Value("<span class="math-inline">\{url\.boottent\-course\-embeded\}"\)
private String embededUrl;
@Value\("</span>{url.boottent-course-game}")
    private String gameUrl;
    @Value("<span class="math-inline">\{url\.boottent\-course\-dt\}"\)
private String dtUrl;
@Value\("</span>{url.boottent-course-design}")
    private String designUrl;
    @Value("${url.boottent-course-startup}")
    private String startupUrl;

    List<CrawledCourses> crawledCourses;
    private String[] urlArray;

    @PostConstruct
    public void init(){
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1000,800");
        this.webDriver = new ChromeDriver(options);
        crawledCourses = new ArrayList<>();

        urlArray = new String[]{
                mobileUrl,
                webUrl,
                dataUrl,
                infraUrl,
                embededUrl,
                gameUrl,
                dtUrl,
                designUrl,
                startupUrl
        };
    }

    public void saveToCSV(List<CrawledCourses> courses,String filePath){
        try (FileWriter fileWriter = new FileWriter(filePath);
             PrintWriter printWriter = new PrintWriter(fileWriter)){

            printWriter.println("CourseName,Institution,SubCourse,StartDate,EndDate,ClosingDate,CodingTestRequired,CardRequirement,IsOnline,Location,Price,ContentSummary,IsFullTime,MaxParticipants");

            for (CrawledCourses course:courses){
                printWriter.println(
                        course.getName() + "," +
                                course.getBootcamp() + "," +
                                course.getSubCourse() + "," +
                                (course.getStartDate() != null ? course.getStartDate() : "") + "," +
                                (course.getEndDate() != null ? course.getEndDate() : "") + "," +
                                (course.getClosingDate() != null ? course.getClosingDate() : "") + "," +
                                course.isCodingTestExempt() + "," +
                                course.isCardRequirement() + "," +
                                course.isOnlineOffline() + "," +
                                (course.getLocation() != null ? course.getLocation() : "") + "," +
                                course.getTuitionType() + "," +
                                course.getSummary().replaceAll(",", "") + "," +
                                course.getParticipationTime() + "," +
                                course.getMaxParticipants()
                );
            }
        }catch (IOException e) {
            log.error("CSV 파일로 데이터를 저장하는 중 오류 발생", e);
        }
    }

    private String getCategory(int j){
        String category = null;
        if(j==0){
            category = "웹개발";
        } else if (j==1) {
            category = "모바일";
        } else if (j==2){
            category = "데이터,AI";
        } else if (j==3){
            category = "클라우드,보안";
        } else if (j==4){
            category = "IoT,임베디드,반도체";
        }else if (j==5){
            category = "게임,블록체인";
        }else if (j==6){
            category = "기획,마케팅,기타";
        }else if (j==7){
            category = "디자인,3D";
        }else if (j == 8){
            category ="프로젝트,취준,창업";
        }

        return category;
    }

    public List<CrawledCourses> crawling() throws InterruptedException {
        List<CrawledCourses> crawledCourses = new ArrayList<>();

        for(int j = 0; j < 9; j++){
            Thread.sleep(2000);

            webDriver.get(urlArray[j]);
            Thread.sleep(2000);

            List<WebElement> elements = webDriver.findElements(By.cssSelector("body > main > section > div:nth-child(5) > div > section > ul > li" ));

            for (int i = 0; i<elements.size(); i++) {
                try {
                    elements = webDriver.findElements(By.cssSelector("body > main > section > div:nth-child(5) > div > section > ul > li"));
                    WebElement element = elements.get(i);

                    WebElement detailButton = element.findElement(By.cssSelector("a"));
                    String hrefValue = detailButton.getAttribute("href");
                    webDriver.get(hrefValue);

                    Thread.sleep(2000);

                    String courseName = webDriver.findElement(By.cssSelector("div.flex.w-full.flex-col.items-start.text-semibold18.md\\:text-bold24 > div:nth-child(3)")).getText();
                    String institutionElement = null;
                    try {
                        institutionElement = webDriver.findElement(By.cssSelector("div.flex.h-full.grow.flex-col.items-start.gap-1.text-regular13.md\\:text-regular14 > a > div > span.line-clamp-1")).getText();
                    } catch (NoSuchElementException e) {
                        log.warn("교육기관 이름을 찾을 수 없습니다. null로 설정합니다.");
                    }

                    String category = getCategory(j);
                    String subcourse = webDriver.findElement(By.cssSelector("div.inline-flex.items-center.text-grey-800:nth-child(2)")).getText();

                    String startEndDateText = webDriver.findElement(By.cssSelector("div[class*='rounded-[10px]'] > ul > li:nth-child(4) > div > div")).getText();
                    String startDateStr = startEndDateText.split("~")[0].trim();
                    String endDateStr = startEndDateText.split("~")[1].split("\\r?\\n")[0].trim();
                    String closingDateText = webDriver.findElement(By.cssSelector("div.flex.flex-wrap.items-center.gap-1\\.5.md\\:gap-2\\.5 > div:nth-of-type(1)")).getText();

                    String testExists = webDriver.findElement(By.xpath("//*[contains(text(), '선발절차')]")).getText();
                    boolean isCodingTestRequired = !testExists.contains("없습니다");

                    String naeBaeKaElement = webDriver.findElement(By.xpath("//span[contains(text(), '💳')]")).getText();
                    Boolean cardRequirement = false;
                    if (naeBaeKaElement.contains("해요.")){
                        cardRequirement = true;
                    }

                    String onlineOffline = webDriver.findElement(By.cssSelector("div[title='수업형태'] > div")).getText();
                    boolean isOnline = onlineOffline.contains("온");

                    String location = null;
                    if(!isOnline) {
                        location = webDriver.findElement(By.cssSelector("ul.grid li:last-child ul > div")).getText();
                    }

                    String priceElement = webDriver.findElement(By.cssSelector("div.text-semibold14.text-main-600.md\\:text-semibold16")).getText();

                    String summary = webDriver.findElement(By.cssSelector("div.
