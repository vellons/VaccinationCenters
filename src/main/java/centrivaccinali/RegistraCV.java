package centrivaccinali;

import global.DatabaseCVInterface;
import global.JTextFieldCharLimit;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.TipologiaCentroVaccinale;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La classe RegistrazioneRistorante permette la registrazione di
 * un centro vaccinale sull'applicazione
 *
 * @author Mahdi Said
 */

public class RegistraCV {

    /**
     * <code>panelRegistraCV</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie la parte che si occupa dell'inserimento
     * dei dati dei centri vaccinali
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; richiamabile da classi esterne
     */

    public JPanel panelRegistraCV;

    /**
     * <code>cv</code> &egrave; un'istanza della classe CentroVaccinale che
     * permette di usare le funzionalit&agrave; per la costruzione dei centri vaccinali.
     *
     * @see CentroVaccinale
     * <p>
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private CentroVaccinale cv;

    /**
     * <code>panelLogo</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie una parte del logo dell'appicazione.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JPanel panelLogo;

    /**
     * <code>panelLogo2</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie una parte del logo dell'appicazione.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JPanel panelLogo2;

    /**
     * <code>btnRegistraCV</code> &egrave; un bottone Swing che attiva la procedura
     * di registrazione di un centro vaccinale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnRegistraCV;

    /**
     * <code>tfNomeCentro</code> &egrave; un campo di testo Swing dedicato al nome del centro
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfNomeCentro;

    /**
     * <code>tfIndirizzo</code> &egrave; un campo di testo Swing dedicato all'indirizzo
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfIndirizzo;

    /**
     * <code>tfCivico</code> &egrave; un campo di testo Swing dedicato al numero civico
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfCivico;

    /**
     * <code>tfComune</code> &egrave; un campo di testo Swing dedicato al comune
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfComune;

    /**
     * <code>tfSiglaProvincia</code> &egrave; un campo di testo Swing dedicato alla sigla provincia
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfSiglaProvincia;

    /**
     * <code>tfCap</code> &egrave; un campo di testo Swing dedicato al cap
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfCap;

    /**
     * <code>cboxTipoCentro</code> &egrave; una combobox Swing usata per la selezione della
     * tipologia del centro vaccinale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JComboBox<String> cboxTipoCentro;

    /**
     * <code>lblNomeCentro</code> &egrave; un'etichetta Swing dedicata al campo nome centro
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblNomeCentro;

    /**
     * <code>lblQualificatore</code> &egrave; un'etichetta Swing dedicata al campo qualificatore
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblQualificatore;

    /**
     * <code>lblIndirizzo</code> &egrave; un'etichetta Swing dedicata al campo indirizzo
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblIndirizzo;

    /**
     * <code>lblCivico</code> &egrave; un'etichetta Swing dedicata al campo numero civico
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblCivico;

    /**
     * <code>lblComune</code> &egrave; un'etichetta Swing dedicata al campo comune
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblComune;

    /**
     * <code>lblSiglaProvincia</code> &egrave; un'etichetta Swing dedicata al campo sigla provincia
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblSiglaProvincia;

    /**
     * <code>lblCap</code> &egrave; un'etichetta Swing dedicata al campo cap
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblCap;

    /**
     * <code>lblTipoCentro</code> &egrave; un'etichetta Swing dedicata al campo del tipo del centro
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblTipoCentro;

    /**
     * <code>lblErrors</code> &egrave; un'etichetta Swing dedicata al campo di controllo errori
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblErrors;

    /**
     * <code>cboxQualificatore</code> &egrave; una combobox Swing usata per la selezione del
     * qualificatore dell'indirizzo
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JComboBox cboxQualificatore;

    /**
     * <code>tipo</code> &egrave; un intero che contiene il valore
     * effettivo della tipologia del centro vaccinale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int tipo=0;

    /**
     * <code>tipologie</code> &egrave; un' arraylist che contiene i
     * possibili valori per la tipologia di centro vaccinale registrati sul database
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    private static List<TipologiaCentroVaccinale> tipologie = new ArrayList<>();

    /**
     * <code>qualificatore</code> &egrave; un array di stringhe che contiene i
     * possibili valori per il qualificatore d'indirizzo
     */

    String[] qualificatore = new String[]{"Via", "Viale", "Piazza", "Corso", "Vicolo"};

    /**
     * Main della classe
     *
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public RegistraCV() throws RemoteException {
        tfNomeCentro.setDocument(new JTextFieldCharLimit(128));
        tfIndirizzo.setDocument(new JTextFieldCharLimit(128));
        tfCivico.setDocument(new JTextFieldCharLimit(8));
        tfComune.setDocument(new JTextFieldCharLimit(64));
        tfSiglaProvincia.setDocument(new JTextFieldCharLimit(2));
        tfCap.setDocument(new JTextFieldCharLimit(5));
        btnRegistraCV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTipo(getTipoCentro());
                if (checkAllInputs()) {
                    try {
                        if (JOptionPane.showOptionDialog(null, "Confermi di voler registrare il nuovo centro vaccinale?",
                                "Conferma registrazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, null, null) == JOptionPane.YES_OPTION) {
                            DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                            cv = new CentroVaccinale(getTfNomeCentro(),tipo,1,getQualificatore(),getTfIndirizzo(),getTfCivico(),getTfComune(),getTfSiglaProvincia().toUpperCase(),getTfCap());
                            db.inserisciCentroVaccinale(cv);
                            CentriVaccinali.closePreviousWindow(CentriVaccinali.registraCVFrame);
                            JOptionPane.showMessageDialog(null, "La registrazione è " +
                                    "andata a buon fine!", "Registrazione effettuate", JOptionPane.PLAIN_MESSAGE);
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "C'è stato un problema. Prova a riavviare l'app",
                                "Attenzione", JOptionPane.PLAIN_MESSAGE);
                        exception.printStackTrace();
                    }
                } else {
                    lblErrors.setFont(new Font("Default", Font.BOLD, 14));
                    lblErrors.setText("Assicurarsi che tutti campi siano stati compilati correttamente");
                    lblErrors.setForeground(Color.RED);
                    lblErrors.setVisible(true);
                }
            }
        });
    }

    //METODI GETTERS

    /**
     * <code>getTfNomeCentro</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del nome del centro vaccinale
     */

