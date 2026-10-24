

package testSuite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    @BeforeEach
    public void openBrowserProxy(){
        Proxy proxy = new Proxy();
        proxy.setHttpProxy("localhost:8080");
        proxy.setSslProxy("localhost:8080");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.setProxy(proxy);

        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("http://demo.owasp-juice.shop/");
    }
    //zapv6@zap.com  12345
    @Test
    public void verifyLogin(){
        // --- SOLUCIÓN PARA VENTANAS EMERGENTES ---
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // 1. Cierra el Banner de Bienvenida ("Welcome Notice") usando su atributo aria-label
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[aria-label='Close Welcome Banner']"))).click();

        // 2. Cierra la barra de Cookies ("Cookie Consent") usando su atributo aria-label
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[aria-label='dismiss cookie message']"))).click();

// XPaths simplificados y corregidos apuntando directamente a los identificadores principales
        String xpathAccountButton = "//*[@id='navbarAccount']";
        String xpathMenuLogin = "//*[@id='navbarLoginButton']";

        driver.findElement(By.xpath(xpathAccountButton)).click();
        driver.findElement(By.xpath(xpathMenuLogin)).click();

        // MODIFICACIÓN: Ingreso de las credenciales comentadas
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("ucb@ucb.com");
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("12345");

        driver.findElement(By.xpath("//*[@id=\"loginButton\"]")).click();

        // Esperamos a que la pantalla principal cargue antes de verificar la aserción
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product")));

        // Aserción corregida buscando la sección principal de resultados
        Assertions.assertTrue(driver.findElement(By.xpath("//app-search-result")).isDisplayed());
    }


    @AfterEach
    public void closeBrowser(){
        driver.quit();

    }




}
