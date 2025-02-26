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

for (String text : buttons) {                                        // Iterate through button labels
            JButton button = new RoundedButton(text);                        // Create custom rounded button
            button.setFont(new Font("JetBrains Mono", Font.BOLD, 30));     // Set button font style and size
            button.addActionListener(this);                                // Add click event handler

            if (Arrays.asList(buttons).indexOf(text) < 8) {
                button.setForeground(new Color(0, 162, 255)); // Light blue for first two rows
            } else if (text.equals("DEL") || text.equals("AC")) {
                button.setForeground(new Color(255, 69, 58)); // Red for DEL and AC
            } else if (text.equals("M+") || text.equals("MR") || text.equals("H")) {
                button.setForeground(new Color(243, 209, 24)); // Red for DEL and AC
            } else {
                button.setForeground(new Color(240, 240, 240)); // White for all other buttons
            }
            if (text.equals("=")) {
                button.setBackground(new Color(37, 175, 236));       // Sets light blue background for equals button
            } else {
                button.setBackground(new Color(77, 77, 77));         // Sets dark gray background for other buttons
            }
            button.setFocusPainted(false);                             // Removes focus border/highlight
            button.putClientProperty("JButton.buttonType", "roundRect"); // Sets button shape to rounded rectangle
            panel.add(button);                                         // Adds the configured button to panel
        }

        add(panel, BorderLayout.CENTER);           // Adds the button panel to center of calculator window
        setVisible(true);                          // Makes the calculator window visible on screen
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();                    // Get the text from the button that was clicked

        if (command.equals("AC")) {                              // All Clear button pressed
            input.setLength(0);                                  // Clear the input buffer
            display.setText("");                                 // Clear the display
        } else if (command.equals("DEL")) {                     // Delete button pressed
            if (!input.isEmpty()) {                             // If there's input to delete
                input.delete(input.length() - 1, input.length());// Remove last character
                display.setText(input.toString());               // Update display
            }
        } else if (command.equals("=")) {                       // Equals button pressed
            try {
                double result = evaluateExpression(input.toString());    // Calculate result
                history.add(input.toString() + " = " + result);          // Add to history
                display.setText(String.format("%.6f", result));          // Show result with 6 decimals
                input.setLength(0);                                      // Clear input buffer
            } catch (Exception ex) {
                display.setText("Error");                                // Show error on invalid expression
                input.setLength(0);                                      // Clear input buffer
            }
        } else if (command.equals("M+")) {                      // Memory store button pressed
            try {
                memory = Double.parseDouble(display.getText());  // Store current display value in memory
            } catch (NumberFormatException ex) {
                display.setText("Error");                        // Show error if value invalid
            }
        } else if (command.equals("MR")) {                      // Memory recall button pressed
            display.setText(String.valueOf(memory));            // Show stored memory value
        } else if (command.equals("H")) {                       // History button pressed
            JOptionPane.showMessageDialog(this,          // Show history dialog
                    String.join("\n", history),                 // Join history entries with newlines
                    "Calculation History",                      // Dialog title
                    JOptionPane.INFORMATION_MESSAGE);           // Dialog type
        } else {
            input.append(command);                              // Add button text to input
            display.setText(input.toString());                  // Update display with new input
        }
    }

    private double evaluateExpression(String expression) throws Exception {
            return evaluatePostfix(infixToPostfix(expression));    // Convert infix to postfix and evaluate
        }

    private String infixToPostfix(String expression) {
        StringBuilder output = new StringBuilder();                    // Store postfix expression
        Stack<String> operators = new Stack<>();                       // Stack for operators
        // Split expression keeping operators and parentheses separate
        String[] tokens = expression.split("(?<=[-+x/^()])|(?=[-+x/^()])");

        for (String token : tokens) {
            token = token.trim();                                      // Remove whitespace
            if (token.matches("\\d+(\\.\\d+)?")) {                    // If token is a number
                output.append(token).append(" ");                      // Add to output
            } else if (UNARY_FUNCTIONS.contains(token) || token.equals("(")) {  // If function or open parenthesis
                operators.push(token);                                 // Push to operator stack
            } else if (token.equals(")")) {                           // If closing parenthesis
                while (!operators.isEmpty() && !operators.peek().equals("(")) {  // Until matching open parenthesis
                    output.append(operators.pop()).append(" ");        // Pop operators to output
                }
                operators.pop();                                       // Remove open parenthesis
            } else {                                                  // If operator
                // Pop operators with higher/equal precedence
                while (!operators.isEmpty() && precedence(token.charAt(0)) <= precedence(operators.peek().charAt(0))) {
                    output.append(operators.pop()).append(" ");
                }
                operators.push(token);                                 // Push current operator
            }
        }

        while (!operators.isEmpty()) {                                // Pop remaining operators
            output.append(operators.pop()).append(" ");
        }

        return output.toString();
    }

        private double evaluatePostfix(String postfix) throws Exception {
        Stack<Double> stack = new Stack<>();                          // Stack for operands
        for (String token : postfix.split(" ")) {                    // Process each token
            if (token.isEmpty()) continue;                           // Skip empty tokens
            try {
                stack.push(Double.parseDouble(token));               // Try to parse as number
            } catch (NumberFormatException e) {                      // If token is operator/function
                double a = stack.pop();                              // Pop operand
                switch (token) {
                    case "sin": stack.push(Math.sin(Math.toRadians(a))); break;  // Sine function
                    case "cos": stack.push(Math.cos(Math.toRadians(a))); break;  // Cosine function
                    case "tan": stack.push(Math.tan(Math.toRadians(a))); break;  // Tangent function
                    case "log": stack.push(Math.log10(a)); break;                // Log base 10
                    case "sqrt": stack.push(Math.sqrt(a)); break;               // Square root
                    case "cot": stack.push(1 / Math.tan(Math.toRadians(a))); break;  // Cotangent
                    case "sec": stack.push(1 / Math.cos(Math.toRadians(a))); break;  // Secant
                    case "csc": stack.push(1 / Math.sin(Math.toRadians(a))); break;  // Cosecant
                    case "+": stack.push(stack.pop() + a); break;               // Addition
                    case "-": stack.push(stack.pop() - a); break;               // Subtraction
                    case "x": stack.push(stack.pop() * a); break;               // Multiplication
                    case "/": stack.push(stack.pop() / a); break;               // Division
                    case "^": stack.push(Math.pow(stack.pop(), a)); break;      // Exponentiation
                    default: throw new Exception("Invalid Operator");           // Unknown operator
                }
            }
        }
        return stack.pop();                                          // Return final result
    }

        private int precedence(char operator) {
            return switch (operator) {
                case '+', '-' -> 1;      // Lowest precedence for addition and subtraction
                case 'x', '/' -> 2;      // Medium precedence for multiplication and division
                case '^' -> 3;           // Highest precedence for exponentiation
                default -> 0;            // Default precedence for other characters
            };
        }


        class RoundedButton extends JButton {
            public RoundedButton(String text) {
                super(text);                        // Call parent constructor with button text
                setContentAreaFilled(false);        // Disable default button fill
                setFocusPainted(false);            // Remove focus border/highlight
                setBorderPainted(false);            // Remove button border
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();    // Create graphics context for custom painting
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,    // Enable antialiasing
                        RenderingHints.VALUE_ANTIALIAS_ON);             // for smoother edges

                g2.setColor(getBackground());               // Set default background color

                if (getModel().isPressed()) {              // If button is being pressed
                    g2.setColor(getBackground().darker());  // Darken the background
                } else if (getModel().isRollover()) {      // If mouse is over button
                    g2.setColor(new Color(100, 100, 100)); // Use hover color
                }

                // Draw rounded rectangle for button shape (40px corner radius)
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();                              // Clean up graphics context

                super.paintComponent(g);                   // Paint button text/icon
            }
        }
        
    public static void main(String[] args) {
        new AdvancedCalculator();    // Create and display calculator window
    }
}
