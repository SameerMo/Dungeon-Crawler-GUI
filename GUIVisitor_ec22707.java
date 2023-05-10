package OOP.ec22707.MP;

import java.awt.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GUIVisitor_ec22707 implements Visitor {
    public static String nickname = "";
    private int purse;
    private ArrayList<Item> items;
    private JFrame rootPanel;
    private JTextArea goldBalance;
    private JTextArea inventory;

    private JTextArea output;
    private JScrollPane outputScrollPane;
    private JPanel inputButtons;
    private JPanel info;
    private JTextArea attempts;
    private JButton[] userInput;

    public GUIVisitor_ec22707() {
        items = new ArrayList<>();
        purse = 0;
        rootPanel = new JFrame("GUIVisitor ec22707");
        goldBalance = new JTextArea(1, 20);
        inventory = new JTextArea(20, 20);
        output = new JTextArea(20, 40);
        inputButtons = new JPanel(new GridLayout(1, 0));
        info = new JPanel(new GridLayout(2, 1));
        attempts = new JTextArea(1,20);
        goldBalance.setEditable(false);
        inventory.setEditable(false);
        output.setEditable(false);
        attempts.setEditable(false);
        inputButtons = new JPanel(new GridLayout(1, 0));
        outputScrollPane = new JScrollPane(output);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        info.add(goldBalance);
        info.add(attempts);
        rootPanel.add(info, BorderLayout.LINE_START);
        rootPanel.add(inventory, BorderLayout.LINE_END);
        rootPanel.add(outputScrollPane, BorderLayout.CENTER);
        rootPanel.add(inputButtons, BorderLayout.PAGE_END);
        rootPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        goldBalance.append("Gold: " + purse);
        inventory.append("Inventory:");
        attempts.append("Previous Attempts:\n"+viewAttempts());
        rootPanel.setPreferredSize(new Dimension(1400, 1000));
        rootPanel.pack();
        rootPanel.setLocationRelativeTo(null);
        rootPanel.setVisible(true);
    }

    @Override
    public void tell(String messageForVisitor) {
        output.append(messageForVisitor + "\n");
    }

    @Override
    public char getChoice(String descriptionOfChoices, char[] arrayOfPossibleChoices) {
        userInput = new JButton[arrayOfPossibleChoices.length];
        for (int i = 0; i < arrayOfPossibleChoices.length; i++) {
            JButton button = new JButton(String.valueOf(arrayOfPossibleChoices[i]));
            userInput[i] = button;
            userInput[i].setPreferredSize(new Dimension(50, 50));
            inputButtons.add(button);
        }
        inputButtons.revalidate();
        inputButtons.repaint();
        output.append(descriptionOfChoices + "\n");
        while (true) {
            for (int i = 0; i < userInput.length; i++) {
                if (userInput[i].getModel().isPressed()) {
                    char choice = arrayOfPossibleChoices[i];
                    for (JButton button : userInput) {
                        inputButtons.remove(button);
                    }
                    inputButtons.revalidate();
                    inputButtons.repaint();
                    return choice;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean giveItem(Item itemGivenToVisitor) {
        char[] yOrN = {'y', 'n'};
        if (getChoice("You are being offered: " + itemGivenToVisitor.name + "\n Do you accept?", yOrN) == 'y') {
            items.add(itemGivenToVisitor);
            inventory.setText("");
            inventory.setText("Inventory: \n" + showInventory());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasIdenticalItem(Item itemToCheckFor) {
        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).equals(items.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasEqualItem(Item itemToCheckFor) {
        if (items.contains(itemToCheckFor)) {
            return true;
        }
        return false;
    }

    @Override
    public void giveGold(int numberOfPiecesToGive) {
        output.append("You are given " + numberOfPiecesToGive + " gold pieces." + "\n");
        purse += numberOfPiecesToGive;
        goldBalance.setText("Gold: " + purse);
    }

    @Override
    public int takeGold(int numberOfPiecesToTake) {
        if (numberOfPiecesToTake < 0) return 0;
        int t = 0;
        if (numberOfPiecesToTake > purse) t = purse;
        else t = numberOfPiecesToTake;
        output.append(t + " pieces of gold are taken from you." + "\n");
        output.append("You now have " + purse + " pieces of gold." + "\n");
        return t;
    }

    public String showInventory() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            Item currentItem = items.get(i);
            boolean isDuplicate = false;
            for (int j = 0; j < i; j++) {
                if (items.get(j).name.equals(currentItem.name)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate) {
                continue;
            }
            int count = 1;
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(j).name.equals(currentItem.name)) {
                    count++;
                }
            }
            sb.append(currentItem.name).append("x").append(count).append("\n ");
        }
        return sb.toString().trim();
    }

    public void addAttempt(){
        char[] yOrN = {'y', 'n'};
        if (getChoice("Would you like to record your attempt?", yOrN) == 'y') {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            try {
                FileWriter writer = new FileWriter("attempts.txt", true);
                writer.write(nickname + "," + purse + "," + currentDate + "\n");
                output.append("Your attempt has been successfully recorded\n");
                writer.close();
            } catch (IOException e) {
                output.append("An error occurred.\n");
            }
        }
    }

    public String viewAttempts() {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader reader = new FileReader("attempts.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String attempt;
            while ((attempt = bufferedReader.readLine()) != null) {
                sb.append(attempt).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            output.append("An error occurred.");
        }
        return sb.toString().trim();
    }

}

