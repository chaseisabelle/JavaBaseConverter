/*
 * 
 * NumberBaseDacument.java
 * 
 */

package DocumentPackage;

/**
 * NumberBaseDocument is a document that can be applied to document's for JText components.
 * The document restricts characters to only those characters that apply to a given
 * numeric base, that is provided as a parameter to the constructor.
 * <p>
 * For example:<br>
 * &nbsp;&nbsp;JTextField jTextField = new JTextField;<br>
 * &nbsp;&nbsp;NumberBaseDocument numberBaseDocument = new NumberBaseDocument(10);<br>
 * &nbsp;&nbsp;jTextField.setDocument(numberBaseDocument);<br>
 * <br>
 * &nbsp;&nbsp;This will restrict the char input of jTextField to ascii '0', '1', '2', ..., '9'.
 * <p>
 * NOTE:  the current version of this class only supports number bases up to 16 (hex).
 * 
 * @author chase isabelle
 * @version %I%, %G%
 */
public class NumberBaseDocument extends javax.swing.text.PlainDocument {
    
    /**
     * Class constrcutor.
     * 
     * @param base is the numeric base for the input restrcition on the text component.
     */
    public NumberBaseDocument(int base) {
        setBase(base);
    }
    
    /**
     * Sets the numeric base of this document object.  Once a new base is set, this
     * document will hence-forth restrict the chars of the newly set numeric base.
     * 
     * @param base is the new numeric base that will represent the restrictor on the
     * input of the jtext component.
     */
    public void setBase(int base) {
        if (base < 0 || base > 16) {
            return;
        }
        this.base = base;
    }
    
    /**
     * This method returns the numeric base that this object is currently set to.
     * 
     * @return the numeric base that the object is currently set to.
     */
    public int getBase() {
        return this.base;
    }
    
    /**
     * This method does all the restricting for the obejct, depnding on the given base.
     * <p>
     * (see http://forum.java.sun.com/thread.jspa?threadID=503846&messageID=2680263 for details)
     * 
     * @param pos is the position of the input
     * @param str is the string that represents the input
     * @param attributeSet is the attribute set, see java API for more info.
     * @throws javax.swing.text.BadLocationException
     */
    public void insertString(int pos, String str, javax.swing.text.AttributeSet attributeSet)
    throws javax.swing.text.BadLocationException {
        // first check to see if any work must be done...
        if (str.length() <= 0) {
            return;
        }
        
        // next, we have to check the validity of the chars in the doc
        // to do this, we iterate through the string char-by-char compaing
        // each char in the string to the valid chars accepted by this doc's
        // number base (this.base)
        for (int i=0; i<str.length(); i++) {
            // NOTE:  try block is used for the java.lang.Integer.parseInt method
            try {
                // if base is zero then no chars are acceptable
                if (base <= 0) {
                    return;
                }
                
                // if base is between 1 and 10 then only numeric chars are acceptable
                else if (base > 0 && base < 11
                        && (java.lang.Integer.parseInt(""+str.charAt(i)) >= base || java.lang.Integer.parseInt(""+str.charAt(i)) < 0)) {
                    return;
                }
                
                // if base is between 11 and 16 then numberics and letters (e.g. A-F) are acceptable
                else if (base > 10 && base < 17) {
                    if ((str.charAt(i) < '0' || str.charAt(i) > '9')
                    && (str.charAt(i) < 'A' || str.charAt(i) > 'A' + (base - 11))
                    && (str.charAt(i) < 'a' || str.charAt(i) > 'a' + (base - 11))) {
                        return;
                    }
                }
            } catch (NumberFormatException NumFrmtExc) {
                System.out.println("Invalid Char:  " + str.charAt(i));
                return;
            }
        }
        
        // pass up to parent object
        super.insertString(pos, str, attributeSet);
    }
    
    /**
     * represents the numeric base of the object
     */
    private int base;
    
}
