/*
 * Author: Alex L
 * Date: January 7th, 2019
 * Description: The Game Over Page when the players get 3 questions wrong.
 */

/**
 *
 * @author Alex!
 */

//importing packages
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class GameOver extends JFrame implements ActionListener {

    //declaring variables
    private final JFrame myFrame;
    private JPanel myPanel;
    private JButton menu;
    private final int score = Game.getScore(); //the users score at the end
    private Font font;
    private JLabel[] fortniteDefaultDance = new JLabel[2];
    private int DancePlacementX = 80;

    public GameOver() { //actual code for the game over page
        createFont(); //creates custom font
        
        myPanel = new JPanel();
        
        myFrame = new JFrame("TriviAddicts - Alex"); //myFrame code
        myFrame.setSize(1630, 950);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);
        myFrame.setResizable(false);
        myFrame.setVisible(false);

        JLabel background; //background code
        ImageIcon img = new ImageIcon("src/Photos/background1.png");
        background = new JLabel("", img, JLabel.CENTER);
        background.setBounds(0, 0, 1630, 1000);

        JLabel gameOver = new JLabel(new ImageIcon("src/Photos/gameOver.png")); //"gameOver header
        gameOver.setBounds(300, 75, 1000, 240);
        gameOver.setLayout(null);

        JLabel finalScore = new JLabel("Score: " + score, SwingConstants.CENTER); //centers the text, score code
        finalScore.setFont(font); //uses custom font
        finalScore.setOpaque(false);
        finalScore.setForeground(Color.white); //sets text color
        finalScore.setBorder(BorderFactory.createLineBorder(Color.white, 3));
        finalScore.setBounds(450, 400, 700, 180);
        
        for (int i = 0; i < 2; i++) { //making the fortnite default dance gifs
            fortniteDefaultDance[i] = new JLabel(new ImageIcon("src/Photos/GameOver.gif")); //gets the component
            fortniteDefaultDance[i].setBounds(DancePlacementX, 360 ,272, 320); //places the gifs
            fortniteDefaultDance[i].setBorder(BorderFactory.createLineBorder(Color.white, 3)); //sets border
            myPanel.add(fortniteDefaultDance[i]);
            DancePlacementX += 1180; //changes the placement for the second gif
        }

        menu = new JButton(new ImageIcon("src/Photos/gameover-menu.png")); //returning to menu button
        menu.setContentAreaFilled(false); //no background
        menu.addActionListener(this);
        menu.setBounds(550, 660, 500, 120);

        myPanel.setLayout(null);
        myPanel.add(gameOver); //adding components to JPanel
        myPanel.add(finalScore);
        myPanel.add(menu);
        myPanel.add(background);

        myFrame.setContentPane(myPanel); //adding JPanel to JFrame
        myFrame.setVisible(true); //Making it all visible
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu) {
            myFrame.dispose(); //closes the frame
            new TitlePage(); //goes back to title page
        }
    }

    private void createFont() { //creates the font
        try {
            Font originalFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Prisma.ttf"));
            font = originalFont.deriveFont(100f);

        } catch (IOException | FontFormatException e) {
            //do nothing
        }

    } //font taken from oracle website; URL: https://docs.oracle.com/javase/tutorial/2d/text/fonts.html
}
