/* 
 * Author: Alex L
 * Date: January 7th, 2019
 * Description: The instructions page for the trivia game/summative.
 */

/**
 *
 * @author Alex!
 */

//importing packages
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Instructions extends JFrame implements ActionListener {

    //declaring variables
    private JFrame myFrame;
    private JPanel myPanel;
    private JButton menu;

    public Instructions() { //actual code for instructions
        myFrame = new JFrame("TriviAddicts - Alex"); //frame code
        myFrame.setSize(1630, 950);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);
        myFrame.setResizable(false);
        myFrame.setVisible(false);

        JLabel background; //background code
        ImageIcon img = new ImageIcon("src/Photos/background1.png");
        background = new JLabel("", img, JLabel.CENTER);
        background.setBounds(0, 0, 1630, 1000);

        JLabel header = new JLabel(new ImageIcon("src/Photos/header.png")); //header code
        header.setBounds(100, 15, 1400, 300);

        JLabel body = new JLabel(new ImageIcon("src/Photos/body.png")); //actual instructions (body of text)
        body.setBounds(200, 345, 1200, 375);    

        menu = new JButton(new ImageIcon("src/Photos/instructions-menu.png")); //returning to menu button code
        menu.setContentAreaFilled(false); //no background
        menu.addActionListener(this);
        menu.setBounds(550, 750, 500, 125);

        myPanel = new JPanel();
        myPanel.setLayout(null);
        myPanel.add(header); //adding components to the panel
        myPanel.add(body);
        myPanel.add(menu);
        myPanel.add(background);

        myFrame.setContentPane(myPanel); //adds the panel to the frame
        myFrame.setVisible(true); //makes everything visible
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu) { //when the menu button is clicked
            myFrame.dispose(); //closes the Frame
            new TitlePage(); //opens the new one
        }
    }
}
