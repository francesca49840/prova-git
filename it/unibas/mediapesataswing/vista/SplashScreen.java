package it.unibas.mediapesataswing.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import org.slf4j.LoggerFactory;

public class SplashScreen extends JWindow {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SplashScreen.class.getName());
    
    public SplashScreen(ImageIcon image, JFrame frame, int durata) {
        super(frame);
        JLabel immagine = new JLabel(image);
        this.getContentPane().add(immagine, BorderLayout.CENTER);
        this.pack();
        this.setLocation(getLocation(frame, immagine.getPreferredSize()));
        aggiungiMouseListenerChiusura(frame);
        aggiungiThreadChiusura(frame, durata);
    }
    
    private void aggiungiMouseListenerChiusura(final JFrame frame) {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setVisible(false);
                dispose();
                frame.setVisible(true);
            }
        });
    }
    
    private void aggiungiThreadChiusura(final JFrame frame, final int durata) {
        final Runnable threadChiusura = new Runnable() {
            public void run() {
                setVisible(false);
                dispose();
                frame.setVisible(true);
            }
        };
        Thread threadSplash = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(durata);
                    SwingUtilities.invokeAndWait(threadChiusura);
                } catch(InterruptedException e) {
                    logger.warn(e.toString());
                } catch (InvocationTargetException e) {
                    logger.warn(e.toString());
                }
            }
        });
        threadSplash.start();
    }
    
    public static Point getLocation(Window finestra, Dimension dimensioneFinestra) {
        Dimension dimensioneSchermo = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((dimensioneSchermo.width / 2) - (dimensioneFinestra.width / 2),
                (dimensioneSchermo.height / 2) - (dimensioneFinestra.height / 2));
    }
}
