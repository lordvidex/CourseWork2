package ru.itis.views;

import ru.itis.SSLConfig;
import ru.itis.dis.s2lab1.annotations.Component;
import ru.itis.dis.s2lab1.annotations.Inject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by IntelliJ IDEA
 * Date: 27.02.2022
 * Time: 12:31 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@Component
public class DirectoryPicker implements ActionListener, DocumentListener, FrameContainer {
    @Inject
    private SSLConfig config;

    @Inject
    private AppMenu appMenu;

    private JButton pickDirectoryBtn;
    private JFrame frame;
    private JButton nextBtn;
    private JLabel errorLabel;
    private JTextField directoryField;

    @Override
    public void initFrame() {
        frame = new JFrame();
        try {
            createInterface();
            frame.setPreferredSize(new Dimension(500, 500));
            frame.pack();
            frame.setVisible(true);
        } catch (IOException | URISyntaxException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void destroyFrame() {
        frame.dispose();
    }

    private void createInterface() throws IOException, URISyntaxException {
        /* button that opens file picker */
        pickDirectoryBtn = new JButton("Choose Folder",
                new ImageIcon("icon.png")
        );
        pickDirectoryBtn.setPreferredSize(new Dimension(150, 20));
        pickDirectoryBtn.addActionListener(this);

        /* text field that shows the directory picked */
        directoryField = new JTextField(config.getDirectory());
        directoryField.setPreferredSize(new Dimension(400, 20));
        directoryField.getDocument().addDocumentListener(this);

        JLabel label = new JLabel("Select a directory to save certificates or get certificates");
        frame.add(label, BorderLayout.NORTH);


        JPanel parentPickerPanel = new JPanel(); // contains pickerPanel and errorLabel
        parentPickerPanel.setLayout(new BorderLayout());

        JPanel pickerPanel = new JPanel();
        pickerPanel.setLayout(new FlowLayout());
        pickerPanel.add(directoryField);
        pickerPanel.add(pickDirectoryBtn);

        errorLabel = new JLabel("* Invalid directory entered");
        errorLabel.setForeground(Color.RED);

        parentPickerPanel.add(pickerPanel, BorderLayout.CENTER);
        parentPickerPanel.add(errorLabel, BorderLayout.SOUTH);
        frame.add(parentPickerPanel, BorderLayout.CENTER);

        nextBtn = new JButton("Next");
        nextBtn.addActionListener(this);
        nextBtn.setEnabled(false);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(nextBtn, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setTitle("Easy OpenSSL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(directoryField.getText());
        if (e.getSource() == pickDirectoryBtn) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("/Users/lordvidex/Desktop"));
            chooser.setDialogTitle("Pick a folder for certificates");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                directoryField.setText(chooser.getSelectedFile().getAbsolutePath());
            } else {
                System.out.println("No Selection ");
            }
        } else if (e.getSource() == nextBtn) {
            config.setDirectory(directoryField.getText());
            appMenu.initFrame();
            destroyFrame();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        onTextChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onTextChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        onTextChanged();
    }

    private void onTextChanged() {
        File f = new File(directoryField.getText());
        boolean isDirectory = f.exists() && f.isDirectory();
        errorLabel.setVisible(!isDirectory);
        nextBtn.setEnabled(isDirectory);
    }
}
