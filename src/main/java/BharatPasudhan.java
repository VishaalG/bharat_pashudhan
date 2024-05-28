import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class BharatPasudhan extends DataProvider {

    public static final String USERNAME = "pdktait37_TN";
    public static final String PASSWORD = "Prem123@#";

    static WebDriver driver = new FirefoxDriver();
    static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
    static Actions action = new Actions(driver);

    public static void main(String[] args) throws IOException {

        doCalving();

    }

    public static void doPregnancyDiagnosis() throws IOException, ParseException {
        // Login flow
        loginToPortal();

        // Artificial Insemination
        clickOnPregnancyDiagnosisTab();

        // Pregnancy Diagnosis
        pregnancyDiagnosis();

        // Close Browser
        closeAll();
    }

    public static void doCalving() throws IOException {
        // Login flow
        loginToPortal();

        // Click Calving
        clickOnCalvingTab();

        // Pregnancy Diagnosis
        calving();

        // Close Browser
        closeAll();
    }

    public static void loginToPortal() {
        driver.get("https://bharatpashudhan.ndlm.co.in/auth/login");
        driver.manage().window().maximize();
        WebElement userIdTextBox = driver.findElement(By.xpath("//input[@name='username']"));
        userIdTextBox.sendKeys(USERNAME);
        WebElement passwordTextBox = driver.findElement(By.xpath("//input[@id='password']"));
        passwordTextBox.sendKeys(PASSWORD);
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
    }

    public static void clickOnArtificialInseminationTab() {
        WebElement animalBreeding = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Animal Breeding')]")));
        animalBreeding.click();
        driver.findElement(By.xpath("//ul[@id='submenu2']//li[1]//span[1]//a[1]")).click();
    }

    public static void clickOnCalvingTab() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Animal Breeding')]")));
        driver.findElement(By.xpath("//div[contains(text(),'Animal Breeding')]")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='submenu2']//li[3]//span[1]//a[1]")));
        driver.findElement(By.xpath("//ul[@id='submenu2']//li[3]//span[1]//a[1]")).click();
    }


    public static void clickOnPregnancyDiagnosisTab() {
        WebElement animalBreeding = driver.findElement(By.xpath("//div[contains(text(),'Animal Breeding')]"));
        new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(animalBreeding));
        animalBreeding.click();
        driver.findElement(By.xpath("//ul[@id='submenu2']//li[2]//span[1]//a[1]")).click();
    }

    public static void clickOnSearchBar() {
        WebElement searchBar = driver.findElement(By.xpath("//input[@id='search-by']"));
        new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(searchBar));
        searchBar.click();
    }

    public static void pregnancyDiagnosis() throws IOException, ParseException {
        for (int i = 0; i < getAllAnimalTagId().size(); i++) {
            // Get values from Excel
            String animalId = getAllAnimalTagId().get(i);
            System.out.println("PD - Animal Id is" + animalId);
            clickOnPregnancyDiagnosisTab();
            new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search-by']")));
            retryingFindClick(By.xpath("//input[@id='search-by']"), animalId);
            driver.findElement(By.cssSelector("button[type=' submit']")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // Data table
            WebElement searchTable = driver.findElement(By.xpath("//table[@role='table']"));
            if (searchTable.isDisplayed()) {
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newAiButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newAiButton));
                if (newAiButton.isEnabled()) {
                    newAiButton.click();
                    WebElement pregnantDate = driver.findElement(By.xpath("//table[@class='mat-table cdk-table mat-elevation-z8']//td[2]"));
                    WebElement pregnancyStatus = driver.findElement(By.xpath("//span[@class='status-highlight']"));
                    wait.until(ExpectedConditions.elementToBeClickable(pregnantDate));
                    wait.until(ExpectedConditions.elementToBeClickable(pregnancyStatus));
                    String animalDate = getAnimalDatePlusThreeMonths(pregnantDate.getText());
                    System.out.println("Animal pregnancy date is set as " + animalDate);
                    if (Objects.equals(pregnancyStatus.getText(), "PD Due")) {
                        WebElement pdDate = driver.findElement(By.xpath("//input[@formcontrolname='pdDate']"));
                        new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@formcontrolname='pdDate']")));
                        clearWebField(pdDate);
                        pdDate.sendKeys(animalDate);
                        pdDate.click();
                        pdDate.sendKeys(Keys.ENTER);
                        Select selectPdResult = new Select(driver.findElement(By.xpath("//select[@formcontrolname='pdResult']")));
                        selectPdResult.selectByIndex(1);
                        clickOutside();
                        selectPdResult.selectByIndex(1);
                        Select serviceTypeDropdown = new Select(driver.findElement(By.xpath("//select[@formcontrolname='serviceType']")));
                        new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(serviceTypeDropdown.getFirstSelectedOption()));
                        String serviceTypeText = serviceTypeDropdown.getFirstSelectedOption().getText();
                        WebElement finalSubmitButton = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
                        if (finalSubmitButton.isDisplayed() && finalSubmitButton.isEnabled() && serviceTypeText.contains("Internal AI")) {
                            finalSubmitButton.click();
                            WebElement modalOk = driver.findElement(By.xpath("//div[@class='campaign-dialog']//button[@type='submit']"));
                            wait.until(ExpectedConditions.elementToBeClickable(modalOk));
                            modalOk.click();
                        }
                        System.out.println("Pregnancy diagnosis updated for " + animalId + " with pregnancy date " + animalDate);

                    } else {
                        System.out.println("Pregnancy status is " + pregnancyStatus.getText());
                        String pregnancyDate = driver.findElement(By.xpath("//table[@class='mat-table cdk-table mat-elevation-z8']//td[4]")).getText();
                        System.out.println("Pregnancy confirmed already for " + animalId);
                        driver.get("https://bharatpashudhan.ndlm.co.in/dashboard");
                        clickOnPregnancyDiagnosisTab();
                    }
                }
            }
        }
    }

    public static void calving() throws IOException {
        for (int i = 0; i < getAllAnimalTagId().size(); i++) {
            // Get values from Excel
            String animalId = getAllAnimalTagId().get(i);
            System.out.println("Calving - Animal Id is" + animalId);
            new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search-by']")));
            retryingFindClick(By.xpath("//input[@id='search-by']"), animalId);
            driver.findElement(By.cssSelector("button[type=' submit']")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // To handle owner is not found case
            if (checkElementExists(By.xpath("//div[@class='common-error-footer']//button[@class='btn btn-primary']"))) {
                System.out.println("Owner is not found error");
                WebElement errorBanner = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='common-error-footer']//button[@class='btn btn-primary']")));
                wait.until(ExpectedConditions.elementToBeClickable(errorBanner));
                errorBanner.click();
                continue;
            }
            // Data table
            WebElement searchTable = driver.findElement(By.xpath("//table[@role='table']"));
            wait.until(ExpectedConditions.visibilityOf(searchTable));
            WebElement isPregnant = driver.findElement(By.xpath("//table[@class='mat-table cdk-table mat-sort mat-elevation-z8 mt-4']//td[8]"));
            WebElement milkingStatus = driver.findElement(By.xpath("//table[@class='mat-table cdk-table mat-sort mat-elevation-z8 mt-4']//td[9]"));
            wait.until(ExpectedConditions.visibilityOf(isPregnant));
            wait.until(ExpectedConditions.visibilityOf(milkingStatus));
            if (searchTable.isDisplayed() && isPregnant.getText().contains("Yes") && milkingStatus.getText().contains("Dry")) {
                System.out.println("Animal is pregnant and is in 'dry' state");
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newCalvingButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newCalvingButton));
                newCalvingButton.click();
                commonFlowForCalving();

            } else if (searchTable.isDisplayed() && isPregnant.getText().contains("Yes") && milkingStatus.getText().contains("NA")) {
                System.out.println("Animal is pregnant and is in 'NA' state");
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newCalvingButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newCalvingButton));
                newCalvingButton.click();
                commonFlowForCalving();

            } else if (searchTable.isDisplayed() && isPregnant.getText().contains("Yes") && milkingStatus.getText().contains("In Milk")) {
                System.out.println("Animal is pregnant and is in 'milk' state");
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newCalvingButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newCalvingButton));
                newCalvingButton.click();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String animalDate = getAnimalDateFromExcelPlusNineMonths(animalId);
                System.out.println("Gestation date added by 9 months from insemination date");
                WebElement dryOffDate = driver.findElement(By.xpath("//div[@class='ear-tag-detail']//input[@placeholder='dd-mm-yyyy']"));
                wait.until(ExpectedConditions.elementToBeClickable(dryOffDate));
                dryOffDate.sendKeys(animalDate);
                Select milkingStatusDropdown = new Select(driver.findElement(By.xpath("//select[@formcontrolname='milkingStatus']")));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                milkingStatusDropdown.selectByIndex(1);
                WebElement submitMilkStatus = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
                wait.until(ExpectedConditions.elementToBeClickable(submitMilkStatus));
                submitMilkStatus.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                commonFlowForCalving();

            } else if (searchTable.isDisplayed() && isPregnant.getText().contains("No")) {
                System.out.println("Animal is not pregnant");
            }
        }
    }

    public static void commonFlowForCalving() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='status-highlight']")));
        WebElement pregnancyStatus = driver.findElement(By.xpath("//span[@class='status-highlight']"));
        wait.until(ExpectedConditions.visibilityOf(pregnancyStatus));
        if (Objects.equals(pregnancyStatus.getText(), "Pregnancy Confirmed")) {
            WebElement calvingDate = driver.findElement(By.xpath("//input[@formcontrolname='calvingDate']"));
            clearWebField(calvingDate);
            String pregnancyDateFromTable = driver.findElement(By.xpath("//table[@class='mat-table cdk-table mat-elevation-z8']//td[2]")).getText();
            String animalDate = getAnimalDatePlusNineMonths(pregnancyDateFromTable);
            calvingDate.sendKeys(animalDate);
            clickOutside();
            WebElement gestationDays = driver.findElement(By.xpath("//table[@class='table animal-table m-0 ng-star-inserted']//td[5]"));
            String modifiedGestationDate = handleGestationDate(animalDate, pregnancyDateFromTable);
            System.out.println("Gestation modified animal date is " + modifiedGestationDate);
            clearWebField(calvingDate);
            calvingDate.sendKeys(modifiedGestationDate);
            clickOutside();
            calvingDate.sendKeys(Keys.ENTER);
            clickOutside();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (checkGestationRange(gestationDays.getText())) {
                Select selectPdResult = new Select(driver.findElement(By.xpath("//select[@formcontrolname='calvingStatus']")));
                selectPdResult.selectByIndex(1);
                clickOutside();
                Select noOfCalves = new Select(driver.findElement(By.xpath("//select[@formcontrolname='noOfCalves']")));
                noOfCalves.selectByIndex(1);
                Select calvingEase = new Select(driver.findElement(By.xpath("//select[@formcontrolname='calvingEase']")));
                calvingEase.selectByIndex(1);
                Select serviceType = new Select(driver.findElement(By.xpath("//select[@formcontrolname='serviceType']")));
                serviceType.selectByIndex(1);
                clickOutside();
                WebElement nextButton = driver.findElement(By.xpath("//button[normalize-space()='Next']"));
                if (nextButton.isDisplayed() && nextButton.isEnabled()) {
                    nextButton.click();
                    WebElement isEarTag = driver.findElement(By.xpath("//div[@class='form-row additional-info ng-star-inserted']//div[2]//input[1]"));
                    wait.until(ExpectedConditions.elementToBeClickable(isEarTag));
                    isEarTag.click();
                    WebElement sex = driver.findElement(By.xpath("(//input[@type='radio'])[4]"));
                    wait.until(ExpectedConditions.elementToBeClickable(sex));
                    sex.click();
                    Select reasonForNotRegistering = new Select(driver.findElement(By.xpath("//select[@formcontrolname='reasonForNotRegistering']")));
                    int randomNumber = (int) (Math.random() * 2) + 1;
                    reasonForNotRegistering.selectByIndex(randomNumber);
                    WebElement submitButton = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
                    if (submitButton.isEnabled() && submitButton.isDisplayed()) {
                        submitButton.click();
                    }
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    clickOutside();
                    driver.navigate().refresh();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                    clickOnCalvingTab();
                }
                System.out.println("Calving is updated");
            } else {
                driver.navigate().refresh();
                clickOnCalvingTab();
            }
        }
    }

    public static void clearWebField(WebElement element) {
        while (!element.getAttribute("value").equals("")) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    public static void clickOutside() {
        action.moveByOffset(0, 0).click().build().perform();
    }

    public static void retryingFindClick(By by, String animalId) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                driver.findElement(by).clear();
                driver.findElement(by).click();
                driver.findElement(by).sendKeys(animalId);
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }

    public static boolean checkGestationRange(String number) {
        int num = Integer.parseInt(number);
        return num >= 270 && num <= 280;
    }

    public static boolean checkElementExists(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                driver.findElement(by);
                result = true;
            } catch (NoSuchElementException e) {
                result = false;
            }
            attempts++;
        }
        return result;
    }

    public static String handleGestationDate(String tableDate, String initialDate) {
        String output;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tableDateParse = LocalDate.parse(initialDate, formatter);
        LocalDate intialDateParse = LocalDate.parse(tableDate, formatter);
        long daysBetween = ChronoUnit.DAYS.between(tableDateParse, intialDateParse);
        int randomNumber = (int) (Math.random() * 3) + 1;

        if (!(daysBetween >= 270 && daysBetween <= 280)) {
            output = intialDateParse.plusDays(270).plusDays(randomNumber).format(formatter);
        } else {
            output = tableDate;
        }
        return output;
    }

    public static void closeAll() {
        driver.close();
        driver.quit();
    }

}
