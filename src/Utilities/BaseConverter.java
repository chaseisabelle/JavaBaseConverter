/*
 * baseConverter.java
 *
 * Created on November 26, 2007, 9:42 PM
 */

package Utilities;

/**
 * The BaseConverter class contsins only public static methods, since the
 * method were not designed to be used by an instantiated object.
 * 
 * @author chase isabelle
 * @version %I%, %G%
 */
public class BaseConverter {
    
    /**
     * default constructor (empty body).
     */
    public BaseConverter() {
        // empty body
    }
    
    /**
     * this method will convert an input to an output depending on the numeric bases of each.
     * <p>
     * for example;<br>
     * &nbsp;&nbsp;let the input parameter be "10"
     * &nbsp;&nbsp;let the inputBase parameter be 10
     * &nbsp;&nbsp;let the outputBase parameter be 2
     * &nbsp;&nbsp;this method will now convert the input string, "10",
     * in base 10 notation to output "1010", which is decimal 10 in base 2,
     * which is the output base.
     * 
     * @param input is the text from the input formatted text field
     * @param inputBase is the base of the input string's notation
     * @param outputBase is the base of the output string's notation
     * @return the value of the input converted to the output base
     */
    public static String convert(String input, int inputBase, int outputBase) {
        // baseTenValue will be the input put into base 10 notation
        long baseTenValue = 0;
        
        // iterate through the string, convert each char to an int value, then convert to base 10 notation
        for (int i=0; i<input.length(); i++) {
            int currentDigit = 0;
            int indexValue = input.length() - 1 - i;
            
            if (inputBase < 2) {
                return "";
            } else {
                try {
                    currentDigit = java.lang.Integer.parseInt("" + input.charAt(i));
                } catch (Exception exc) {
                    if (input.charAt(i) >= 'a' && input.charAt(i) <= 'z') {
                        currentDigit = (int)input.charAt(i) - 'a' + 10;
                    } else if (input.charAt(i) >= 'A' && input.charAt(i) <= 'Z') {
                        currentDigit = (int)input.charAt(i) - 'A' + 10;
                    } else {
                        return "";
                    }
                }
            }
            
            baseTenValue += currentDigit * java.lang.Math.pow(inputBase, indexValue);
        }
        
        // return value is the string that will represetn the value of the new base for the input
        String returnValue = "";
        
        // convert from base 10 to whatever the user has selected for the output base
        for (baseTenValue=baseTenValue; baseTenValue>0; baseTenValue/=outputBase) {
            long remainderValue = baseTenValue%outputBase;
            if (remainderValue > 10 + ('z' - 'a')) {
                return "";
            } else if (remainderValue > 9) {
                returnValue = (char)(remainderValue - 10 + 'a') + returnValue;
            } else {
                returnValue = java.lang.Long.toString(remainderValue) + returnValue;
            }
        }
        
        // return the result
        return returnValue;
    }
    
}
