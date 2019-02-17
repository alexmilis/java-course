package hr.fer.zemris.java.hw11.jnotepadapp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Implementation of {@link Action} that is synchronized with language changes.
 * @author Alex
 *
 */
public class LocalizableAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	/**
	 * Key of this action used in resource bundles.
	 */
	private String key;

	/**
	 * Constructor of this class.
	 * @param key string
	 * @param lp {@link ILocalizationProvider}.
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		putValue(Action.NAME, lp.getString(key));
		putValue(Action.SHORT_DESCRIPTION, lp.getString(key + "Description"));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(AbstractAction.NAME, lp.getString(key));
				putValue(AbstractAction.SHORT_DESCRIPTION, lp.getString(key + "Description"));
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
