import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class BharatPasudhan extends DataProvider {

    static WebDriver driver = new FirefoxDriver();
    static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    static Actions action = new Actions(driver);

    public static void main(String[] args) throws IOException, InterruptedException {

        doArtificialInsemination();
//        doPregnancyDiagnosis();
//        doCalving();
//        doVaccination();
    }

    public static void doArtificialInsemination() throws IOException, InterruptedException {
        loginToPortal();
        clickOnArtificialInseminationTab();
        artificialInsemination();
        closeAll();
    }

    public static void doPregnancyDiagnosis() throws IOException, InterruptedException {
        loginToPortal();
        clickOnPregnancyDiagnosisTab();
        pregnancyDiagnosis();
        closeAll();
    }

    public static void doCalving() throws IOException, InterruptedException {
        loginToPortal();
        clickOnCalvingTab();
        calving();
        closeAll();
    }


    public static void doVaccination() throws IOException, InterruptedException {
        loginToPortal();
        clickOnVaccinationTab();
        vaccination();
        closeAll();
    }

    // Start of common navigation tab methods

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

    public static void clickOnArtificialInseminationTab() throws InterruptedException {
        if (retryingFindingElement(By.xpath("//a[@href='#submenu2'][@aria-expanded='true']"))) {
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Artificial Insemination']")));
            driver.findElement(By.xpath("//a[normalize-space()='Artificial Insemination']")).click();
        } else {
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Animal Breeding')]")));
            driver.findElement(By.xpath("//div[contains(text(),'Animal Breeding')]")).click();
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Artificial Insemination']")));
            driver.findElement(By.xpath("//a[normalize-space()='Artificial Insemination']")).click();
        }
    }

    public static void clickOnCalvingTab() throws InterruptedException {
        if (retryingFindingElement(By.xpath("//a[@href='#submenu2'][@aria-expanded='true']"))) {
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Calving']")));
            driver.findElement(By.xpath("//a[normalize-space()='Calving']")).click();
        } else {
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Animal Breeding')]")));
            driver.findElement(By.xpath("//div[contains(text(),'Animal Breeding')]")).click();
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Calving']")));
            driver.findElement(By.xpath("//a[normalize-space()='Calving']")).click();
        }
    }

    public static void clickOnPregnancyDiagnosisTab() throws InterruptedException {
        if (retryingFindingElement(By.xpath("//a[@href='#submenu2'][@aria-expanded='true']"))) {
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Pregnancy Diagnosis']")));
            driver.findElement(By.xpath("//a[normalize-space()='Pregnancy Diagnosis']")).click();
        } else {
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Animal Breeding')]")));
            driver.findElement(By.xpath("//div[contains(text(),'Animal Breeding')]")).click();
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Pregnancy Diagnosis']")));
            driver.findElement(By.xpath("//a[normalize-space()='Pregnancy Diagnosis']")).click();
        }
    }

    public static void clickOnVaccinationTab() throws InterruptedException {
        if (retryingFindingElement(By.xpath("//a[@href='#submenu1'][@aria-expanded='true']"))) {
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Vaccination']")));
            driver.findElement(By.xpath("//a[normalize-space()='Vaccination']")).click();
        } else {
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Animal Health')]")));
            driver.findElement(By.xpath("//div[contains(text(),'Animal Health')]")).click();
            wait.ignoring(ElementNotInteractableException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Vaccination']")));
            driver.findElement(By.xpath("//a[normalize-space()='Vaccination']")).click();
        }
    }

    public static String getInseminationDateFromCalvingHistoryTable() {
        final String inseminationDate;
        System.out.println("Animal is pregnant and is in 'milk' state");
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='View']")));
        driver.findElement(By.xpath("//a[normalize-space()='View']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='table-responsive custom-view-table']//td[normalize-space()='Pregnancy Confirmed']/ancestor::tr//td[2]")));
        inseminationDate = driver.findElement(By.xpath("//div[@class='table-responsive custom-view-table']//td[normalize-space()='Pregnancy Confirmed']/ancestor::tr//td[2]")).getText();
        System.out.println("Insemination date from calving table is " + inseminationDate);
        return inseminationDate;
    }

    // End of common navigation tab methods

    // Start of Artificial Insemination flow

    public static void artificialInsemination() throws IOException, RuntimeException, InterruptedException {
        System.out.println("AI - Total " + getAllAnimalTagId().size() + " entries from excel sheet");
        for (int i = 0; i < getAllAnimalTagId().size(); i++) {
            // Get values from Excel
            String animalId = getAllAnimalTagId().get(i);
            String calvingDateFromAIHistoryTable;
            System.out.println("------ " + i + " ------");
            System.out.println("AI - Animal Id is " + animalId);
            wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search-by']")));
            retryingFindClick(By.xpath("//input[@id='search-by']"), animalId);
            Thread.sleep(1500);
            wait.ignoring(StaleElementReferenceException.class).ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=' submit']")));
            driver.findElement(By.cssSelector("button[type=' submit']")).click();

            // Data table
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@role='table']")));
            WebElement aiHistoryViewButton = driver.findElement(By.xpath("//td[normalize-space()='" + animalId + "']/ancestor::tr//td[12]/a"));
            if (aiHistoryViewButton.isDisplayed()) {
                aiHistoryViewButton.click();
                Thread.sleep(1000);
                if (checkCandidatureForArtificialInsemination(animalId)) {
                    if (RUN_IN_R1.equalsIgnoreCase("Yes")) {
                         calvingDateFromAIHistoryTable = driver.findElement(By.xpath("//td[normalize-space()='PD Due']/ancestor::tr//td[5]")).getText();
                    } else {
                         calvingDateFromAIHistoryTable = driver.findElement(By.xpath("//td[normalize-space()='Successful Calving']/ancestor::tr//td[5]")).getText();
                    }
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='fa fa-chevron-left mr-2 back-section']")));
                    driver.findElement(By.xpath("//i[@class='fa fa-chevron-left mr-2 back-section']")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='selectedTagId']")));
                    driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='New AI']")));
                    driver.findElement(By.xpath("//button[normalize-space()='New AI']")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@formcontrolname='aiDate']")));
                    WebElement aiDate = driver.findElement(By.xpath("//input[@formcontrolname='aiDate']"));
                    Thread.sleep(1000);
                    clearWebField(aiDate);
                    if (getDatePlusSixMonths(calvingDateFromAIHistoryTable) == null) {
                        System.out.println("Insemination date is after current date. Skipping");
                        driver.get("https://bharatpashudhan.ndlm.co.in/dashboard/animal-breeding/artificial-insemination");
                        Thread.sleep(2000);
                        continue;
                    }
                    aiDate.sendKeys(getDatePlusSixMonths(calvingDateFromAIHistoryTable));
                    System.out.println("AI - Insemination date is set as " + getDatePlusSixMonths(calvingDateFromAIHistoryTable));
                    clickOutside();
                    handleAiTimestampAndBullId();
                    driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();
                    updateExcelSheetWithRunDetails(animalId, "Inseminated", "Y");
                    Thread.sleep(5000);
                    System.out.println("AI - Updated for AnimalId " + animalId);
                    clickOutside();
                    System.out.println("---------------");
                }
            }
        }
    }

    public static boolean checkCandidatureForArtificialInsemination(String animalId) throws InterruptedException {
        boolean checkCandidatureForArtificialInsemination = false;
        WebElement checkAiHistoryTable = driver.findElement(By.xpath("(//div[@class='table-responsive custom-view-table'])[1]"));
        wait.until(ExpectedConditions.elementToBeClickable(checkAiHistoryTable));
        if (driver.findElement(By.xpath("(//div[@class='table-responsive custom-view-table'])[1]")).isDisplayed()) {
            Thread.sleep(1000);
            WebElement aiHistoryLatestTableEntry = driver.findElement(By.xpath("(//table[@role='table']//td[8])[1]//span"));
            if (aiHistoryLatestTableEntry.getText().contains("Successful Calving")) {
                System.out.println("AI - Successful calving found for " + animalId);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='fa fa-chevron-left mr-2 back-section']")));
                checkCandidatureForArtificialInsemination = true;
            } else if (aiHistoryLatestTableEntry.getText().contains("PD Due") && RUN_IN_R1.equalsIgnoreCase("Yes")) {
                System.out.println("AI in R1 - PD Due found for " + animalId);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='fa fa-chevron-left mr-2 back-section']")));
                checkCandidatureForArtificialInsemination = true;
            } else {
                System.out.println("History shows '" + aiHistoryLatestTableEntry.getText() + "' for " + animalId + ".  Skipping..");
                Thread.sleep(1000);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='fa fa-chevron-left mr-2 back-section']")));
                driver.findElement(By.xpath("//i[@class='fa fa-chevron-left mr-2 back-section']")).click();
                System.out.println("---------------");
            }
        }
        return checkCandidatureForArtificialInsemination;
    }

    public static void handleAiTimestampAndBullId() throws InterruptedException {
        if (BULL_ID.isEmpty()) {
            driver.close();
        }
        System.out.println("AI - Bull ID is " + BULL_ID);
        WebElement aiTimeStampField = driver.findElement(By.xpath("//input[@formcontrolname='aiTimestamp']"));
        Thread.sleep(1000);
        aiTimeStampField.click();
        aiTimeStampField.clear();
        List<String> randomTime = Arrays.asList("08:32:01", "09:12:14", "10:28:35", "11:38:22", "12:23:45", "13:32:11");
        int randomNumber = (int) (Math.random() * 3) + 1;
        aiTimeStampField.sendKeys(randomTime.get(randomNumber));
        driver.findElement(By.xpath("//input[@formcontrolname='bullId']")).sendKeys(BULL_ID);
        clickOutside();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='selectSemen']")));
        Select semenType = new Select(driver.findElement(By.xpath("//select[@name='selectSemen']")));
        semenType.selectByValue("2");
        clickOutside();
    }

    // End of Artificial Insemination flow and it's methods

    // Start of Pregnancy Diagnosis flow

    public static void pregnancyDiagnosis() throws IOException, InterruptedException {
        System.out.println("PD - Found total of " + getAllAnimalTagId().size() + " entries from excel sheet");
        for (int i = 0; i < getAllAnimalTagId().size(); i++) {
            // Get values from Excel
            String animalId = getAllAnimalTagId().get(i);
            System.out.println("------ " + i + " ------");
            System.out.println("PD - Animal Id is " + animalId);
            wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search-by']")));
            retryingFindClick(By.xpath("//input[@id='search-by']"), animalId);
            wait.ignoring(StaleElementReferenceException.class).ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=' submit']")));
            Thread.sleep(1000);
            driver.findElement(By.cssSelector("button[type=' submit']")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // Data table
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@role='table']//td[8]")));
            WebElement isPregnant = driver.findElement(By.xpath("//table[@role='table']//td[8]"));
            if (isPregnant.getText().contains("No")) {
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newPdButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newPdButton));
                if (newPdButton.isEnabled()) {
                    driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@class='mat-table cdk-table mat-elevation-z8']//td[2]")));
                    WebElement inseminationDate = driver.findElement(By.xpath("//table[@class='mat-table cdk-table mat-elevation-z8']//td[2]"));
                    WebElement pregnancyStatus = driver.findElement(By.xpath("//span[@class='status-highlight']"));
                    String pregnancyStatusDate = pregnancyStatus.getText();
                    wait.until(ExpectedConditions.elementToBeClickable(inseminationDate));
                    wait.until(ExpectedConditions.elementToBeClickable(pregnancyStatus));
                    System.out.println("Pregnancy status is " + pregnancyStatusDate);
                    System.out.println("Insemination date is " + inseminationDate.getText());
                    String pregnancyDate = getPregnancyDateOfAnimalTagId(inseminationDate.getText());
                    System.out.println("Pregnancy date is " + pregnancyStatusDate);
                    if (pregnancyDate.equalsIgnoreCase("Before one year")) {
                        Thread.sleep(2000);
                        wait.ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='fa fa-chevron-left mr-2 back-section']")));
                        driver.findElement(By.xpath("//i[@class='fa fa-chevron-left mr-2 back-section']")).click();
                        System.out.println("Skipped - Pregnancy date is before one year");
                        updateExcelSheetWithRunDetails(animalId, pregnancyStatusDate, "Before One Year");
                        continue;
                    }
                    if (pregnancyDate.equalsIgnoreCase("After current date")) {
                        Thread.sleep(2000);
                        wait.ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='fa fa-chevron-left mr-2 back-section']")));
                        driver.findElement(By.xpath("//i[@class='fa fa-chevron-left mr-2 back-section']")).click();
                        System.out.println("Skipped - Pregnancy date is after current date");
                        updateExcelSheetWithRunDetails(animalId, pregnancyStatusDate, "After current date");
                        continue;
                    }
                    if (Objects.equals(pregnancyStatusDate, "PD Due")) {
                        WebElement pdDate = driver.findElement(By.xpath("//input[@formcontrolname='pdDate']"));
                        wait.ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@formcontrolname='pdDate']")));
                        clearWebField(pdDate);
                        pdDate.sendKeys(pregnancyDate);
                        clickOutside();
                        System.out.println("Pregnancy date is set as " + pregnancyDate);
                        Select selectPdResult = new Select(driver.findElement(By.xpath("//select[@formcontrolname='pdResult']")));
                        Thread.sleep(2000);
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
                        System.out.println("Pregnancy diagnosis updated for " + animalId + " with pregnancy date " + pregnancyDate);
                        System.out.println("---------------");
                        updateExcelSheetWithRunDetails(animalId, pregnancyDate, "Y");
                        clickOnPregnancyDiagnosisTab();
                    } else {
                        System.out.println("Pregnancy is 'NO' and status is '" + pregnancyStatusDate + "' for AnimalId " + animalId);
                        System.out.println("---------------");
                        updateExcelSheetWithRunDetails(animalId, pregnancyStatusDate, "N");
                        clickOnPregnancyDiagnosisTab();
                    }
                }
            } else {
                System.out.println("Animal pregnancy status is '" + isPregnant.getText() + "'.  Skipping " + getAllAnimalTagId().get(i));
                updateExcelSheetWithRunDetails(animalId, "Already Pregnant", "N");
                System.out.println("---------------");
            }
        }
    }

    // End of pregnancy diagnosis flow and it's methods

    // Start of calving

    public static void calving() throws IOException, InterruptedException {
        System.out.println("Calving - Found total of " + getAllAnimalTagId().size() + " entries from excel sheet");
        for (int i = 0; i < getAllAnimalTagId().size(); i++) {
            // Get values from Excel
            String animalId = getAllAnimalTagId().get(i);
            System.out.println("------ " + i + " ------");
            System.out.println("Calving - Animal Id is " + animalId);
            wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search-by']")));
            retryingFindClick(By.xpath("//input[@id='search-by']"), animalId);
            wait.ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=' submit']")));
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
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@role='table']")));
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newCalvingButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newCalvingButton));
                newCalvingButton.click();
                if (i % 10 == 0) {
                    commonFlowForCalving(animalId, CALVING_SEX.MALE);
                } else {
                    commonFlowForCalving(animalId, CALVING_SEX.FEMALE);
                }
            } else if (searchTable.isDisplayed() && isPregnant.getText().contains("Yes") && milkingStatus.getText().contains("NA")) {
                System.out.println("Animal is pregnant and is in 'NA' state");
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@role='table']")));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='selectedTagId']")));
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newCalvingButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newCalvingButton));
                newCalvingButton.click();
                if (i % 10 == 0) {
                    commonFlowForCalving(animalId, CALVING_SEX.MALE);
                } else {
                    commonFlowForCalving(animalId, CALVING_SEX.FEMALE);
                }
            } else if (searchTable.isDisplayed() && isPregnant.getText().contains("Yes") && milkingStatus.getText().contains("In Milk")) {
                String calvingDate = getDatePlusSixMonths(getInseminationDateFromCalvingHistoryTable());
                if (calvingDate == null) {
                    System.out.println("Calving date is after current date. Skipping");
                    continue;
                }
                System.out.println("Gestation date is added by 6 months from insemination date - " + calvingDate);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='back-arrow']")));
                driver.findElement(By.xpath("//div[@class='back-arrow']")).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@role='table']")));
                driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
                WebElement newCalvingButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
                wait.until(ExpectedConditions.elementToBeClickable(newCalvingButton));
                newCalvingButton.click();
                WebElement dryOffDate = driver.findElement(By.xpath("//div[@class='ear-tag-detail']//input[@placeholder='dd-mm-yyyy']"));
                wait.until(ExpectedConditions.elementToBeClickable(dryOffDate));
                Thread.sleep(1000);
                dryOffDate.sendKeys(calvingDate);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@formcontrolname='milkingStatus']")));
                Select milkingStatusDropdown = new Select(driver.findElement(By.xpath("//select[@formcontrolname='milkingStatus']")));
                milkingStatusDropdown.selectByIndex(1);
                WebElement submitMilkStatus = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
                wait.until(ExpectedConditions.elementToBeClickable(submitMilkStatus));
                submitMilkStatus.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                if (i % 10 == 0) {
                    commonFlowForCalving(animalId, CALVING_SEX.MALE);
                } else {
                    commonFlowForCalving(animalId, CALVING_SEX.FEMALE);
                }
            } else if (searchTable.isDisplayed() && isPregnant.getText().contains("No")) {
                System.out.println("Animal is not pregnant. Skipping");
                updateExcelSheetWithRunDetails(animalId, "", "N");
            }
        }
    }

    public static void commonFlowForCalving(String animalId, CALVING_SEX sex) throws IOException, InterruptedException {
        System.out.println("Calving for sex type - " + sex.toString());
        wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='status-highlight']")));
        WebElement pregnancyStatus = driver.findElement(By.xpath("//span[@class='status-highlight']"));
        wait.until(ExpectedConditions.visibilityOf(pregnancyStatus));
        if (Objects.equals(pregnancyStatus.getText(), "Pregnancy Confirmed")) {
            WebElement calvingDate = driver.findElement(By.xpath("//input[@formcontrolname='calvingDate']"));
            clearWebField(calvingDate);
            String pregnancyDateFromTable = driver.findElement(By.xpath("//table[@class='mat-table cdk-table mat-elevation-z8']//td[2]")).getText();
            String addedNineMonthsDate = getDatePlusNineMonths(pregnancyDateFromTable);
            if (addedNineMonthsDate == null) {
                System.out.println("Gestation date is after current date. Skipping");
            }
            WebElement gestationDays = driver.findElement(By.xpath("//table[@class='table animal-table m-0 ng-star-inserted']//td[5]"));
            String modifiedGestationDate = handleGestationDate(addedNineMonthsDate, pregnancyDateFromTable);
            System.out.println("Pregnancy date of animal is " + pregnancyDateFromTable);
            System.out.println("Gestation modified animal date is " + modifiedGestationDate);
            calvingDate.sendKeys(modifiedGestationDate);
            clickOutside();
            Thread.sleep(4000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//table[@class='table animal-table m-0 ng-star-inserted']//td[5]"))));
            if (checkGestationRange(driver.findElement(By.xpath("//table[@class='table animal-table m-0 ng-star-inserted']//td[5]")).getText())) {
                wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@formcontrolname='calvingStatus']")));
                Select selectPdResult = new Select(driver.findElement(By.xpath("//select[@formcontrolname='calvingStatus']")));
                selectPdResult.selectByIndex(1);
                Thread.sleep(2000);
                if (selectPdResult.getFirstSelectedOption().getText().contains("Successful Calving")) {
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
                        if (sex.equals(CALVING_SEX.MALE)) {
                            wait.until(ExpectedConditions.elementToBeClickable(male));
                            male.click();
                        }
                        if (sex.equals(CALVING_SEX.FEMALE)) {
                            wait.until(ExpectedConditions.elementToBeClickable(female));
                            female.click();
                        }
                        Select reasonForNotRegistering = new Select(driver.findElement(By.xpath("//select[@formcontrolname='reasonForNotRegistering']")));
                        int randomNumber = (int) (Math.random() * 2) + 1;
                        reasonForNotRegistering.selectByIndex(randomNumber);
                        WebElement submitButton = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
                        if (submitButton.isEnabled() && submitButton.isDisplayed()) {
                            submitButton.click();
                        }
                        clickOutside();
                        updateExcelSheetWithRunDetails(animalId, modifiedGestationDate, "Y");
                        Thread.sleep(8000);
                        driver.get("https://bharatpashudhan.ndlm.co.in/dashboard/");
                        clickOnCalvingTab();
                    }
                } else {
                    System.out.println("Unable to select 'Successful Calving' option from calving status dropdown'");
                }
                System.out.println("Calving is updated");
                clickOutside();
                driver.get("https://bharatpashudhan.ndlm.co.in/dashboard/");
                clickOnCalvingTab();
                System.out.println("---------------");
            } else {
                System.out.println("The gestation date range is out of bounds - " + gestationDays.getText());
                clickOnCalvingTab();
            }
        }
    }

    // End of calving flow and it's methods

    // Start of vaccination flow

    public static void vaccination() throws IOException, InterruptedException {
        System.out.println("Vaccination - Found total of " + getAllAnimalTagId().size() + " entries from excel sheet");
        commonFlowForVaccination();
        for (int i = 0; i < getAllAnimalTagId().size(); i++) {
            System.out.println("------ " + i + " ------");
            // Get values from Excel
            String animalId = getAllAnimalTagId().get(i);
            System.out.println("Vaccination - Animal Id is " + animalId);
            if (verifyAnimalVaccinationDetailsByVillageSearch(animalId)) {
                wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@id='filter-by'])[2]")));
                driver.findElement(By.xpath("(//input[@id='filter-by'])[2]")).clear();
                Thread.sleep(500);
                driver.findElement(By.xpath("(//input[@id='filter-by'])[2]")).sendKeys(animalId);
                Thread.sleep(800);
                if (retryingFindingElement(By.xpath("//button[normalize-space()='Okay']"))) {
                    System.out.println("Animal not found for selected village. Check Manually for AnimalId - " + animalId);
                    updateExcelSheetWithRunDetails(animalId, "Animal not found", "N");
                    clickOutside();
                    continue;
                }
                wait.ignoring(TimeoutException.class).ignoring(TimeoutException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[normalize-space()='" + animalId + "']/ancestor::tr//td[10]//a")));
                driver.findElement(By.xpath("//td[normalize-space()='" + animalId + "']/ancestor::tr//td[10]//a")).click();
                Thread.sleep(500);
                if (retryingFindingElement(By.xpath("//p[@class='timeline-heading']"))) {
                    WebElement vaccinationDetails = driver.findElement(By.xpath("//p[@class='timeline-heading']"));
                    WebElement vaccinationTimeline = driver.findElement(By.xpath("//span[@class='timeline-date']"));
                    if (vaccinationDetails.getText().equals("Vaccination")) {
                        System.out.println("Animal history shows it's already vaccinated on " + vaccinationTimeline.getText());
                        driver.findElement(By.xpath("//mat-icon[@role='img']")).click();
                        driver.findElement(By.xpath("//input[@id='search-by']")).clear();
                        updateExcelSheetWithRunDetails(animalId, "Already vaccinated", "N");
                        System.out.println("---------------");
                        continue;
                    }
                }
                if (retryingFindingElement(By.xpath("//h3[normalize-space()='No history record found']"))) {
                    System.out.println("Animal history shows it's not vaccinated");
                    wait.ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-icon[@role='img']")));
                    driver.findElement(By.xpath("//mat-icon[@role='img']")).click();
                    wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@id='filter-by'])[2]")));
                    driver.findElement(By.xpath("(//input[@id='filter-by'])[2]")).clear();
                    Thread.sleep(500);
                    driver.findElement(By.xpath("(//input[@id='filter-by'])[2]")).sendKeys(animalId);
                    driver.findElement(By.xpath("//table//td[normalize-space()='" + animalId + "']/ancestor::tr//td[1]//input")).click();
                    retryingFindingElement(By.xpath("//button[normalize-space()='Proceed']"));
                    driver.findElement(By.xpath("//button[normalize-space()='Proceed']")).click();
                    String vaccinationDate = getRandomDateInRange();
                    wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@formcontrolname='vaccinationDate']")));
                    WebElement vaccinationDateCalendar = driver.findElement(By.xpath("(//input[@id='mat-input-0'])[1]"));
                    wait.ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(vaccinationDateCalendar));
                    Thread.sleep(1000);
                    clearWebField(vaccinationDateCalendar);
                    vaccinationDateCalendar.sendKeys(vaccinationDate);
                    wait.ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Submit']")));
                    driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();
                    System.out.println("Vaccinated animal on " + vaccinationDate);
                    updateExcelSheetWithRunDetails(animalId, "Vaccinated", "Y");
                    Thread.sleep(3000);
                    driver.get("https://bharatpashudhan.ndlm.co.in/dashboard/vaccination");
                    System.out.println("---------------");
                    commonFlowForVaccination();
                }
            } else {
                updateExcelSheetWithRunDetails(animalId, "Not found", "Run Again");
            }
        }
    }

    public static boolean verifyAnimalVaccinationDetailsByVillageSearch(String animalId) throws InterruptedException {
        boolean ableToFindAnimalByVillage = false;
        wait.ignoring(NoSuchElementException.class).ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search-by']")));
        driver.findElement(By.xpath("//input[@id='search-by']")).clear();
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@id='search-by']")).sendKeys(animalId);
        clickOutside();
        retryingFindingElement(By.xpath("//button[normalize-space()='Search']"));
        driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
        if (retryingFindingElement(By.xpath("//table[@role='table']"))) {
            ableToFindAnimalByVillage = true;
            System.out.println("Able to find vaccination table details");
            return ableToFindAnimalByVillage;
        }
        System.out.println("Unable to find vaccination table details");
        return ableToFindAnimalByVillage;
    }

    public static void commonFlowForVaccination() throws InterruptedException {
        wait.ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//label[normalize-space()='Include Data Entry Campaigns']")));
        Thread.sleep(1000);
        retryingFindingElement(By.xpath("//label[normalize-space()='Include Data Entry Campaigns']"));
        driver.findElement(By.xpath("//label[normalize-space()='Include Data Entry Campaigns']")).click();
        clickOutside();
        retryingFindingElement(By.xpath("//button[@id='carousel-control-next']"));
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[@id='carousel-control-next']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[@id='carousel-control-next']")).click();
        wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='17534']")));
        driver.findElement(By.xpath("//button[@name='17534']")).click();
        Thread.sleep(2000);
        wait.ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='combobox']")));
        retryingFindingElement(By.xpath("//div[@role='combobox']"));
        driver.findElement(By.xpath("//div[@role='combobox']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[normalize-space()='" + VACCINATION_VILLAGE_NAME + "']/..")).click();
        clickOutside();
    }

    // End of vaccination flow and it's methods

    // Archives

    public static String getInseminationDateFromPregnancyDiagnosis(String animalId) throws InterruptedException {
        String inseminationDate = null;
        System.out.println("Animal is pregnant and is in 'milk' state");
        clickOnPregnancyDiagnosisTab();
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search-by']")));
        retryingFindClick(By.xpath("//input[@id='search-by']"), animalId);
        new WebDriverWait(driver, Duration.ofSeconds(20)).ignoring(StaleElementReferenceException.class).ignoring(ElementClickInterceptedException.class).until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=' submit']")));
        driver.findElement(By.cssSelector("button[type=' submit']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@role='table']")));
        driver.findElement(By.xpath("//input[@name='selectedTagId']")).click();
        WebElement newPdButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']"));
        wait.until(ExpectedConditions.elementToBeClickable(newPdButton));
        if (newPdButton.isEnabled()) {
            driver.findElement(By.xpath("//button[@class='btn btn-primary mr-2']")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@class='mat-table cdk-table mat-elevation-z8']//td[2]")));
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

    // Start of web utility methods

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

    public static boolean retryingFindingElement(By by) throws InterruptedException {
        Thread.sleep(1000);
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

    public static void closeAll() {
        driver.close();
        driver.quit();
    }
}
