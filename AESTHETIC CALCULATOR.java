import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PastelCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private String input = "";
    
    private static final Color PASTEL_COLOR = new Color(214, 234, 248);
    private static final Color HIGHLIGHT_COLOR = new Color(162, 187, 202);

    public PastelCalculator() {
        setTitle("Aesthethic Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        display = new JTextField();
        display.setEditable(false);
        display.setBackground(PASTEL_COLOR);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonPanel.setBackground(PASTEL_COLOR);
        
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };
        
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.setBackground(PASTEL_COLOR);
            button.setBorder(BorderFactory.createLineBorder(HIGHLIGHT_COLOR, 2));
            button.addActionListener(this);
            buttonPanel.add(button);
        }
        
        add(buttonPanel, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("=")) {
            try {
                input = String.valueOf(eval(input));
            } catch (Exception ex) {
                input = "Error";
            }
        } else {
            input += command;
        }
        
        display.setText(input);
    }
    
    private double eval(String expression) {
    String[] tokens = expression.split(" ");
    Stack<Double> values = new Stack<>();
    
    for (String token : tokens) {
        if (isNumber(token)) {
            values.push(Double.parseDouble(token));
        } else if (isOperator(token)) {
            double operand2 = values.pop();
            double operand1 = values.pop();
            double result = performOperation(token, operand1, operand2);
            values.push(result);
        }
    }
    
    return values.pop();
}

private boolean isNumber(String token) {
    return token.matches("-?\\d+(\\.\\d+)?");
}

private boolean isOperator(String token) {
    return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
}

private double performOperation(String operator, double operand1, double operand2) {
    switch (operator) {
        case "+":
            return operand1 + operand2;
        case "-":
            return operand1 - operand2;
        case "*":
            return operand1 * operand2;
        case "/":
            if (operand2 != 0) {
                return operand1 / operand2;
            } else {
                throw new ArithmeticException("Division by zero");
            }
        default:
            throw new IllegalArgumentException("Unknown operator: " + operator);
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PastelCalculator());
    }
}
