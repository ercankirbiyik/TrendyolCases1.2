package org.example.methods;

import com.google.common.base.Splitter;
import org.example.base.UIBaseTest;
import org.example.model.ElementInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class UIMethods extends UIBaseTest {

    public static By getElementInfoToBy(ElementInfo elementInfo) {
        By by = null;
        if (elementInfo.getType().equals("css")) {
            by = By.cssSelector(elementInfo.getValue());
        } else if (elementInfo.getType().equals(("name"))) {
            by = By.name(elementInfo.getValue());
        } else if (elementInfo.getType().equals("id")) {
            by = By.id(elementInfo.getValue());
        } else if (elementInfo.getType().equals("xpath")) {
            by = By.xpath(elementInfo.getValue());
        } else if (elementInfo.getType().equals("linkText")) {
            by = By.linkText(elementInfo.getValue());
        } else if (elementInfo.getType().equals(("partialLinkText"))) {
            by = By.partialLinkText(elementInfo.getValue());
        }
        return by;
    }

    public static WebElement findElement(String key) {
        By infoParam = getElementInfoToBy(findElementInfoByKey(key));
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    public ElementInfo getElementInfo(String key){

        return findElementInfoByKey(key);
    }

    public void keyValueChangerMethodWithNewElement(String key, String newKey, String value, String splitValue){

        ElementInfo elementInfo = getElementInfo(key);
        String getValue = elementInfo.getValue();
        String type = elementInfo.getType();
//logger.info(value);
        String[] arrayValue = Splitter.on(splitValue).splitToList(value).toArray(new String[0]);
        String newValue = String.format(getValue, arrayValue);
        logger.info(newValue);
        createElementInfo(newKey,newValue,type);
    }


    public void createElementInfo(String key, String value, String type){

        ElementInfo elementInfo = new ElementInfo();
        elementInfo.setKey(key);
        elementInfo.setValue(value);
        elementInfo.setType(type);
        addElementInfoByKey(key, elementInfo);
    }


    public UIMethods() throws IOException {
        String currentWorkingDir = System.getProperty("user.dir");
        initMap(getFileList(currentWorkingDir + "/src/test/resources"));
        //initMap(getFileList());
    }

    public static void clickElement(WebElement element) {
        element.click();
    }

    public static WebElement findElementWithKey(String key) {
        return findElement(key);
    }

    public void saveValue(String key, String value) {
        elementMapList.put(key, value);
    }

    public String getValue(String key) {
        return elementMapList.get(key).toString();
    }

    public static void addElementInfoByKey(String key, ElementInfo elementInfo){elementMapList.put(key,elementInfo);}

}
