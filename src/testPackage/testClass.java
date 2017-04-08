package testPackage;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class testClass {
	RemoteWebDriver driver;
	PerfectoExecutionContext perfectoExecutionContext;
	ReportiumClient reportiumClient;
	
	@Parameters({ "platformName", "platformVersion", "manufacturer", "model", "appType", "appID" })
	@BeforeTest
	public void beforeTest(String platformName, String platformVersion, String manufacturer, String model, String appType, String appID) throws IOException {
		driver = Utils.getRemoteWebDriver(platformName, platformVersion, manufacturer, model, appType, appID );        
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("My Project", "1.0"))
                .withJob(new Job("My Job", 45))
                .withContextTags("tag1")
                .withWebDriver(driver)
                .build();
         reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	} 
  @Test
  public void test() {
      try {
          reportiumClient.testStart("Surface Edge test", new TestContext("tag2", "tag3"));
          System.out.println("Yay");

          // write your code here
          testEdge(driver);
          //testCalculator(driver);

          // reportiumClient.testStep("step1"); // this is a logical step for reporting
          // reportiumClient.testStep("step2");

          reportiumClient.testStop(TestResultFactory.createSuccess());
      } catch (Exception e) {
          reportiumClient.testStop(TestResultFactory.createFailure(e.getMessage(), e));
          e.printStackTrace();
      }
  }

  @AfterTest
  public void afterTest() {
      try {
          // Retrieve the URL of the Single Test Report, can be saved to your execution summary and used to download the report at a later point
          String reportURL = reportiumClient.getReportUrl();

          // For documentation on how to export reporting PDF, see https://github.com/perfectocode/samples/wiki/reporting
          // String reportPdfUrl = (String)(driver.getCapabilities().getCapability("reportPdfUrl"));

          driver.close();
          System.out.println("Report: "+ reportURL);


          // In case you want to download the report or the report attachments, do it here.
          // PerfectoLabUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
          // PerfectoLabUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

      } catch (Exception e) {
          e.printStackTrace();
      }

      driver.quit();
  }
    private static void testEdge (RemoteWebDriver driver){
        // launch EDGE
        PerfectoUtils.ocrImageSelect(driver, "PUBLIC:EDGE logo.png", 99 , 20);
        // Search for Macbook
        PerfectoUtils.ocrTextClick(driver, "web address", 90, 20);
        PerfectoUtils.typeText(driver, "ipad");
        PerfectoUtils.screenshot(driver);
        PerfectoUtils.ocrTextClick(driver, "pro", 99, 20);
        // Validate result
        PerfectoUtils.ocrTextCheck(driver, "Apple", 99, 20);
        long UXTimer = PerfectoUtils.getUXTimer(driver);
        System.out.println("UX Timer: "+ UXTimer);
        // close browser
        PerfectoUtils.ocrImageSelect(driver, "PUBLIC:Close edge.png", 95, 20);
        try {
            PerfectoUtils.ocrImageSelect(driver, "PUBLIC:Surface edge close all.png", 95, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void testCalculator (RemoteWebDriver driver) {

        driver.findElementByName("Clear").click();
        driver.findElementByName("Two").click();
        driver.findElementByName("Zero").click();
        driver.findElementByName("One").click();
        driver.findElementByName("Two").click();
        driver.findElementByName("Zero").click();
        driver.findElementByName("One").click();
        driver.findElementByName("Six").click();
        driver.findElementByName("Minus").click();
        driver.findElementByName("One").click();
        driver.findElementByName("Nine").click();
        driver.findElementByName("Five").click();
        driver.findElementByName("Three").click();
        driver.findElementByName("Equals").click();

        WebElement res = driver.findElementByName("Display is  63 ");


        System.out.println(res.getText());
    }

}
