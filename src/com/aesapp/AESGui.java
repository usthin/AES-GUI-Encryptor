package com.aesapp;

import javax.swing.*;
import java.awt.*;

public class AESGui {

    public static void createGUI() {

        JFrame frame = new JFrame("AES Encryption & Decryption System");
        frame.setSize(700,550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel inputLabel = new JLabel("Plaintext / Ciphertext:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(inputLabel, gbc);

        JTextArea inputArea = new JTextArea(5,40);
        JScrollPane inputScroll = new JScrollPane(inputArea);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(inputScroll, gbc);

        gbc.gridwidth = 1;

        JLabel keyLabel = new JLabel("Secret Key:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(keyLabel, gbc);

        JTextField keyField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(keyField, gbc);

        JLabel modeLabel = new JLabel("Mode:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(modeLabel, gbc);

        String[] modes = {"ECB","CBC","CFB"};
        JComboBox<String> modeBox = new JComboBox<>(modes);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(modeBox, gbc);

        JLabel keyLengthLabel = new JLabel("Key Length:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(keyLengthLabel, gbc);

        String[] lengths = {"128","192","256"};
        JComboBox<String> keyLengthBox = new JComboBox<>(lengths);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(keyLengthBox, gbc);

        JLabel operationLabel = new JLabel("Operation:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(operationLabel, gbc);

        String[] ops = {"Encrypt","Decrypt"};
        JComboBox<String> operationBox = new JComboBox<>(ops);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(operationBox, gbc);

        JButton runButton = new JButton("Run");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(runButton, gbc);

        JButton saveButton = new JButton("Save Ciphertext");
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(saveButton, gbc);

        JButton loadButton = new JButton("Load From File");
        gbc.gridx = 2;
        gbc.gridy = 6;
        panel.add(loadButton, gbc);

        JLabel resultLabel = new JLabel("Result:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(resultLabel, gbc);

        JTextArea resultArea = new JTextArea(5,40);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane resultScroll = new JScrollPane(resultArea);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        panel.add(resultScroll, gbc);

        runButton.addActionListener(e -> {

            try {

                String text = inputArea.getText();
                String key = keyField.getText();

                if(key.isEmpty()) {
                    resultArea.setText("Key cannot be empty.");
                    return;
                }

                String mode = (String) modeBox.getSelectedItem();
                int keyLength = Integer.parseInt((String) keyLengthBox.getSelectedItem());
                String operation = (String) operationBox.getSelectedItem();

                if(operation.equals("Encrypt")) {

                    String encrypted = AESUtil.encrypt(text,key,mode,keyLength);

                    resultArea.setText(encrypted);

                } else {

                    String decrypted = AESUtil.decrypt(text,key,mode,keyLength);

                    resultArea.setText(decrypted);

                }

            } catch(Exception ex) {

                resultArea.setText("Error: " + ex.getMessage());

            }

        });

        saveButton.addActionListener(e -> {

            try {

                FileManager.saveToFile(resultArea.getText());

                resultArea.setText("Ciphertext saved to ciphertext.txt");

            } catch(Exception ex) {

                resultArea.setText("File save error.");

            }

        });

        loadButton.addActionListener(e -> {

            try {

                String content = FileManager.readFromFile();

                inputArea.setText(content);

            } catch(Exception ex) {

                resultArea.setText("File read error.");

            }

        });

        frame.add(panel);
        frame.setVisible(true);
    }
}