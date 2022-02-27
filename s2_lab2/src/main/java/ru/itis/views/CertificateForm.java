package ru.itis.views;

import ru.itis.SSLConfig;
import ru.itis.SSLService;
import ru.itis.dis.s2lab1.annotations.Component;
import ru.itis.dis.s2lab1.annotations.Inject;
import ru.itis.models.CertificateType;
import ru.itis.models.Subject;
import ru.itis.utils.FileTypeFilter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA
 * Date: 27.02.2022
 * Time: 7:34 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@Component
public class CertificateForm implements FrameContainer, ActionListener {
    @Inject
    private SSLService sslService;

    @Inject
    private SSLConfig config;

    public CertificateType type = CertificateType.ADMIN;

    /* Widgets */
    private JFrame frame;
    private JTextField certNameField;
    private JTextField countryField;
    private JTextField stateField;
    private JTextField cityField;
    private JTextField organizationField;
    private JTextField organizationUnitField;
    private JTextField nameField;
    private JTextField emailField;
    private JButton createBtn;
    private JButton cancelBtn;
    private JLabel certificatePath;
    private JLabel keyPath;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelBtn) {
            destroyFrame();
        } else if (e.getSource() == createBtn) {
            if (validateTextFields()) {
                Subject s = createSubject();
                try {
                    if (type == CertificateType.CLIENT) {
                        sslService.createUserWithCenter(certificatePath.getText(), keyPath.getText(), s);
                    } else {
                        sslService.createCertifyingCenter(s);
                    }
                    destroyFrame();
                } catch (IOException err) {
                    JOptionPane.showMessageDialog(frame,
                            err.getMessage(),
                            "Signature Generator Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Invalid Input, Check and try again",
                        "TextField Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateTextFields() {
        if (type == CertificateType.CLIENT) {
            File f = new File(certificatePath.getText());
            if (!f.exists() && !f.getAbsolutePath().endsWith(".crt")) {
                return false;
            }
            f = new File(keyPath.getText());
            if (!f.exists() && !f.getAbsolutePath().endsWith(".key")) {
                return false;
            }

        }
        return !certNameField.getText().isEmpty() &&
                countryField.getText().length() == 2 &&
                !stateField.getText().isEmpty() &&
                !cityField.getText().isEmpty() &&
                !organizationField.getText().isEmpty() &&
                !organizationUnitField.getText().isEmpty() &&
                !nameField.getText().isEmpty() &&
                !emailField.getText().isEmpty();
    }

    private Subject createSubject() {
        return new Subject(
                certNameField.getText(),
                countryField.getText(),
                stateField.getText(),
                cityField.getText(),
                organizationField.getText(),
                organizationUnitField.getText(),
                nameField.getText(),
                emailField.getText()
                );
    }

    @Override
    public void initFrame()  {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("Easy OpenSSL - SSL Form");
        frame.setSize(new Dimension(500,600));
        frame.setPreferredSize(frame.getSize());
        initFields();

        JPanel clusterPanel = new JPanel();
        clusterPanel.setLayout(new BoxLayout(clusterPanel, BoxLayout.Y_AXIS));

        JPanel tPanel = new JPanel();
        tPanel.setBorder(new EmptyBorder(new Insets(20, 50, 0, 50)));
        tPanel.setLayout(new BoxLayout(tPanel, BoxLayout.Y_AXIS));
        tPanel.add(textFieldPanel("File Name(without extension)", certNameField));
        // if admin add buttons to select files
        if (type == CertificateType.CLIENT) {
            tPanel.add(fileChooserPanel("Admin Certificate", certificatePath, new FileTypeFilter(".crt", "OpenSSL Certificate")));
            tPanel.add(fileChooserPanel("Admin Key", keyPath, new FileTypeFilter(".key", "OpenSSL Key")));
        }
        clusterPanel.add(tPanel);

        JPanel fieldPanels = new JPanel();
        fieldPanels.setBorder(new EmptyBorder(new Insets(20, 50, 20, 50)));
        fieldPanels.setLayout(new BoxLayout(fieldPanels, BoxLayout.Y_AXIS));
        fieldPanels.add(textFieldPanel("Country", countryField));
        fieldPanels.add(textFieldPanel("State", stateField));
        fieldPanels.add(textFieldPanel("City", cityField));
        fieldPanels.add(textFieldPanel("Organization", organizationField));
        fieldPanels.add(textFieldPanel("Organization Unit", organizationUnitField));
        fieldPanels.add(textFieldPanel("Name", nameField));
        fieldPanels.add(textFieldPanel("Email Address", emailField));
        clusterPanel.add(fieldPanels);

        frame.add(clusterPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        bottomPanel.add(createBtn);
        bottomPanel.add(cancelBtn);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        //TODO:
        frame.pack();
        frame.setVisible(true);
    }

    private void initFields() {
        countryField = getTextField();
        stateField = getTextField();
        cityField = getTextField();
        organizationField = getTextField();
        organizationUnitField = getTextField();
        nameField = getTextField();
        emailField = getTextField();
        certNameField = getTextField();

        createBtn = new JButton("Create");
        createBtn.setBackground(Color.BLUE);
        createBtn.setForeground(Color.WHITE);
        createBtn.addActionListener(this);
        cancelBtn = new JButton("Cancel");
        cancelBtn.setForeground(Color.RED);
        cancelBtn.addActionListener(this);

        certificatePath = new JLabel("*");
        certificatePath.setForeground(Color.gray);
        keyPath = new JLabel("*");
        keyPath.setForeground(Color.gray);
    }

    @Override
    public void destroyFrame() {
        frame.dispose();
    }

    /**
     * creates a textfield row with a JLabel containing it's description and the textfield itself
     * @param description content of the JLabel
     * @param container the textField where data is entered
     * @return a JPanel containing these items
     */
    private JPanel textFieldPanel(String description, JTextField container) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel(description+": "));
        p.add(container);
        return p;
    }

    private JTextField getTextField() {
        JTextField tf = new JTextField();
        tf.setSize(new Dimension(250, 20));
        tf.setPreferredSize(tf.getSize());
        return tf;
    }

    private JPanel fileChooserPanel(String description, JLabel label, FileTypeFilter filter) {
        JPanel jp = new JPanel();

        JLabel desc = new JLabel(description+": ");
        jp.add(desc);
        Font font  = new Font(desc.getFont().getName(), Font.BOLD, desc.getFont().getSize());
        desc.setFont(font);

        JButton btn = new JButton("Choose File");
        btn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(config.getDirectory()));
            chooser.setDialogTitle("Pick a file");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.addChoosableFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                label.setText(chooser.getSelectedFile().getAbsolutePath());
            } else {
                System.out.println("No Selection ");
            }
        });
        jp.add(desc);
        jp.add(label);
        jp.add(btn);
        return jp;
    }
}
