package dr.controller;

import dr.action.AbstractAction;
import dr.model.Ensaio;
import dr.ui.Dialog;
import dr.ui.coleta.uniformidade.UniformidadeListView;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela  <code>Uniformidades </code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @andre
 */
public class UniformidadeController extends PersistenceController {
    
    private UniformidadeListView uniformidadeView;  

    public UniformidadeController(AbstractController parent) {
        this.uniformidadeView = new UniformidadeListView();
        
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
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        } 
                    }

          
                });
                
            }
        });
        
        
        
    }
    
    public void writeXLSXFile(ObservableList<ObservableList> sobreposicao) throws IOException {

        String excelFileName = System.getProperty("user.dir")+"/sobreposicao.xlsx";//name of excel file

        String sheetName = "Sobreposicao";//name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);

        //iterating r number of rows
        for (int r = 0; r < sobreposicao.size(); r++) {
            XSSFRow row = sheet.createRow(r);

            //iterating c number of columns
            for (int c = 0; c < sobreposicao.get(r).size(); c++) {
                XSSFCell cell = row.createCell(c);

                cell.setCellValue((float)sobreposicao.get(r).get(c));
            }
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        //write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
        Dialog.showInfo("Exportação", "Sobreposição exportada com sucesso no seguinte caminho: "+excelFileName);
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
