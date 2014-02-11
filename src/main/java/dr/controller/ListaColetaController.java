package dr.controller;

import dr.action.AbstractAction;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.model.Coleta;
import dr.model.Ensaio;
import dr.ui.Dialog;
import dr.ui.coleta.ColetaListView;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela com a lista de <code>Coleta</code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @andre
 */
public class ListaColetaController extends PersistenceController {
    
    private ColetaListView coletaView;
    
    

    public ListaColetaController(AbstractController parent) {
        this.coletaView = new ColetaListView();      
        
        registerAction(this.coletaView.getButtonExportar(), new AbstractAction() {

            @Override
            protected void action() {
                writeXLSXFileReport(coletaView.getEnsaio());

            }
        });
        
    }
    
    public void writeXLSXFileReport(Ensaio e) {
        try {
            
            ColetaDAO dao = new ColetaDAOImpl(getPersistenceContext());
            List<Coleta> coletas = dao.findColetasByEnsaio(e);


            String excelFileName = System.getProperty("user.dir") + "/EXP_Coletas_" + e.getId() + e.getDescricao().replaceAll(" ", "_") +".xlsx";//name of excel file

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
            cell.setCellValue(e.getDescricao());

            cell = row.createCell(2);
            cell.setCellValue("Data");
            
            cell = row.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(e.getData());
            
            row = sheet.createRow(1);
            cell = row.createCell(0);
            cell.setCellValue("Hora Inicio");
            cell = row.createCell(1);
            cell.setCellValue(e.getInicio());
            cell = row.createCell(2);
            cell.setCellValue("Duração");
            cell = row.createCell(3);
            cell.setCellValue(e.getDuracao());
            
            row = sheet.createRow(2);
            cell = row.createCell(0);
            cell.setCellValue("Bocal (bocal x quebra jato)");
            cell = row.createCell(1);
            cell.setCellValue(e.getBocal().getDescricao() + " x " +e.getQuebraJato().getDescricao());
            cell = row.createCell(2);
            cell.setCellValue("Pressão");
            cell = row.createCell(3);
            cell.setCellValue(e.getPressao());
            row = sheet.createRow(3);
            cell = row.createCell(0);
            cell.setCellValue("Velocidade do vento(m/s)");
            cell = row.createCell(1);
            cell.setCellValue(e.getVelocidadeVento());
            cell = row.createCell(2);
            cell.setCellValue("Direção do vento");
            cell = row.createCell(3);
            cell.setCellValue(e.getDirecaoVentoGraus());
            row = sheet.createRow(4);
            cell = row.createCell(0);
            cell.setCellValue("Coletas");

            int linha = 5;
            //iterating r number of rows
            int contador = 0;
            if (coletas != null) {

                for (int r = 0; r < 16; r++) {
                    row = sheet.createRow(linha);
                    linha++;
                    //iterating c number of columns
                    for (int c = 0; c < 16; c++) {
                        cell = row.createCell(c);
                        cell.setCellValue((float) coletas.get(contador).getValor());
                        contador ++;
                    }
                }
            }

             
            try (FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
                wb.write(fileOut);
                fileOut.flush();
            }
            Dialog.showInfo("Exportação", "Valores da analise exportado com sucesso no seguinte caminho: " + excelFileName);
        } catch (IOException ex) {
            Logger.getLogger(AnaliseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ListaColetaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setEnsaio(Ensaio e) {
        this.coletaView.setEnsaio(e);
    }

    void reRenderTable() {
        this.coletaView.reRenderTable();
    }

    void show() {
        this.coletaView.show();
    }
}
