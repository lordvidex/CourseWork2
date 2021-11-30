package views;

import controllers.BlockController;
import models.Block;
import models.BlockChain;
import models.BlockData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA
 * Date: 29.11.2021
 * Time: 1:38 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class BlockChainFrame extends JFrame implements ActionListener {
    /** UI */
    private MenuItem myBlocks; // menu item to fetch blocks for this user
    private MenuItem allBlocks; // menu items to fetch blocks for this chain
    private MenuItem createBlockMenu; // menu item as a shortcut to create a new block
    private JPanel tablePanel; // table that holds the data
    private JTextField nameTF;
    private JTextField dataTF;
    private JButton createBtn; // button that creates a new Block and adds to the chain

    /** CONTROLLER */
    private BlockController controller; // BlockController that handles Database and REST calls

    /** DATA */
    private final String[] tableHeader = new String[] {"PrevHash",
            "Signature",
            "Name",
            "Data",
            "Time",
            "Public Key"};

    public BlockChainFrame(BlockController controller) {
        this.controller = controller;
        initFrame();
    }

    private void initFrame() {
        setTitle("BlockChain Client"); // sets the title of the jFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits our app on close
        getContentPane().setBackground(Color.decode("#FAF3FF")); // set background color
        setSize(1000, 500);
        /* set the menu section */
        MenuBar menuBar = new MenuBar();
        setMenuBar(menuBar);
        Menu create = new Menu("Create");
        createBlockMenu = new MenuItem("Create new Block");
        createBlockMenu.setShortcut(new MenuShortcut(KeyEvent.VK_N,false));
        createBlockMenu.addActionListener(this);
        create.add(createBlockMenu);
        Menu view = new Menu("View");
        myBlocks = new MenuItem("View My Blocks");
        myBlocks.addActionListener(this);
        allBlocks = new MenuItem("View Chain");
        allBlocks.addActionListener(this);
        view.add(myBlocks);
        view.add(allBlocks);


        menuBar.add(create);
        menuBar.add(view);
        addEditorPanel();

        addTablePanel(new JTable(controller.getBlockChain().getData(), tableHeader));
//        pack();
        setVisible(true); // makes us see the frame
    }
    private void addEditorPanel() {
        JPanel editorPanel = new JPanel();
        JLabel nameLabel = new JLabel("Name");
        nameTF = new JTextField();
        JLabel dataLabel = new JLabel("Data");
        dataTF = new JTextField();
        nameTF.setPreferredSize(new Dimension(200, 30));
        dataTF.setPreferredSize(new Dimension(200, 30));
        createBtn = new JButton("Create Block");
        createBtn.addActionListener(this);
        nameTF.setName("Name");

        editorPanel.setPreferredSize(new Dimension(100, 100));
        editorPanel.add(nameLabel);
        editorPanel.add(nameTF);
        editorPanel.add(dataLabel);
        editorPanel.add(dataTF);
        editorPanel.add(createBtn);
        add(editorPanel, BorderLayout.NORTH);
    }

    private void addTablePanel(JTable table) {
        if(tablePanel != null) {
            remove(tablePanel);
        }
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        tablePanel.add(scrollPane);
        add(tablePanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myBlocks) {
            BlockChain chain = controller.getUserBlocks();
            String[][] data = chain.getData();
            updateTableWithData(data);
        } else if (e.getSource() == allBlocks) {
            BlockChain chain = controller.getBlockChain();
            String[][] data = chain.getData();
            updateTableWithData(data);
        } else if (e.getSource() == createBtn || e.getSource() == createBlockMenu) {
            if (nameTF.getText().length() < 3 || dataTF.getText().length() < 3) {
                JOptionPane.showMessageDialog(null, "Text is too short", "Warning",JOptionPane.WARNING_MESSAGE);
            } else {
                controller.createNewBlock(new BlockData(dataTF.getText(), nameTF.getText()));
                nameTF.setText("");
                dataTF.setText("");
            }
        }
    }

    private void updateTableWithData(String[][] data) {
        JTable table = new JTable(data, tableHeader);
        addTablePanel(table);
        revalidate();
    }
}
