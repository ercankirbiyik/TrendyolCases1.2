package org.example.steps;

import com.thoughtworks.gauge.Step;
import org.example.base.UIBaseTest;
import org.example.methods.UIMethods;
import org.example.model.ElementInfo;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.Assert.assertFalse;

public class UIBaseSteps extends UIBaseTest {


    //WebDriver driver = new ChromeDriver();
    //UIMethods uiMethods = new UIMethods();
    public static int DEFAULT_MAX_ITERATION_COUNT = 1;
    public static int DEFAULT_MILLISECOND_WAIT_AMOUNT = 1;


/*
    @Step({"Go to <url> address",
            "<url> adresine git"})
    public void goToUrl(String url) {
        driver.get(url);
        logger.info(url + " adresine gidiliyor.");
    }

 */

    @Step({"Wait <value> seconds",
            "<int> saniye bekle"})
    public void waitBySeconds(int seconds) {
        try {
            logger.info(seconds + " saniye bekleniyor.");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Step({"Wait <value> milliseconds",
            "<long> milisaniye bekle"})
    public void waitByMilliSeconds(long milliseconds) {
        try {
            logger.info(milliseconds + " milisaniye bekleniyor.");
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step({"Check if element <key> exists",
            "Element var mı kontrol et <key>"})
    public WebElement getElementWithKeyIfExists(String key) {
        WebElement webElement;
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            try {
                webElement = UIMethods.findElementWithKey(key);
                logger.info(key + " elementi bulundu.");
                return webElement;
            } catch (WebDriverException e) {
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        assertFalse(Boolean.parseBoolean("Element: '" + key + "' bulanamadı"));
        return null;
    }
    @Step({"Click to element <key>",
            "Elementine tıkla <key>"})
    public void clickElement(String key) {
        if (!key.isEmpty()) {
            ElementInfo elementInfo = findElementInfoByKey(key);
            if (elementInfo != null) {
                UIMethods.clickElement(UIMethods.findElement(key));
                logger.info(key + " elementine tıklandı.");
            } else {
                logger.error("ElementInfo bulunamadı. Anahtar: " + key);
            }
        } else {
            logger.error("Geçersiz anahtar değeri: " + key);
        }
    }


    @Step({"Accept Chrome alert popup",
            "Chrome uyarı popup'ını kabul et"})
    public void acceptChromeAlertPopup() {
        String alertText = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        logger.info(alertText + " Popupı kabul edildi.");
    }

    @Step({"<key> li elementi bul, temizle ve <text> değerini yaz",
            "Find element by <key> clear and send keys <text>"})
    public void sendKeysByKey(String key, String text) {
        WebElement webElement = UIMethods.findElement(key);
        webElement.clear();
        webElement.sendKeys(text);
        logger.info(key + " alanina " + text + " degeri yazildi. ");
    }


    @Step({"Check if element <key> contains text <expectedText>",
            "<key> elementi <text> değerini içeriyor mu kontrol et"})
    public void checkElementContainsText(String key, String expectedText) {
        Boolean containsText = UIMethods.findElement(key).getText().contains(expectedText);
        logger.info("Beklenen text: " + expectedText);
        logger.info("Gerçek text: " + containsText);
        assertTrue(containsText, "Expected text is not contained!!  " +
                "  Beklenen text ile gerçek text aynı değil!!");
        logger.info(key + " elementi " + "'"+ expectedText + "'" + " degerini iceriyor.");
    }

}
