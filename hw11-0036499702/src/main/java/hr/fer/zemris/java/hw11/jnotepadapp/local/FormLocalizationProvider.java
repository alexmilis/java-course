package hr.fer.zemris.java.hw11.jnotepadapp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import hr.fer.zemris.java.hw11.jnotepadapp.JNotepadPP;

/**
 * Implementation of {@link LocalizationProviderBridge} that adds a listener to {@link JNotepadPP}.
 * Its purpose is to stop memory leakage.
 * @author Alex
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Constructor.
	 * @param lp {@link ILocalizationProvider}
	 * @param frame JFrame to which listener should be added.
	 */
	public FormLocalizationProvider(ILocalizationProvider lp, JFrame frame) {
		super(lp);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}
	
	public String getCurrentLanguage() {
		return ((LocalizationProvider) parent).getCurrentLanguage();
	}

}
