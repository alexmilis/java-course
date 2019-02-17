package hr.fer.zemris.java.hw11.jnotepadapp.local;

import javax.swing.JLabel;

/**
 * Implementation of {@link JLabel} that is synchronized with language changes.
 * @author Alex
 *
 */
public class LocalizableJLabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	/**
	 * Key of this label used in resource bundles.
	 */
	private String key;
	
	/**
	 * Name of this command depending on current language.
	 */
	private String labelName;
	
	/**
	 * Constructor.
	 * @param key string
	 * @param lp {@link ILocalizationProvider}.
	 */
	public LocalizableJLabel(String key, ILocalizationProvider lp) {
		this.key = key;
		this.labelName = lp.getString(key);
		setText(labelName);
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String number = getText().substring(labelName.length());
				labelName = lp.getString(key);
				setText(labelName + number);
			}
		});
	}
	
	/**
	 * Gets name of label (changes with language).
	 * @return
	 */
	public String getLabelName() {
		return labelName;
	}

}
