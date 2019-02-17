package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Special kind of map that stores pairs (name, stack). Stacks are realized by single linked lists
 * of inner static class {@link MultistackEntry}.
 * @author Alex
 *
 */
public class ObjectMultistack {
	
	/**
	 * Map that stores pairs (name, stack).
	 */
	private Map<String, MultistackEntry> dict;
	
	/**
	 * Constructor of ObjectMultistack.
	 */
	public ObjectMultistack() {
		this.dict = new HashMap<>();
	}

	/**
	 * Pushes new value to the stack that is stored under key name.
	 * @param name key under which stack is stored.
	 * @param valueWrapper value to be pushed on stack.
	 * @throws NullPointerException if name or valueWrapper is null.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(valueWrapper);
		MultistackEntry stack = dict.get(name);
		if(stack == null) {
			dict.put(name, new MultistackEntry(valueWrapper, null));
			return;
		}
		
		MultistackEntry newEntry = new MultistackEntry(valueWrapper, stack);
		dict.put(name, newEntry);
	}
	
	/**
	 * Pops value from the top of the stack that is stored under name.
	 * @param name key under which stack is stored in map.
	 * @return valueWrapper from stack specified by name.
	 * @throws NullPointerException if name is null.
	 * @throws EmptyStackException if stack under name is empty.
	 */
	public ValueWrapper pop(String name) {
		Objects.requireNonNull(name);
		if(isEmpty(name)) {
			throw new EmptyStackException();
		}
		
		MultistackEntry stack = dict.get(name);
		if(stack != null) {
			if(stack.next != null) {
				dict.put(name, stack.next);
			}
		}
		
		return stack == null? null : stack.value;
	}
	
	/**
	 * Gets the value from the top of the stack without removing it.
	 * @param name key under which stack is stored in map.
	 * @return valueWrapper from the top of the specified stack.
	 * @throws NullPointerException if name is null.
	 * @throws EmptyStackException if stack under name is empty.
	 */
	public ValueWrapper peek(String name) {
		Objects.requireNonNull(name);
		if(isEmpty(name)) {
			throw new EmptyStackException();
		}
		
		MultistackEntry stack = dict.get(name);
		
		return stack == null? null : stack.value;
	}
	
	/**
	 * Checks if specified stack is empty.
	 * @param name key under which stack is stored in map.
	 * @return true if specified stack is empty.
	 */
	public boolean isEmpty(String name) {
		MultistackEntry stack = dict.get(name);
		return stack == null;
	}
	
	/**
	 * Node of single linked list used to implement stacks in map.
	 * @author Alex
	 *
	 */
	private static class MultistackEntry {
		
		/**
		 * {@link ValueWrapper}.
		 */
		private ValueWrapper value;
		
		/**
		 * Pointer to the next node.
		 */
		private MultistackEntry next;
		
		/**
		 * Constructor of MultistackEntry.
		 * @param value valueWrapper to be stored.
		 * @param next pointer to the next node.
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			super();
			this.value = value;
			this.next = next;
		}
	}

}
