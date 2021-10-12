package centrivaccinali;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.TipologiaVaccino;
import models.Vaccinato;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * La classe CentriVaccinali permette la registrazione di un nuovo utente vaccinato presso un centro
 *
 * @author Mahdi Said
 */

public class RegistraVaccinato{

    /**
     * <code>panelRegistraVaccino</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie la parte che si occupa dell'inserimento
     * dei dati degli utenti vaccinati
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; richiamabile da classi esterne
     */

    public JPanel panelRegistraVaccinato;

    /**
     * <code>vax</code> &egrave; un'istanza della classe Vaccinato che
     * permette di usare le funzionalit&agrave; per la registrazione degli utenti vaccinati.
     *
     * @see Vaccinato
     * <p>
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private Vaccinato vax;

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
     * <code>tfNomeCentro</code> &egrave; un campo di testo Swing dedicato al nome del centro
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfNomeCentro;

    /**
     * <code>tfNome</code> &egrave; un campo di testo Swing dedicato al nome utente
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfNome;

    /**
     * <code>tfCognome</code> &egrave; un campo di testo Swing dedicato al cognome utente
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfCognome;

    /**
     * <code>tfCodiceFiscale</code> &egrave; un campo di testo Swing dedicato al codice fiscale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfCodiceFiscale;

    /**
     * <code>tfDataVaccino</code> &egrave; un campo di testo Swing dedicato alla data di somministrazione
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfDataVaccino;

    /**
     * <code>tfIDUnivoco</code> &egrave; un campo di testo Swing dedicato all'identificatore univoco
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfIDUnivoco;

    /**
     * <code>btnRegistraVaccinato</code> &egrave; un bottone Swing che attiva la procedura
     * di registrazione di un utente vaccinato
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnRegistraVaccinato;

    /**
     * <code>lblErrors</code> &egrave; un'etichetta Swing dedicata al campo di controllo errori
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblErrors;

    /**
     * <code>lblNomeCentro</code> &egrave; un'etichetta Swing dedicata al campo del nome del centro
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblNomeCentro;

    /**
     * <code>lblNome</code> &egrave; un'etichetta Swing dedicata al campo del nome utente
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblNome;

    /**
     * <code>lblCognome</code> &egrave; un'etichetta Swing dedicata al campo del cognome utente
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblCognome;

    /**
     * <code>lblCodiceFiscale</code> &egrave; un'etichetta Swing dedicata al campo del codice fiscale
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblCodiceFiscale;

    /**
     * <code>lblDataVaccino</code> &egrave; un'etichetta Swing dedicata al campo della data di somministrazione
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblDataVaccino;

    /**
     * <code>lblTipoVaccino</code> &egrave; un'etichetta Swing dedicata al campo della tipologia del vaccino
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblTipoVaccino;

    /**
     * <code>lblIDUnivoco</code> &egrave; un'etichetta Swing dedicata al campo dell'identificatore univoco
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblIDUnivoco;

    /**
     * <code>tipo</code> &egrave; un timestamp che contiene il valore
     * effettivo della data di somministrazione
     */

    private Timestamp dataVaccino= null;

    /**
     * <code>cboxTipoVaccino</code> &egrave; una combobox Swing usata per la selezione della
     * tipologia del vaccino.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JComboBox<String> cboxTipoVaccino;

    /**
     * <code>cboxNomeCentro</code> &egrave; una combobox Swing usata per la selezione del
     * nome del centro vaccinale.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JComboBox cboxNomeCentro;

    /**
     * <code>btnGeneraID</code> &egrave; un bottone Swing che attiva la procedura
     * di compilazione del campo dell'identificatore univoco.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnGeneraID;

    /**
     * <code>btnDataCorrente</code> &egrave; un bottone Swing che attiva la procedura
     * di compilazione del campo relativo alla corrente data di somministrazione.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnDataCorrente;

    /**
     * <code>tipo</code> &egrave; un intero che contiene il valore
     * effettivo della tipologia del vaccino.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int tipo=0;

    /**
     * <code>tipo</code> &egrave; un intero che contiene il valore
     * effettivo dell'identificativo del centro vaccinale.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int centro_id=0;

    /**
     * <code>nomi</code> &egrave; un' arraylist che contiene i
     * possibili valori dei nomi dei centri vaccinali registrati sul database.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    private static List<CentroVaccinale> nomi = new ArrayList<>();

    /**
     * <code>tipologie</code> &egrave; un' arraylist che contiene i
     * possibili valori per la tipologia di vaccino registrati sul database.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    private static List<TipologiaVaccino> tipologie = new ArrayList<>();

    /**
     * <code>CF_REGEX</code> &egrave; un' espressione regolare che serve per verificare
     * l'integrit&agrave; del codice fiscale inserito in input.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>final</strong> in quanto il suo valore &egrave; immutabile nel corso dell'esecuzione
     */

    private final String CF_REGEX = "^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$";

    /**
     * Main della classe
     *
     * @throws Exception &egrave; utilizzata quando non si sa che tipo di eccezzione possa verificarsi
     */

