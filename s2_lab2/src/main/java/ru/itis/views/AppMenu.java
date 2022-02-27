package ru.itis.views;

import ru.itis.SSLConfig;
import ru.itis.dis.s2lab1.annotations.Component;
import ru.itis.dis.s2lab1.annotations.Inject;
import ru.itis.models.CertificateType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA
 * Date: 27.02.2022
 * Time: 2:22 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Component
public class AppMenu implements FrameContainer, ActionListener {

    @Inject
    private SSLConfig config;

    @Inject
    private CertificateForm formWindow;

    @Inject
    private DirectoryPicker picker;

    private JFrame frame;
    private JButton adminBtn;
    private JButton userBtn;
    private JButton openBtn;
    private JButton backBtn;

    public void initFrame() {
        frame = new JFrame();
        frame.setTitle("Easy OpenSSL - "+ config.getDirectory());
        frame.setSize(new Dimension(500, 500));
        frame.setPreferredSize(frame.getSize());
        frame.setLayout(new GridBagLayout());
        addComponents();
        frame.pack();
        frame.setVisible(true);
    }

    public void destroyFrame() {
        frame.dispose();
    }

    private void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        // add first button
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        adminBtn = new JButton("New Utility Center Certificate");
        adminBtn.addActionListener(this);
        frame.add(adminBtn, gbc);

        // add second button
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        userBtn = new JButton("New User Certificate");
        userBtn.addActionListener(this);
        frame.add(userBtn, gbc);

        // add third button
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        openBtn = new JButton("Open Certificates");
        openBtn.addActionListener(this);
        frame.add(openBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        backBtn = new JButton("Back", new ImageIcon("icon.png"));
        backBtn.addActionListener(this);
        frame.add(backBtn, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adminBtn) {
            formWindow.type = CertificateType.ADMIN;
            formWindow.initFrame();
        } else if (e.getSource() == userBtn) {
            formWindow.type = CertificateType.CLIENT;
            formWindow.initFrame();
        } else if (e.getSource() == openBtn) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File(config.getDirectory()));
            } catch (IOException err) {
                JOptionPane.showMessageDialog(frame, "Error opening folder","Folder not Found", JOptionPane.ERROR_MESSAGE );
            }
        } else if (e.getSource() == backBtn) {
            picker.initFrame();
            frame.dispose();
        }
    }
}
