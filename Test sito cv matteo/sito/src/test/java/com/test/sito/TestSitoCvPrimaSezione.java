package com.test.sito;

import com.test.sito.utils.DriverUtils;
import com.test.sito.utils.TestReport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;


import static com.test.sito.utils.DriverUtils.assertTrueCustom;
import static com.test.sito.utils.DriverUtils.openTestReport;

class TestSitoCvPrimaSezione {
    private static DriverUtils driverUtils;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static TestReport testReport;

    @BeforeEach
    public void setUpReport() {
        testReport = new TestReport();
        testReport.setupReport();
    }

    @BeforeEach
    public void setUp() {
        driverUtils = new DriverUtils();
        driverUtils.init();
        driver = DriverUtils.getDriver();
        wait = DriverUtils.getWait();
    }

    @Test
    void testGeneralePrimaSezione() throws InterruptedException {
        testReport.startTest("Test Sito Cv Matteo - sezione 'Su di me'",
                "Test sezione 'Su di me', click sulle bandiere e download PDF");

        /**
         CONTROLLA SE VIENE CLICCATA LA BANDIERA INGLESE
         **/
        DriverUtils.clickElement(By.xpath("//*[@id=\"one\"]/a[2]"));
        String expectedEnglishText = "by utilizing technology";
        String actualEnglishText = DriverUtils.getElementText(By.xpath("//*[@id='one']/p[1]"));
        assertTrueCustom(testReport, actualEnglishText.contains(expectedEnglishText),
                "Il testo specifico è presente, la lingua è inglese",
                "Il testo specifico non è presente, non è cambiata la lingua");

        /**
         CONTROLLA SE IL CV CARTACEO E' STATO SCARICATO
         **/
        DriverUtils.clickElement(By.className("button"));
        // Ottieni il percorso della directory di download predefinita per il sistema operativo
        String downloadDirectory = System.getProperty("user.home") + File.separator + "Downloads";

        // Attendi un po' per il download
        Thread.sleep(3000);

        // Verifica se il file è stato scaricato correttamente
        String partialFileName = "Matteo";
        File downloadDir = new File(downloadDirectory);
        File[] matchingFiles = downloadDir.listFiles((dir, name) -> name.contains(partialFileName));
        assertTrueCustom(testReport, matchingFiles != null && matchingFiles.length > 0, "Il file è stato scaricato!", "Il file non è stato scaricato");

        /**
         CONTROLLA SE VIENE CLICCATA LA BANDIERA ITALIANA
         **/
        DriverUtils.clickElement(By.xpath("//*[@id='one']/a[1]"));
        String expectedItalianText = "ho conoscenze di sviluppo front-end";
        String actualItalianText = DriverUtils.getElementText(By.xpath("//*[@id='one']/p[1]"));
        assertTrueCustom(testReport, actualItalianText.contains(expectedItalianText),
                "Il testo specifico è presente, la lingua ora è italiana",
                "Il testo specifico non è presente, non è cambiata la lingua");

        /**
         CLICCA SU CONTATTI E CONTROLLA CHE CI SIA LA SCRITTA "SU DI ME". OVVIAMENTE E' NON LA TROVERA' E IL TEST FALLIRA' PRODUCENDO LO SCREENSHOT SUL REPORT
         **/
        // Clicca sul link "Contatti"
        DriverUtils.clickElement(By.xpath("//*[@id=\"sidebar\"]/div/nav/ul/li[3]/a")); //*[@id="sidebar"]/div/nav/ul/li[2]/a

        // Verifica se il titolo della pagina coincide con "Su di me"
        String expectedTitle = "Su di me"; //Se qui mettiamo "Contatti", il test ovviamente passa
        By elementLocator = By.xpath("//*[@id=\"three\"]/h2");
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));

        // Verifica se l'elemento è visibile a schermo e contiene il testo atteso
        boolean isTextPresentInSection = element.isDisplayed() && element.getText().contains(expectedTitle);

        Thread.sleep(1000);
        assertTrueCustom(testReport, isTextPresentInSection,
                "Il titolo della pagina coincide con 'Su di me'. Sei nella sezione corretta!",
                "Il titolo della pagina non coincide con 'Su di me'. Sei nella sezione sbagliata!");

    }

    @AfterAll
    public static void teardown() {
        driverUtils.close();
        testReport.tearDown();
        openTestReport();
    }
}
