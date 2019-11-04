package fr.tamcity.bootstrap;

import java.io.File;

import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ClasspathConstructor;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.explorer.ExploredDirectory;
import fr.theshark34.openlauncherlib.util.explorer.Explorer;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.animation.Animator;

public class TAMCityBootstrap{
	

	public static final String DOMAINE = new String ("http://mdl-anguier.fr/TAMCityLauncher");
	
	static final File TAM_B_DIR = new File(GameDirGenerator.createGameDir("TAMCity"), "Launcher");
	public static final File TAM_B_CRASHES = new File(TAM_B_DIR, "crashes");
	
	

	
	private static Thread updateThread;	
	private static CrashReporter crashReporter = new CrashReporter("TAMCity Bootstrap", TAM_B_CRASHES);

	

public static void updateLauncher() throws Exception {
		SUpdate su = new SUpdate(DOMAINE + "/bootstrap/", TAM_B_DIR);
		if(BootstrapPanel.betaActive.isSelected()) {
			su = new SUpdate(DOMAINE + "/bootstrap_beta/", TAM_B_DIR);
		}
		su.addApplication(new FileDeleter());
		su.getServerRequester().setRewriteEnabled(true);
		
		
		updateThread = new Thread() {
			
			private int val;
			private int max;
			
			@Override
			public void run() {
				
				while(!this.isInterrupted()) {
					
					
					
					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);
					
					BootstrapFrame.getInstance().getLauncherPanel().getPrograssBar().setMaximum(max);
					BootstrapFrame.getInstance().getLauncherPanel().getPrograssBar().setValue(val);
					
				}
			}
		};
		updateThread.start();
		su.start();
		updateThread.interrupt();
		
	}

static void launch() throws LaunchException {
	
	
	
	
	ClasspathConstructor constructor = new ClasspathConstructor();
	ExploredDirectory gameDir = Explorer.dir(TAM_B_DIR);
	constructor.add(gameDir.sub("Libs").allRecursive().files().match("^(.*\\.((jar)$))*$"));
	constructor.add(gameDir.get("launcher.jar"));
	
	
	ExternalLaunchProfile profile = new ExternalLaunchProfile("fr.tamcity.launcher.LauncherFrame", constructor.make());
	ExternalLauncher launcher = new ExternalLauncher(profile);

	Process b = launcher.launch();
	BootstrapFrame.getInstance().setVisible(false);

	try {
		b.waitFor();
	} catch (InterruptedException ignored) {
	
	}
	
	Animator.fadeOutFrame(BootstrapFrame.getInstance(), 5,new Runnable() {
		
		public void run() {
			System.exit(0);
			
		}
	});
	
}
	
	

public static void interruptThread() {
	updateThread.interrupt();
}
	
	
public static CrashReporter getCrashReporter(){
	return crashReporter;
}	
	

	

}
