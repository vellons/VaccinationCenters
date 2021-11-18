package serverCV;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Permission;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * La classe Server permette la connessione al database.
 * Tramite RMI è possibile invocare metodi per eseguire operazioni sul database.
 * Le operazioni sul database avvengono mediante metodi definiti nella classe DatabaseCV.
 * <br>
 * Questo programma può essere eseguito con i seguenti parametri:<br>
 * --remote: imposta l'hostname RMI all'indirizzo IP della WAN (internet) a cui è collegato il PC.<br>
 * --no-gui: avvia il programma senza interfaccia grafica, solo output su console.<br>
 * <br>
 * La variabile d'ambiente <b>CV_HOST</b>, se impostata, prepopola il campo della JTextField.<br>
 * La variabile d'ambiente <b>CV_USER</b>, se impostata, prepopola il campo della JTextField.<br>
 * La variabile d'ambiente <b>CV_PASS</b>, se impostata, prepopola il campo della JTextField.<br>
 *
 * @author Alex Vellone e Macaj Manuel
 * @see DatabaseCV
 */
public class Server {
    /**
     * <code>tfUsernameDB</code> &egrave; un campo di testo Swing.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JTextField tfUsernameDB;

    /**
     * <code>tfPasswordDB</code> &egrave; un campo di testo per password Swing.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPasswordField tfPasswordDB;

    /**
     * <code>btnAccedi</code> &egrave; un bottone Swing.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnAccedi;

    /**
     * <code>btnDisconnetti</code> &egrave; un bottone Swing.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnDisconnetti;

    /**
     * <code>textAreaServerStatus</code> &egrave; una area di testo Swing.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JTextArea textAreaServerStatus;

    /**
     * <code>panelServer</code> &egrave; un pannello Swing.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPanel panelServer;

    /**
     * <code>tfSiglaProvincia</code> &egrave; un campo di testo Swing.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JTextField tfHostDB;


    /**
     * <code>DB_NAME</code> nome del database sul quale sono salvate le informazioni.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private final static String DB_NAME = "wewhpzry";

    /**
     * <code>REGISTRY_NAME</code> nome del registry RMI con il quale i client possono collegarsi.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     *
     * @see global.ServerConnectionSingleton
     */
    private final static String REGISTRY_NAME = "CVDatabaseServer";

    /**
     * <code>REGISTRY_PORT</code> contiene la porta RMI utilizzata dal server RMI.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     *
     * @see global.ServerConnectionSingleton
     */
    private final static int REGISTRY_PORT = 1099;

    /**
     * <code>ALLOW_REMOTE</code> se true autorizza le connessioni remote.
     * --remote: imposta l'hostname RMI all'indirizzo IP della WAN (internet) a cui è collegato il PC.<br>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private static boolean ALLOW_REMOTE = false;

    /**
     * <codeNO_GUI></code> se true avvia l'applicazione senza GUI.
     * --no-gui: avvia il programma senza interfaccia grafica, solo output su console.<br>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private static boolean NO_GUI = false;

    /**
     * <code>username</code> contiene la stinga riferita all'username del database.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private String username;

    /**
     * <code>password</code> contiene la stinga riferita alla password del database.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private String password;

    /**
     * <code>host</code> contiene la stinga riferita all'hostname del database.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private String host;

    /**
     * <code>conn</code> contiene l'oggetto per la connessione con il database.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private Connection conn;

    /**
     * <code>dbCV</code> contiene l'istanza della classe per le operazioni su DB.
     *
     * @see DatabaseCV
     */
    DatabaseCV dbCV;

