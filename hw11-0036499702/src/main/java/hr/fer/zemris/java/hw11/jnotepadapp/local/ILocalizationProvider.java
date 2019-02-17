package hr.fer.zemris.java.hw11.jnotepadapp.local;

/**
 * Interface that specifies basic methods for localization providers.
 * @author Alex
 *
 */
public interface ILocalizationProvider {
	
	/**
	 * Adds a localization listener to list of listeners.
	 * @param l {@link ILocalizationListener}.
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Removes localization listener from list of listeners.
	 * @param l {@link ILocalizationListener}.
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Gets text associated with given key in resource bundle of current language.
	 * @param key string 
	 * @return string text in current language.
	 */
	String getString(String key);

}
