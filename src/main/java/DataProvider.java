import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class DataProvider {

    public static List<String> getAllAnimalTagId() throws IOException {
        InputStream excelFile = new FileInputStream("/users/vishag/Downloads/Rangiun PD and Calf.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(excelFile);
        XSSFSheet sheet = wb.getSheetAt(0);
        List<String> animalTagIdValues = new ArrayList<>();
        for (Row r : sheet) {
            Cell animalTagIdColumn = r.getCell(0);
            animalTagIdValues.add(animalTagIdColumn.getStringCellValue());
        }
        return animalTagIdValues;
    }

    public static String getDescDateOfAnimalTagId(String animalTagIdValues) throws IOException {
        InputStream excelFile = new FileInputStream("/users/vishag/Downloads/Rangiun PD and Calf.xlsx");
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

    public static String getDuplicateDescDateOfAnimalTagId(String animalTagIdValues) throws IOException, ParseException {
        InputStream excelFile = new FileInputStream("/users/vishag/Downloads/Meenaveli PD calving.xlsx");
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

    public static String getAnimalDatePlusThreeMonths(String input) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, formatter);
        // Add three months to the date
        int randomNumber = (int) (Math.random() * 3) + 1;
        LocalDate newDate = date.plusMonths(3).plusDays(randomNumber);
        String formattedNewDate = newDate.format(formatter);
        return formattedNewDate;

    }


    public static String getAnimalDateFromExcelPlusNineMonths(String animalTagIdValues) throws IOException {
        InputStream excelFile = new FileInputStream("/users/vishag/Downloads/Rangiun PD and Calf.xlsx");
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

    public static String getAnimalDatePlusNineMonths(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, formatter);
        // Add three months to the date
        int randomNumber = (int) (Math.random() * 3) + 1;
        LocalDate newDate = date.plusMonths(9).plusDays(randomNumber);
        String formattedNewDate = newDate.format(formatter);
        return formattedNewDate;
    }

    public static void main(String[] args) throws IOException {
    }
}