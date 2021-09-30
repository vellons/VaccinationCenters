package serverCV;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server {
    private JLabel lblUsername;
    private JTextField tfUsernameDB;
    private JLabel lblPassword;
    private JPasswordField tfPasswordDB;
    private JButton btnAccedi;
    private JButton btnChiudi;
    private JLabel lbHost;
    private JTextArea textAreaServerStatus;
    private JPanel panelServer;
    private JTextField tfHostDB;

    private final static String DB_TABLE_NAME = "wewhpzry";
    private String username;
    private String password;
    private String host;
    private Connection conn;

    public Server() {

        btnChiudi.setEnabled(false);

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
            System.out.println(host + "\n" + password + "\n" + username);

            tryConnectionToDB();

            btnAccedi.setEnabled(false);
            btnChiudi.setEnabled(true);
        });

        btnChiudi.addActionListener(e -> {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Connessione chiusa");
                    textAreaServerStatus.selectAll();
                    textAreaServerStatus.replaceSelection("");
                }

            } catch (SQLException throwable) {
                System.out.println(throwable.getMessage());
            }
            btnAccedi.setEnabled(true);
            btnChiudi.setEnabled(false);
        });
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JFrame mainServer = new JFrame("Centri Vaccinali Server");

        mainServer.setContentPane(new Server().panelServer);
        initUI(mainServer);
        System.out.println(System.getenv("CV_HOST"));

        mainServer.pack();
        mainServer.setLocationRelativeTo(null); // Mette la finestra al centro (da richiamare dopo .pack())
        mainServer.setVisible(true);
    }

    public static void initUI(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Definisce il comportamento della finestra

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

    private void tryConnectionToDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        String url = "jdbc:postgresql://" + host + "/" + DB_TABLE_NAME;
        System.out.println(url);

        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connessione col DB remoto stabilita");
            textAreaServerStatus.setText("[" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss")) + "] "
                    + "Connessione col DB remoto stabilita\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
// Queste impostazioni vengo applicate al frame passato
        /* ImageIcon imageIcon = new ImageIcon("media/EatAdvisorIcon.png");
        Image image = imageIcon.getImage();
        frame.setIconImage(image);*/