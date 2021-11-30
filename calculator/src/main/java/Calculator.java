// Java program to create a simple Calculator
// with basic +, -, /, * using java swing elements

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame implements ActionListener {
    // create a frame
    static JFrame frame;

    // create a textfield
    static JTextField textArea;

    // store operator and operands
    String s0, s1, s2;

    // default constructor
    Calculator()
    {
        s0 = s1 = s2 = "";
    }

    // main function
    public static void main(String[] args)
    {
        // create a frame
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);

        Calculator c = new Calculator();
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create a textfield
        textArea = new JTextField(16);

        // set the textfield to non editable
        textArea.setEditable(false);

        // create number buttons and some operators
        JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, ba, bs, bd, bm, be, beq, beq1;

        // create number buttons
        b0 = new JButton("0");
        b1 = new JButton("1");
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("9");

        // equals button
        beq1 = new JButton("=");

        // create operator buttons
        ba = new JButton("+");
        bs = new JButton("-");
        bd = new JButton("/");
        bm = new JButton("*");
        beq = new JButton("C");

        // create . button
        be = new JButton(".");

        // create a panel
        JPanel panel = new JPanel();

        // add action listeners
        addListener(c, b8, b9, ba, bs, bd, bm, b7, b6);
        addListener(c, b0, b1, b2, b3, b4, b5, be, beq);
        beq1.addActionListener(c);

        // add elements to panel
        panel.add(textArea);
        panel.add(ba);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(bs);
        panel.add(b4);
        panel.add(b5);
        panel.add(b6);
        panel.add(bm);
        panel.add(b7);
        panel.add(b8);
        panel.add(b9);
        panel.add(bd);
        panel.add(be);
        panel.add(b0);
        panel.add(beq);
        panel.add(beq1);

        // set Background of panel
        panel.setBackground(Color.GRAY);

        // add panel to frame
        frame.add(panel);

        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    private static void addListener(Calculator c,
                                    JButton b0,
                                    JButton b1,
                                    JButton b2,
                                    JButton b3,
                                    JButton b4,
                                    JButton b5,
                                    JButton be,
                                    JButton beq) {
        b5.addActionListener(c);
        b4.addActionListener(c);
        b3.addActionListener(c);
        b2.addActionListener(c);
        b1.addActionListener(c);
        b0.addActionListener(c);
        be.addActionListener(c);
        beq.addActionListener(c);
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        // if the value is a number
        if ((s.charAt(0) >= '0' && s.charAt(0) <= '9') || s.charAt(0) == '.') {
            // if operand is present then add to second no
            if (!s1.equals(""))
                s2 = s2 + s;
            else
                s0 = s0 + s;

            // set the value of text
            textArea.setText(s0 + s1 + s2);
        }
        else if (s.charAt(0) == 'C') {
            // clear the one letter
            s0 = s1 = s2 = "";

            // set the value of text
            textArea.setText(s0 + s1 + s2);
        }
        else if (s.charAt(0) == '=') {

            double te;

            // store the value in 1st
            if (s1.equals("+"))
                te = (Double.parseDouble(s0) + Double.parseDouble(s2));
            else if (s1.equals("-"))
                te = (Double.parseDouble(s0) - Double.parseDouble(s2));
            else if (s1.equals("/"))
                te = (Double.parseDouble(s0) / Double.parseDouble(s2));
            else
                te = (Double.parseDouble(s0) * Double.parseDouble(s2));

            // set the value of text
            textArea.setText(s0 + s1 + s2 + "=" + te);

            // convert it to string
            s0 = Double.toString(te);

            s1 = s2 = "";
        }
        else {
            // if there was no operand
            if (s1.equals("") || s2.equals(""))
                s1 = s;
                // else evaluate
            else {
                double te;

                // store the value in 1st
                if (s1.equals("+"))
                    te = (Double.parseDouble(s0) + Double.parseDouble(s2));
                else if (s1.equals("-"))
                    te = (Double.parseDouble(s0) - Double.parseDouble(s2));
                else if (s1.equals("/"))
                    te = (Double.parseDouble(s0) / Double.parseDouble(s2));
                else
                    te = (Double.parseDouble(s0) * Double.parseDouble(s2));

                // convert it to string
                s0 = Double.toString(te);

                // place the operator
                s1 = s;

                // make the operand blank
                s2 = "";
            }

            // set the value of text
            textArea.setText(s0 + s1 + s2);
        }
    }
}
