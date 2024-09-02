package likelion.eight.domain.course.service;

import jakarta.annotation.PostConstruct;
import likelion.eight.course.ParticipationTime;
import likelion.eight.domain.course.model.CrawledCourses;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CourseCrawlingService {
    private WebDriver webDriver;

    @Value("${webdriver.id}")
    private String WEB_DRIVER_ID;
    @Value("${webdriver.path}")
    private String WEB_DRIVER_PATH;

    //url
    @Value("${url.boottent-course-web}")
    private String webUrl;
    @Value("${url.boottent-course-mobile}")
    private String mobileUrl;
    @Value("${url.boottent-course-data}")
    private String dataUrl;
    @Value("${url.boottent-course-infra}")
    private String infraUrl;
    @Value("${url.boottent-course-embeded}")
    private String embededUrl;
    @Value("${url.boottent-course-game}")
    private String gameUrl;
    @Value("${url.boottent-course-dt}")
    private String dtUrl;
    @Value("${url.boottent-course-design}")
    private String designUrl;
    @Value("${url.boottent-course-startup}")
    private String startupUrl;

    List<CrawledCourses> crawledCourses;
    private String[] urlArray;

    @PostConstruct
    public void init(){
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 창 띄우지 않고 크롤링
        options.addArguments("--window-size=1000,800"); // 창 크기 고정 (창 크기에 따라 레이아웃이 바뀌는 문제 해결)
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

            // CSV 헤더 작성
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
                                course.getSummary().replaceAll(",", "") + "," + // 내용 요약에서 쉼표 제거
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

        for(int j = 0; j < 9; j++){ //카테고리가 9개이므로 카테고리별로 한번씩
            Thread.sleep(2000);

            webDriver.get(urlArray[j]);
            Thread.sleep(2000);


            //부트캠프들의 리스트를 찾아옴
            List<WebElement> elements = webDriver.findElements(By.cssSelector("body > main > section > div:nth-child(5) > div > section > ul > li" ));


            for (int i = 0; i<elements.size(); i++) {
                try {
                    //i번째 부트캠프 찾아옴
                    elements = webDriver.findElements(By.cssSelector("body > main > section > div:nth-child(5) > div > section > ul > li"));
                    WebElement element = elements.get(i);

                    // i번째 부트캠프의 상세페이지에 접속
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

                    //시작 날짜와 종료 날짜가 함께 써있어 각각 추출 필
                    String startEndDateText = webDriver.findElement(By.cssSelector("div[class*='rounded-[10px]'] > ul > li:nth-child(4) > div > div")).getText();
                    //시작 날짜만 추출
                    String startDateStr = startEndDateText.split("~")[0].trim();
                    // 종료 날짜 부분만 추출 후 나머지 문자 무시
                    String endDateStr = startEndDateText.split("~")[1].split("\\r?\\n")[0].trim();
                    //모집마감 날짜 텍스트로 추출
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

                    //온라인일 경우 location을 무시할 수 있도록 함
                    String location = null;
                    if(!isOnline) {
                        location = webDriver.findElement(By.cssSelector("ul.grid li:last-child ul > div")).getText();
                    }

                    String priceElement = webDriver.findElement(By.cssSelector("div.text-semibold14.text-main-600.md\\:text-semibold16")).getText();

                    String summary = webDriver.findElement(By.cssSelector("div.p-4.mt-5.flex.flex-col")).getText();

                    //풀타임인지 아닌지 확인하기 위함
                    ParticipationTime isFullTime = ParticipationTime.PART_TIME;
                    String fullTimeDiv = webDriver.findElement(By.xpath("//div[contains(@class, 'whitespace-nowrap') and contains(@class, 'bg-white') and contains(@class, 'text-grey-800') and contains(text(), '타임')]")).getText();
                    if (fullTimeDiv.contains("풀")){
                        isFullTime = ParticipationTime.FULL_TIME;
                    }

                    String capacityDiv = webDriver.findElement(By.xpath("//div[contains(text(), '모집정원')]/ancestor::li/following-sibling::li[1]/div/div")).getText();
                    // 모집 정원 문자열에서 숫자만 추출
                    Pattern pattern = Pattern.compile("\\d+");
                    Matcher matcher = pattern.matcher(capacityDiv);

                    // 결과 변수 선언
                    int maxParticipants;

                    if (matcher.find()) {
                        // 숫자가 있으면 추출한 숫자를 정수로 변환합니다.
                        maxParticipants = Integer.parseInt(matcher.group());
                    } else {
                        // 숫자가 없으면 -1로 설정합니다.
                        maxParticipants = -1;
                    }


                    //원래 페이지로 돌아가기
                    webDriver.navigate().back();

                    // 원래 페이지가 다시 로드될 때까지 기다립니다.
                    Thread.sleep(2000);

                    CrawledCourses build = CrawledCourses.builder()
                            .bootcamp(institutionElement)
                            .category(category)
                            .subCourse(subcourse)
                            .name(courseName)
                            .startDate(startDateStr)
                            .endDate(endDateStr)
                            .closingDate(closingDateText)
                            .codingTestExempt(isCodingTestRequired)
                            .cardRequirement(cardRequirement)
                            .onlineOffline(isOnline)
                            .location(location)
                            .tuitionType(priceElement)
                            .summary(summary)
                            .participationTime(isFullTime)
                            .maxParticipants(maxParticipants)
                            .build();
                    crawledCourses.add(build);

                } catch (StaleElementReferenceException e) {
                    log.error("StaleElementReferenceException: 요소가 더 이상 유효하지 않습니다.", e);
                    webDriver.navigate().refresh();
                    i--;  // 현재 인덱스에서 재시도
                }
            }
        }

        // 코스 리스트를 CSV 파일로 저장
        saveToCSV(crawledCourses, "courses.csv");

        return crawledCourses;
    }

}
