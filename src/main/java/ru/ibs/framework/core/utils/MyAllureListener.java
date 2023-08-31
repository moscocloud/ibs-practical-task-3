package ru.ibs.framework.core.utils;

import io.qameta.allure.Attachment;
import io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.ibs.framework.ui.managers.DriverManager;


public class MyAllureListener extends AllureCucumber7Jvm implements TestWatcher {

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] getScreenshot() {
        return ((TakesScreenshot) DriverManager.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        getScreenshot();

    }
}
