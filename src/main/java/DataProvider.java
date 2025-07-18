import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DataProvider {

    // Mandatory Fields for all runs.
    public static final String USERNAME = "pdktait24_TN";
    public static final String PASSWORD = "pdktait24_TN";
    public static final String EXCEL_FILE_LOCATION = "/Users/vishag/Downloads/Vetta FMD.xlsx";

    // Project Name
    public static final String PROJECT_NAME = " Livestack LH and DCP "; // Livestack NAIP IV ,  Livestack LH and DCP ,  Surabhi Chayan Shrankhla


    // Vaccination
    public static final boolean VACCINATION_BULK_RUN = true;
    public static final int VACCINATION_BULK_RUN_ITEMS = 22;
    public static final String VACCINATION_CAMPAIGN_ID = "25016";
    public static final String VACCINATION_VILLAGE_NAME = "Pallavarayanpathai";
    public static final String VACCINATION_START_DATE_RANGE = "11/07/2025";
    public static final String VACCINATION_END_DATE_RANGE = "11/07/2025";

    // Artificial Insemination
    public static final String BULL_ID = "SAG-RS-10006";
    public static final List<String> RS = List.of("SAG-RS-10006", "SAG-RS-10008");
    public static final List<String> JERSEY = List.of("ABC-JY-21032", "ABC-JY-21033");
    public static final List<String> CBHF = List.of("ALM-HX-40073", "ALM-HX-40074");
    public static final List<String> CBJ = List.of("ALM-JS-40282", "ALM-JS-40351");
    private static XSSFWorkbook wb;

    // Calving
    public enum CALVING_SEX {
        MALE,
        FEMALE,
    }
    public static int currentRun = 2;
    // Script to convert the date from Excel.
    // =TEXT(DATE(VALUE(MID(B1,7,4)), VALUE(MID(B1,4,2)), VALUE(LEFT(B1,2))), "dd/mm/yy")

    public static List<String> getAllAnimalTagId() throws IOException {
        String sanitizedPath = EXCEL_FILE_LOCATION.trim().replace("\u202A", "");
        InputStream excelFile = Files.newInputStream(Paths.get(sanitizedPath));
        //InputStream excelFile = Files.newInputStream(Paths.get(EXCEL_FILE_LOCATION));
        wb = new XSSFWorkbook(excelFile);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<String> animalTagIdValues = new ArrayList<>();
        for (Row r : sheet) {
            Cell animalTagIdColumn = r.getCell(0);
            if (animalTagIdColumn != null) {
                if (animalTagIdColumn.getCellType().equals(CellType.STRING)) {
                    animalTagIdValues.add(animalTagIdColumn.getStringCellValue());
                } else if (animalTagIdColumn.getCellType().equals(CellType.NUMERIC)) {
                    long animalNumericalValue = (long) animalTagIdColumn.getNumericCellValue();
                    animalTagIdValues.add(String.valueOf(animalNumericalValue));
                }
            }
        }
        return animalTagIdValues;
    }

    public static int findRowOfAnimalId(String animalId) throws IOException {
        String sanitizedPath = EXCEL_FILE_LOCATION.trim().replace("\u202A", "");
        InputStream excelFile = Files.newInputStream(Paths.get(sanitizedPath));
        XSSFWorkbook wb = new XSSFWorkbook(excelFile);
        XSSFSheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == CellType.STRING) {
                    if (cell.getRichStringCellValue().getString().trim().equals(animalId)) {
                        return row.getRowNum();
                    }
                }
                if (cell.getCellType() == CellType.NUMERIC) {
                    long animalNumericalValue = (long) cell.getNumericCellValue();
                    if (String.valueOf(animalNumericalValue).equals(animalId)) {
                        return row.getRowNum();
                    }
                }
            }
        }
        return 0;
    }

    public static void updateExcelSheetWithRunDetails(String animalId, String status, String result) throws IOException {
        String sanitizedPath = EXCEL_FILE_LOCATION.trim().replace("\u202A", "");
        InputStream excelFile = Files.newInputStream(Paths.get(sanitizedPath));
        // InputStream excelFile = Files.newInputStream(Paths.get(EXCEL_FILE_LOCATION));
        XSSFWorkbook wb = new XSSFWorkbook(excelFile);
        XSSFSheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(findRowOfAnimalId(animalId));
        Cell updatePregnancyDate = row.getCell(1);
        Cell pdResult = row.getCell(2);
        // Update pregnancy result
        if (updatePregnancyDate == null) {
            updatePregnancyDate = row.createCell(1);
        }
        updatePregnancyDate.setCellValue(status);
        // Update pregnancy result
        if (pdResult == null) {
            pdResult = row.createCell(2);
        }
        pdResult.setCellValue(result);
        OutputStream outputStream = Files.newOutputStream(Paths.get(sanitizedPath));
        wb.write(outputStream);
        outputStream.close();
    }

    public static String getPregnancyDateOfAnimalTagId(String inseminationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inputDate = LocalDate.parse(inseminationDate, formatter);
        // Add three months to the date
        int randomNumber = (int) (Math.random() * 2) + 1;
        LocalDate threeMonthsAddedDate = inputDate.plusMonths(3).plusDays(randomNumber);
        LocalDate currentDate = LocalDate.now();
        LocalDate oneYearBeforeToday = currentDate.minusYears(1);

        if (threeMonthsAddedDate.isBefore(oneYearBeforeToday)) {
            return "Before one year";
        } else if (threeMonthsAddedDate.isAfter(currentDate)) {
            return "After current date";
        } else {
            return threeMonthsAddedDate.format(formatter);
        }
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
            } else if (finalDateParse.isBefore(LocalDate.now().minusYears(1))) {
                output = currentDate.format(formatter);
            } else {
                output = finalDateParse.plusDays(randomNumber).format(formatter);
            }
        }
        return output;
    }

    public static boolean checkGestationRange(String number) {
        int num = Integer.parseInt(number);
        System.out.println("Gestation days is - " + num);
        return num >= 270 && num <= 280;
    }

    public static String getDatePlusFiveMonths(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, formatter);
        LocalDate newDate = date.plusMonths(5);
        if (newDate.isAfter(LocalDate.now())) {
            return null;
        }
        return newDate.format(formatter);
    }

    public static String getDatePlusSixMonths(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, formatter);
        LocalDate newDate = date.plusMonths(6);
        return newDate.format(formatter);
    }

    public static String getDatePlusSevenMonths(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, formatter);
        LocalDate newDate = date.plusMonths(7);
        System.out.println("7 months added from input date is - " + newDate.format(formatter));
        if (newDate.isAfter(LocalDate.now()) || newDate.isBefore(LocalDate.now().minusYears(1))) {
            return null;
        }
        return newDate.format(formatter);
    }

    public static String getDatePlusNineMonths(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, formatter);
        Random random = new Random();
        int randomInt = random.nextInt(3) + 1;
        LocalDate newDate = date.plusMonths(9).plusDays(randomInt);
        System.out.println("9 months added from input date is - " + newDate.format(formatter));
        if (newDate.isAfter(LocalDate.now()) || newDate.isBefore(LocalDate.now().minusYears(1))) {
            return null;
        }
        return newDate.format(formatter);
    }

    public static String getDatePlusOneMonth(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, formatter);
        LocalDate newDate = date.plusMonths(1);
        if (newDate.isAfter(LocalDate.now())) {
            return null;
        }
        return newDate.format(formatter);
    }

    public static boolean checkIfDateIsInRange(String input) {
        boolean result = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inputDate = LocalDate.parse(input, formatter);
        if (inputDate.isBefore(LocalDate.now()) && inputDate.isAfter(LocalDate.now().minusYears(1))) {
            result = true;
        } else {
            System.out.println("Date is not within range");
        }
        return result;
    }

    public static String getCurrentDateMinusFourMonths() {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateMinusFourMonths = currentDate.minusMonths(4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(dateMinusFourMonths.isAfter(LocalDate.now())) {
            return null;
        }
        return dateMinusFourMonths.format(formatter);
    }

    public static String getRandomDateInRange() {
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Parse the start and end dates
            Date startDate = dateFormat.parse(VACCINATION_START_DATE_RANGE);
            Date endDate = dateFormat.parse(VACCINATION_END_DATE_RANGE);
            // Generate a random date between startDate and endDate
            long randomMillis = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
            Date randomDate = new Date(randomMillis);
            // Format and return the random date
            return dateFormat.format(randomDate);
        } catch (Exception e) {
            return null;
        }
    }
}