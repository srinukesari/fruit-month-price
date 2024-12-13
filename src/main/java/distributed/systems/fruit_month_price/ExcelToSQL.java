package distributed.systems.fruit_month_price;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelToSQL {
    public static void main(String[] args) {
        String filePath = "src/main/java/distributed/systems/fruit_month_price/FMP.xlsx"; // Path to your XLSX file
        String outputFilePath = "src/main/resources/data.sql"; // Path to your output SQL file
        int id = 1; // Start ID from 1

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream);
             PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {

            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Header row is missing.");
            }

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { // Skip header row
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String fruit = row.getCell(0).getStringCellValue(); // First column is fruit name

                for (int j = 1; j < headerRow.getPhysicalNumberOfCells(); j++) { // Start from column 1 (Jan)
                    Cell monthCell = headerRow.getCell(j);
                    Cell valueCell = row.getCell(j);

                    if (monthCell == null || valueCell == null) continue;

                    String month = monthCell.getStringCellValue();
                    double value = valueCell.getNumericCellValue();

                    // Generate SQL INSERT command
                    String sql = String.format(
                        "INSERT INTO fruit_month_price (id, fruit, f_month, fmp) VALUES (%d, '%s', '%s', %.2f);",
                        id++, fruit, month, value
                    );

                    writer.println(sql);
                }
            }

            System.out.println("sql commands have been written to data.sql");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
