package fr.tamcity.bootstrap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JCheckBox;
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
public class BootstrapPanel extends JPanel implements SwingerEventListener, ActionListener{
	
	private Image background = Swinger.getResource("splash.png");
	private STexturedButton playButton = new STexturedButton(Swinger.getResource("playbutton.png"));
	static JCheckBox betaActive = new JCheckBox("béta");

	private Saver saver = new Saver(new File(TAMCityBootstrap.TAM_B_DIR, "bootstrap.properties"));
	
	private SColoredBar progressBar = new SColoredBar(Swinger.getTransparentWhite(100), Swinger.getTransparentWhite(175));

	private JLabel versionLabel = new JLabel("v1.0.10");

	private JFrame updateError = new JFrame();
	
	public BootstrapPanel(){
		this.setLayout(null);
		


		playButton.setBounds(155, 161); //155,161,114,54
		playButton.addEventListener(this);
		this.add(playButton);
		
		progressBar.setBounds(0, 220, 416, 13);
		this.add(progressBar);
		
		versionLabel.setForeground(Color.WHITE);
		versionLabel.setBounds(5, 205, 416, 13);
		this.add(versionLabel);
		
		betaActive.setBounds(5,5,60,20);
		betaActive.setOpaque(false);
		betaActive.setForeground(Color.WHITE);
		betaActive.addActionListener(this);
		this.add(betaActive);
		beta();
		
	}
	
	
	
	
	
	
	
	
	

	@SuppressWarnings("deprecation")
	public void onEvent(SwingerEvent ex) {

		if(ex.getSource() == playButton){
			System.out.println("jouer");
			playButton.disable();
			Thread t = new Thread(){
				public void run(){
					
					try {
						TAMCityBootstrap.updateLauncher();
					} catch (Exception e) {
						updateError.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(updateError,
						    "Impossible de se connecter au serveur de mise à jour!\n"
						    	+ "Vous n'aurez donc pas les dernières mises à jour du launcher!",
						    "Impossible de mettre à jour !",
						    JOptionPane.WARNING_MESSAGE);
						
						
					}
					
					
					
					
					try {
						TAMCityBootstrap.launch();
					} catch (LaunchException e) {
							TAMCityBootstrap.getCrashReporter().catchError(e, "Impossible de lancer le jeu");
					}
					

				}
			};
			t.start();
			
			
			

		}
		
		
	}
	
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		Swinger.drawFullsizedImage(graphics, this, background);
		
	}
	

	
	public SColoredBar getPrograssBar() {
		return progressBar;
	}




	public void beta(){
		try{
			if(saver.get("beta").equals("true")){
				betaActive.setSelected(true);

			}else{
				betaActive.setSelected(false);
			}
			
		} catch (Exception errorRemind){
			System.out.println("Erreur beta non reconnu");
		}
		
	}





	public void actionPerformed(ActionEvent e) {
		if(betaActive.isSelected()){
			saver.set("beta", "true");
		} else if(!betaActive.isSelected()){
			saver.set("beta", "false");

		}
	}
	
	
}
