package cittadini;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DashboardSegnalaEventiAvversi {
    private JPanel panelListaNuoveSegnalazioni;
    public JPanel panelNewReport;
    private JButton btnInviaSegnalazione;
    private JPanel panelLogo;


    public DashboardSegnalaEventiAvversi() {
        btnInviaSegnalazione.setVisible(true);
        btnInviaSegnalazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("TODO: CLICK");
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
