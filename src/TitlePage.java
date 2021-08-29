/*  
 * Author: Alex L
 * Date: January 7th, 2018 
 * Description: The title page for the trivia game/summative
 */

//importing packages
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TitlePage extends JFrame implements ActionListener {

    //Declaring Variables
    private JFrame myFrame;
    private JPanel myPanel;
    private JButton play, leaderboards, instructions, exit;
    private static String name = ""; //Player's Username for Leaderboard

    public static void main(String[] args) { //executes the program
        name = JOptionPane.showInputDialog("Please Input Your Username (1-15 characters long)"); //input message box
        checkName(name); //method that checks if the name is not null
        new TitlePage(); //executes the title page
    }

    public TitlePage() { //actual code for title page
        myFrame = new JFrame("TriviAddicts  - Alex"); //frame code
        myFrame.setSize(1630, 950);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setVisible(false);

        JLabel background; //background code
        ImageIcon img = new ImageIcon("src/Photos/background1.png");
        background = new JLabel("", img, JLabel.CENTER);
        background.setBounds(0, 0, 1630, 1000);

        JLabel title = new JLabel(new ImageIcon("src/Photos/TitleLabel.png")); //title "TriviAddicts" code
        title.setBounds(100, 10, 1400, 300);

        play = new JButton(new ImageIcon("src/Photos/play.png")); //play button code
        play.setContentAreaFilled(false); //no background
        play.addActionListener(this);
        play.setBounds(970, 340, 500, 500);

        leaderboards = new JButton(new ImageIcon("src/Photos/leaderboards.png")); //leaderboards button code
        leaderboards.setContentAreaFilled(false); //no background
        leaderboards.addActionListener(this);
        leaderboards.setBounds(100, 340, 700, 150);

        instructions = new JButton(new ImageIcon("src/Photos/instructions.png")); //instructions button code
        instructions.setContentAreaFilled(false); //no background
        instructions.addActionListener(this);
        instructions.setBounds(100, 515, 700, 150);

        exit = new JButton(new ImageIcon("src/Photos/exit.png")); //exit button code        
        exit.setContentAreaFilled(false); //no background
        exit.addActionListener(this);
        exit.setBounds(100, 690, 700, 150);

        myPanel = new JPanel(); //main panel
        myPanel.setLayout(null);
        myPanel.add(title); //adding all the components
        myPanel.add(play);
        myPanel.add(leaderboards);
        myPanel.add(instructions);
        myPanel.add(exit);
        myPanel.add(background);

        myFrame.setContentPane(myPanel); //setting the content pane
        myFrame.setVisible(true); //making everything visible
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) { //when the exit button is clicked
            System.exit(0); //close program

        } else if (e.getSource() == instructions) { //when the instructions button is clicked
            myFrame.dispose(); //closes frame
            new Instructions(); //goes to a new one

        } else if (e.getSource() == leaderboards) { //when the leaderboards button is clicked
            myFrame.dispose(); //closes frame
            new Leaderboards(); //goes to a new one

        } else if (e.getSource() == play) { //when the play button is clicked
            myFrame.dispose(); //closes frame
            new Game(); //goes to a new one
        }
    }

    private static void checkName(String s) { //checks whether or not the name is legitimate
        if (s == null) {
            System.exit(0);
            
        } else if (s.length() > 15 || "".equals(s)) {
            name = "Guest";
        }

    }

    public static String getUsername() { //returns the Username if needed to other classes
        return name;
    }

}