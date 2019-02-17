package hr.fer.zemris.java.hw11.jnotepadapp.local;

import javax.swing.JMenu;

/**
 * Implementation of {@link JMenu} that is synchronized with language changes.
 * @author Alex
 *
 */
public class LocalizableJMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	/**
	 * Key of this component used in resource bundles.
	 */
	private String key;
	
	/**
	 * Constructor.
	 * @param key string
	 * @param lp {@link ILocalizationProvider}.
	 */
	public LocalizableJMenu(String key, ILocalizationProvider lp) {
		super(lp.getString(key));
		this.key = key;
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(lp.getString(key));
			}
		});
	}

}