    public RegistraVaccinato() throws Exception {

        btnRegistraVaccinato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTipo(getTipoVaccino());
                setID(getNomeCentro());
                dataVaccino=stringToTimestamp(getTfDataVaccino());
                if (checkAllInputs()) {
                    try {
                        if (JOptionPane.showOptionDialog(null, "Confermi di voler registrare il nuovo utente?",
                                "Conferma registrazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, null, null) == JOptionPane.YES_OPTION) {
                            DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                            vax = new Vaccinato(getTfIDUnivoco(),centro_id,tipo,getTfNome(),getTfCognome(),getTfCodiceFiscale(),dataVaccino,"","");
                            db.inserisciCittadinoVaccinato(vax);
                            CentriVaccinali.closePreviousWindow(CentriVaccinali.registraVaccinatoFrame);
                            JOptionPane.showMessageDialog(null, "La registrazione e'" +
                                    "andata a buon fine!", "Registrazione effettuate", JOptionPane.PLAIN_MESSAGE);
                        }
                    } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, "C'e'; stato un problema. Prova a riavviare l'app",
                                    "Attenzione", JOptionPane.PLAIN_MESSAGE);
                    }
                } else {
                    lblErrors.setFont(new Font("Default", Font.BOLD, 14));
                    lblErrors.setText("Assicurarsi che tutti campi siano stati compilati correttamente");
                    lblErrors.setForeground(Color.RED);
                    lblErrors.setVisible(true);
                }

            }
        });
        btnDataCorrente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
                Date date = new Date();
                tfDataVaccino.setText(formatter.format(date));
            }
        });
        btnGeneraID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfIDUnivoco.setText(UUID.randomUUID().toString());
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

    public String getNomeCentro() { return Objects.requireNonNull(cboxNomeCentro.getSelectedItem()).toString();}

    /**
     * <code>getTfNome</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del nome utente
     */

    public String getTfNome() {
        return tfNome.getText();
    }

    /**
     * <code>getTfCognome</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del cognome utente
     */

    public String getTfCognome() {
        return tfCognome.getText();
    }

    /**
     * <code>getTfCodiceFiscale</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del codice fiscale
     */

    public String getTfCodiceFiscale() {
        return tfCodiceFiscale.getText();
    }

    /**
     * <code>getTfDataVaccino</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore della data di somministrazione
     */

    public String getTfDataVaccino() {return tfDataVaccino.getText();}

    /**
     * <code>getTfIDUnivoco</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore dell'identificatore univoco
     */

    public String getTfIDUnivoco() {
        return tfIDUnivoco.getText();
    }

    /**
     * <code>getTipoVaccino</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del tipo del vaccino
     */

    public String getTipoVaccino() { return Objects.requireNonNull(cboxTipoVaccino.getSelectedItem()).toString();}

    /**
     * <code>checkAllInputs</code> &egrave; un metodo per controllare il contenuto dei textfield
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @return valore booleano che indica se i dati inseriti nei textfield sono validi
     */

    private boolean checkAllInputs(){
        boolean allFieldsValid;  // Tramite una variabile booleana, verifico se tutti i campi siano completi

        allFieldsValid = checkInput(getTfNome(), tfNome);
        allFieldsValid &= isAlphabetic(getTfNome());
        allFieldsValid &= checkInput(getTfCognome(), tfCognome);
        allFieldsValid &= isAlphabetic(getTfCognome());
        allFieldsValid &= getTfCodiceFiscale().matches(CF_REGEX);
        allFieldsValid &= dataVaccino != null;
        allFieldsValid &= checkInput(getTfIDUnivoco(), tfIDUnivoco);

        return allFieldsValid;
    }

    /**
     * <code>isAlphabetic</code> &egrave; un metodo per controllare se l'input di un textfield sia formato da sole lettere
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param input &egrave; una stringa rappresentante il contenuto della stringa da analizzare
     * @return valore booleano che indica se il dato &egrave; di tipo alfabetico
     */

    private static boolean isAlphabetic(String input) {
        for (int i = 0; i != input.length(); ++i) {
            if (!Character.isLetter(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * <code>stringToTimestamp</code> &egrave; un metodo per convertire una stringa in un timestamp
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param stringa &egrave; una stringa rappresentante il contenuto della stringa da convertire
     * &egrave; dichiarata <strong>Timestamp</strong> in quanto il metodo restituisce un valore di una data e di un'ora
     */


    private Timestamp stringToTimestamp(String stringa){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(stringa);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            return timestamp;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
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
     * <code>setTipo</code> &egrave; un metodo per impostare l'effettivo valore del tipo
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param type &egrave; una stringa rappresentante il contenuto della stringa da analizzare
     * &egrave; dichiarata <strong>void</strong> in quanto il metodo non restituisce alcun valore
     */

    private void setTipo(String type){

        for (TipologiaVaccino obj : tipologie) {
            if(Objects.equals(obj.getNome(), type)){
                tipo=obj.getId();
            }
        }

    }

    /**
     * <code>setID</code> &egrave; un metodo per impostare il valore dell'ID di un centro
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param name &egrave; una stringa rappresentante il contenuto della stringa da analizzare
     * &egrave; dichiarata <strong>void</strong> in quanto il metodo non restituisce alcun valore
     */

    private void setID(String name){

        for (CentroVaccinale obj : nomi) {
            if(Objects.equals(obj.getNome(), name)){
                centro_id=obj.getId();
            }
        }

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
        // Prendo le tipologie di centro vaccinale
        if (tipologie.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                tipologie = db.getTipologiaVaccino();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        List<String> tipologieCombo = new ArrayList<>();
        for (TipologiaVaccino obj : tipologie) {
            tipologieCombo.add(obj.getNome());
        }

        cboxTipoVaccino = new JComboBox(tipologieCombo.toArray());
        if (nomi.size() == 0) {
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();
                nomi = db.getCentriVaccinali("");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        List<String> nomiCombo = new ArrayList<>();
        for (CentroVaccinale obj : nomi) {
            nomiCombo.add(obj.getNome());
        }

        cboxNomeCentro = new JComboBox(nomiCombo.toArray());

    }
}
