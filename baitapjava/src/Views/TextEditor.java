package Views;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import Controllers.TextEditorController;

import java.awt.*;
import java.io.File;
import java.util.List;

public class TextEditor extends JFrame {
    private JTextArea textArea;
    private JTree fileTree;

    public TextEditor() {
        super("Text Editor");

        textArea = new JTextArea();
        JScrollPane textScrollPane = new JScrollPane(textArea);

        fileTree = new JTree();
        fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        JScrollPane fileScrollPane = new JScrollPane(fileTree);

        JButton openButton = new JButton("Mở");
        openButton.addActionListener(e -> openFile());

        JButton saveButton = new JButton("Lưu");
        saveButton.addActionListener(e -> saveFile());



        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
    

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(fileScrollPane, BorderLayout.WEST);
        contentPane.add(textScrollPane, BorderLayout.CENTER);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(489, 400);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            List<String> content = TextEditorController.loadFromFile(selectedFile.getAbsolutePath());
            if (content != null) {
                StringBuilder sb = new StringBuilder();
                for (String line : content) {
                    sb.append(line).append("\n");
                }
                textArea.setText(sb.toString());
            }
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getAbsolutePath();
            String content = textArea.getText();
            try {
                TextEditorController.saveToFile(fileName, content);
                JOptionPane.showMessageDialog(this, "Saved successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving  " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void browseDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(selectedDirectory);
            populateTree(selectedDirectory, rootNode);
            fileTree.setModel(new DefaultTreeModel(rootNode));
        }
    }

    private void populateTree(File directory, DefaultMutableTreeNode parentNode) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(file);
                parentNode.add(node);
                if (file.isDirectory()) {
                    populateTree(file, node);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextEditor textView = new TextEditor();
            textView.setVisible(true);
        });
    }
}