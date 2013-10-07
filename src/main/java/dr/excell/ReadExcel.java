package dr.excell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

    public static void readXLSFile() throws IOException {
        InputStream ExcelFileToRead = new FileInputStream("C:/Test.xls");
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;

        Iterator rows = sheet.rowIterator();

        while (rows.hasNext()) {
            row = (HSSFRow) rows.next();
            Iterator cells = row.cellIterator();

            while (cells.hasNext()) {
                cell = (HSSFCell) cells.next();

                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    System.out.print(cell.getStringCellValue() + " ");
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    System.out.print(cell.getNumericCellValue() + " ");
                } else {
                    //U Can Handel Boolean, Formula, Errors
                }
            }
            System.out.println();
        }

    }

    public static void writeXLSFile() throws IOException {

        String excelFileName = "C:/Test.xls";//name of excel file

        String sheetName = "Sheet1";//name of sheet

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);

        //iterating r number of rows
        for (int r = 0; r < 5; r++) {
            HSSFRow row = sheet.createRow(r);

            //iterating c number of columns
            for (int c = 0; c < 5; c++) {
                HSSFCell cell = row.createCell(c);

                cell.setCellValue("Cell " + r + " " + c);
            }
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        //write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

    public static void readXLSXFile() throws IOException {
        int ensaios = 0;

        String caminho = "/Users/andregnhoato/Dropbox/ensaios/";
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("0.00");
        //String arquivo = "1_LINHA_REPETICAO_1/Ensaios_L1-R1-A.xlsx";

        int linha = 1;
        while (linha <= 9) {
            int repeticao = 1;
            while (repeticao <= 3) {
                //System.out.print(ensaios + " - " + caminho + linha + "_LINHA_REPETICAO_" + repeticao + "/Ensaios_L" + linha + "-R" + repeticao + "-A.xlsx\n");
                InputStream ExcelFileToRead = new FileInputStream(caminho + linha + "_LINHA_REPETICAO_" + repeticao + "/Ensaios_L" + linha + "-R" + repeticao + "-A.xlsx");

                XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

                int pages = 0;
                while (pages < 4) {
                    int cellcont = 0;
                    FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                    XSSFSheet sheet = wb.getSheetAt(pages);
                    XSSFRow row;
                    XSSFCell cell;

                    //bocal pequeno
                    sb.append(sheet.getRow(19).getCell(1).getStringCellValue().trim().substring(0, 3).replace(",", "."));
                    sb.append(";");
                    //bocal grande
                    sb.append(sheet.getRow(21).getCell(1).getStringCellValue().trim().substring(0, 3).replace(",", "."));
                    sb.append(";");
                    //pressao
                    sb.append(sheet.getRow(20).getCell(1).getStringCellValue().trim().substring(0, 3).replace(",", "."));
                    sb.append(";");
                    //velocidade vento
                    sb.append(df.format(Double.parseDouble(evaluator.evaluate(sheet.getRow(23).getCell(1)).formatAsString().replaceAll("\"", ""))));
                    sb.append(";");
                    //direcao do vento MODA
                    sb.append(evaluator.evaluate(sheet.getRow(26).getCell(1)).formatAsString().replaceAll("\"", ""));
                    sb.append(";");

                    Iterator rows = sheet.rowIterator();
                    //coleta
                    while (rows.hasNext()) {
                        row = (XSSFRow) rows.next();
                        if (row.getRowNum() <= 15) {
                            Iterator cells = row.cellIterator();
                            while (cells.hasNext()) {
                                cell = (XSSFCell) cells.next();
                                if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                                    sb.append(df.format(cell.getNumericCellValue()).replace(",", "."));
                                    sb.append(";");
                                    cellcont ++;
                                }
                            }

                        }
                    }
                    sb.append("\n");
                    pages++;
                }
                repeticao++;
            }
            linha++;
        }



        System.out.print(sb.toString().replaceAll(",", "."));
        writeTxtFile(sb.toString());

    }

    public static void writeXLSXFile() throws IOException {

        String excelFileName = "C:/Test.xlsx";//name of excel file

        String sheetName = "Sheet1";//name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);

        //iterating r number of rows
        for (int r = 0; r < 5; r++) {
            XSSFRow row = sheet.createRow(r);

            //iterating c number of columns
            for (int c = 0; c < 5; c++) {
                XSSFCell cell = row.createCell(c);

                cell.setCellValue("Cell " + r + " " + c);
            }
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        //write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

    public static void writeTxtFile(String text) {
        PrintWriter writer;
        try {
            writer = new PrintWriter("EnsaiosGeral.txt", "UTF-8");
            writer.println(text);
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws IOException {

//        writeXLSFile();
//        readXLSFile();

//        writeXLSXFile();
        readXLSXFile();

    }
}
