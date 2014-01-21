package dr.controller;

import dr.action.AbstractAction;
import dr.dao.EnsaioDAO;
import dr.dao.EnsaioDAOImpl;
import dr.event.AbstractEventListener;
import dr.event.AtualizaListaEnsaioEvent;
import dr.event.BuscarEnsaioEvent;
import dr.model.Ensaio;
import dr.ui.perfil.PerfilView;
import dr.util.JPAUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Define a
 * <code>Controller</code> principal do sistema, responsável por gerir a tela
 * com a lista de
 * <code>Ensaio</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @andre
 */
public class PerfilController extends ListaEnsaioController {

    private PerfilView pView;
    private List<Ensaio> ensaios;

    public PerfilController(AbstractController parent) {
        super(parent);
        this.pView = new PerfilView();
        this.pView.refreshTable(null);
        ensaios = new ArrayList<>();

        registerAction(pView.getBtLimpar(), new AbstractAction() {
            @Override
            protected void action() {
                pView.limparGrafico();
                ensaios.clear();
            }
        });
        
        registerAction(pView.getBtExportarExcel(), new AbstractAction() {
            @Override
            protected void action() {
//                Dialog.showError("Erro", "Não implementado");
                writeXLSXFilePerfil();
            }
        });


        pView.getTable().setMouseEvent(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    Ensaio e = pView.getTable().getEnsaioSelected();
                    if (e != null) {
                        pView.setEnsaio(e);
                        pView.reRenderTable();
                        ensaios.add(pView.getEnsaio());
                    }
                }

            }
        });

        registerEventListener(AtualizaListaEnsaioEvent.class, new AbstractEventListener<AtualizaListaEnsaioEvent>() {
            @Override
            public void handleEvent(AtualizaListaEnsaioEvent event) {
                refreshTable();
            }
        });

        registerEventListener(BuscarEnsaioEvent.class, new AbstractEventListener<BuscarEnsaioEvent>() {
            @Override
            public void handleEvent(BuscarEnsaioEvent event) {
                List<Ensaio> list = event.getTarget();
                if (list != null) {
                    refreshTable(event.getTarget());
                }
            }
        });

        refreshTable();
    }
    
        private void writeXLSXFilePerfil() {
        try {
            XSSFWorkbook workbook;
            workbook = new XSSFWorkbook(OPCPackage.open(new FileInputStream(System.getProperty("user.dir") + "/template/perfildedistribuicao.xlsx"))); // or sample.xls
            XSSFSheet sheet = workbook.getSheetAt(0);
            String sheetName = sheet.getSheetName();
            XSSFRow row;
            XSSFCell cell;

            //Set headers for the data
            sheet.createRow(0).createCell(0).setCellValue("Distância(m)");
            sheet.getRow(0).createCell(1).setCellValue("Precipitação(mm)");

            
            int linha = 1;
            for (int r = 0; r < ensaios.size(); r++) {
                row = sheet.createRow(r + 1);

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

        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(AnaliseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void show() {
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        refreshTable(null);
        pView.show();
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        JPAUtil.closeEntityManagerFactory();
    }

    private void refreshTable() {
        refreshTable(null);
    }

    private void refreshTable(List<Ensaio> list) {
        if (list != null) {
            pView.refreshTable(list);
            return;
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (pView == null) {
                    Logger.getLogger("nulo a parada da view");
                }
                try {
                    EnsaioDAO dao = new EnsaioDAOImpl(getPersistenceContext());
                    pView.refreshTable(dao.findAll());
                } catch (Exception ex) {
                    Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
