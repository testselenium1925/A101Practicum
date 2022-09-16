package com.a101.methods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.a101.driver.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Methods extends BaseTest {
    //private final static String ELEMENTS_PATH = "src/test/resources/elements/elements.json";
    private final static String ELEMENTS_JSON_PATH = "element";
    private final static String WEB = "web";
    private final static String ANDROID = "android";
    private final static String IOS = "ios";

    protected static WebElement webElement;
    public Map<String, Object> elementsMap;
    public Logger logger;
    protected MobileElement mobileElement;

    public Methods() {
        logger = Logger.getLogger(Methods.class);
        initByMap(getFileList());
    }


    public void waitBySeconds(long seconds) {
        try {
            Thread.sleep(seconds * 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void click(String keyword) {
        String device = getDeviceInfo(keyword);
        if (device.equals(ANDROID) || device.equals(IOS)) {
            //LocalDateTime now = LocalDateTime.now();
            findElementMobile(keyword).click();
            //System.out.println("time click => "+now);
            logger.info(keyword + " ANDROID elementine tıklandı.");
           } else if (device.equals(WEB)) {
              WebElement element = findElementWeb(keyword);
            element.click();
            logger.info(keyword + " WEB elementine tıklandı.");
            } else {
            logger.error(keyword + " elementine tıklanamadı.");
        }
    }

    public WebElement findElement(String keyword) {
        By by = getByTypeWithMap(keyword);
        return webWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public MobileElement findElementMobile(String keyword) {
        By by = getByTypeWithMap(keyword);
        //LocalDateTime now = LocalDateTime.now();
        //System.out.println("mobile find element => "+now);
        return (MobileElement) mobileWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement findElementWeb(String keyword) {
        By by = getByTypeWithMap(keyword);
        return webWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public List<MobileElement> findElementsMobile(String keyword) {
        By by = getByTypeWithMap(keyword);
        mobileWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        return appiumDriver.findElements(by);
    }

    public List<WebElement> findElementsWeb(String keyword) {
        By by = getByTypeWithMap(keyword);
        webWait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return webDriver.findElements(by);
    }

    public void randomElementClick(String keyword) {
        Random random = new Random();
        int randomInt;
        if (getDeviceInfo(keyword).equals(ANDROID) || getDeviceInfo(keyword).equals(IOS)) {
            List<MobileElement> elemets = findElementsMobile(keyword);
            randomInt = random.nextInt(elemets.size());
            mobileElement = elemets.get(randomInt);
        } else if (getDeviceInfo(keyword).equals(WEB)) {
            List<WebElement> elements = findElementsWeb(keyword);
            randomInt = random.nextInt(elements.size());
            webElement = elements.get(randomInt);
        } else {
            logger.error(keyword + " elementine tıklanamadı.");
        }
    }

    public void selectRandom(String keyword) {
        Random random = new Random();
        int randomInt;
        List<MobileElement> elemets = findElementsMobile(keyword);
        randomInt = random.nextInt(elemets.size());
        mobileElement = elemets.get(randomInt);
    }

    public void swipeUp(int repeat) {
        try {
            Dimension dims = appiumDriver.manage().window().getSize();
            PointOption pointOptionStart, pointOptionEnd;
            int edgeBorder = 2;
            final int PRESS_TIME = 1200; // ms
            pointOptionStart = PointOption.point(dims.width / 2, dims.height / 3);
            pointOptionEnd = PointOption.point(dims.width / 2, edgeBorder);
            for (int i = 0; i < repeat; i++) {
                new TouchAction(appiumDriver).press(pointOptionStart).waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME))).moveTo(pointOptionEnd).release().perform();
            }
            logger.info(repeat + " defa  sayfa scroll edildi.");
        } catch (Exception e) {
            logger.error("Sayfa scrol edilemedi hata meydana geldi :" + e);
        }
    }

    public void sendKeys(String keyword, String text) {
        if (getDeviceInfo(keyword).equals(ANDROID) || getDeviceInfo(keyword).equals(IOS)) {
            findElementMobile(keyword).clear();
            findElementMobile(keyword).sendKeys(text);
            logger.info(keyword + " elementine " + text + " değeri girildi");
        } else if (getDeviceInfo(keyword).equals(WEB)) {
            findElementWeb(keyword).clear();
            findElementWeb(keyword).sendKeys(text);
            logger.info(keyword + " elementine " + text + " değeri girildi");
        }
    }

    public Select getSelectText(String keyword) {
        return new Select(findElement(keyword));
    }

    public String getTextMobile(String keyword) {
        By by = getByTypeWithMap(keyword);
        mobileWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        return appiumDriver.findElement(by).getText();
    }

    public String getTextWeb(String keyword) {
        By by = getByTypeWithMap(keyword);
        webWait.until(ExpectedConditions.presenceOfElementLocated(by));
        return findElement(keyword).getText();
    }

    public boolean isElementContainText(String key,String text) {
        waitBySeconds(9);
        boolean degisken = false;
        try {
            findElement(key).getText().contains(text);
            logger.info(key + " elementi görünür durumdadır.");
            degisken=true;
        } catch (Exception e) {
            logger.info(key+" Elementi görünür değil");
            degisken= false;
        }
        return degisken;
    }

    public void selectTextMobileDrop(String keyword, String text){
        By by = getByTypeWithMap(keyword);
        mobileWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        Select drpCountry = new Select(appiumDriver.findElement(by));
        drpCountry.selectByVisibleText(text);
    }

    public By getByTypeWithMap(String keyword) {
        ElementInfo elements = (ElementInfo) elementsMap.get(keyword);
        Map<String, By> map = initByMap(elements.getLocatorValue());
        return map.getOrDefault(elements.getLocatorType(), null);
        //Map<String, By> map = initByMap(getLocatorValue(keyword));
        //return map.getOrDefault(getLocatorType(keyword), null);
    }

    public Map<String, By> initByMap(String locatorValue) {
        Map<String, By> map = new HashMap<>();
        map.put("id", By.id(locatorValue));
        map.put("css", By.cssSelector(locatorValue));
        map.put("xpath", By.xpath(locatorValue));
        map.put("class", By.className(locatorValue));
        map.put("linktext", By.linkText(locatorValue));
        map.put("name", By.name(locatorValue));
        map.put("partial", By.partialLinkText(locatorValue));
        map.put("tagname", By.tagName(locatorValue));
        return map;
    }

    public void initByMap(File[] fileList) {
        elementsMap = new ConcurrentHashMap<>();
        Type elementType = new TypeToken<List<ElementInfo>>() {
        }.getType();
        Gson gson = new Gson();
        List<ElementInfo> elementInfoList;
        for (File file : fileList) {
            try {
                elementInfoList = gson.fromJson(new FileReader(file), elementType);
                elementInfoList.parallelStream().forEach(elementInfo -> elementsMap.put(elementInfo.getKeyword(), elementInfo));
            } catch (FileNotFoundException e) {
            }
        }
    }

    public File[] getFileList() {
        File[] fileList = new File(this.getClass().getClassLoader().getResource(ELEMENTS_JSON_PATH).getFile()).listFiles(pathname -> !pathname.isDirectory() && pathname.getName().endsWith(".json"));
        if (fileList == null) {
            throw new NullPointerException("Belirtilen dosya bulunamadı.");
        }
        return fileList;
    }

    public void mouseMove(int x, int y) {
        Actions action = new Actions(appiumDriver);
        action.moveByOffset(x, y).perform();
    }

    public void selectByText(String keyword, String text) {
        getSelectText(keyword).selectByVisibleText(text);
    }

    public void mouseHover(String keyword) {
        Actions actions = new Actions(appiumDriver);
        actions.moveToElement(findElementMobile(keyword)).perform();
    }

    public void mouseHover(WebElement webElement) {
        Actions actions = new Actions(appiumDriver);
        actions.moveToElement(webElement).perform();
    }

    public void moveToElement(String keyword) {
            getJSExecutor.executeScript("arguments[0].scrollIntoView(true);", findElement(keyword));
    }

    public void mouseMoveOnElement(String keyword, int x, int y) {
        Actions action = new Actions(webDriver);
        mouseHover(keyword);
        action.moveByOffset(x, y).perform();
    }

    public void mouseMoveOnElement(WebElement webElement, int x, int y) {
        Actions action = new Actions(webDriver);
        mouseHover(webElement);
        action.moveByOffset(x, y).perform();
    }


    public void goBackPage() {
        appiumDriver.navigate().back();
    }

    public void goForwardPage() {
        appiumDriver.navigate().forward();
    }

    public void refreshPage() {
        appiumDriver.navigate().refresh();
    }

    public void goToUrl(String url) {
        appiumDriver.get(url);
    }

    public List<MobileElement> getElements(String keyword) {
        By by = getByTypeWithMap(keyword);
        List<MobileElement> l1 = appiumDriver.findElements(by);
        return l1;
    }

    public void randomElementClicker(List<MobileElement> list) {
        Random rand = new Random();
        list.get(rand.nextInt(list.size())).click();
    }

    public boolean isElementVisible(String keyword) {
        if (getDeviceInfo(keyword).equals(ANDROID) || getDeviceInfo(keyword).equals(IOS)) {
            try {
                mobileElement = findElementMobile(keyword);
                logger.info(keyword + " elementi görünür.");
                return true;
            } catch (Exception e) {
                logger.warn(keyword + " Element görünür değil yada bulunamadı." + e);
                return false;
            }
        } else if (getDeviceInfo(keyword).equals(WEB)) {
            try {
                webElement = findElementWeb(keyword);
                logger.info(keyword + " elementi görünür.");
                return true;
            } catch (Exception e) {
                logger.warn(keyword + " Element görünür değil yada bulunamadı." + e);
                return false;
            }
        }
        return false;
    }

    public boolean isElementVisibleNoWait(String keyword) {
        By by = getByTypeWithMap(keyword);
        try {
            webDriver.findElement(by);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public String getText(String keyword) {
        return findElement(keyword).getText();
    }

    public void clickWithText(String keyword, String text) {
        click(String.valueOf(By.xpath("//" + keyword + "[text()='" + text + "']")));
    }

    public void clickIfExist(String keyword) {
        if (isElementVisible(keyword)) {
            click(keyword);
        }
    }



    public void scrollWithAction(List<MobileElement> list) {
        Actions actions = new Actions(appiumDriver);
        actions.moveToElement(mobileElement).build().perform();
    }

    public void scrollWithAction(WebElement webElement) {
        Actions actions = new Actions(webDriver);
        actions.moveToElement(webElement).build().perform();
    }



    public void moveToElementWithJS(WebElement element) {
        webWait.until(ExpectedConditions.visibilityOf(element));
        getJSExecutor.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", element);
    }



    public String getDeviceInfo(String keyword) {
        ElementInfo elements = (ElementInfo) elementsMap.get(keyword);
        return elements.getDeviceInfo();
    }

    public static class ElementInfo {
        @Getter
        protected String keyword;
        @Getter
        protected String locatorValue;
        @Getter
        protected String locatorType;
        @Getter
        protected String deviceInfo;

        @Override
        public String toString() {
            return "Elements[" + "keyword=" + keyword + ",locatorType=" + locatorType + ",locatorValue=" + locatorValue + "deviceInfo=" + deviceInfo + "]";
        }
    }
}

