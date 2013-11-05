package dr.controller;

import dr.action.AbstractAction;
import dr.model.Ensaio;
import dr.report.AnaliseJavaBeanDataSource;
import dr.ui.Dialog;
import dr.ui.analise.AnaliseView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
                        try {
                            writeXLSXFile(uniformidadeView.getSobreposicao());
                            writeXLSXFileReport(uniformidadeView.getReport());

//                            WritableImage snapShot = uniformidadeView.getScene().snapshot(null);
//                            ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png", new File("test.png"));
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    }
                });

            }
        });



    }

    public void writeXLSXFileReport(AnaliseJavaBeanDataSource report) {
        try {


            String excelFileName = System.getProperty("user.dir") + "/Analise.xlsx";//name of excel file

            String sheetName = "analise";//name of sheet

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(sheetName);

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
            cell.setCellValue(report.getCv());

            FileOutputStream fileOut = new FileOutputStream(excelFileName);

            //write this workbook to an Outputstream.
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
//        Dialog.showInfo("Exportação", "Sobreposição exportada com sucesso no seguinte caminho: "+excelFileName);
        } catch (Exception e) {
            System.err.println(e.getCause()+"\n"+e.getMessage());
        }

    }

    public void writeXLSXFile(ObservableList<ObservableList> sobreposicao) throws IOException {

        String excelFileName = System.getProperty("user.dir") + "/sobreposicao.xlsx";//name of excel file

        String sheetName = "Sobreposicao";//name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);

        //iterating r number of rows
        for (int r = 0; r < sobreposicao.size(); r++) {
            XSSFRow row = sheet.createRow(r);

            //iterating c number of columns
            for (int c = 0; c < sobreposicao.get(r).size(); c++) {
                XSSFCell cell = row.createCell(c);

                cell.setCellValue((float) sobreposicao.get(r).get(c));
            }
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        //write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
        Dialog.showInfo("Exportação", "Sobreposição exportada com sucesso no seguinte caminho: " + excelFileName);
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
