package fr.tamcity.bootstrap;

import java.awt.Window;

import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;

@SuppressWarnings("serial")
public class BootstrapFrame extends JFrame
{
    private static BootstrapFrame instance;
    private BootstrapPanel bootstrapPanel;
    private static CrashReporter crashReporter;
    
    public BootstrapFrame() {
        this.setTitle("TAMCity Bootstrap");
        this.setAlwaysOnTop(true);
        this.setSize(416, 233);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setIconImage(Swinger.getResource("icon.png"));
        this.setContentPane(this.bootstrapPanel = new BootstrapPanel());
        AWTUtilities.setWindowOpacity((Window)this, 0.0f);
        this.setVisible(true);
        Animator.fadeInFrame((Window)this, 2);
    }
    
    public static void main(final String[] args) {
        Swinger.setSystemLookNFeel();
        Swinger.setResourcePath("/fr/tamcity/bootstrap/resources/");
        TAMCityBootstrap.TAM_B_CRASHES.mkdir();
        BootstrapFrame.crashReporter = new CrashReporter("TAMCity Bootstrap", TAMCityBootstrap.TAM_B_CRASHES);
        BootstrapFrame.instance = new BootstrapFrame();
    }
    
    public static BootstrapFrame getInstance() {
        return BootstrapFrame.instance;
    }
    
    public static CrashReporter getCrashReporter() {
        return BootstrapFrame.crashReporter;
    }
    
    public BootstrapPanel getLauncherPanel() {
        return this.bootstrapPanel;
    }
}