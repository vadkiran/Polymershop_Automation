package listeners;

import managers.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class TestListener implements ITestListener {

    private String getTestName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName() + "." + result.getName();
    }

    private void takeScreenshot(String testName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver instanceof TakesScreenshot) {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                String timestamp = new Date().toString().replace(" ", "_").replace(":", "-");
                Path destination = Paths.get("output", "screenshots", testName + "_" + timestamp + ".png");
                Files.createDirectories(destination.getParent());
                Files.copy(screenshotFile.toPath(), destination);
                System.out.println("Screenshot saved to " + destination.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to save screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting test: " + getTestName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + getTestName(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + getTestName(result));
        takeScreenshot(getTestName(result));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + getTestName(result));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not used for this framework
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Starting TestNG execution for suite: " + context.getSuite().getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Finished TestNG execution for suite: " + context.getSuite().getName());
    }
}