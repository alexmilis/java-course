package ljir;

import java.awt.Component;
import java.util.List;

public class TabComponent extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Pie pie;
	
	private List<Artikl> artikli;

	public TabComponent(List<Artikl> artikli) {
		super();
		this.pie = new Pie();
		this.artikli = artikli;
	}
	
	class Pie{
		
	}

}
