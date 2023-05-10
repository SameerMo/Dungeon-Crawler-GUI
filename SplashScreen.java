package OOP.ec22707.MP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen {
    private boolean completed;
    public SplashScreen(){
        JFrame splashScreen = new JFrame();
        JLabel welcomeMessage = new JLabel("Welcome to the haunted neighbourhood game");
        JLabel credits = new JLabel("Credits:");
        JLabel credits2 = new JLabel("Everyone");
        JLabel credits3 = new JLabel("(except those with code that didn't compile...smh my head)");
        JLabel nicknameLabel = new JLabel("Enter a Nickname:");
        JTextField nicknameField = new JTextField(10);
        JButton continueButton = new JButton("Continue");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        welcomePanel.add(welcomeMessage);
        panel.add(welcomePanel);
        JPanel creditsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        creditsPanel.add(credits);
        panel.add(creditsPanel);
        JPanel credits2Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        credits2Panel.add(credits2);
        panel.add(credits2Panel);
        JPanel credits3Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        credits3Panel.add(credits3);
        panel.add(credits3Panel);
        JPanel nicknamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nicknamePanel.add(nicknameLabel);
        nicknamePanel.add(nicknameField);
        panel.add(nicknamePanel);
        JPanel continueButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        continueButtonPanel.add(continueButton);
        panel.add(continueButtonPanel);
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nick = nicknameField.getText().trim();
                if (nick.isEmpty()) {
                    JOptionPane.showMessageDialog(splashScreen, "Please enter a nickname.");
                    return;
                }
                setNick(nick);
                completed = true;
                splashScreen.dispose();
            }
        });
        splashScreen.getContentPane().add(panel);
        splashScreen.setSize(400, 400);
        splashScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setVisible(true);
    }
    public static void setNick(String nick) {
        GUIVisitor_ec22707.nickname = nick;
    }

    public boolean isCompleted() {
        return completed;
    }

}