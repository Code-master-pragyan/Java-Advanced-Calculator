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
