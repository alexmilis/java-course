package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Wrapper for value that supports arithmetic operations if value types are compatible.
 * Supported operations: add, subtract, multiply, divide.
 * It also provides a method that compares values.
 * @author Alex
 *
 */
public class ValueWrapper {
	
	/**
	 * Value of wrapper.
	 */
	private Object value;
	
	/**
	 * Constant string that represents operation add.
	 */
	private static final String ADD = "+";
	
	/**
	 * Constant string that represents operation subtract.
	 */
	private static final String SUB = "-";
	
	/**
	 * Constant string that represents operation multiply.
	 */
	private static final String MUL = "*";
	
	/**
	 * Constant string that represents operation divide.
	 */
	private static final String DIV = "/";
	
	/**
	 * Constant string that represents operation that compares values.
	 */
	private static final String EQ = "=";

	/**
	 * Constructor of ValueWrapper.
	 * @param value of wrapper.
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}
	
	/**
	 * Getter for value.
	 * @return
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * If possible, adds incvalue to value of wrapper.
	 * @param incValue value to be added.
	 */
	public void add(Object incValue) {
		compareType(incValue, ADD);
	}
	
	/**
	 * If possible, subtracts decvalue from value of wrapper.
	 * @param decValue subtract.
	 */
	public void subtract(Object decValue) {
		compareType(decValue, SUB);
	}
	
	/**
	 * If possible, multiplies value of wrapper with mulvalue.
	 * @param mulValue multiplier.
	 */
	public void multiply(Object mulValue) {
		compareType(mulValue, MUL);
	}
	
	/**
	 * If possible, divides value of wrapper with divvalue.
	 * @param divValue divisor.
	 */
	public void divide(Object divValue) {
		compareType(divValue, DIV);
	}
	
	/**
	 * Compares value of wrapper with withvalue.
	 * @param withValue value to which wrapper value should be compared.
	 * @return	0 if values are equal.
	 * 			1 if wrapper value is bigger than withvalue.
	 * 			-1 if weapper value is smaller than withvalue.
	 */
	public int numCompare(Object withValue) {
		if(this == withValue) {
			return 0;
		}
		
		if(this.value == null && withValue == null) {
			return 0;
		}
		
		Object temp1 = checkType(this.value);
		Object temp2 = checkType(withValue);
		return (int) doOperation(temp1, temp2, EQ);
	}
	
	/**
	 * Compares types of values (this, othervalue) and performs operation specified by operator.
	 * @param otherValue second argument for operation.
	 * @param operator specifies operation.
	 */
	private void compareType(Object otherValue, String operator) {
		Object temp1 = checkType(this.value);
		Object temp2 = checkType(otherValue);
		
		if(temp1.getClass() == Integer.class && 
				temp2.getClass() == Integer.class) {
			this.value = (int) doOperation(temp1, temp2, operator);
		} else {
			this.value = doOperation(temp1, temp2, operator);
		}
	}
	
	/**
	 * Does operation specified by operator with arguments o1 and o2.
	 * @param o1 first argument of operation.
	 * @param o2 second argument of operation.
	 * @param operator specifies operation.
	 * @return double result of operation.
	 * @throws RuntimeException if operator is invalid.
	 */
	private double doOperation(Object o1, Object o2, String operator) {
		double result;
		double op1 = Double.valueOf(o1.toString());
		double op2 = Double.valueOf(o2.toString());
		switch(operator) {
		case ADD:
			result = op1 + op2;
			break;
		case SUB:
			result = op1 - op2;
			break;
		case MUL:
			result = op1 * op2;
			break;
		case DIV:
			result = op1 / op2;
			break;
		case EQ:
			if(op1 == op2) {
				result = 0;
			} else {
				result = op1 < op2 ? -1 : 1;
			}
			break;
		default:
			throw new RuntimeException("Invalid operator: " + operator);
		}
		return result;
	}
	
	/**
	 * Checks if type of value is supported by this value wrapper or not.
	 * Supported types are Integer, Double and String that can be parsed as number.
	 * @param o object that needs to be checked.
	 * @return object of supported type
	 * @throws RuntimeException if type of o is not supported.
	 * 							if type is String but cannot be parsed as number.
	 */
	private Object checkType(Object o) {
		if(o == null) {
			return Integer.valueOf(0);
		} else {
			if(o.getClass() != Integer.class &&
					o.getClass() != Double.class &&
					o.getClass() != String.class) {
				throw new RuntimeException("Object " + o + "is not null or instance of classes Integer, Double or String");
			}
			
			if(o.getClass() == String.class) {
				try {
					return Integer.parseInt((String) o);
				} catch (NumberFormatException ex) {
					try {
						return Double.parseDouble((String) o);
					} catch (NumberFormatException ex2) {
						throw new RuntimeException("String " + o.toString() + " cannot be parsed as Integer or Double");
					}
				}
			}
			
			return o;
		}
	}

	/**
	 * Setter for value.
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
