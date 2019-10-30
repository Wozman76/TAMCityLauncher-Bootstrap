package fr.tamcity.bootstrap;

import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
//test

@SuppressWarnings("serial")
public class BootstrapFrame extends JFrame{

	
	private static BootstrapFrameinstance;
	private BootstrapPanel bootstrapPanel;
	private static CrashReporter crashReporter;
	
	
	public BootstrapFrame() {
		this.setTitle("TAMCity Bootstrap");
		this.setAlwaysOnTop(true);
		this.setSize(416, 233);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setIconImage(Swinger.getResource("icon.png"));
		this.setContentPane(bootstrapPanel = new BootstrapPanel());
		AWTUtilities.setWindowOpacity(this, 0.0F);
		
		
		
		this.setVisible(true);
		
		Animator.fadeInFrame(this, 2);
		
	}
	public static void main(String[] args) {
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/fr/tamcity/bootstrap/resources/");
		TAMCityBootstrap.TAM_B_CRASHES.mkdir();
		crashReporter = new CrashReporter("TAMCity Bootstrap", TAMCityBootstrap.TAM_B_CRASHES);
		
		instance = new BootstrapFrame();
	}

	public static BootstrapFrame getInstance() {
		return instance;
	}
	
	
	public static CrashReporter getCrashReporter() {
		return crashReporter;
	}
	
	
	public BootstrapPanel getLauncherPanel() {
		return this.bootstrapPanel;
	}
	
	
}
