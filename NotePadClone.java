import javax.swing.ImageIcon; 
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;

class NotePad extends JFrame implements ActionListener {
    JFrame frame;
    JMenuBar menuBar;
    JMenu file, edit, view, color;
    JMenuItem newFile, open, save, saveAs, cut, copy, paste, clear, dark, light;
    JTextArea textArea;
    JFileChooser fileOpen, fileSave;
    String path = "res/untitled.txt"; // Updated relative path
    JScrollPane scrollPane;

    @SuppressWarnings("deprecation")
    NotePad() {
        // frame
        frame = new JFrame("NotePad");
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

        // Use a relative path for icon
        ImageIcon icon = new ImageIcon("res/icon2.png"); 
        frame.setIconImage(icon.getImage());
        frame.setCursor(Cursor.HAND_CURSOR);

        // menu items
        newFile = new JMenuItem("New File");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        saveAs = new JMenuItem("Save As");
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        clear = new JMenuItem("Clear Screen");
        dark = new JMenuItem("Dark");
        light = new JMenuItem("Light");

        // Menus
        color = new JMenu("Color");
        color.add(dark);
        color.add(light);

        file = new JMenu("File");
        file.add(newFile);
        file.add(open);
        file.add(save);
        file.add(saveAs);

        edit = new JMenu("Edit");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);

        view = new JMenu("View");
        view.add(clear);
        view.add(color);

        // MenuBar
        menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(view);

        // TextArea
        textArea = new JTextArea();
        textArea.setForeground(Color.BLACK);
        textArea.setBackground(new Color(255, 213, 98));
        Font font = new Font("Arial", Font.PLAIN, 32);
        textArea.setFont(font);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretColor(Color.BLACK);

        // ScrollPane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 10, 1170, 740);

        // ActionListeners
        newFile.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        clear.addActionListener(this);
        dark.addActionListener(this);
        light.addActionListener(this);

        // Add components to frame
        frame.add(menuBar);
        frame.setJMenuBar(menuBar);
        frame.add(scrollPane);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newFile) {
            textArea.setText("");
            String fileName = JOptionPane.showInputDialog(frame, "Enter File Name:");
            path = "res/" + fileName + ".txt"; // Updated relative path

            File myFile = new File(path);
            try {
                myFile.createNewFile();
                JOptionPane.showMessageDialog(frame, "File Created");
            } catch (Exception fileE) {
                JOptionPane.showMessageDialog(frame, "Error");
                fileE.printStackTrace();
            }

        } else if (e.getSource() == open) {
            try {
                fileOpen = new JFileChooser();
                fileOpen.showOpenDialog(frame);
                path = String.valueOf(fileOpen.getSelectedFile());

                try (FileReader openFile = new FileReader(path);
                     Scanner sc = new Scanner(openFile)) {
                    StringBuilder fileContent = new StringBuilder();
                    while (sc.hasNextLine()) {
                        fileContent.append(sc.nextLine()).append("\n");
                    }
                    textArea.setText(fileContent.toString());
                } catch (FileNotFoundException openE) {
                    JOptionPane.showMessageDialog(frame, "Error: file not found");
                    openE.printStackTrace();
                }
            } catch (Exception openE) {
                JOptionPane.showMessageDialog(frame, "Error: file not found");
                openE.printStackTrace();
            }
        } else if (e.getSource() == save) {
            try (FileWriter fileWriter = new FileWriter(path)) {
                fileWriter.write(textArea.getText());
                JOptionPane.showMessageDialog(frame, "File Saved");
            } catch (Exception saveE) {
                JOptionPane.showMessageDialog(frame, "Error: file not saved");
                saveE.printStackTrace();
            }
        } else if (e.getSource() == saveAs) {
            fileSave = new JFileChooser();
            fileSave.showOpenDialog(frame);

            String newPath = fileSave.getSelectedFile().getAbsolutePath() + ".txt";
            try {
                File newFile = new File(newPath);
                newFile.createNewFile();
                try (FileWriter fileWriter = new FileWriter(newPath)) {
                    fileWriter.write(textArea.getText());
                    JOptionPane.showMessageDialog(frame, "File Saved at: " + newPath);
                }
            } catch (Exception fileE) {
                JOptionPane.showMessageDialog(frame, "Error file not saved");
                fileE.printStackTrace();
            }

            new File(path).delete();
        } else if (e.getSource() == cut) {
            textArea.cut();
        } else if (e.getSource() == copy) {
            textArea.copy();
        } else if (e.getSource() == paste) {
            textArea.paste();
        } else if (e.getSource() == clear) {
            if (JOptionPane.showConfirmDialog(frame, "Are you sure?") == 0) {
                textArea.setText("");
            }
        } else if (e.getSource() == dark) {
            textArea.setForeground(new Color(255, 213, 98));
            textArea.setBackground(Color.BLACK);
            textArea.setCaretColor(Color.YELLOW);
        } else if (e.getSource() == light) {
            textArea.setForeground(Color.BLACK);
            textArea.setBackground(new Color(255, 213, 98));
            textArea.setCaretColor(Color.BLACK);
        }
    }
}

public class NotePadClone {
    public static void main(String[] args) {
        new NotePad();
    }
}
