package dr.controller;

import dr.action.AbstractAction;
import dr.model.Ensaio;
import dr.report.AnaliseJavaBeanDataSource;
import dr.ui.Dialog;
import dr.ui.analise.AnaliseView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Define a
 * <code>Controller</code> principal do sistema, responsável por gerir a tela
 * <code>Uniformidades </code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @andre
 */
public class AnaliseController extends PersistenceController {

    private AnaliseView uniformidadeView;

    public AnaliseController(AbstractController parent) {
        this.uniformidadeView = new AnaliseView();

        registerAction(this.uniformidadeView.getComboEspacamento(), new AbstractAction() {
            @Override
            protected void action() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        uniformidadeView.reRenderTable();
                    }
                });
            }
        });

        registerAction(this.uniformidadeView.getBtExportar(), new AbstractAction() {
            @Override
            protected void action() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        writeXLSXFile(uniformidadeView.getReport());

                    }
                });

            }
        });

        registerAction(this.uniformidadeView.getBtExportarExcel(), new AbstractAction() {
            @Override
            protected void action() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

//                        writeXLSXFile(uniformidadeView.getReport());
                        writeXLSXFileReport(uniformidadeView.getReport());
                        writeXLSXFilePerfil(uniformidadeView.getReport());


                    }
                });

            }
        });



    }

    private void writeXLSXFilePerfil(AnaliseJavaBeanDataSource report) {
        try {
            XSSFWorkbook workbook;
            workbook = new XSSFWorkbook(OPCPackage.open(new FileInputStream(System.getProperty("user.dir") + "/template/perfildedistribuicao.xlsx"))); // or sample.xls

//            CreationHelper cHelper = workbook.getCreationHelper();
            XSSFSheet sheet = workbook.getSheetAt(0);
            String sheetName = sheet.getSheetName();
            XSSFRow row;
            XSSFCell cell;

//            sh.getRow(0).getCell(0).setCellValue("");
//            sh.getRow(0).getCell(1).setCellValue("");
            //Set headers for the data
            sheet.createRow(0).createCell(0).setCellValue("Distância(m)");
            sheet.getRow(0).createCell(1).setCellValue("Precipitação(mm)");


            int linha = 1;
            for (int r = 0; r < report.getDistancia().size(); r++) {
                row = sheet.createRow(r + 1);

                cell = row.createCell(0);
                cell.setCellValue((float) report.getDistancia().get(r));
                cell = row.createCell(1);
                cell.setCellValue((float) report.getPerfil().get(r));
//                //iterating c number of columns
//                for (int c = 0; c < report.getDistancia().size(); c++) {
//                }
                linha++;
            }

            //Search for named range
            XSSFName rangeCell = workbook.createName();
            rangeCell.setNameName("Distância(m)");
            //Set new range for named range
            String reference = sheetName + "!$A$" + (1) + ":$A$" + (1 + linha);
            //Assigns range value to named range
            rangeCell.setRefersToFormula(reference);
            rangeCell = workbook.createName();
            rangeCell.setNameName("Precipitação(mm)");
            reference = sheetName + "!$B$" + (1) + ":$B$" + (1 + linha);
            rangeCell.setRefersToFormula(reference);
            FileOutputStream f = new FileOutputStream(System.getProperty("user.dir") + "/Perfil_Distruibuicao" + report.getEnsaio().getDescricao().replace(" ", "_") + ".xlsx");
            
            workbook.write(f);
            f.flush();
            f.close();
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(AnaliseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void writeXLSXFileReport(AnaliseJavaBeanDataSource report) {
        try {


            String excelFileName = System.getProperty("user.dir") + "/Analise" + report.getEnsaio().getDescricao().replace(" ", "_") + report.getSobredimensao() +".xlsx";//name of excel file

            String sheetName = "relatorio";//name of sheet

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(sheetName);
            CreationHelper createHelper = wb.getCreationHelper();
            //create cell style for date format
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/MM/yyyy"));



            XSSFRow row = sheet.createRow(0);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("Ensaio");
            cell = row.createCell(1);
            cell.setCellValue(report.getEnsaio().getDescricao());
            cell = row.createCell(2);
            cell.setCellValue("Pressão");
            cell = row.createCell(3);
            cell.setCellValue(report.getEnsaio().getPressao());
            row = sheet.createRow(1);
            cell = row.createCell(0);
            cell.setCellValue("Data");
            cell = row.createCell(1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(report.getEnsaio().getData());
            cell = row.createCell(2);
            cell.setCellValue("Vel. Vento(m/s)");
            cell = row.createCell(3);
            cell.setCellValue(report.getEnsaio().getVelocidadeVento());
            row = sheet.createRow(2);
            cell = row.createCell(0);
            cell.setCellValue("Inicio");
            cell = row.createCell(1);
            cell.setCellValue(report.getEnsaio().getInicio());
            cell = row.createCell(2);
            cell.setCellValue("Sentido Vento");
            cell = row.createCell(3);
            cell.setCellValue(report.getEnsaio().getDirecaoVentoGraus());
            row = sheet.createRow(3);
            cell = row.createCell(0);
            cell.setCellValue("Espaçamento");
            cell = row.createCell(1);
            cell.setCellValue(report.getEnsaio().getEspacamentoPluviometro());
            cell = row.createCell(2);
            cell.setCellValue("Tamanho do grid");
            cell = row.createCell(3);
            cell.setCellValue(report.getEnsaio().getGridAltura() * report.getEnsaio().getGridLargura() + " m2");
            row = sheet.createRow(4);
            cell = row.createCell(0);
            cell.setCellValue("sobreposicao");

            int linha = 5;
            //iterating r number of rows
            if (report.getSobreposicao() != null) {


                for (int r = 0; r < report.getSobreposicao().size(); r++) {
                    row = sheet.createRow(linha);
                    linha++;
                    //iterating c number of columns
                    for (int c = 0; c < report.getSobreposicao().get(r).size(); c++) {
                        cell = row.createCell(c);
                        cell.setCellValue((float) report.getSobreposicao().get(r).get(c));
                    }
                }

                linha++;
                row = sheet.createRow(linha);
                cell = row.createCell(0);
                cell.setCellValue("CUC");
                cell = row.createCell(1);
                cell.setCellValue(report.getCuc());
                linha++;
                row = sheet.createRow(linha);
                cell = row.createCell(0);
                cell.setCellValue("CUD");
                cell = row.createCell(1);
                cell.setCellValue(report.getCud());
                linha++;
                row = sheet.createRow(linha);
                cell = row.createCell(0);
                cell.setCellValue("CUE");
                cell = row.createCell(1);
                cell.setCellValue(report.getCue());
                linha++;
                row = sheet.createRow(linha);
                cell = row.createCell(0);
                cell.setCellValue("Desvio Padrão");
                cell = row.createCell(1);
                cell.setCellValue(report.getDp());
                linha++;
                row = sheet.createRow(linha);
                cell = row.createCell(0);
                cell.setCellValue("Media 1 quartil");
                cell = row.createCell(1);
                cell.setCellValue(report.getMedia());
                linha++;
                row = sheet.createRow(linha);
                cell = row.createCell(0);
                cell.setCellValue("CV");
                cell = row.createCell(1);
                cell.setCellValue(report.getCv() + "%");
                linha++;
            }
            row = sheet.createRow(linha);
            cell = row.createCell(0);
            cell.setCellValue("Distância(\"m\")");
            cell = row.createCell(1);
            cell.setCellValue("Precipitação(\"mm\")");
//            int rowNum = linha+1;
            linha++;

            for (int r = 0; r < report.getDistancia().size(); r++) {
                row = sheet.createRow(linha);

                cell = row.createCell(0);
                cell.setCellValue((float) report.getDistancia().get(r));
                cell = row.createCell(1);
                cell.setCellValue((float) report.getPerfil().get(r));
//                //iterating c number of columns
//                for (int c = 0; c < report.getDistancia().size(); c++) {
//                }
                linha++;
            }




            FileOutputStream fileOut = new FileOutputStream(excelFileName);

            //write this workbook to an Outputstream.
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
            Dialog.showInfo("Exportação", "Valores da analise exportado com sucesso no seguinte caminho: " + excelFileName);
        } catch (IOException ex) {
            Logger.getLogger(AnaliseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void writeXLSXFile(AnaliseJavaBeanDataSource report) {
        try {
            String excelFileName = System.getProperty("user.dir") + "/" + report.getEnsaio().getDescricao().replace(" ", "_") + "_sobreposicao" + report.getSobredimensao() + ".xlsx";//name of excel file

            String sheetName = "Sobreposicao";//name of sheet

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(sheetName);

            //iterating r number of rows
            for (int r = 0; r < report.getSobreposicao().size(); r++) {
                XSSFRow row = sheet.createRow(r);

                //iterating c number of columns
                for (int c = 0; c < report.getSobreposicao().get(r).size(); c++) {
                    XSSFCell cell = row.createCell(c);

                    cell.setCellValue((float) report.getSobreposicao().get(r).get(c));
                }
            }

            FileOutputStream fileOut = new FileOutputStream(excelFileName);

            //write this workbook to an Outputstream.
            wb.write(fileOut);
            fileOut.flush();

            fileOut.close();
            Dialog.showInfo("Exportação", "Sobreposição exportada com sucesso no seguinte caminho: " + excelFileName);

        } catch (IOException ex) {
            Logger.getLogger(AnaliseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setEnsaio(Ensaio e) {
        this.uniformidadeView.setEnsaio(e);
    }

    void reRenderTable() {
        this.uniformidadeView.reRenderTable();
    }

    void show() {
        this.uniformidadeView.show();
    }
}
