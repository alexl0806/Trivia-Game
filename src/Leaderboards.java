/*
 * Author: Alex L
 * Date: January 7th, 2019
 * Description: The leaderboards part of the summative that will use String Manipulation & File I/O
 */

/**
 *
 * @author Alex!
 */
//import packages
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import java.io.IOException;

public class Leaderboards extends JFrame implements ActionListener {

    //declaring variables
    private JFrame myFrame;
    private static JPanel myPanel;
    private static JLabel leaderboards, header; //displays the header & the actual leaderboards page
    private JButton menu;

    private static Font font, headerFont;

    private int score = Game.getScore();
    private String name = TitlePage.getUsername();
    
    //html parts
    private static final String html = "</div></html>"; //centers JLabel text
    private static final String anotherHTML = "<html><div style='text-align: center;'>";

    private static String filepath = "Leaderboards.txt"; //file path of the text file

    public Leaderboards() {
        createFont(); //creates font

        myFrame = new JFrame("TriviAddicts - Alex"); //myFrame code
        myFrame.setSize(1630, 950);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);
        myFrame.setResizable(false);
        myFrame.setVisible(false);

        myPanel = new JPanel(); //instantiating the Jpanel
        myPanel.setLayout(null); //ensure that .setBounds() actually works

        JLabel background; //background code
        ImageIcon img = new ImageIcon("src/Photos/background1.png");
        background = new JLabel("", img, JLabel.CENTER);
        background.setBounds(0, 0, 1630, 1000);

        menu = new JButton(new ImageIcon("src/Photos/game-menu.png")); //menu button code
        menu.setContentAreaFilled(false); //removes the background
        menu.addActionListener(this);
        menu.setBounds(15, 18, 150, 150);
        menu.setBorderPainted(false); //makes the auto-generated border around buttons invisible

        header = new JLabel("Leaderboards", SwingConstants.CENTER); //header label code
        header.setFont(headerFont); //sets custom font
        header.setForeground(Color.white); //sets text color
        header.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5)); //creates border
        header.setBounds(215, 25, 1200, 190);

        displayLeaderboards();

        myPanel.add(menu); //adding componenents to JPanel
        myPanel.add(header);
        myPanel.add(background);

        myFrame.setContentPane(myPanel); //making JPanel visible on JFrame
        myFrame.setVisible(true); //makes frame visible

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu) { //if menu button is clicked
            myFrame.dispose(); //closes frame
            new TitlePage(); //goes back to title page
        }
    }

    private void createFont() { //creates the font
        try {
            Font originalFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Prisma.ttf")); //takes font file
            font = originalFont.deriveFont(43f); //changes its size
            headerFont = originalFont.deriveFont(130f);

        } catch (IOException | FontFormatException e) {
            //do nothing
        }

    } //font code inspired from oracle website; URL: https://docs.oracle.com/javase/tutorial/2d/text/fonts.html

    public static void checkLeaderboards(String nme, int s) { //checks the leaderboards
        String[] username = new String[10]; //string for the top 10 score usernames
        int[] scores = new int[10]; //top 10 scores

        try {
            IO.openInputFile(filepath); //opens .txt file

            for (int i = 0; i < 10; i++) {  //for-loop (prints the .txt files information to the arrays)
                String n = IO.readLine(); //lines of the .txt file
                int lastSpace = n.lastIndexOf(" "); //to get their score

                String playerNames = n.substring(3, lastSpace); //takes their username
                int playerScores = Integer.parseInt(n.substring(lastSpace + 1, n.length())); //parses their score into an int

                username[i] = playerNames; //saves the usernames
                scores[i] = playerScores; //saves the scores
            }

            for (int j = 0; j < 10; j++) { //for-loop (compares the scores)
                if (s > scores[j]) { //if their score is larger
                    for (int k = 9; k > j; k--) { //shifts each score down accordingly
                        scores[k] = scores[k - 1]; //shifts scores
                        username[k] = username[k - 1]; //shifts usernames
                    }
                    scores[j] = s; //if its equal or less (but still in top 10), includes score
                    username[j] = nme; //includes username

                    break; //stops loop before bugging the leaderboards

                }
            }

            IO.closeInputFile(); //closes .txt file

            IO.createOutputFile(filepath); //re-creates the .txt file 

            for (int l = 0; l < 10; l++) { //for-loop (prints the arrays into the .txt file)
                IO.println((l + 1) + ". " + username[l] + " " + scores[l]); //prints the information
            }

            IO.closeOutputFile(); //closes the .txt file

        } catch (IOException e) { //if an error is present
            System.out.println("error");
        }
    }

    private static void displayLeaderboards() { //displays the leaderboards
        String leaderboardsText = ""; //String (concatenates the information to be displayed from the .txt file)

        leaderboards = new JLabel("", SwingConstants.CENTER); //JLabel that displays the leaderboards
        leaderboards.setFont(font);
        leaderboards.setForeground(Color.white);
        leaderboards.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5)); //creates border
        leaderboards.setBounds(215, 250, 1200, 625);

        try {
            IO.openInputFile(filepath); //opens .txt file

            int i = 0; //for while-loop

            while (i < 10) { //while-loop (copies the .txt file's information to the String)
                leaderboardsText += IO.readLine() + "<br/>"; //adds a new line every time
                i++; //increment
            }

        } catch (IOException e) {

        }

        leaderboards.setText(anotherHTML + leaderboardsText + html); //sets text
        myPanel.add(leaderboards); //adds to JPanel

    }

}
