package hr.fer.zemris.java.hw11.jnotepadapp.local;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract implementation of {@link ILocalizationProvider}.
 * Implements addition and removal of listeners.
 * Provides method that notifies listeners.
 * @author Alex
 *
 */
public class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * List of listeners.
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * Constructor.
	 */
	public AbstractLocalizationProvider() {
		this.listeners = new LinkedList<>();
	}
	

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Notifies listeners.
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}


	@Override
	public String getString(String key) {
		return null;
	}

}
