package org.example.steps;

import com.thoughtworks.gauge.Step;
import org.example.base.UIBaseTest;
import org.example.methods.UIMethods;
import org.example.model.ElementInfo;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Random;

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


    @Step({"Send ENTER key to element <key>",
            "Elemente ENTER keyi yolla <key>"})
    public void sendKeyToElementENTER(String key) {
        UIMethods.findElement(key).sendKeys(Keys.ENTER);
        logger.info(key + " elementine ENTER keyi yollandı.");
    }

    @Step({"<key> Elementine kadar kaydır",
            "Scroll to Element <key>"})
    public WebElement scrollToElementToBeVisible(String key) {
        ElementInfo elementInfo = findElementInfoByKey(key);
        WebElement webElement = driver.findElement(UIMethods.getElementInfoToBy(elementInfo));
        if (webElement != null) {
            UIMethods.scrollTo(webElement.getLocation().getX(), webElement.getLocation().getY() - 100);
        }
        return webElement;
    }


    @Step({"<key> alanına kaydır"})
    public void scrollToElement(String key) {
        scrollToElementToBeVisible(key);
        logger.info(key + " elementinin olduğu alana kaydırıldı");
    }


    @Step({"Random click in <key> list",
            "<key> listesinden rastgele elemente tıkla"})
    public void randomPick(String key) {
        List<WebElement> elements = UIMethods.findElements(key);
        Random random = new Random();
        int index = random.nextInt(elements.size());
        if (index == 0)
            index += 1;
        System.out.println("element count = " + elements.size());
        System.out.println("çıktı = " + elements.get(index).getText());
        scrollToElement(String.valueOf(elements.get(index)));
        elements.get(index).click();
        logger.info(elements + " listesinden " + key + " elementi secildi");
    }

    @Step({"Check if <key> element's attribute <attribute> contains the value <expectedValue>",
            "<key> elementinin <attribute> niteliği <value> değerini içeriyor mu"})
    public void checkElementAttributeContains(String key, String attribute, String expectedValue) {
        WebElement element = UIMethods.findElement(key);

        String actualValue;
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            actualValue = element.getAttribute(attribute).trim();
            if (actualValue.contains(expectedValue)) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assertions.fail("Element's attribute value doesn't contain expected value");
    }

    @Step({"Check if element <key> not contains text <expectedText>",
            "<key> elementi <text> değerini içermiyor mu kontrol et"})
    public void checkElementNotContainsText(String key, String expectedText) {
        Boolean containsText = UIMethods.findElement(key).getText().contains(expectedText);
        logger.info("Beklenmeyen text: " + expectedText);
        logger.info("Gerçek text: " + containsText);
        Assertions.assertFalse(containsText, "Expected text is contained!!  " +
                "  Beklenen text ile gerçek text aynı!!!");
        logger.info(key + " elementi " + expectedText + " degerini icermiyor.");
    }


}
