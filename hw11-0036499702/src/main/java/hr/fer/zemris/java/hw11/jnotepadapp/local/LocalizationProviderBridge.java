package hr.fer.zemris.java.hw11.jnotepadapp.local;
/**
 * Implementation of {@link AbstractLocalizationProvider} that is a decorator 
 * for an implementation of {@link ILocalizationProvider}.
 * It manages connection between {@link ILocalizationProvider} and its listener {@link ILocalizationListener}.
 * @author Alex
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	/**
	 * True if provider and listener are connected.
	 */
	private boolean connected;
	
	/**
	 * Localization provider.
	 */
	protected ILocalizationProvider parent;
	
	/**
	 * Localization listener.
	 */
	private ILocalizationListener listener;
	
	/**
	 * Constructor.
	 * @param lp {@link ILocalizationProvider}
	 */
	public LocalizationProviderBridge(ILocalizationProvider lp) {
		this.connected = false;
		this.parent = lp;
		this.listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	/**
	 * Disconnects provider and listener.
	 */
	public void disconnect() {
		if(connected) {
			parent.removeLocalizationListener(listener);
		}
	}
	
	/**
	 * Connects provider and listener.
	 */
	public void connect() {
		if(!connected) {
			parent.addLocalizationListener(listener);
		}
	}
	
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}
	
}
