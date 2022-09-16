package com.a101.imp;

import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.a101.methods.Methods;
import java.util.List;


public class StepImp  {
    protected Methods methods;


    public List<WebElement> list;
    protected WebElement webElement;
    public By by;

    public StepImp() {
        methods = new Methods();
    }


    @Step("<key> li elemente tıklanır.")
    public void click(String keyword) {
        methods.click(keyword);
    }

    @Step("<key> 'li elemente görünür ise  tıklanır.")
    public void clickIfExist(String keyword) {
        methods.clickIfExist(keyword);
    }

    @Step("<second> saniye kadar beklenir.")
    public void waitForsecond(int second) throws InterruptedException {
        methods.waitBySeconds(second);
    }

    @Step("<Key> li elementinin görünürlüğü kontrol edilir.")
    public boolean isElementVisible(String keyword) {
        return methods.isElementVisible(keyword);
    }

    @Step("<repeat> defa scroll edilir.")
    public void swipeUp(int repeat) {
        methods.swipeUp(repeat);
    }

    @Step("<keyword> 'li elemente scroll edilir.")
    public void scrollWithAction(String keyword) {
        methods.scrollWithAction(methods.getElements(keyword));
    }

    @Step("<key> 'li elemente <text> degeri girilir.")
    public void sendKeys(String keyword, String text) {
        methods.sendKeys(keyword, text);
    }

    @Step("<key> elementinin üzerinde durulur.")
    public void mouseHover(String keyword) {
        methods.mouseHover(keyword);
    }

    @Step("<key> web elementinin üzerinde durulur.")
    public void mouseHoverWeb(String keyword) {
        methods.moveToElement(keyword);
    }

    @Step("Bir önceki sayfaya gidilir.")
    public void goBackPage() {
        methods.goBackPage();
    }

    @Step("Sayfayı yeniden yükler.")
    public void refreshPage() {
        methods.refreshPage();
    }

    @Step("Bir sonraki sayfaya gidilir.")
    public void goForwardPage() {
        methods.goForwardPage();
    }

    @Step("<key> adresine gidilir.")
    public void goToUrl(String url) { methods.goToUrl(url); }

    @Step("elementin <key> , <text> texti seçilir.")
    public void selectByText(String keyword, String text) {
        methods.selectByText(keyword, text);
    }

    @Step("<keyword> ürünün siyah renkli olduğu doğrulanır.")
        public void getTextMob(String keyword){
        System.out.println(methods.getTextMobile(keyword));
        Assert.assertTrue("Urun siyah renkli degil.",methods.getTextMobile(keyword).contains("Siyah"));
    }

    @Step("<keyword> ödeme ekranına gelindiği kontrol edilir.")
    public void getTextMobs(String keyword){
        System.out.println(methods.getTextMobile(keyword));
        Assert.assertTrue("Ödeme ekranına gelemedi!",methods.getTextMobile(keyword).contains("Masterpass"));
    }

    @Step("<keyword> ürünün siyah renkli olduğu kontrol edilir.")
    public void getTextWeb(String keyword){
        System.out.println(methods.getTextWeb(keyword));
       Assert.assertTrue("Urun siyah renkli degil!.",methods.getTextWeb(keyword).contains("SİYAH"));
    }

    @Step("<keyword> web ödeme ekranına gelindiği kontrol edilir.")
    public void getTextWebs(String keyword){
        System.out.println(methods.getTextWeb(keyword));
        Assert.assertTrue("Ödeme ekranına gelemedi!",methods.getTextWeb(keyword).contains("Masterpass"));
    }

    @Step("<keyword>'li elementin <text> textine tıklar.")
    public void selectTextMobile(String keyword, String text){
        methods.selectTextMobileDrop(keyword,text);
    }

    @Step("Fare <key> elementinin üzerinde <x>,<y> kadar hareket ettirilir.")
    public void mouseMove(String keyword, int x, int y) {
        methods.mouseMoveOnElement(keyword, x, y);
    }

    @Step("Fare <x>,<y> kadar hareket ettirilir.")
    public void mouseMove( int x, int y) {
        methods.mouseMove( x, y);
    }


    @Step("<key> 'li elemente rastgele tıklanır.")
    public void randomClick(String keyword) {
        methods.randomElementClicker(methods.getElements(keyword));
    }

    @Step("<key> 'li text bilgisi yazılır.")
    public void getSelectText(String keyword){
        methods.getSelectText(keyword);
    }

}

