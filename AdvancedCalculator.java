// Essential imports for GUI components, event handling, and utilities
// Includes Swing/AWT for interface elements and event listeners
// Collections from java.util for data structures (ArrayList, HashSet)

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

    // Main calculator class extending JFrame for GUI window and implementing ActionListener for button events
    public class AdvancedCalculator extends JFrame implements ActionListener {
        private JTextField display;          // Text field to show input and results
        private StringBuilder input;         // Stores the current input expression
        private ArrayList<String> history;   // Keeps track of past calculations
        private double memory;               // Stores value for memory recall (MR) function
        private static final Set<String> UNARY_FUNCTIONS = new HashSet<>();  // Set of supported math functions

    // Static initializer block to populate the set of unary mathematical functions
    // Adds trigonometric functions (sin, cos, tan, cot, sec, csc) and other operations (log, sqrt)
    // These functions will be available as single-operand operations in the calculator
    static {
        UNARY_FUNCTIONS.add("sin");
        UNARY_FUNCTIONS.add("cos");
        UNARY_FUNCTIONS.add("tan");
        UNARY_FUNCTIONS.add("log");
        UNARY_FUNCTIONS.add("sqrt");
        UNARY_FUNCTIONS.add("cot");
        UNARY_FUNCTIONS.add("sec");
        UNARY_FUNCTIONS.add("csc");
    }


    public AdvancedCalculator() {
        setTitle("Advanced Calculator");                           // Sets the window title
        setSize(600, 700);                             // Sets window dimensions
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // Defines window close behavior
        setLayout(new BorderLayout());                             // Sets the main layout manager
        getContentPane().setBackground(new Color(38, 38, 38));       // Sets dark background color

        // Get the screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   // Gets screen resolution
        // Get the frame dimensions
        Dimension frameSize = getSize();                                      // Gets calculator window size
        // Center the frame
        setLocation(                                                         // Centers the window on screen
                (screenSize.width - frameSize.width) / 2,                    // Calculates x position
                (screenSize.height - frameSize.height) / 2                   // Calculates y position
        );

        input = new StringBuilder();                                  // Initializes input buffer
        history = new ArrayList<>();                                  // Initializes calculation history
        memory = 0.0;                                                 // Initializes memory function

        display = new JTextField();                                   // Creates display field
        display.setFont(new Font("SansSerif", Font.BOLD, 60));        // Sets display font
        display.setHorizontalAlignment(JTextField.RIGHT);                // Right-aligns display text
        display.setForeground(Color.WHITE);                              // Sets text color to white
        display.setBackground(new Color(51, 51, 51));            // Sets display background
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adds padding
        display.setEditable(false);                                    // Makes display read-only
        add(display, BorderLayout.NORTH);                              // Adds display to top

        display.setCaretColor(new Color(240, 240, 240));              // Sets cursor color
        display.setSelectionColor(new Color(0, 240, 240, 240));    // Sets text selection color

        JPanel panel = new JPanel();                                  // Creates button panel
        panel.setLayout(new GridLayout(8, 4, 10, 10));               // Sets grid layout with gaps
        panel.setBackground(new Color(38, 38, 38));                               // Sets panel background
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adds panel padding

        // Array of button labels arranged in a 8x4 grid layout:
        String[] buttons = {
                "sin", "cos", "tan", "cot",
                "sec", "csc", "sqrt", "log",
                "^", "M+", "MR", "H",
                "(", ")", "DEL", "AC",
                "7", "8", "9", "/",
                "4", "5", "6", "x",
                "1", "2", "3", "-",
                "0", ".", "+", "="
        };
