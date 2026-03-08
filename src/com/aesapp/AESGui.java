package com.aesapp;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AESGui {

    public static void createGUI() {

        JFrame frame = new JFrame("AES Encryption System");
        frame.setSize(600,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JTextArea inputText = new JTextArea();
        inputText.setBounds(50,50,500,80);
        frame.add(inputText);

        JTextField keyField = new JTextField();
        keyField.setBounds(50,150,200,30);
        frame.add(keyField);

        String[] modes = {"ECB","CBC","CFB"};
        JComboBox<String> modeBox = new JComboBox<>(modes);
        modeBox.setBounds(300,150,100,30);
        frame.add(modeBox);

        String[] keys = {"128","192","256"};
        JComboBox<String> keyBox = new JComboBox<>(keys);
        keyBox.setBounds(420,150,100,30);
        frame.add(keyBox);

        JTextArea output = new JTextArea();
        output.setBounds(50,300,500,80);
        frame.add(output);

        JButton encryptBtn = new JButton("Encrypt");
        encryptBtn.setBounds(150,220,120,40);
        frame.add(encryptBtn);

        JButton decryptBtn = new JButton("Decrypt");
        decryptBtn.setBounds(300,220,120,40);
        frame.add(decryptBtn);

        JButton saveBtn = new JButton("Save to File");
        saveBtn.setBounds(200,400,150,30);
        frame.add(saveBtn);

        encryptBtn.addActionListener((ActionEvent e) -> {

            try {

                String text = inputText.getText();
                String key = keyField.getText();
                String mode = (String) modeBox.getSelectedItem();
                int keyLen = Integer.parseInt((String) keyBox.getSelectedItem());

                String encrypted = AESUtil.encrypt(text,key,mode,keyLen);

                output.setText(encrypted);

            } catch(Exception ex){
                output.setText("Error: "+ex.getMessage());
            }

        });

        decryptBtn.addActionListener((ActionEvent e) -> {

            try {

                String text = inputText.getText();
                String key = keyField.getText();
                String mode = (String) modeBox.getSelectedItem();
                int keyLen = Integer.parseInt((String) keyBox.getSelectedItem());

                String decrypted = AESUtil.decrypt(text,key,mode,keyLen);

                output.setText(decrypted);

            } catch(Exception ex){
                output.setText("Error: "+ex.getMessage());
            }

        });

        saveBtn.addActionListener(e -> {

            try {

                FileManager.saveToFile(output.getText(),"encrypted.txt");

            } catch(Exception ex){
                output.setText("File error");
            }

        });

        frame.setVisible(true);
    }
}