import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class BharatPasudhan extends DataProvider {

    public static final String USERNAME = "pdktait84_TN";
    public static final String PASSWORD = "pdktait84_TN";

    static WebDriver driver = new FirefoxDriver();
    static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    static Actions action = new Actions(driver);

    public static void main(String[] args) throws IOException {

        doPregnancyDiagnosis();
//        doCalving();

    }

    public static void doPregnancyDiagnosis() throws IOException {
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
        if (retryingFindingElement(By.xpath("//a[@href='#submenu2'][@aria-expanded='true']"))) {
            new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='submenu2']//li[3]//span[1]//a[1]")));
            driver.findElement(By.xpath("//ul[@id='submenu2']//li[3]//span[1]//a[1]")).click();
        } else {
            new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Animal Breeding')]")));
            driver.findElement(By.xpath("//div[contains(text(),'Animal Breeding')]")).click();
            new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='submenu2']//li[3]//span[1]//a[1]")));
            driver.findElement(By.xpath("//ul[@id='submenu2']//li[3]//span[1]//a[1]")).click();
        }
    }


    public static void clickOnPregnancyDiagnosisTab() {
        if (retryingFindingElement(By.xpath("//a[@href='#submenu2'][@aria-expanded='true']"))) {
            new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='submenu2']//li[2]//span[1]//a[1]")));
            driver.findElement(By.xpath("//ul[@id='submenu2']//li[2]//span[1]//a[1]")).click();
        } else {
            new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Animal Breeding')]")));
            driver.findElement(By.xpath("//div[contains(text(),'Animal Breeding')]")).click();
            new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='submenu2']//li[2]//span[1]//a[1]")));
            driver.findElement(By.xpath("//ul[@id='submenu2']//li[2]//span[1]//a[1]")).click();
        }
    }

    public static String getInseminationDateFromCalvingHistoryTable() {
        final String inseminationDate;
        System.out.println("Animal is pregnant and is in 'milk' state");
        new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='View']")));
        driver.findElement(By.xpath("//a[normalize-space()='View']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='table-responsive custom-view-table']//td[normalize-space()='Pregnancy Confirmed']/ancestor::tr//td[2]")));
        inseminationDate = driver.findElement(By.xpath("//div[@class='table-responsive custom-view-table']//td[normalize-space()='Pregnancy Confirmed']/ancestor::tr//td[2]")).getText();
        System.out.println("Insemination date from calving table is " + inseminationDate);
        return  inseminationDate;
    }

    public static String getInseminationDateFromPregnancyDiagnosis(String animalId) {
        String inseminationDate = null;
        System.out.println("Animal is pregnant and is in 'milk' state");
        clickOnPregnancyDiagnosisTab();
        new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search-by']")));
        retryingFindClick(By.xpath("//input[@id='search-by']"), animalId);
        new WebDriverWait(driver, Duration.ofSeconds(20)).ignoring(StaleElementReferenceException.class).ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=' submit']")));
        driver.findElement(By.cssSelector("button[type=' submit']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@role='table']")));
        driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
        WebElement newPdButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
        wait.until(ExpectedConditions.elementToBeClickable(newPdButton));
        if (newPdButton.isEnabled()) {
            driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']")).click();
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@class='mat-table cdk-table mat-elevation-z8']//td[2]")));
            inseminationDate = driver.findElement(By.xpath("//table[@class='mat-table cdk-table mat-elevation-z8']//td[2]")).getText();
        }
        return inseminationDate;
    }

    public static void handleOwnerNotFound() {
        // To handle owner is not found case
        if (checkElementExists(By.xpath("//div[@class='common-error-footer']//button[@class='btn btn-primary']"))) {
            System.out.println("Owner is not found error");
            WebElement errorBanner = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='common-error-footer']//button[@class='btn btn-primary']")));
            wait.until(ExpectedConditions.elementToBeClickable(errorBanner));
            errorBanner.click();
//            continue;
        }
    }

    public static void pregnancyDiagnosis() throws IOException {
        for (int i = 0; i < getAllAnimalTagId().size(); i++) {
            // Get values from Excel
            String animalId = getAllAnimalTagId().get(i);
            System.out.println("------------");
            System.out.println("PD - Animal Id is " + animalId);
            new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search-by']")));
            retryingFindClick(By.xpath("//input[@id='search-by']"), animalId);
            new WebDriverWait(driver, Duration.ofSeconds(20)).ignoring(StaleElementReferenceException.class).ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=' submit']")));
            driver.findElement(By.cssSelector("button[type=' submit']")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // Data table
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@role='table']//td[8]")));
            WebElement isPregnant = driver.findElement(By.xpath("//table[@role='table']//td[8]"));
            if (isPregnant.getText().contains("No")) {
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newPdButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newPdButton));
                if (newPdButton.isEnabled()) {
                    driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']")).click();
                    new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@class='mat-table cdk-table mat-elevation-z8']//td[2]")));
                    WebElement inseminationDate = driver.findElement(By.xpath("//table[@class='mat-table cdk-table mat-elevation-z8']//td[2]"));
                    WebElement pregnancyStatus = driver.findElement(By.xpath("//span[@class='status-highlight']"));
                    wait.until(ExpectedConditions.elementToBeClickable(inseminationDate));
                    wait.until(ExpectedConditions.elementToBeClickable(pregnancyStatus));
                    System.out.println("Pregnancy status is " + pregnancyStatus.getText());
                    System.out.println("Insemination date is " + inseminationDate.getText());
                    String animalDate = getDescDateOfAnimalTagId(inseminationDate.getText());
                    if (Objects.equals(pregnancyStatus.getText(), "PD Due")) {
                        WebElement pdDate = driver.findElement(By.xpath("//input[@formcontrolname='pdDate']"));
                        new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@formcontrolname='pdDate']")));
                        clearWebField(pdDate);
                        pdDate.sendKeys(animalDate);
                        clickOutside();
                        System.out.println("Pregnancy date is set as " + animalDate);
                        Select selectPdResult = new Select(driver.findElement(By.xpath("//select[@formcontrolname='pdResult']")));
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        selectPdResult.selectByIndex(1);
                        clickOutside();
                        Select serviceTypeDropdown = new Select(driver.findElement(By.xpath("//select[@formcontrolname='serviceType']")));
                        clickOutside();
                        WebElement finalSubmitButton = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
                        if (finalSubmitButton.isDisplayed() && finalSubmitButton.isEnabled() && serviceTypeDropdown.getFirstSelectedOption().getText().contains("Internal AI") && selectPdResult.getFirstSelectedOption().getText().contains("Pregnancy Confirmed")) {
                            finalSubmitButton.click();
                            if (checkElementExists(By.xpath("//div[@class='common-info']//button[normalize-space()='Yes']"))) {
                                driver.findElement(By.xpath("//div[@class='common-info']//button[normalize-space()='Yes']")).click();
                                if (checkElementExists(By.xpath("//div[@class='common-info']//button[normalize-space()='OK']"))) {
                                    driver.findElement(By.xpath("//div[@class='common-info']//button[normalize-space()='OK']")).click();
                                }
                            } else {
                                WebElement modalOk = driver.findElement(By.xpath("//div[@class='campaign-dialog']//button[@type='submit']"));
                                wait.until(ExpectedConditions.elementToBeClickable(modalOk));
                                modalOk.click();
                            }
                        }
                        System.out.println("Pregnancy diagnosis updated for " + animalId + " with pregnancy date " + animalDate);
                        System.out.println("------------");
                        updateExcelSheetWithPregnancyDetails(animalId, animalDate, "Updated");
                        clickOnPregnancyDiagnosisTab();
                    } else {
                        System.out.println("Pregnancy is 'NO' and status is '" + pregnancyStatus.getText() + "' for AnimalId " + animalId);
                        System.out.println("------------");
                        clickOnPregnancyDiagnosisTab();
                        updateExcelSheetWithPregnancyDetails(animalId, animalDate, "Skipped");
                    }
                }
            } else {
                System.out.println("Animal pregnancy status is '" + isPregnant.getText() + "'.  Skipping " + getAllAnimalTagId().get(i));
                System.out.println("------------");
                updateExcelSheetWithPregnancyDetails(animalId, "", "Skipped");
            }
        }
    }

    public static void calving() throws IOException {
        for (int i = 0; i < getAllAnimalTagId().size(); i++) {
            // Get values from Excel
            String animalId = getAllAnimalTagId().get(i);
            System.out.println("------------");
            System.out.println("Calving - Animal Id is " + animalId);
            new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search-by']")));
            retryingFindClick(By.xpath("//input[@id='search-by']"), animalId);
            new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=' submit']")));
            driver.findElement(By.cssSelector("button[type=' submit']")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

//            handleOwnerNotFound();

            WebElement searchTable = driver.findElement(By.xpath("//table[@role='table']"));
            wait.until(ExpectedConditions.visibilityOf(searchTable));
            WebElement isPregnant = driver.findElement(By.xpath("//table[@class='mat-table cdk-table mat-sort mat-elevation-z8 mt-4']//td[8]"));
            WebElement milkingStatus = driver.findElement(By.xpath("//table[@class='mat-table cdk-table mat-sort mat-elevation-z8 mt-4']//td[9]"));
            wait.until(ExpectedConditions.visibilityOf(isPregnant));
            wait.until(ExpectedConditions.visibilityOf(milkingStatus));
            if (searchTable.isDisplayed() && isPregnant.getText().contains("Yes") && milkingStatus.getText().contains("Dry")) {
                System.out.println("Animal is pregnant and is in 'dry' state");
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@role='table']")));
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newCalvingButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newCalvingButton));
                newCalvingButton.click();
                commonFlowForCalving();

            } else if (searchTable.isDisplayed() && isPregnant.getText().contains("Yes") && milkingStatus.getText().contains("NA")) {
                System.out.println("Animal is pregnant and is in 'NA' state");
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@role='table']")));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='selectedTagId']")));
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newCalvingButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newCalvingButton));
                newCalvingButton.click();
                commonFlowForCalving();

            } else if (searchTable.isDisplayed() && isPregnant.getText().contains("Yes") && milkingStatus.getText().contains("In Milk")) {
                String calvingDate = getAnimalDatePlusSixMonths(getInseminationDateFromCalvingHistoryTable());
                System.out.println("Gestation date is added by 6 months from insemination date - " + calvingDate);
                // Calving flow
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='back-arrow']")));
                driver.findElement(By.xpath("//div[@class='back-arrow']")).click();
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@role='table']")));
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newCalvingButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newCalvingButton));
                newCalvingButton.click();
                WebElement dryOffDate = driver.findElement(By.xpath("//div[@class='ear-tag-detail']//input[@placeholder='dd-mm-yyyy']"));
                wait.until(ExpectedConditions.elementToBeClickable(dryOffDate));
                dryOffDate.sendKeys(calvingDate);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@formcontrolname='milkingStatus']")));
                Select milkingStatusDropdown = new Select(driver.findElement(By.xpath("//select[@formcontrolname='milkingStatus']")));
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
            String addedNineMonthsDate = getAnimalDatePlusNineMonths(pregnancyDateFromTable);
            WebElement gestationDays = driver.findElement(By.xpath("//table[@class='table animal-table m-0 ng-star-inserted']//td[5]"));
            String modifiedGestationDate = handleGestationDate(addedNineMonthsDate, pregnancyDateFromTable);
            System.out.println("Gestation modified animal date is " + modifiedGestationDate);
            clearWebField(calvingDate);
            calvingDate.sendKeys(modifiedGestationDate);
            clickOutside();
            if (checkGestationRange(gestationDays.getText())) {
                new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@formcontrolname='calvingStatus']")));
                Select selectPdResult = new Select(driver.findElement(By.xpath("//select[@formcontrolname='calvingStatus']")));
                selectPdResult.selectByIndex(1);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(selectPdResult.getFirstSelectedOption().getText().contains("Successful Calving")) {
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@formcontrolname='noOfCalves']")));
                    Select noOfCalves = new Select(driver.findElement(By.xpath("//select[@formcontrolname='noOfCalves']")));
                    noOfCalves.selectByIndex(1);
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@formcontrolname='calvingEase']")));
                    Select calvingEase = new Select(driver.findElement(By.xpath("//select[@formcontrolname='calvingEase']")));
                    calvingEase.selectByIndex(1);
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@formcontrolname='serviceType']")));
                    Select serviceType = new Select(driver.findElement(By.xpath("//select[@formcontrolname='serviceType']")));
                    serviceType.selectByIndex(1);
                    clickOutside();
                    WebElement nextButton = driver.findElement(By.xpath("//button[normalize-space()='Next']"));
                    if (nextButton.isDisplayed() && nextButton.isEnabled()) {
                        nextButton.click();
                        WebElement isEarTag = driver.findElement(By.xpath("//div[@class='form-row additional-info ng-star-inserted']//div[2]//input[1]"));
                        wait.until(ExpectedConditions.elementToBeClickable(isEarTag));
                        isEarTag.click();
                        WebElement male = driver.findElement(By.xpath("(//input[@id='sexOfCalf_0'])[1]"));
                        WebElement female = driver.findElement(By.xpath("(//input[@id='sexOfCalf_0'])[2]"));
                        wait.until(ExpectedConditions.elementToBeClickable(male));
                        male.click();
                        Select reasonForNotRegistering = new Select(driver.findElement(By.xpath("//select[@formcontrolname='reasonForNotRegistering']")));
                        int randomNumber = (int) (Math.random() * 2) + 1;
                        reasonForNotRegistering.selectByIndex(randomNumber);
                        WebElement submitButton = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
                        if (submitButton.isEnabled() && submitButton.isDisplayed()) {
                            submitButton.click();
                        }
                        clickOutside();
                        clickOnCalvingTab();
                    }
                } else {
                    System.out.println("Unable to select 'Successful Calving option from calving status dropdown'");
                }
                System.out.println("Calving is updated");
                clickOutside();
                driver.get("https://bharatpashudhan.ndlm.co.in/dashboard/");
                clickOnCalvingTab();
                System.out.println("------------");
            } else {
                System.out.println("The gestation date range is out of bounds - " + gestationDays.getText());
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

    public static boolean retryingFindClick(By by, String animalId) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                driver.findElement(by).clear();
                driver.findElement(by).sendKeys(animalId);
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
            } catch (NoSuchElementException e) {
            }
            attempts++;
        }
        return result;
    }

    public static boolean retryingFindingElement(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                driver.findElement(by).isDisplayed();
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
            } catch (NoSuchElementException e) {
            }
            attempts++;
        }
        return result;
    }

    public static boolean checkGestationRange(String number) {
        int num = Integer.parseInt(number);
        System.out.println("Gestation days is - " + num);
        return num >= 270 && num <= 280;
    }

    public static boolean checkElementExists(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 1) {
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

    public static String handleGestationDate(String addedDate, String initialDate) {
        String output;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate initialDateParse = LocalDate.parse(initialDate, formatter);
        LocalDate finalDateParse = LocalDate.parse(addedDate, formatter);
        long daysBetween = ChronoUnit.DAYS.between(initialDateParse, finalDateParse);
        int randomNumber = (int) (Math.random() * 2) + 1;
        if (!(daysBetween >= 270 && daysBetween <= 280)) {
            output = initialDateParse.plusMonths(9).plusDays(randomNumber).format(formatter);
        } else {
            if (finalDateParse.isAfter(currentDate)) {
                output = currentDate.format(formatter);
            } else {
                output = finalDateParse.plusDays(randomNumber).format(formatter);
            }
        }
        return output;
    }

    public static void closeAll() {
        driver.close();
        driver.quit();
    }

}
