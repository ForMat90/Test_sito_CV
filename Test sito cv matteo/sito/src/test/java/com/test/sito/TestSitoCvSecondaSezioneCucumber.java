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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static com.test.sito.utils.DriverUtils.assertTrueCustom;
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
        String actualText = DriverUtils.getElementText(By.xpath("//*[@id=\"two\"]/h2"));
        String expectedText = "I miei lavori";
        String alternativeText = "My works";
        assertTrueCustom(testReport, actualText.contains(expectedText) || actualText.contains(alternativeText),
                "Il testo specificato è presente, siamo nella sezione giusta",
                "Il testo specificato non è presente, la sezione che stiamo verificando è errata!");

        By fullGalleryButton = By.xpath("//*[@id=\"main\"]/ul/li");
        wait.until(ExpectedConditions.elementToBeClickable(fullGalleryButton));
        DriverUtils.clickElement(By.xpath("//*[@id=\"main\"]/ul/li"));
    }

    @Then("See if there are more clickable sections on the designated page in 'Other Works'")
    public void view_page_other_works() {

        boolean isAltriLavoriPresent = DriverUtils.ifElementExist(DriverUtils.getWebElement(By.id("more-works")), "Altri lavori");
        boolean isMoreWorksPresent = DriverUtils.ifElementExist(DriverUtils.getWebElement(By.id("more-works")), "More works");

        boolean isPagePresent = isAltriLavoriPresent || isMoreWorksPresent;

        assertTrueCustom(testReport, isPagePresent,
                "La pagina 'Altri lavori' è presente!",
                "La pagina 'Altri lavori' non è presente!");
    }

    @When("I try opening project")
    public void i_try_opening_project() {
        // Ottieni l'elenco delle gestioni delle finestre prima di fare clic
        Set<String> beforeWindowHandles = driver.getWindowHandles();

        // Fai clic sull'elemento che dovrebbe aprire una nuova scheda o finestra
        DriverUtils.clickElement(By.cssSelector("#more-works > div > article > a"));

        // Ottieni l'elenco delle gestioni delle finestre dopo il clic
        Set<String> afterWindowHandles = driver.getWindowHandles();

        // Verifica se c'è una nuova finestra o scheda aperta
        boolean isNewTabOrWindowOpened = afterWindowHandles.size() > beforeWindowHandles.size();

        assertTrueCustom(testReport, isNewTabOrWindowOpened, "Aperta una nuova scheda", "Nuova scheda non aperta");
    }

    @Then("I redirects to GitHub")
    public void i_redirects_to_GitHub() {
        // Ottieni l'elenco delle gestioni delle finestre prima di fare click
        Set<String> windowHandles = driver.getWindowHandles();

        // Passa il controllo alla nuova scheda se ce n'è una
        for (String handle : windowHandles) {
            if (!handle.equals(driver.getWindowHandle())) {
                driver.switchTo().window(handle);
                break;
            }
        }
        // Verifica se la URL corrente è esattamente uguale al link desiderato
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "https://github.com/ForMat90/Test_sito_CV";
        assertTrueCustom(testReport, currentUrl.equals(expectedUrl), "La scheda aperta contiene il link esatto del progetto su GitHub",
                "La scheda aperta non contiene il link esatto del progetto su GitHub");
    }

    @And("I want to close new page")
    public void close_new_page() {

        // Chiudi la scheda corrente
        driver.close();

        // Passa il controllo alla finestra del CV
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        // Verifica se sei alla scheda corretta con l'URL desiderato
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        assertTrueCustom(testReport, currentUrl.equals("https://cv-matteo.onrender.com/"), "Siamo alla scheda corretta",
                "Non siamo alla scheda corretta");
    }

    @After
    public static void teardown() {
        driverUtils.close();
        testReport.tearDown();
        openTestReport();
    }

}
