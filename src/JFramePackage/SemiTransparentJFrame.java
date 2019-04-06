/**
 * file:  SemiTransparentJFrame.java
 */

package JFramePackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * this class creates a JFrame with a "transparent" background.  the only problem with
 * this code is that the illsuion isn't very smooth when the frame is moved, etc.
 * <p>
 * NOTE:  visit http://forum.java.sun.com/thread.jspa?threadID=490146&messageID=2301312 for more details
 * 
 * @author chase isabelle
 * @author http://forum.java.sun.com/thread.jspa?threadID=490146&messageID=2301312
 * @version %I%, %G%
 */
public class SemiTransparentJFrame
extends JFrame 
implements MouseListener, MouseMotionListener {
    
    // robot will provide a way to capture the screen
    private Robot robot;
    
    // screenCaptureBufferedImage will be the screen capture, provided by robot
    private BufferedImage screenCaptureBufferedImage = new BufferedImage(Toolkit.getDefaultToolkit().getScreenSize().width,
            Toolkit.getDefaultToolkit().getScreenSize().height, 
            BufferedImage.TYPE_INT_RGB);
    
    // screenRectangle will be the portion of the screen being capture for the screenCaptureBufferedImage
    private Rectangle screenRectangle;
    
    // the illsuion effect created by a custom JPanel
    private SemiTransparentIllusionJPanel transparentIllusionJPanel = new SemiTransparentIllusionJPanel();
    
    // not sure what this does yet...
    boolean userActivate = false;
    
    // variables for dragging the jframe without the title bar
    // NOTE: see http://forum.java.sun.com/thread.jspa?threadID=406114&forumID=57 for more details
    private MouseEvent mouseEvent;
    private Point jFrameLocationPoint;
        
    /**
     * default class constructor.  
     */
    public SemiTransparentJFrame() {
        // call to the parent constrcutor
        super();
        
        // get rid of the title bar
        setUndecorated(true);
        
        // JavaDoc not found
        createScreenImage();
        
        // set the content pane (background pane) for transparent effect
        setContentPane(transparentIllusionJPanel);
        
        // component listener details...
        this.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent e) {
                // empty body
            }
            public void componentMoved(ComponentEvent e) {
                resetUnderImage();
                repaint();
            }
            
            public void componentResized(ComponentEvent e) {
                resetUnderImage();
                repaint();
            }
            
            public void componentShown(ComponentEvent e) {
                resetUnderImage();
                repaint();
            }
        });
        
        // window listener details...
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // empty body
            }
            
            public void windowClosed(WindowEvent e) {
                // empty body
            }
            
            public void windowOpened(WindowEvent e) {
                // empty body
            }
            
            public void windowIconified(WindowEvent e) {
                // empty body
            }
            
            public void windowDeiconified(WindowEvent e) {
                // empty body
            }
            
            public void windowActivated(WindowEvent e) {
                if (userActivate) {
                    userActivate = false;
                    SemiTransparentJFrame.this.setVisible(false);
                    createScreenImage();
                    resetUnderImage();
                    SemiTransparentJFrame.this.setVisible(true);
                } else {
                    userActivate = true;
                }
            }
            
            public void windowDeactivated(WindowEvent e) {
                // empty body
            }
        });
        
        // set up the mouse listeners, etc.  for dragging the jframe without a title bar
        // since we've setUndecorated(true)
        setLocationRelativeTo(null);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    /**
     * this method is used to get the image that is behind the JFrame to create the
     * transparency illusion.
     */
    protected void createScreenImage() {
        try {
            if (robot == null) {
                robot = new Robot();
            }
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenRectangle = new Rectangle(0, 0, screenSize.width, screenSize.height);
        screenCaptureBufferedImage = robot.createScreenCapture(screenRectangle);
    }
    
    /**
     * this method updates the background image to keep the illusion consistant.
     */
    public void resetUnderImage() {
        if (robot != null && screenCaptureBufferedImage != null) {
            Rectangle frameRect = getBounds();
            int x = frameRect.x;// + 4;
            transparentIllusionJPanel.paintX = 0;
            transparentIllusionJPanel.paintY = 0;
            
            if (x < 0) {
                transparentIllusionJPanel.paintX = -x;
                x = 0;
            }
            
            int y = frameRect.y;
            if (y < 0) {
                transparentIllusionJPanel.paintY = -y;
                y = 0;
            }
            
            int w = frameRect.width;
            if (x + w > screenCaptureBufferedImage.getWidth()) {
                w = screenCaptureBufferedImage.getWidth() - x;
            }
            
            int h = frameRect.height;
            if (y + h > screenCaptureBufferedImage.getHeight()) {
                h = screenCaptureBufferedImage.getHeight() - y;
            }
            
            // set the background to create the illusion
            float[] blurMatrix = {0.1f, 0.1f, 0.1f,
            0.1f, 0.2f, 0.1f,
            0.1f, 0.1f, 0.1f};
            Kernel blurKernel = new Kernel(3, 3, blurMatrix);
            ConvolveOp convolveOp = new ConvolveOp(blurKernel, ConvolveOp.EDGE_ZERO_FILL, null);
            transparentIllusionJPanel.underFrameImage = convolveOp.filter(screenCaptureBufferedImage.getSubimage(x, y, w, h), null);
        }
    }
    
    /// The fallowing code is used for dragging the jframe without a title bar
    /// NOTE:  see http://forum.java.sun.com/thread.jspa?threadID=406114&forumID=57 for more details
    
    public void mouseClicked(MouseEvent e) {
        // empty body
    }

    public void mousePressed(MouseEvent e) {
        mouseEvent = e;
    }

    public void mouseReleased(MouseEvent e) {
        // empty body
    }

    public void mouseEntered(MouseEvent e) {
        // empty body
    }

    public void mouseExited(MouseEvent e) {
        // empty body
    }

    public void mouseDragged(MouseEvent e) {
        jFrameLocationPoint = getLocation(jFrameLocationPoint);
        int x = jFrameLocationPoint.x - mouseEvent.getX() + e.getX();
        int y = jFrameLocationPoint.y - mouseEvent.getY() + e.getY();
        setLocation(x, y);
    }

    public void mouseMoved(MouseEvent e) {
        // empty body
    }
    
    /// end of jframe dragging code
    
}

/**
 * this class is the illusion, a JPanel that is the background of the JFrame.
 * 
 * @author chase isabelle
 * @author http://forum.java.sun.com/thread.jspa?threadID=490146&messageID=2301312
 */
class SemiTransparentIllusionJPanel extends JPanel {
    
    /// underFrameImage is the image that will represent the background of the jFraame
    public BufferedImage underFrameImage;
    
    int paintX = 0;
    int paintY = 0;
    
    /// constrcutor
    public SemiTransparentIllusionJPanel() {
        super();
        setOpaque(true);
    }
    
    /// standard method
    public void paint(Graphics graphics) {
        // call to parent's paint method
        super.paint(graphics);
    }
    
    /// paint each of the components in the container
    protected void paintComponent(Graphics graphics) {
        // first paint all default stuff
        super.paintComponent(graphics);
        
        // then paint the JPanel's image to give the illusion
        // of transparency
        graphics.drawImage(underFrameImage, paintX, paintY, null);
    }
}