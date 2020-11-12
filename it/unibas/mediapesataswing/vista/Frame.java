package it.unibas.mediapesataswing.vista;

import it.unibas.mediapesataswing.Applicazione;
import it.unibas.mediapesataswing.Costanti;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frame extends javax.swing.JFrame {

    private static final Logger logger = LoggerFactory.getLogger(Frame.class.getName());

    static {
        selezionaLookAndFeel();
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
    }

    private static void selezionaLookAndFeel() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Eccezione nella selezione del look and feel: " + e);
        }
    }

    private final JFileChooser fileChooser = new JFileChooser();

    public JFileChooser getFileChooser() {
        return this.fileChooser;
    }

    /* *******************************************
     *       Inizializzazione del frame
     * ********************************************/
    public void inizializza() {
        Applicazione applicazione = Applicazione.getInstance();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(applicazione.getResourceManager().getStringResource(Costanti.STRINGATITOLO));
        this.fileChooser.setFileFilter(new TxtFileFilter());
        creaMenu();
        ((JPanel) this.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        VistaPrincipale vistaPrincipale = applicazione.getVistaPrincipale();
        JScrollPane scrollPane = new JScrollPane(vistaPrincipale);
        this.getContentPane().add(scrollPane);
        this.pack();
        this.setLocationRelativeTo(null);
        eseguiSplashScreen();
//        this.setVisible(true);
    }

    private void eseguiSplashScreen() {
        Applicazione applicazione = Applicazione.getInstance();
        String fileSplashScreen = "/risorse/" +applicazione.getResourceManager().getStringResource(Costanti.STRINGAFILESPLASHSCREEN);
        ImageIcon immagineSplash = applicazione.getResourceManager().getImageResource(fileSplashScreen);
        SplashScreen splashScreen = new SplashScreen(immagineSplash, this, Costanti.DURATA_SPLASH_SCREEN);
        splashScreen.setVisible(true);
    }

    /* *******************************************
     *       Gestione menu
     * ********************************************/
    private void creaMenu() {
        JMenuBar barraMenu = new JMenuBar();
        this.setJMenuBar(barraMenu);
        creaMenuFile(barraMenu);
        creaMenuModifica(barraMenu);
        creaMenuHelp(barraMenu);
    }

    private void creaMenuFile(javax.swing.JMenuBar barraMenu) {
        Applicazione applicazione = Applicazione.getInstance();
        JMenu menuFile = new JMenu(applicazione.getResourceManager().getStringResource(Costanti.STRINGAMENUFILE));
        menuFile.setMnemonic(java.awt.event.KeyEvent.VK_F);
        barraMenu.add(menuFile);
        JMenuItem voceNuovo = new JMenuItem(applicazione.getControlloMenu().getAzioneNuovo());
        JMenuItem voceApri = new JMenuItem(applicazione.getControlloMenu().getAzioneApri());
        JMenuItem voceSalva = new JMenuItem(applicazione.getControlloMenu().getAzioneSalva());
        JMenuItem voceApriExcel = new JMenuItem(applicazione.getControlloMenu().getAzioneApriExcel());
        JMenuItem voceApriExcelNoThread = new JMenuItem(applicazione.getControlloMenu().getAzioneApriExcelNoThread());
        JMenuItem voceEsci = new JMenuItem(applicazione.getControlloMenu().getAzioneEsci());
        menuFile.add(voceNuovo);
        menuFile.addSeparator();
        menuFile.add(voceApri);
        menuFile.add(voceSalva);
        menuFile.addSeparator();
        menuFile.add(voceApriExcel);
        menuFile.add(voceApriExcelNoThread);
        menuFile.addSeparator();
        menuFile.add(voceEsci);
    }

    private void creaMenuModifica(javax.swing.JMenuBar barraMenu) {
        Applicazione applicazione = Applicazione.getInstance();
        JMenu menuModifica = new JMenu(applicazione.getResourceManager().getStringResource(Costanti.STRINGAMENUMODIFICA));
        menuModifica.setMnemonic(java.awt.event.KeyEvent.VK_M);
        barraMenu.add(menuModifica);
        JMenuItem voceModificaDatiStudente = new JMenuItem(applicazione.getControlloPrincipale().getAzioneFinestraModificaStudente());
        JMenuItem voceInserisciEsame = new JMenuItem(applicazione.getControlloPrincipale().getAzioneInserisciEsame());
        JMenuItem voceModificaEsame = new JMenuItem(applicazione.getControlloPrincipale().getAzioneFinestraModificaEsame());
        JMenuItem voceEliminaEsame = new JMenuItem(applicazione.getControlloPrincipale().getAzioneEliminaEsame());
        menuModifica.add(voceModificaDatiStudente);
        menuModifica.addSeparator();
        menuModifica.add(voceInserisciEsame);
        menuModifica.add(voceModificaEsame);
        menuModifica.add(voceEliminaEsame);
    }

    private void creaMenuHelp(javax.swing.JMenuBar barraMenu) {
        JMenu menuHelp = new javax.swing.JMenu("?");
        menuHelp.setMnemonic(java.awt.event.KeyEvent.VK_H);
        barraMenu.add(menuHelp);
        JMenuItem voceAbout = new javax.swing.JMenuItem(Applicazione.getInstance().getControlloMenu().getAzioneFinestraInformazioni());
        menuHelp.add(voceAbout);
    }

    /* *******************************************
     *              Finestre di Dialogo
     * ********************************************/
    public void finestraAbout() {
        Applicazione applicazione = Applicazione.getInstance();
        JOptionPane.showMessageDialog(this, applicazione.getResourceManager().getStringResource(Costanti.STRINGAMESSAGGIOINFORMAZIONI));
    }

    public void finestraErrore(String messaggio) {
        Applicazione applicazione = Applicazione.getInstance();
        JOptionPane.showMessageDialog(this, messaggio, applicazione.getResourceManager().getStringResource(Costanti.STRINGAERRORE), JOptionPane.ERROR_MESSAGE);
    }

    private class TxtFileFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File pathname) {
            if (pathname.isDirectory()) {
                return true;
            }
            return pathname.toString().endsWith(Costanti.ESTENSIONE);
        }

        public String getDescription() {
            return "*" + Costanti.ESTENSIONE;
        }
    }

}
