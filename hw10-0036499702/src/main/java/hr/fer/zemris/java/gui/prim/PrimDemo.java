package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Creates a window with two displays of same list of prime numbers.
 * It has a button that generates new prime numbers.
 * @author Alex
 *
 */
public class PrimDemo extends JFrame {
	
	/**
	 * List model for this class.
	 */
	private PrimListModel<Integer> model;
	
	/**
	 * Constructor.
	 */
	public PrimDemo() {
		this.model = new PrimListModel<>();
		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		setLayout(new BorderLayout());
		
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(1, 2));
		
		JList<Integer> list1 = new JList<>(model);
		pane.add(new JScrollPane(list1), BorderLayout.LINE_START);

		JList<Integer> list2 = new JList<>(model);
		pane.add(new JScrollPane(list2), BorderLayout.LINE_END);
		
		add(pane, BorderLayout.CENTER);
		
		JButton button = new JButton("Next");
		button.addActionListener(a -> {
			model.next();
		});
		
		add(button, BorderLayout.PAGE_END);
		setSize(getPreferredSize());
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Main method.
	 * @param args not needed here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			PrimDemo prim = new PrimDemo();
			prim.pack();
			prim.setVisible(true);
		});
	}
	
	/**
	 * Implementation of {@link ListModel} that works with prime numbers.
	 * @author Alex
	 *
	 * @param <T>
	 */
	protected class PrimListModel<T> implements ListModel<T> {
		
		/**
		 * List of prime numbers. Starts with value 1.
		 */
		private List<Integer> primes;
		
		/**
		 * List of data listeners.
		 */
		private List<ListDataListener> listeners;
		
		/**
		 * Constructor.
		 */
		public PrimListModel() {
			super();
			primes = new ArrayList<>();
			primes.add(Integer.valueOf(1));
			
			this.listeners = new LinkedList<>();
		}

		/**
		 * Method for generating next prime number.
		 */
		public void next() {
			Thread t = new Thread(() -> {
				int current = primes.get(primes.size() - 1) + 1;
				while(true) {
					if(isPrime(current)) {
						break;
					}
					current++;
				}
				primes.add(Integer.valueOf(current));
			});
			t.start();
			
			try {
				t.join();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(PrimDemo.this, "Program was interrupted.", 
						"Error", JOptionPane.ERROR_MESSAGE);;
			}
			
			int index = primes.size() - 1;
			
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index);
			for(ListDataListener l : listeners) {
				l.intervalAdded(event);
			}
		}
		
		@Override
		public int getSize() {
			return primes.size();
		}

		@SuppressWarnings("unchecked")
		@Override
		public T getElementAt(int index) {
			return (T) primes.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			listeners.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
		}
		
		/**
		 * Method that checks if number is prime.
		 * @param n number to be checked
		 * @return true is n is prime
		 */
		private boolean isPrime(int n) {
			for(int i = 2; i*i <= n; i++) {
				if(n % i == 0) {
					return false;
				}
			}
			return true;
		}
		
	}

	/**
	 * Getter for prim list model.
	 * @return {@link PrimListModel}
	 */
	public PrimListModel<Integer> getModel() {
		return model;
	}
	
}
