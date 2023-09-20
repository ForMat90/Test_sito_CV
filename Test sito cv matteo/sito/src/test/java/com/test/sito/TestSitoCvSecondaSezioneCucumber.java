package com.test.sito;

import com.test.sito.utils.DriverUtils;
import com.test.sito.utils.TestReport;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.test.sito.utils.DriverUtils.openTestReport;

public class TestSitoCvSecondaSezioneCucumber {

    private static DriverUtils driverUtils;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static TestReport testReport;

    @Before
    public void setUpReport() {
        testReport = new TestReport();
        testReport.setupReport();
    }

    @Before
    public void setUp() {
        driverUtils = new DriverUtils();
        driverUtils.init();
        driver = DriverUtils.getDriver();
        wait = DriverUtils.getWait();
    }

    @Given("I want to click on the 'My Works' section")
    public void i_am_on_the_cv_matteo_homepage() {
        testReport.startTest("Test Sito Cv Matteo - sezione 'I miei lavori' con Cucumber",
                "Test sezione 'I miei lavori', vedere tutti i progetti e se sono corettamente cliccabili");
        DriverUtils.clickElement(By.xpath("//*[@id=\"sidebar\"]/div/nav/ul/li[2]/a"));
    }

    @When("I see correct section, i want to click 'View Full Gallery' button")
    public void i_am_in_correct_section_and_click_full_gallery() {

    }

    @Then("See if there are more clickable sections on the designated page in 'Other Works'")
    public void view_page_other_works() {

    }

    @When("I try opening project")
    public void i_try_opening_project() {

    }

    @Then("I redirects to GitHub")
    public void i_redirects_to_GitHub() {

    }

    @And("I want to close new page")
    public void close_new_page() {

    }

    @After
    public static void teardown() {
        driverUtils.close();
        testReport.tearDown();
        openTestReport();
    }

}
