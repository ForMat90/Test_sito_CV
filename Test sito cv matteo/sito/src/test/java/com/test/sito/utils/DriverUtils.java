package com.test.sito.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverUtils {

    @Getter
    private static WebDriver driver;
    @Getter
    private static WebDriverWait wait;
    private final int waitTimeInSeconds = 30;
    private final Duration duration = Duration.ofSeconds(waitTimeInSeconds);
    private static final Logger logger = Logger.getLogger(DriverUtils.class.getName());

    public void init() {
        try {
            // Leggere il file Json
            InputStream inputStream = getClass().getResourceAsStream("/jsonConfig/JsonDriverConfig.json");
            assert inputStream != null;
            InputStreamReader reader = new InputStreamReader(inputStream);
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(reader).getAsJsonObject();

            // Inizializzare il driver con le specifiche del Json
            String browser = "chrome";

            JsonArray drivers = jsonObject.getAsJsonArray("drivers");
            for (int i = 0; i < drivers.size(); i++){
                JsonObject driverOptions = drivers.get(i).getAsJsonObject();
                String driverType = driverOptions.get("driverType").getAsString();
                if (driverType.equals(browser)){
                    switch (driverType.toLowerCase()) {
                        case "chrome" -> {
                            WebDriverManager.chromedriver().setup();
                            JsonObject chromeOptionsJson = driverOptions.getAsJsonObject("chromeOptions");
                            ChromeOptions chromeOptions = new ChromeOptions();
                            chromeOptions.addArguments(chromeOptionsJson.get("startMaximized").getAsString());
                            driver = new ChromeDriver(chromeOptions);
                        }
                        case "firefox" -> {
                            WebDriverManager.firefoxdriver().setup();
                            JsonObject firefoxOptionsJson = driverOptions.getAsJsonObject("firefoxOptions");
                            FirefoxOptions firefoxOptions = new FirefoxOptions();
                            firefoxOptions.addArguments(firefoxOptionsJson.get("startMaximized").getAsString());
                            driver = new FirefoxDriver(firefoxOptions);
                        }
                        case "edge" -> {
                            WebDriverManager.edgedriver().setup();
                            JsonObject edgeOptionsJson = driverOptions.getAsJsonObject("edgeOptions");
                            EdgeOptions edgeOptions = new EdgeOptions();
                            edgeOptions.addArguments(edgeOptionsJson.get("startMaximized").getAsString());
                            driver = new EdgeDriver(edgeOptions);
                        }
                        default -> throw new IllegalArgumentException("Driver non valido: " + driverType);
                    }

                }
            }

            wait = new WebDriverWait(driver, duration);
            String baseUrl = "https://cv-matteo.onrender.com/";
            driver.get(baseUrl);
            ColoredLogger.info(logger, "Browser avviato al link " + baseUrl);

        } catch (Exception e) {

            logger.severe("Errore durante l'inizializzazione del driver: " + e.getMessage());
            ColoredLogger.info(logger, "Il tipo di driver specificato non è valido. Scegliere uno dei seguenti: chrome, firefox, edge.");
        }
    }

    public void close() {
        try {
            driver.quit();
            ColoredLogger.info(logger, "Browser chiuso");

        } catch (Exception e) {
            logger.severe("Errore durante la chiusura del browser: " + e.getMessage());
        }
    }

    public static WebElement getWebElement(By by) {
        try {
            return wait.until(driver -> driver.findElement(by));
        } catch (TimeoutException e) {
            System.err.println("Timed out: " + by);
            return null;
        } catch (NoSuchElementException e) {
            System.err.println("Elemento non trovato: " + by);
            return null;
        }
    }


    public static List<WebElement> getWebElements(By by) {
        try {
            return wait.until(driver -> driver.findElements(by));
        } catch (TimeoutException e) {
            System.err.println("Timed out: " + by);
            return null;
        } catch (NoSuchElementException e) {
            System.err.println("Elemento non trovato: " + by);
            return null;
        }
    }

    public static void clickElement(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by)).click();
        } catch (TimeoutException e) {
            System.err.println("Timed out: " + by);

        } catch (NoSuchElementException e) {
            System.err.println("Elemento non trovato: " + by);

        }
    }

    public static boolean titleInPage(String expectedTitle) {
        try {
            return wait.until(ExpectedConditions.titleContains(expectedTitle));
        } catch (TimeoutException e) {
            System.err.println("Timed out: " + expectedTitle);
            return false;
        }
    }


    public static boolean hrefContains(WebElement element, String hrefValue) {
        try {
            return element.getAttribute("href").contains(hrefValue);
        } catch (NoSuchElementException | NullPointerException e) {
            return false;
        }
    }

    public static void writeText(By by, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            element.clear();
            element.sendKeys(text);
        } catch (TimeoutException e) {
            System.err.println("Timed out: " + by);
        } catch (NoSuchElementException e) {
            System.err.println("Elemento non trovato: " + by);
        }
    }

    public static boolean ifElementExist(WebElement element, String text) {
        try {
            String actualText = element.getText();
            return actualText.contains(text);
        } catch (NoSuchElementException | StaleElementReferenceException | NullPointerException e) {
            return false;
        }
    }

    public static String getElementText(By element) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            return DriverUtils.getDriver().findElement(element).getText();
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
            logger.log(Level.WARNING, "\033[33mImpossibile trovare l'elemento: " + element, e + "\033[0m");
            return "";
        }
    }

    public static void assertTrueCustom(TestReport testReport, boolean condition, String messagePass, String messageFail) {
        if (condition) {
            testReport.pass(messagePass);
        } else {
            testReport.fail(messageFail);
            Assert.fail(messageFail);
        }
    }

    public static void openTestReport() {
        String filePath = "test-report.html";
        File file = new File(filePath);

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("L'apertura del file non è supportata sulla piattaforma corrente.");
        }
    }

}
