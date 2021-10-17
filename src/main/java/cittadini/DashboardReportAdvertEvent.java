package cittadini;

import javax.swing.*;

public class DashboardReportAdvertEvent {
    private JPanel panelListaNuoveSegnalazioni;
    private JPanel panelNewReport;
    private JButton btnInviaSegnalazione;


    private void createUIComponents() {
        panelListaNuoveSegnalazioni = new JPanel();
        panelListaNuoveSegnalazioni.add(new ListaEventiAvversiPanel());
    }
}
