package hr.fer.zemris.java.hw11.jnotepadapp.local;

/**
 * Functional interface of localization listeners.
 * Observes {@link LocalizationProvider}.
 * @author Alex
 *
 */
public interface ILocalizationListener {

	void localizationChanged();
	
}
