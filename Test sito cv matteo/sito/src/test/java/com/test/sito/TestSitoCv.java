package com.test.sito;

import com.test.sito.utils.DriverUtils;
import com.test.sito.utils.TestReport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.test.sito.utils.DriverUtils.openTestReport;

class TestSitoCv {
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
    void testGenerale() {
        testReport.startTest("Test Sito Cv Matteo", "Test funzionamento pagina");

    }

//    // Cliccare sul terzo prodotto di makeUp
//        DriverUtils.clickElement(By.xpath(clickThirdProductMakeup));
//    assertTrueCustom(testReport, DriverUtils.titleInPage("Waterproof"), "Hai cliccato il terzo prodotto di MakeUp",
//            "Non hai cliccato il terzo prodotto di MakeUp");
//
//    // Inserire nel carrello il terzo elemento 3 volte
//        DriverUtils.writeText(By.xpath(insertIntoCartThreeItemsOfProductMakeup), "3");
//
//    WebElement addToCartButton = DriverUtils.getWebElement(By.xpath(confirmInsertIntoCartThreeItemsOfProductMakeup));
//        assert addToCartButton != null;
//        addToCartButton.click();
//    assertTrueCustom(testReport, DriverUtils.titleInPage("Shopping Cart"), "Hai inserito il terzo elemento per 3 volte nel carrello",
//            "Non hai inserito il terzo elemento per 3 volte nel carrello");
//
//    // Selezionare il carrello
//    WebElement cartLink = DriverUtils.getWebElement(By.xpath(getCart));
//        assert cartLink != null;
//        cartLink.click();
//
//    assertTrueCustom(testReport, DriverUtils.titleInPage("Shopping Cart"), "Hai cliccato il carello",
//            "Non hai cliccato il carello");

    @AfterAll
    public static void teardown() {
        driverUtils.close();
        testReport.tearDown();
        openTestReport();
    }
}
