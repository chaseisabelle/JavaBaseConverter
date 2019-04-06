/**
 * This class is the main class.  it is the beginning of the whole program.
 *
 * @author chase isabelle
 * @version %I%, %G%
 */

import java.applet.*;
import javax.swing.*;

public class Main 
extends Applet {

    // the main window of the program
    private static final JFramePackage.MainJFrame mainJFrame = new JFramePackage.MainJFrame();
    
    /**
     * This is the main method.
     * 
     * @param args is the args passed to main method
     */
    public static void main(String[] args) {
        // set up the system properties for this program
        // change the jmenu text from Main to something more professional, only really needed
        // for os x...
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Numeric Base Converter");
        // NOTE:  the previous code snippet will not work with later versions of OS X!!!
        
        // this chunk of code is generated for main by NetBeans
        // will not work without it in NetBeans!
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainJFrame.setVisible(true);
            }
        });
    }
    
    // testing...
    public void start() {
        JOptionPane.showMessageDialog(null, "POOP");
    }

}

/**
 * this class will be the main menu for the program
 * 
 * NOTE:  this class is not used for this program, it is only here
 * for consistency amoung my projects.  tis program doesn't have
 * a menu cause it's too simple.  -chase
 */
class MainJMenuBar
extends javax.swing.JMenuBar {
    
    /**
     * default constructor
     */
    public MainJMenuBar() {
        // chekc to see if the menu should be for the OS X main menu or a windows jframe menu
        if (Utilities.Global.getOperatingSystem() == Utilities.Global.MAC_OS_X) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        } else {
            System.setProperty("apple.laf.useScreenMenuBar", "false");
        }
    }
    
}