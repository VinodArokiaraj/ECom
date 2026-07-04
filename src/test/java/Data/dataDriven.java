package Data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class dataDriven {

    public ArrayList<String> getData (String testCaseName) throws IOException {

        ArrayList<String> a = new ArrayList<String>();
        FileInputStream fis = new FileInputStream("/Users/vinodmac/Learning/TestDataExcel.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase("Sheet1")) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row firstRow = rows.next();
                Iterator<Cell> cells = firstRow.cellIterator();
                int k = 0;
                int column = 0;
                while (cells.hasNext()) {
                    Cell value = cells.next();
                    if (value.getStringCellValue().equalsIgnoreCase("TestCases")) {
                        column = k;
                    }
                    k++;
                }
                System.out.println(column);

                while(rows.hasNext()) {
                    Row row = rows.next();
                    if (row.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
                        Iterator<Cell> cell = row.cellIterator();
                        while (cell.hasNext()) {
                            Cell c = cell.next();
                            if (c.getCellType() == CellType.STRING) {
                                a.add(c.getStringCellValue());
                            }
                            else {
                                a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                                }
                        }
                    }

                }
            }
        }
        return a;
    }
}
