package hr.fer.zemris.java.hw07.shell.commands.massrename;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.commands.MassrenameCommand;

/**
 * Parser for expression that determines new name/name of copy in command {@link MassrenameCommand}.
 * @author Alex
 *
 */
public class NameBuilderParser {
	
	/**
	 * Data to be parsed.
	 */
	private char[] data;
	
	/**
	 * Current index.
	 */
	private int current;
	
	/**
	 * List of {@link NameBuilder} that are needed to make final NameBuilder.
	 */
	List<NameBuilder> builders;
	
	/**
	 * State of parser, true -> in tag.
	 */
	private boolean state;
	
	/**
	 * If a change of state occured in previous iteration, changed = true.
	 */
	private boolean changed;
	
	/**
	 * Constructor of {@link NameBuilderParser}.
	 * @param expression expression to be parsed.
	 * @throws IllegalArgumentException if tag is not closed.
	 */
	public NameBuilderParser(String expression) {
		this.data = expression.toCharArray();
		this.current = 0;
		this.state = false;
		this.changed = false;
		this.builders = new ArrayList<>();
		parse();
	}
	
	/**
	 * Constructs complex NameBuilder from list builders.
	 * @return NameBuilder
	 */
	public NameBuilder getNameBuilder() {
		return new NameBuilder() {
			
			List<NameBuilder> nameBuilders = builders;

			@Override
			public void execute(NameBuilderInfo info) {
				nameBuilders.forEach(builder -> builder.execute(info));
			}
			
		};
	}
	
	/**
	 * Parses data and creates {@link NameBuilder} that are stored in list builders.
	 * @throws IllegalArgumentException if tag is not closed.
	 */
	private void parse() {
		
		String word = "";
		
		while(current < data.length) {
			if(changed) {
				if(!state) {
					String parts[] = word.split(",");
					String description = "";
					if(parts.length == 2) {
						description = parts[1];
					} else if(parts.length != 1) {
						throw new IllegalArgumentException("Invalid argument for parsing: " + word);
					}
					builders.add(new NameBuilderGroup(parts[0].trim(), description.trim()));
					
				} else {
					builders.add(new NameBuilderWord(word));
				}
				word = "";
				changed = false;
			} else {
				if(state) {
					if(data[current] == '}') {
						current++;
						changed = true;
						state = false;
					} else {
						word += data[current++];
					}
				} else {
					if(data[current] == '$') {
						if(current + 1 < data.length) {
							if(data[current + 1] == '{') {
								state = true;
								changed = true;
								current += 2;
							}
						}
					} else {
						word += data[current++];
					}
				}
			}
		}
		
		if(state) {
			throw new IllegalArgumentException("Supstitutional command not finished (${ } not closed.");
		} else if(!word.isEmpty()) {
			builders.add(new NameBuilderWord(word));
		}
	}
	
	
	/**
	 * Implemetation of {@link NameBuilder} that adds a word to stringbuilder.
	 * @author Alex
	 *
	 */
	private class NameBuilderWord implements NameBuilder {
		/**
		 * Word to be added.
		 */
		private String word;

		/**
		 * Constuctor.
		 * @param word
		 */
		private NameBuilderWord(String word) {
			this.word = word;
		}

		@Override
		public void execute(NameBuilderInfo info) {
			info.getStringBuilder().append(word);
		}
	}
	
	/**
	 * Implementation of {@link NameBuilder} that adds a group to stringbuilder.
	 * @author Alex
	 *
	 */
	private class NameBuilderGroup implements NameBuilder {
		/**
		 * Index of group to be added to stringbuilder.
		 */
		private int groupNo;
		
		/**
		 * String format for group.
		 */
		private String description;
		
		/**
		 * Help char for formatting strings.
		 */
		private char zero = ' ';
		
		/**
		 * Constructor.
		 * @param groupNo index of group.
		 * @param description format.
		 */
		private NameBuilderGroup(String groupNo, String description) {
			super();
			this.groupNo = Integer.parseInt(groupNo);
			if(this.groupNo < 0) {
				throw new IllegalArgumentException("Index of group cannot be negative: " + groupNo);
			}
			if(!description.isEmpty()) {
				this.description = "%" + Integer.parseInt(description) + "s";
				if(description.startsWith("0")) {
					this.zero = '0';
				}
			}
		}

		@Override
		public void execute(NameBuilderInfo info) {
			if(description == null) {
				info.getStringBuilder().append(info.getGroup(groupNo));
			} else {
				info.getStringBuilder().append(String.format(description, info.getGroup(groupNo)).replace(' ', zero));
			}
		}
	}
}
