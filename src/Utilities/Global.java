/*
 * Global.java
 *
 * Created on December 10, 2007, 8:25 PM
 *
 */

package Utilities;

/**
 * this class was designed for quick refrencing to misc. global attributes for a program.
 * <p>
 * for example;  this class contains a method for determining the operating system of
 * the current computer running this program.
 * 
 * @author cisabell
 * @version %I%, %G%
 */
public class Global {
    
    /**
     * default constructor (empty body).
     */
    public Global() {
        // empty body
    }
    
    /**
     * this method is used to determine the operating system of the current computer running
     * the program.
     * 
     * @return a public static int that represents the user's operating system.
     */
    public static int getOperatingSystem() {
        if (System.getProperty("mrj.version") == null) {
            return WINDOWS;
        } else {
            return MAC_OS_X;
        }
    }
    
    /**
     * operating system codes, used with method getOperatingSystem
     */
    public static int UNKNOWN = 0;
    public static int MAC_OS_X = 1;
    public static int WINDOWS = 2;
    
}