    /**
     * Costruttore della classe Server
     *
     * @throws RemoteException eccezione per RMI
     */
    public Server() throws RemoteException {

        btnDisconnetti.setEnabled(false);

        // Se ci sono delle variabili d'ambiente prepopolo i campi
        if (System.getenv("CV_HOST") != null) {
            tfHostDB.setText(System.getenv("CV_HOST"));
        }
        if (System.getenv("CV_USER") != null) {
            tfUsernameDB.setText(System.getenv("CV_USER"));
        }
        if (System.getenv("CV_PASS") != null) {
            tfPasswordDB.setText(System.getenv("CV_PASS"));
        }

        btnAccedi.addActionListener(e -> {
            host = tfHostDB.getText();
            password = String.valueOf(tfPasswordDB.getPassword());
            username = tfUsernameDB.getText();

            if (username == null || username.length() < 3 || password == null || password.length() < 3 ||
                    host == null || host.length() < 3) {
                logMessage("Inserisci username, password e server host");
            } else {
                connectToDB();
            }
        });

        btnDisconnetti.addActionListener(e -> disconnectToDB());

        // Print the server IP
        String WAN_IP = null;
        String LAN_IP;
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("1.1.1.1", 80));
            LAN_IP = socket.getLocalAddress().toString().replace("/", "");
            socket.close();
            logMessage("Server LAN IP: " + LAN_IP);
            if (ALLOW_REMOTE) {
                URL url = new URL("http://checkip.amazonaws.com/");
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                WAN_IP = br.readLine();
                logMessage("Server WAN IP: " + WAN_IP);
            }
        } catch (Exception ignored) {
        }

        // Creazione della regola di sicurezza per l'accesso remoto
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission p) {
            }
        });

        // Configurazione per l'accesso remoto con RMI
        if (ALLOW_REMOTE && WAN_IP != null) {
            System.setProperty("java.rmi.server.hostname", WAN_IP);
            logMessage("Registry RMI hostname=" + WAN_IP + " (Autorizzata connessione remota)");
        }

        // Bind del registry per la connessione con RMI
        try {
            dbCV = new DatabaseCV(textAreaServerStatus);
            Registry reg = LocateRegistry.createRegistry(REGISTRY_PORT);
            reg.rebind(REGISTRY_NAME, dbCV);
            logMessage("Registry RMI attivo. Porta=" + REGISTRY_PORT + ". Nome=" + REGISTRY_NAME);
        } catch (Exception e) {
            logMessage("ERROR registry: " + e.getMessage());
            e.printStackTrace();
        }

        // Se le variabili d'ambiente sono gia' disponibili mi connetto al database
        if (System.getenv("CV_HOST") != null && System.getenv("CV_USER") != null && System.getenv("CV_PASS") != null) {
            btnAccedi.doClick();
        }
    }

    /**
     * Main.
     * Punto di partenza del programma Server.
     *
     * @param args argomenti in ingresso
     * @throws UnsupportedLookAndFeelException eccezione in caso di problemi con gli oggetti JSwing
     * @throws ClassNotFoundException          eccezione nel caso si cerchi di utilizzare una classe che non esiste
     * @throws InstantiationException          se per qualche motivo la classe non può essere istanziata
     * @throws IllegalAccessException          eccezione in caso di accesso non consentito
     * @throws RemoteException                 eccezione in caso di problemi di connessione usando RMI
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, RemoteException {
        for (String option : args) {
            if (option.toLowerCase().contains("--remote")) {
                ALLOW_REMOTE = true;
                System.err.println("ALLOW_REMOTE = " + ALLOW_REMOTE);
            }
            if (option.toLowerCase().contains("--no-gui")) {
                NO_GUI = true;
                System.err.println("NO_GUI = " + NO_GUI);
            }
        }

        if (NO_GUI) {
            new Server();
        } else {
            JFrame mainServer = new JFrame("Centri Vaccinali - Server");
            try {
                mainServer.setContentPane(new Server().panelServer);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            initUI(mainServer);

            mainServer.pack();
            mainServer.setLocationRelativeTo(null); // Mette la finestra al centro (da richiamare dopo .pack())
            mainServer.setVisible(true);
        }
    }

    /**
     * <code>initUI</code> &egrave; una procedura per inizializzare l'interfaccia
     * utente su una finestra e per finalizzarne le impostazioni.
     * <p>
     * &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     *
     * @param frame &egrave; il frame sul quale applicare le impostazioni
     * @throws ClassNotFoundException          se non trova la classe da caricare
     * @throws UnsupportedLookAndFeelException e le classi look and feel richieste non sono presenti sul sistema
     * @throws InstantiationException          se per qualche motivo la classe non può essere istanziata
     * @throws IllegalAccessException          quando si cerca di effettuare l'accesso ad un campo laddove non &egrave; possibile
     */
    public static void initUI(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Definisce il comportamento della finestra
        ImageIcon imageIcon = new ImageIcon("media/IconServer.png");
        Image image = imageIcon.getImage();
        frame.setIconImage(image);

        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            System.setProperty("apple.awt.brushMetalLook", "true");
            // use the mac system menu bar
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            // set the "About" menu item name
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Centri Vaccinali Server");
            // use smoother fonts
            System.setProperty("apple.awt.antialiasing", "true");
            // ref: http://developer.apple.com/releasenotes/Java/Java142RNTiger/1_NewFeatures/chapter_2_section_3.html
            System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
            // use the system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
    }

    /**
     * <code>logMessage</code> procedura per stampare un messaggio in console e sulla GUI.
     * Questo metodo aggiunge automaticamente la data e l'ora corrente.
     *
     * @param message messaggio da stampare
     */
    public void logMessage(String message) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
        String out = "[" + currentTime + "] " + message + "\n";
        System.err.print(out);
        textAreaServerStatus.append(out);
        textAreaServerStatus.setCaretPosition(textAreaServerStatus.getDocument().getLength()); // autoscroll automatico
    }

    /**
     * <code>connectToDB</code> metodo da eseguire per la connessione al database
     */
    private void connectToDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            logMessage("ERROR: Driver mancante: " + e.getMessage());
        }

        String url = "jdbc:postgresql://" + host + "/" + DB_NAME;

        try {
            conn = DriverManager.getConnection(url, username, password);
            logMessage("Connessione con il database stabilita");
            btnAccedi.setEnabled(false);
            btnDisconnetti.setEnabled(true);
            tfHostDB.setEditable(false);
            tfUsernameDB.setEditable(false);
            tfPasswordDB.setEditable(false);
            dbCV.setConnection(conn);
        } catch (SQLException e) {
            logMessage("ERROR: " + e.getMessage());
        }
    }

    /**
     * <code>disconnectToDB</code> metodo da eseguire per disconnettersi dal database
     */
    private void disconnectToDB() {
        try {
            if (conn != null) {
                conn.close();
                logMessage("Connessione con il database chiusa");
                btnAccedi.setEnabled(true);
                btnDisconnetti.setEnabled(false);
                tfHostDB.setEditable(true);
                tfUsernameDB.setEditable(true);
                tfPasswordDB.setEditable(true);
                dbCV.setConnection(conn);
            }
        } catch (SQLException e) {
            logMessage("ERROR: " + e.getMessage());
        }
    }
}
