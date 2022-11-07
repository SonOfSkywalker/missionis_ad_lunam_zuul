package pkg_Game.pkg_UserInterface; 

import pkg_Game.pkg_GameEngine.*;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import java.net.URL;

/**
 * This class implements a simple graphical user interface with a text entry
 * area, a text output area and an optional image.
 * 
 * @author Michael Kolling + Raphaël CANIN
 * @version 1.0 (Jan 2003) DB edited (2019), 2.0 2021
 */
public class UserInterface implements ActionListener
{
    private GameEngine aEngine;
    private JFrame     aMyFrame;
    private JTextField aEntryField;
    private JTextArea  aLog;
    private JLabel     aImage;

    private JButton    aLookBtn = new JButton("look");
    private JButton    aInventoryBtn = new JButton("inventory");
    private JButton    aHelpBtn = new JButton("help");

    private JPanel     aEntryPanel = new JPanel();
    
    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * 
     * @param pGameEngine  The GameEngine object implementing the game logic.
     */
    public UserInterface( final GameEngine pGameEngine )
    {
        this.aEngine = pGameEngine;
        this.createGUI();
    } // UserInterface(.)

    /**
     * Print out some text into the text area.
     * @param pText String to print
     */
    public void print( final String pText )
    {
        this.aLog.append( pText );
        this.aLog.setCaretPosition( this.aLog.getDocument().getLength() );
    } // print(.)

    /**
     * Print out some text into the text area, followed by a line break.
     * @param pText String to print
     */
    public void println( final String pText )
    {
        this.print( pText + "\n" );
    } // println(.)

    /**
     * Show an image file in the interface.
     * @param pImageName Name of the image to show
     */
    public void showImage( final String pImageName )
    {
        String vImagePath = "" + pImageName; // to change the directory
        URL vImageURL = this.getClass().getClassLoader().getResource( vImagePath );
        if ( vImageURL == null )
            System.out.println( "Image not found : " + vImagePath );
        else {
            ImageIcon vIcon = new ImageIcon( vImageURL );
            this.aImage.setIcon( vIcon );      
            this.aImage.setHorizontalAlignment(0);
            this.aMyFrame.pack();
        }
    } // showImage(.)

    /**
     * Enable or disable input in the input field and the buttons.
     * @param pOnOff boolean to enable or disable input
     */
    public void enable( final boolean pOnOff )
    {
        this.aEntryField.setEditable( pOnOff ); // enable/disable
        this.aLookBtn.setEnabled( pOnOff ); // enable/disable
        this.aInventoryBtn.setEnabled( pOnOff ); // enable/disable
        this.aHelpBtn.setEnabled( pOnOff ); // enable/disable
        
        if ( ! pOnOff ) { // disable
            this.aEntryField.getCaret().setBlinkRate( 0 ); // cursor won't blink
            this.aEntryField.removeActionListener( this ); // won't react to entry
        }
    } // enable(.)

    /**
     * Set up graphical user interface.
     */
    private void createGUI()
    {
        this.aMyFrame = new JFrame( "Missionis Ad Lunam" ); // change the title
        this.aEntryField = new JTextField( 34 );

        this.aLog = new JTextArea();
        this.aLog.setEditable( false );
        JScrollPane vListScroller = new JScrollPane( this.aLog );
        vListScroller.setPreferredSize( new Dimension(300, 300) );
        vListScroller.setMinimumSize( new Dimension(150,150) );

        JPanel vPanel = new JPanel();
        this.aImage = new JLabel();

        vPanel.setLayout( new BorderLayout() ); // ==> only five places
        vPanel.add( this.aImage, BorderLayout.NORTH );
        vPanel.add( vListScroller, BorderLayout.CENTER );
        
        vPanel.add(this.aEntryPanel, BorderLayout.SOUTH );
        
        aEntryPanel.add( this.aEntryField);;
        aEntryPanel.add(this.aLookBtn);
        aEntryPanel.add(this.aInventoryBtn);
        aEntryPanel.add(this.aHelpBtn);

        this.aMyFrame.getContentPane().add( vPanel, BorderLayout.CENTER );

        // add some event listeners to some components
        this.aLookBtn.addActionListener( this );
        this.aInventoryBtn.addActionListener( this );
        this.aHelpBtn.addActionListener( this );
        
        this.aEntryField.addActionListener( this );

        

        // to end program when window is closed
        this.aMyFrame.addWindowListener( new WindowAdapter() {
                public void windowClosing(WindowEvent e) { System.exit(0); }
            } );

        this.aMyFrame.pack();
        this.aMyFrame.setVisible( true );
        this.aEntryField.requestFocus();
    } // createGUI()

    /**
     * Actionlistener interface for entry textfield.
     * @param pE Event that just occured
     */
    public void actionPerformed( final ActionEvent pE ) 
    {
        if (pE.getActionCommand().equals("look")){
           aEngine.interpretCommand("look");
        }
        else if (pE.getActionCommand().equals("inventory")){
           aEngine.interpretCommand("inventory");
        }
        else if (pE.getActionCommand().equals("help")){
           aEngine.interpretCommand("help");
        }
        else{
            this.processCommand();
        }
    } // actionPerformed(.)

    /**
     * A command has been entered. Read the command and do whatever is 
     * necessary to process it.
     */
    private void processCommand()
    {
        String vInput = this.aEntryField.getText();
        this.aEntryField.setText( "" );

        this.aEngine.interpretCommand( vInput );
    } // processCommand()
} // UserInterface 