package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.EventoAvverso;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class DashboardSegnalaEventiAvversi {
    private JPanel panelListaNuoveSegnalazioni;
    public JPanel panelNewReport;
    private JButton btnInviaSegnalazione;
    private JPanel panelLogo;
    protected static List<EventoAvversoPerLista> listEvento = new ArrayList<>();


    public DashboardSegnalaEventiAvversi() {
        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();
        
        btnInviaSegnalazione.addActionListener(e -> {
            System.out.println("TODO: CLICK");
            for (EventoAvversoPerLista elem : listEvento) {
                if (elem.getValoreSeverita() > 0) {
                    try {
                        EventoAvverso ea = new EventoAvverso(Login.utenteLoggato.getId(), elem.getTipologiaEvento().getId(), elem.getValoreSeverita(), elem.getNota());
                        db.inserisciNuovoEventoAvversoCittadino(ea);
                        //System.out.println("Invio info al server\n" + ea);
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void createUIComponents() throws IOException {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/ItaliaRinasce.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);
        panelListaNuoveSegnalazioni = new JPanel();
        panelListaNuoveSegnalazioni.add(new ListaSegnalaEventiAvversiPanel());
    }
}
