package hr.fer.zemris.java.hw11.jnotepadapp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Implementation of {@link ILocalizationProvider} that is a singleton.
 * It ensures that at all times in the system there is only one instance of this class.
 * Default language is english.
 * @author Alex
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	
	/**
	 * Current language.
	 */
	private String language;
	
	/**
	 * Bundle of current language.
	 */
	private ResourceBundle bundle;
	
	/**
	 * Only instance of this class.
	 */
	private static LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Default language - english.
	 */
	private final static String DEFAULT_LANGUAGE = "en";
	
	/**
	 * Private constructor, sets default language.
	 */
	private LocalizationProvider() {
		this.language = DEFAULT_LANGUAGE;
		this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadapp.local.languages",
				Locale.forLanguageTag(language));
	}
	
	/**
	 * Gets instance of this class.
	 * @return localization provider.
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * Sets new language.
	 * @param language string
	 */
	public void setLanguage(String language) {
		if(!language.equals(this.language)) {
			this.language = language;
			
			this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadapp.local.languages",
					Locale.forLanguageTag(language));
		
			fire();
		}
	}
	
	/**
	 * Gets current language.
	 * @return
	 */
	public String getCurrentLanguage() {
		return language;
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
}
