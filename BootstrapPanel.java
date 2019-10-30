package fr.tamcity.bootstrap;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

@SuppressWarnings("serial")
public class BootstrapPanel extends JPanel implements SwingerEventListener, ActionListener
{
    private Image background;
    private STexturedButton playButton;
    static JCheckBox betaActive;
    private Saver saver;
    private SColoredBar progressBar;
    private JLabel versionLabel;
    private JFrame updateError;
    
    static {
        BootstrapPanel.betaActive = new JCheckBox("b\u00e9ta");
    }
    
    public BootstrapPanel() {
        this.background = Swinger.getResource("splash.png");
        this.playButton = new STexturedButton(Swinger.getResource("playbutton.png"));
        this.saver = new Saver(new File(TAMCityBootstrap.TAM_B_DIR, "bootstrap.properties"));
        this.progressBar = new SColoredBar(Swinger.getTransparentWhite(100), Swinger.getTransparentWhite(175));
        this.versionLabel = new JLabel("v1.0.8");
        this.updateError = new JFrame();
        this.setLayout(null);
        this.playButton.setBounds(155, 161);
        this.playButton.addEventListener((SwingerEventListener)this);
        this.add((Component)this.playButton);
        this.progressBar.setBounds(0, 220, 416, 13);
        this.add((Component)this.progressBar);
        this.versionLabel.setForeground(Color.WHITE);
        this.versionLabel.setBounds(5, 205, 416, 13);
        this.add(this.versionLabel);
        BootstrapPanel.betaActive.setBounds(5, 5, 60, 20);
        BootstrapPanel.betaActive.setOpaque(false);
        BootstrapPanel.betaActive.setForeground(Color.WHITE);
        BootstrapPanel.betaActive.addActionListener(this);
        this.add(BootstrapPanel.betaActive);
        this.beta();
    }
    
    @SuppressWarnings("deprecation")
	public void onEvent(final SwingerEvent ex) {
        if (ex.getSource() == this.playButton) {
            System.out.println("jouer");
            this.playButton.disable();
            final Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        TAMCityBootstrap.updateLauncher();
                    }
                    catch (Exception e2) {
                        BootstrapPanel.this.updateError.setAlwaysOnTop(true);
                        JOptionPane.showMessageDialog(BootstrapPanel.this.updateError, "Impossible de se connecter au serveur de mise \u00e0 jour!\nVous n'aurez donc pas les derni\u00e8res mises \u00e0 jour du launcher!", "Impossible de mettre \u00e0 jour !", 2);
                    }
                    try {
                        TAMCityBootstrap.launch();
                    }
                    catch (LaunchException e) {
                        TAMCityBootstrap.getCrashReporter().catchError((Exception)e, "Impossible de lancer le jeu");
                    }
                }
            };
            t.start();
        }
    }
    
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        Swinger.drawFullsizedImage(graphics, (JComponent)this, this.background);
    }
    
    public SColoredBar getPrograssBar() {
        return this.progressBar;
    }
    
    public void beta() {
        try {
            if (this.saver.get("beta").equals("true")) {
                BootstrapPanel.betaActive.setSelected(true);
            }
            else {
                BootstrapPanel.betaActive.setSelected(false);
            }
        }
        catch (Exception errorRemind) {
            System.out.println("Erreur beta non reconnu");
        }
    }
    
    public void actionPerformed(final ActionEvent e) {
        if (BootstrapPanel.betaActive.isSelected()) {
            this.saver.set("beta", "true");
        }
        else if (!BootstrapPanel.betaActive.isSelected()) {
            this.saver.set("beta", "false");
        }
    }
}