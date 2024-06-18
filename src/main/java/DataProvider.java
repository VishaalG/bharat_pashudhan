import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.   *;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class DataProvider {

    public static final String USERNAME = "pdktait175_TN";
    public static final String PASSWORD = "pdktait175_TN";
    public static String EXCEL_FILE_LOCATION = "/Users/vishag/Downloads/Thondaii FMD.xlsx";
    public static String VACCINATION_VILLAGE_NAME = "Thennathirayanpatti";
    public static String VACCINATION_START_DATE_RANGE = "12/11/2023";
    public static String VACCINATION_END_DATE_RANGE = "16/12/2023";
    public enum CALVING_SEX {
        MALE,
        FEMALE,
    }
    public enum BULL_TYPE {
        RS,
        JERSEY,
        CBHF,
        CBJ
    }
    public static List<String> RS = List.of("SAG-RS-10006", "SAG-RS-10008");
    public static List<String> JERSEY = List.of("ABC-JY-21032", "ABC-JY-21033");
    public static List<String> CBHF = List.of("ALM-HX-40073", "ALM-HX-40074");
    public static List<String> CBJ = List.of("ALM-JS-40282", "ALM-JS-40351");
    // =TEXT(DATE(VALUE(MID(B1,7,4)), VALUE(MID(B1,4,2)), VALUE(LEFT(B1,2))), "dd/mm/yy")

    public static List<String> getAllAnimalTagId() throws IOException {
        InputStream excelFile = Files.newInputStream(Paths.get(EXCEL_FILE_LOCATION));
        XSSFWorkbook wb = new XSSFWorkbook(excelFile);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<String> animalTagIdValues = new ArrayList<>();
        for (Row r : sheet) {
            Cell animalTagIdColumn = r.getCell(0);
            if(animalTagIdColumn != null) {
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
        InputStream excelFile = Files.newInputStream(Paths.get(EXCEL_FILE_LOCATION));
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
                    if (Long.valueOf(animalNumericalValue).equals(animalId)) {
                        return row.getRowNum();
                    }
                }
            }
        }
        return 0;
    }

    public static void updateExcelSheetWithRunDetails(String animalId, String pregnancyDate, String result) throws IOException {
        InputStream excelFile = Files.newInputStream(Paths.get(EXCEL_FILE_LOCATION));
        XSSFWorkbook wb = new XSSFWorkbook(excelFile);
        XSSFSheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(findRowOfAnimalId(animalId));
        Cell updatePregnancyDate = row.getCell(1);
        Cell pdResult = row.getCell(2);
        // Update pregnancy result
        if (updatePregnancyDate == null) {
            updatePregnancyDate = row.createCell(1);
        }
        updatePregnancyDate.setCellValue(pregnancyDate);
        // Update pregnancy result
        if (pdResult == null) {
            pdResult = row.createCell(2);
        }
        pdResult.setCellValue(result);
        OutputStream outputStream = Files.newOutputStream(Paths.get(EXCEL_FILE_LOCATION));
        wb.write(outputStream);
        outputStream.close();
    }

    public static String getDescDateOfAnimalTagId(String inseminationDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inputDate = LocalDate.parse(inseminationDate, formatter);
        // Add three months to the date
        int randomNumber = (int) (Math.random() * 3) + 1;
        LocalDate threeMonthsAddedDate = inputDate.plusMonths(3).plusDays(randomNumber);
        LocalDate currentDate = LocalDate.now();
        LocalDate oneYearBeforeToday = currentDate.minusYears(1);


        if (threeMonthsAddedDate.isBefore(oneYearBeforeToday)) {
            return oneYearBeforeToday.plusDays(1).format(formatter);
        } else if (threeMonthsAddedDate.isAfter(currentDate)) {
            return currentDate.format(formatter);
        } else {
            return threeMonthsAddedDate.format(formatter);
        }

    }

    public static String getDuplicateDescDateOfAnimalTagId(String animalTagIdValues) throws IOException {
        InputStream excelFile = Files.newInputStream(Paths.get(EXCEL_FILE_LOCATION));
        XSSFWorkbook wb = new XSSFWorkbook(excelFile);
        XSSFSheet sheet = wb.getSheetAt(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        List<String> dates = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (Row r : sheet) {
            Cell animalId = r.getCell(0);
            if (animalId.getStringCellValue().contentEquals(animalTagIdValues)) {
                dates.add(r.getCell(1).getStringCellValue());
                List<String> sortedDateTimeStrings = dates.stream()
                        .sorted((s1, s2) -> {
                            LocalDateTime dateTime1 = LocalDateTime.parse(s1, formatter);
                            LocalDateTime dateTime2 = LocalDateTime.parse(s2, formatter);
                            return dateTime2.compareTo(dateTime1); // For descending order
                        })
                        .collect(Collectors.toList());

                map.put(animalId.getStringCellValue(), sortedDateTimeStrings.get(0));
            }
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        // Remove the square brackets and parse the input date
        LocalDate date = LocalDate.parse(dates.get(0), inputFormatter);
        // Add three months to the date
        LocalDate newDate = date.plusMonths(3);

        LocalDate currentDate = LocalDate.now();
        LocalDate oneYearBefore = currentDate.minusYears(1);

        // Format the new date back to the original format
        String formattedNewDate = newDate.format(formatter);

        if (newDate.isBefore(oneYearBefore)) {
            return oneYearBefore.plusDays(1).format(formatter);
        } else {
            return formattedNewDate;
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
            }
            else if (finalDateParse.isBefore(LocalDate.now().minusYears(1))) {
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

    public static String getDateFromExcelPlusNineMonths(String animalTagIdValues) throws IOException {
        InputStream excelFile = new FileInputStream(EXCEL_FILE_LOCATION);
        XSSFWorkbook wb = new XSSFWorkbook(excelFile);
        XSSFSheet sheet = wb.getSheetAt(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<String> dates = new ArrayList<>();
        for (Row r : sheet) {
            Cell animalId = r.getCell(0);
            Cell animalDate = r.getCell(1);
            if (animalId.getStringCellValue().contains(animalTagIdValues)) {
                dates.add(animalDate.toString());
            }
        }
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        // Remove the square brackets and parse the input date
        LocalDate date = LocalDate.parse(dates.get(0), inputFormatter);
        // Add three months to the date
        LocalDate newDate = date.plusMonths(9).minusDays(2);

        String formattedNewDate = newDate.format(formatter);
        return formattedNewDate;

    }

    public static String getDatePlusThreeMonths(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, formatter);
        // Add three months to the date
        int randomNumber = (int) (Math.random() * 3) + 1;
        LocalDate newDate = date.plusMonths(3).plusDays(randomNumber);
        String formattedNewDate = newDate.format(formatter);
        if (newDate.isAfter(LocalDate.now())) {
            return null;
        }
        return formattedNewDate;

    }

    public static String getDatePlusSixMonths(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, formatter);
        // Add three months to the date
        LocalDate newDate = date.plusMonths(6);
        if (newDate.isAfter(LocalDate.now())) {
            return null;
        }
        return newDate.format(formatter);
    }

    public static String getDatePlusNineMonths(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, formatter);
        // Add Nine months to the date
        LocalDate newDate = date.plusMonths(9);
        if (newDate.isAfter(LocalDate.now())) {
            return null;
        }
        return newDate.format(formatter);
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
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
    }
}