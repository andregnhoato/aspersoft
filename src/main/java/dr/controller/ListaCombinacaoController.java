package dr.controller;

import dr.action.AbstractAction;
import dr.dao.CombinacaoDAO;
import dr.dao.CombinacaoDAOImpl;
import dr.event.AbstractEventListener;
import dr.event.IncluirCombinacaoEvent;
import dr.event.RemoveCombinacaoEvent;
import dr.event.AtualizaListaCombinacaoEvent;
import dr.event.BuscarCombinacaoEvent;
import dr.model.Combinacao;
import dr.report.AnaliseJavaBeanDataSource;
import dr.ui.Dialog;
import dr.ui.combinacao.CombinacaoListView;
import dr.util.JPAUtil;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Define a
 * <code>Controller</code> principal do sistema, responsável por gerir a tela
 * com a lista de
 * <code>Combinacao</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @andre
 */
public class ListaCombinacaoController extends PersistenceController {

    private CombinacaoListView view;
    private IncluirCombinacaoController addCombinacaoController;
    private BuscarCombinacaoController buscarController;

    public ListaCombinacaoController(AbstractController parent) {
        super(parent);
        loadPersistenceContext();
        this.view = new CombinacaoListView();
        this.addCombinacaoController = new IncluirCombinacaoController(this);
        this.buscarController = new BuscarCombinacaoController(this);

        registerAction(view.getNewButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaCombinacaoController.this.addCombinacaoController.show();
            }
        });

        registerAction(view.getFindButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaCombinacaoController.this.buscarController.show();
            }
        });

        registerAction(view.getRefreshButton(), new AbstractAction() {
            @Override
            protected void action() {
                refreshTable();
            }
        });


        registerAction(view.getImprimirButton(), new AbstractAction() {
            @Override
            protected void action() {

                try {
                    CombinacaoDAO dao = new CombinacaoDAOImpl(JPAUtil.getEntityManager());
                    List<Combinacao> combinacoes = dao.findAll();

                    if (combinacoes.size() > 0)
                        writeXLSXTabelaDesempenhoAspersor(combinacoes);
                   
                } catch (Exception ex) {
                    Logger.getLogger(ListaCombinacaoController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });



        view.getTable().setMouseEvent(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    Combinacao c = view.getTable().getCombinacaoSelected();
                    if (c != null) {
                        ListaCombinacaoController.this.addCombinacaoController.show(c);
                    }
                }
            }
        });

        registerEventListener(IncluirCombinacaoEvent.class, new AbstractEventListener<IncluirCombinacaoEvent>() {
            @Override
            public void handleEvent(IncluirCombinacaoEvent event) {
                refreshTable();
            }
        });

        registerEventListener(RemoveCombinacaoEvent.class, new AbstractEventListener<RemoveCombinacaoEvent>() {
            @Override
            public void handleEvent(RemoveCombinacaoEvent event) {
                refreshTable();
            }
        });

        registerEventListener(AtualizaListaCombinacaoEvent.class, new AbstractEventListener<AtualizaListaCombinacaoEvent>() {
            @Override
            public void handleEvent(AtualizaListaCombinacaoEvent event) {
                refreshTable();
            }
        });

        registerEventListener(BuscarCombinacaoEvent.class, new AbstractEventListener<BuscarCombinacaoEvent>() {
            @Override
            public void handleEvent(BuscarCombinacaoEvent event) {
                List<Combinacao> list = event.getTarget();
                if (list != null) {
                    refreshTable(event.getTarget());
                }
            }
        });

        refreshTable();
    }

    public void writeXLSXTabelaDesempenhoAspersor(List<Combinacao> combinacoes) {
        try {
            String excelFileName = System.getProperty("user.dir") + "/TabelaDesempenhoAspersosPingo.xlsx";//name of excel file

            String sheetName = "Tabela";//name of sheet

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(sheetName);

            XSSFRow row = sheet.createRow(0);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("Descrição");
            cell = row.createCell(1);
            cell.setCellValue("Combinação de bocais");
            cell = row.createCell(2);
            cell.setCellValue("Pressão (mca)");
            cell = row.createCell(3);
            cell.setCellValue("Vazão total (m³/h)");
            cell = row.createCell(4);
            cell.setCellValue("Diâmetro Irrigado (m)");
            cell = row.createCell(5);
            cell.setCellValue(" A x L  \n(mXm)");
            cell = row.createCell(6);
            cell.setCellValue("Peq (mm/h)");

            //iterating r number of rows
            for (int r = 1; r <= combinacoes.size(); r++) {
                row = sheet.createRow(r);        
                cell = row.createCell(0);
                cell.setCellValue(combinacoes.get(r-1).getDescricao());
                cell = row.createCell(1);
                cell.setCellValue(combinacoes.get(r-1).getBocal().getDescricao()+" X " +combinacoes.get(r-1).getQuebraJato().getDescricao());
                cell = row.createCell(2);
                cell.setCellValue(combinacoes.get(r-1).getPressao());
                cell = row.createCell(3);
                cell.setCellValue(combinacoes.get(r-1).getVazao());
                cell = row.createCell(4);
                cell.setCellValue(combinacoes.get(r-1).getDiametroIrrigado());
                cell = row.createCell(5);
                cell.setCellValue(combinacoes.get(r-1).getAltura()+ " X "+ combinacoes.get(r-1).getLargura());
                cell = row.createCell(6);
                cell.setCellValue(combinacoes.get(r-1).getPeq());

            }

            FileOutputStream fileOut = new FileOutputStream(excelFileName);

            //write this workbook to an Outputstream.
            wb.write(fileOut);
            fileOut.flush();

            fileOut.close();
            Dialog.showInfo("Exportação", "Tabela de Desempenho Aspersor Pingo exportada com sucesso no seguinte caminho: " + excelFileName);

        } catch (IOException ex) {
            Logger.getLogger(AnaliseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void show() {
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        view.show();
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        JPAUtil.closeEntityManagerFactory();
    }

    private void refreshTable() {
        refreshTable(null);
    }

    private void refreshTable(List<Combinacao> list) {
        if (list != null) {
            view.refreshTable(list);
            return;
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (view == null) {
                    Logger.getLogger("nulo a parada da view");
                }
                try {
                    CombinacaoDAO dao = new CombinacaoDAOImpl(JPAUtil.getEntityManager());
                    view.refreshTable(dao.findAll());
                } catch (Exception ex) {
                    Logger.getLogger(ListaCombinacaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