    public String getTfNomeCentro() {return tfNomeCentro.getText();}

    /**
     * <code>getTfQualificatore</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del qualificatore d'indirizzo
     */

    public String getQualificatore() { return Objects.requireNonNull(cboxQualificatore.getSelectedItem()).toString();}

    /**
     * <code>getTfIndirizzo</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del nome d'indirizzo
     */

    public String getTfIndirizzo() {return tfIndirizzo.getText();}

    /**
     * <code>getTfCivico</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del numero civico
     */

    public String getTfCivico() {return tfCivico.getText();}

    /**
     * <code>getTfComune</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del comune
     */

    public String getTfComune() {return tfComune.getText();}

    /**
     * <code>getTfSiglaProvincia</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore della sigla provincia
     */

    public String getTfSiglaProvincia() {return tfSiglaProvincia.getText();}

    /**
     * <code>getTfCap</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del cap
     */

    public String getTfCap() {return tfCap.getText();}

    /**
     * <code>getTipoCentro</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore della tipologia del centro vaccinale
     */

    public String getTipoCentro() { return Objects.requireNonNull(cboxTipoCentro.getSelectedItem()).toString();}

    /**
     * <code>setTipo</code> &egrave; un metodo per impostare il valore effettivo del tipo
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param type &egrave; una stringa rappresentante il contenuto della stringa da analizzare
     * &egrave; dichiarata <strong>void</strong> in quanto il metodo non restituisce alcun valore
     */

    private void setTipo(String type){

        for (TipologiaCentroVaccinale obj : tipologie) {
            if(Objects.equals(obj.getNome(), type)){
                tipo=obj.getId();
            }
        }

    }

    /**
     * <code>checkAllInputs</code> &egrave; un metodo per controllare il contenuto dei textfield
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @return valore booleano che indica se i dati inseriti nei textfield sono validi
     */

    private boolean checkAllInputs(){
        boolean allFieldsValid;  // Tramite una variabile booleana, verifico se tutti i campi siano completi

        allFieldsValid = checkInput(getTfNomeCentro(), tfNomeCentro);
        allFieldsValid &= checkInput(getTfIndirizzo(), tfIndirizzo);
        allFieldsValid &= isAlphabetic(getTfIndirizzo());
        allFieldsValid &= checkInput(getTfCivico(), tfCivico);
        allFieldsValid &= isNumeric(getTfCivico());
        allFieldsValid &= checkInput(getTfComune(), tfComune);
        allFieldsValid &= isAlphabetic(getTfComune());
        allFieldsValid &= checkInput(getTfSiglaProvincia(), tfSiglaProvincia);
        allFieldsValid &= isAlphabetic(getTfSiglaProvincia());
        allFieldsValid &= checkInput(getTfCap(), tfCap);
        allFieldsValid &= isNumeric(getTfCap());

        return allFieldsValid;
    }

    /**
     * <code>isNumeric</code> &egrave; un metodo per controllare se l'input di un textfield sia formato da soli numeri
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param str &egrave; una stringa rappresentante il contenuto della stringa da analizzare
     * @return valore booleano che indica se il dato &egrave; di tipo numerico
     */

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * <code>isAlphabetic</code> &egrave; un metodo per controllare se l'input di un textfield sia formato da sole lettere
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param input &egrave; una stringa rappresentante il contenuto della stringa da analizzare
     * @return valore booleano che indica se il dato &egrave; di tipo alfabetico
     */

    private boolean isAlphabetic(String input) {
        if(Character.isSpaceChar(input.charAt(0))){
            return false;
        }
        for (int i = 0; i != input.length(); ++i) {
            if (!Character.isLetter(input.charAt(i)) && !Character.isSpaceChar(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <code>checkInput</code> &egrave; un metodo per controllare l'input di un textfield
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param input     &egrave; una stringa rappresentante il contenuto del campo da analizzare
     * @param textField &egrave; il textfield di riferimento dell'input
     * @return valore booleano che indica se il dato &egrave; inserito nel textfield
     */

    private boolean checkInput(String input, JTextField textField){
        boolean res;
        String tmp = "";
        tmp += input;
        // Se il campo e vuoto, visualizzo una scritta
        res = !tmp.equals("");
        return res;
    }

    /**
     * <code>createUIComponents</code> &egrave; una procedura per impostare la grafica
     * quando viene caricato il frame
     * &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     *
     * @throws IOException &egrave; utilizzata quando si verificano errori nelle fasi di input e di output
     */

    private void createUIComponents() throws IOException {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/CVLogo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);
        panelLogo2 = new JPanel();
        BufferedImage myPicture2 = ImageIO.read(new File("media/ItaliaRinasce.png"));
        JLabel picLabel2 = new JLabel(new ImageIcon(myPicture2));
        panelLogo2.add(picLabel2);
        cboxQualificatore = new JComboBox<String>(qualificatore);
        if (tipologie.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                tipologie = db.getTipologiaCentroVaccinale();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        List<String> tipologieCombo = new ArrayList<>();
        for (TipologiaCentroVaccinale obj : tipologie) {
            tipologieCombo.add(obj.getNome());
        }

        cboxTipoCentro = new JComboBox(tipologieCombo.toArray());
    }
}
