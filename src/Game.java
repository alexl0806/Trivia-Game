/* 
 * Author: Alex L
 * Date: January 7th, 2019
 * Description: Code for the actual trivia game itself for the ICS3U summative.
 */

/**
 *
 * @author Alex!
 */

//importing packages
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;

public class Game extends JFrame implements ActionListener {

    //declaring variables
    
    //components
    private final JFrame myFrame;
    private final JPanel myPanel;
    private final JButton menu;
    JLabel wrong, timerBorder, clock, questionBox, question, scoreLabel;

    //timer
    private Timer timer;

    //font
    private Font font; //question font
    private Font buttonFont;
    private Font livesFont; //font for lives & score

    //arrays
    private final JLabel[] lifeIcon = new JLabel[4]; //for life icons
    private final static String[] questions = new String[35]; //questions
    private Button[] buttons = new Button[4]; //using the new class
    private final String[] wrongAnswers = new String[105]; //wrong answers
    private final String[] correctAnswers = new String[35]; //right answers
    private int[] answers = new int[35]; //just memorizing their placement to check if an answer is correct or not 

    //integers
    private static int score = 0;
    private int lives = 3;
    private int questionNum = 0; //question number
    private int lifeIconBox = 3; //graphical lives
    private int buttonPlacementX = 240; //since the for-loop creates the buttons, the positions need to change every loop
    private int buttonPlacementY = 450; //^
    private int buttonPlacementCount = 0; //since two button will have the same x and y axis, it needs to be able to reset
    private int numQuestionChecker = 0; //helps input the right text for the buttons
    private int livesPlacementX = 1320; //changes the placement of the life icons

    //html parts
    private final String html = "</div></html>"; //helps center JLabel text when breaking a line is required
    private final String anotherHTML = "<html><div style='text-align: center;'>";

    public Game() {
        fillArray(); //gets the correct answers
        fillQuestions(); //fills the question array with questions
        fillBadAnswers(); //fills the possible answers arraywith wrong answers 
        fillRightAnswers(); //fills the correct answers array with correct answers

        createFont(); //creates the font

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

        timerBorder = new JLabel(new ImageIcon("src/Photos/timer-circle.png")); //border of the timer
        timerBorder.setBounds(1450, 18, 150, 150);

        clock = new JLabel("5", SwingConstants.CENTER); //template that the timer has to follow
        clock.setFont(font);
        clock.setForeground(Color.WHITE);
        clock.setBounds(1450, 18, 150, 150);

        menu = new JButton(new ImageIcon("src/Photos/game-menu.png")); //menu button code
        menu.setContentAreaFilled(false); //removes the background
        menu.addActionListener(this);
        menu.setBounds(15, 18, 150, 150);
        menu.setBorderPainted(false); //makes the auto-generated border around buttons invisible

        questionBox = new JLabel(new ImageIcon("src/Photos/questionBox.png")); //created the border of each question
        questionBox.setLayout(null);
        questionBox.setBounds(180, 18, 1250, 400);

        question = new JLabel("", SwingConstants.CENTER); //question box code
        question.setFont(font); //sets custom font
        question.setForeground(Color.white); //sets text color
        question.setBounds(180, 18, 1250, 400);

        wrong = new JLabel(new ImageIcon("src/Photos/wrongLabel.png")); //wrong label code (the box)
        wrong.setLayout(null);
        wrong.setBounds(180, 200, 1250, 500);
        wrong.setVisible(false); //makes it invisible until called upon

        //answer buttons
        for (int b = 0; b < buttons.length; b++) { //for each button
            buttons[b] = new Button();
            buttons[b].setNum(b); //sets a number for answer recognition
            buttons[b].setBounds(buttonPlacementX, buttonPlacementY, 525, 150);

            if (buttonPlacementCount % 2 == 0 || buttonPlacementCount == 0) { //if it is the second & fourth button
                buttonPlacementX += 600; //change their x-placement
            } else if (buttonPlacementCount % 2 != 0) { //if its the 1st or third button
                buttonPlacementY += 200; //change their y=placement
                buttonPlacementX -= 600; //reset x-placement
            }

            buttonPlacementCount++; //adds a button count (so the placement works)

            buttons[b].setContentAreaFilled(false); //no background
            buttons[b].setFocusPainted(false); //no border around the text
            buttons[b].setFont(buttonFont); //sets custom font
            buttons[b].setForeground(Color.white); //sets text color
            buttons[b].addActionListener(this);
            buttons[b].setBorder(BorderFactory.createLineBorder(Color.white, 3)); //sets a border
            myPanel.add(buttons[b]);
        }

        //life icon label loop
        for (int i = 0; i < 4; i++) {
            if (i == 0) { //if it is the first (ie: the text label)
                lifeIcon[i] = new JLabel("Lives:", SwingConstants.CENTER); //initialize
                lifeIcon[i].setForeground(Color.white); //sets text color
                lifeIcon[i].setFont(livesFont); //sets custom font
                lifeIcon[i].setBounds(1200, 780, 180, 150);

            } else { //if it is the actual lives icon
                lifeIcon[i] = new JLabel(new ImageIcon("src/Photos/lives.png")); //sets image & initialize
                lifeIcon[i].setBounds(livesPlacementX, 835, 51, 45);

            }

            livesPlacementX += 53; //changes the placement of each icon
            myPanel.add(lifeIcon[i]); //adds icon to panel

        }

        scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER); //score label code
        scoreLabel.setFont(livesFont); //sets custom font
        scoreLabel.setForeground(Color.WHITE); //sets text color
        scoreLabel.setBounds(-70, 775, 500, 150); //placement

        nextQuestion(0); //starts the game

        myPanel.add(wrong); //adding components to JPanel
        myPanel.add(clock);
        myPanel.add(timerBorder);
        myPanel.add(menu);
        myPanel.add(questionBox);
        myPanel.add(question);
        myPanel.add(scoreLabel);
        myPanel.add(background);

        myFrame.setContentPane(myPanel); //displays the JPanel on the Frame
        myFrame.setVisible(true); //makes the frame visible
        

    }

    private void timer() { //code for the actual timer
        timer = new Timer(1, new ActionListener() { //instantiates a new timer with a 0.001 second delay
            int count = 5; //10 seconds to countdown .setText on clock
            int countDown = 0; //actual timer itself

            public void actionPerformed(ActionEvent e) {
                if (count <= 0) { //if they're out of time
                    clock.setText("0"); //0 seconds

                    ((Timer) e.getSource()).stop(); //stops the timer

                    count = 5; //resets the timer
                    countDown = 0; //&  

                    wrongLabel(); //display the "wrong!"

                    lives--; //subtract a live
                    lifeIcon[lifeIconBox].setVisible(false); //takes a life away graphically

                    checkGameOver(); //checks to see if all the lives have been lost

                    lifeIconBox--; //increases it by one so next time, it'll remove the next life

                    questionNum++; //the question number goes up if GameOver() is false
                    nextQuestion(questionNum); //next question

                } else {
                    countDown++;
                    clock.setText(String.valueOf(count)); //if they still have time, set the text to that time

                    if (countDown % 500 == 0) { //timer code
                        count--;
                    }

                }
            }
        });

        timer.start(); //start the timer

    } //code is inspired from a StackOverflow Question, URL: https://stackoverflow.com/questions/26965049/java-gui-countdown

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttons.length; i++) { //for every button
            if (e.getSource() == buttons[i]) { //if that button was clicked
                timer.stop(); //stop the timer
                if (checkCorrect(e) == true) { //if they clicked the right answer
                    questionNum++; //the question number goes up

                    score += 500; //score increases
                    scoreLabel.setText("Score: " + score); //updates score

                    nextQuestion(questionNum); //next question

                } else {
                    wrongLabel(); //display the "wrong!"

                    lives--; //subtract a live
                    lifeIcon[lifeIconBox].setVisible(false); //takes a life away graphically

                    checkGameOver(); //checks to see if all the lives have been lost

                    lifeIconBox--; //decreases it by one so next time, it'll remove the next life

                    questionNum++; //the question number goes up if GameOver() is false
                    nextQuestion(questionNum); //next question
                }
            }
        }

        if (e.getSource() == menu) { //if menu button is cliced
            myFrame.dispose(); //close frame
            new TitlePage();
            timer.stop(); //stops timer
            score = 0; //resets score
        }
    }

    public static int getScore() {
        return score;
    } //returns score

    private void checkGameOver() { //checks to see if it's game over for the player
        if (lives == 0) { //if they have no more lives
            gameOverTimer();
        }
    } //checks if the game is over (0 lives remaining)

    private void gameOverTimer() { //delays the arrival of the game over page, lets user see wrong label
        Timer gameOverTimer = new Timer(1, new ActionListener() { //initializing new timer
            int delay = 0; //count

            public void actionPerformed(ActionEvent e) {
                if (delay == 300) { //if a certain time has passed
                    myFrame.dispose(); //close frame
                    Leaderboards.checkLeaderboards(TitlePage.getUsername(), score);

                    new GameOver(); //game over
                    timer.stop(); //stops timer (prevents bugging)
                    score = 0; //resets score
                    ((Timer) e.getSource()).stop(); //stops this timer
                    delay = 0; //resets count

                } else {
                    wrongLabel(); //just display wrong label
                    delay++; //increase delay
                }
            }
        });
        gameOverTimer.start();
    } //basically a method that shows the player the wrong label before entering the game over page (aesthetics)

    private boolean checkCorrect(ActionEvent e) {
        for (int k = 0; k < buttons.length; k++) { //check for every button

            if (e.getSource() == buttons[k]) { //if they did indeed click a button

                if (buttons[k].getNum() == answers[questionNum]) { //if the numbers match up
                    return true; //it is correct

                } else { //else, it is incorrect
                    return false; //return false

                }
            }
        }

        return false; //hopefully never arrives here

    } //checks if the user clicked the correct button

    public void nextQuestion(int qNum) {
        Timer delay = new Timer(1, new ActionListener() { //timer for delay (wrong label pops up too late)
            int delayTime = 0; //sets a timer variable

            public void actionPerformed(ActionEvent e) { //timer
                if (delayTime >= 2) { //just waiting ~0.01 seconds
                    delayTime = 0; //resets timer
                    ((Timer) e.getSource()).stop(); //stops timer

                    if (qNum > 34) { //if it is the last question
                        myFrame.dispose(); //close frame
                        Leaderboards.checkLeaderboards(TitlePage.getUsername(), score);
                        new GameOver(); //instant game over
                        score = 0; //resets score
                        timer.stop(); //stops timer

                    } else { //if not last question
                        question.setText(questions[qNum]); //sets question text
                        buttons[answers[qNum]].setText(correctAnswers[qNum]); //sets button text for the one whose .getNum corresponds with the integer inside the answers array

                        for (int i = 0; i < buttons.length; i++) { //loop for every other button
                            if (i != answers[qNum]) { //if it isn't the answer button
                                buttons[i].setText(wrongAnswers[numQuestionChecker]); //use the other wrong answers
                                numQuestionChecker++; //this is to prevents bugs, won't add for the one with the right answer, prevents a button from showing a wrong answer for a different question
                            }

                        }

                        timer(); //calls upon a new timer everytime    

                    }
                } else {
                    delayTime++; //keeps timer going before next question
                }
            }
        });

        delay.start(); //starts delay

    } //essentially a method that goes to the next question of the quiz

    private void wrongLabel() { //exactly what the name says; displays a label that has the text: "wrong!"
        Timer wrongLabel = new Timer(1, new ActionListener() { //starts a timer
            int wrongLabelTimer = 0; //begins at 0

            public void actionPerformed(ActionEvent e) {
                if (wrongLabelTimer == 400) { //once two seconds happen
                    wrong.setVisible(false); //turn off the wrong label
                    questionBox.setVisible(true); //counteracts the .setVisible(false)
                    question.setVisible(true); //^

                    for (int i = 0; i < 4; i++) { //turns all the buttons visible
                        buttons[i].setVisible(true);
                    }

                    ((Timer) e.getSource()).stop(); //stop timer
                    wrongLabelTimer = 0; //reset

                } else {
                    wrong.setVisible(true); //keep the wrong label
                    questionBox.setVisible(false); //makes it so the label is the only visible thing
                    question.setVisible(false); //^

                    for (int i = 0; i < 4; i++) { //turns all the buttons invisible
                        buttons[i].setVisible(false);
                    }

                    wrongLabelTimer++; //add
                }
            }
        });

        wrongLabel.start(); //starts timer

    } //displays a fat "wrong!" when the player gets the question wrong

    private void createFont() { //creates the font
        try {
            Font originalFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Prisma.ttf")); //takes font file
            font = originalFont.deriveFont(80f); //changes its size
            buttonFont = originalFont.deriveFont(60f); //changes its size
            livesFont = originalFont.deriveFont(50f);

        } catch (IOException | FontFormatException e) {
            //do nothing
        }

    } //font code inspired from oracle website; URL: https://docs.oracle.com/javase/tutorial/2d/text/fonts.html

    private void fillArray() { //fills the answer array with random number. This ensure a randomized quiz.
        for (int i = 0; i < answers.length; i++) { //for every value in the array
            answers[i] = (int) (Math.random() * 4); //sets a range
        }
    } //fills up the answers array 

    private void fillQuestions() { //fills up the array of questions (could've used I/O, but I'd need to type out the questions anyway)
        questions[0] = anotherHTML + "What is the 5th book in the Diary of a Wimpy Kid Series?" + html;
        questions[1] = anotherHTML + "How Many Electrons Does <br> a Hydrogen Atom Have?" + html;
        questions[2] = anotherHTML + "A Blunderbuss is an obsolete type of what?" + html;
        questions[3] = anotherHTML + "What is the highest number found on a roulette Wheel?" + html;
        questions[4] = anotherHTML + "In what country were the winter olympics first held?" + html;
        questions[5] = anotherHTML + "Alexander the Great was taught by which Greek Philosopher?" + html;
        questions[6] = anotherHTML + "What heavy metal <br> element was <br> once known as quicksilver?" + html;
        questions[7] = anotherHTML + "What gives red blood cells their color?" + html;
        questions[8] = anotherHTML + "The adult human skeleton <br> is made up of how many bones?" + html;
        questions[9] = anotherHTML + "The Expression 'Oy Vey' <br> Comes from which language?" + html;
        questions[10] = anotherHTML + "Where does Spongebob Squarepants work?" + html;
        questions[11] = anotherHTML + "What comic's final panel shows a boy & a tiger sledding away?" + html;
        questions[12] = anotherHTML + "What flightless bird is featured on New Zealand's <br> 1 Dollar Coin?" + html;
        questions[13] = anotherHTML + "What is the name of <br> Donald Duck's Sister?" + html;
        questions[14] = anotherHTML + "What Was the name of the first electronic <br> general-purpose computer?" + html;
        questions[15] = anotherHTML + "Which country does not use the metric system?" + html;
        questions[16] = anotherHTML + "In What Country Would <br> you find the yellow river?" + html;
        questions[17] = anotherHTML + "Which Spanish Island is known as “The Island of Eternal Spring”?" + html;
        questions[18] = anotherHTML + "What is the white part of <br> the inside of an egg called?" + html;
        questions[19] = anotherHTML + "Ferdinand III was the longest <br> reigning monarch of what <br> former Kingdom?" + html;
        questions[20] = anotherHTML + "Who Was the First Frankish King?" + html;
        questions[21] = anotherHTML + "The island of Saipan is a commonwealth of which country?" + html;
        questions[22] = anotherHTML + "Which painter started the impressionist movement?" + html;
        questions[23] = anotherHTML + "What hills border Scotland and England?" + html;
        questions[24] = anotherHTML + "Rubies are made of what <br> rock-forming mineral?" + html;
        questions[25] = anotherHTML + "What is the only sea on Earth with no coastline?" + html;
        questions[26] = anotherHTML + "Aspirin comes from the <br> bark of what tree?" + html;
        questions[27] = anotherHTML + "Who sang 'Mo Bamba'?" + html;
        questions[28] = anotherHTML + "Bubble tea originated in which country?" + html;
        questions[29] = anotherHTML + "what part of the eye determines ones eye color?" + html;
        questions[30] = anotherHTML + "The desire to eat strange, <br> non-nutritive things is:" + html;
        questions[31] = anotherHTML + "Who is known as the <br> 'X-God'?" + html;
        questions[32] = anotherHTML + "At what temperature are Celsius <br> and Fahrenheit equal?" + html;
        questions[33] = anotherHTML + "Who has the highest IQ?" + html;
        questions[34] = anotherHTML + "What grade will Mr. <br> Roller give this Project?" + html; //last question
    } //fills up the question array with questions

    private void fillRightAnswers() { //fills up the array of correct answers
        correctAnswers[0] = "The Ugly Truth";
        correctAnswers[1] = "1";
        correctAnswers[2] = "Gun";
        correctAnswers[3] = "36";
        correctAnswers[4] = "France";
        correctAnswers[5] = "Aristotle";
        correctAnswers[6] = "Mercury";
        correctAnswers[7] = "Hemoglobin";
        correctAnswers[8] = "206";
        correctAnswers[9] = "Yiddish";
        correctAnswers[10] = "Krusty Krabs";
        correctAnswers[11] = "Calvin & Hobbes";
        correctAnswers[12] = "Kiwi";
        correctAnswers[13] = "Della Duck";
        correctAnswers[14] = "ENIAC";
        correctAnswers[15] = "Liberia";
        correctAnswers[16] = "China";
        correctAnswers[17] = "Tenerife";
        correctAnswers[18] = "Albumen";        
        correctAnswers[19] = "Sicily";
        correctAnswers[20] = "Clovis";
        correctAnswers[21] = "U.S.A";
        correctAnswers[22] = "Monet";
        correctAnswers[23] = "Cheviot Hills";
        correctAnswers[24] = "Corundum";
        correctAnswers[25] = "Sargasso Sea";
        correctAnswers[26] = "White Willow";
        correctAnswers[27] = "Sheck Wes";
        correctAnswers[28] = "Taiwan";
        correctAnswers[29] = "Iris";
        correctAnswers[30] = "Pica";
        correctAnswers[31] = "Dosia";
        correctAnswers[32] = "-40";
        correctAnswers[33] = "Alex";
        correctAnswers[34] = "Four";
        
    } //fills up the array of correct answers

    private void fillBadAnswers() { //fills up the array of incorrect answers
        wrongAnswers[0] = "Dog Days"; //1st question
        wrongAnswers[1] = "Rodrick Rules";
        wrongAnswers[2] = "Cabin Fever";
        wrongAnswers[3] = "3"; //second question
        wrongAnswers[4] = "5";
        wrongAnswers[5] = "7";
        wrongAnswers[6] = "Car"; //3rd question
        wrongAnswers[7] = "Lance";
        wrongAnswers[8] = "Book";
        wrongAnswers[9] = "40"; //4th question
        wrongAnswers[10] = "18";
        wrongAnswers[11] = "30";
        wrongAnswers[12] = "Norway"; //5th question
        wrongAnswers[13] = "Sweden";
        wrongAnswers[14] = "Denmark";
        wrongAnswers[15] = "Plato"; //6th question
        wrongAnswers[16] = "Socrates";
        wrongAnswers[17] = "Archimedes";
        wrongAnswers[18] = "Silver"; //7th question
        wrongAnswers[19] = "Copernicium";
        wrongAnswers[20] = "Barium";
        wrongAnswers[21] = "Oxygen"; //8th question
        wrongAnswers[22] = "Aorta";
        wrongAnswers[23] = "Nitrogen";
        wrongAnswers[24] = "300"; //9th question
        wrongAnswers[25] = "180";
        wrongAnswers[26] = "250";
        wrongAnswers[27] = "Spanish"; //10th question
        wrongAnswers[28] = "Portugese";
        wrongAnswers[29] = "Italian";
        wrongAnswers[30] = "Bikini Bottom"; //11th question
        wrongAnswers[31] = "Chengamike's";
        wrongAnswers[32] = "Foot Locker";
        wrongAnswers[33] = "Garfield"; //12th question
        wrongAnswers[34] = "Dilbert";
        wrongAnswers[35] = "Big Nate";
        wrongAnswers[36] = "Ostrich"; //13th question
        wrongAnswers[37] = "Emu";
        wrongAnswers[38] = "UFO";
        wrongAnswers[39] = "Daisy Duck"; //14th question
        wrongAnswers[40] = "Mina Duck";
        wrongAnswers[41] = "Danold Duck";
        wrongAnswers[42] = "UNOPA"; //15th question
        wrongAnswers[43] = "CHENG";
        wrongAnswers[44] = "B.E.N";
        wrongAnswers[45] = "Laos"; //16th question
        wrongAnswers[46] = "Siberia";
        wrongAnswers[47] = "Somalia";
        wrongAnswers[48] = "Brazil"; //17th question
        wrongAnswers[49] = "Egypt";
        wrongAnswers[50] = "Iceland";
        wrongAnswers[51] = "Mallorca"; //18th question
        wrongAnswers[52] = "Ibiza";
        wrongAnswers[53] = "Fuerteventura";
        wrongAnswers[54] = "Egg Whites"; //19th question
        wrongAnswers[55] = "Luciferase";
        wrongAnswers[56] = "Actin";
        wrongAnswers[57] = "Spain"; //20th question
        wrongAnswers[58] = "Austria";
        wrongAnswers[59] = "Gael Telusmo";
        wrongAnswers[60] = "Charlemagne"; //21st question
        wrongAnswers[61] = "William";
        wrongAnswers[62] = "Jaimin";
        wrongAnswers[63] = "Japan"; //22nd question
        wrongAnswers[64] = "China";
        wrongAnswers[65] = "U.K";
        wrongAnswers[66] = "Rembrandt"; //23rd question
        wrongAnswers[67] = "Bob Ross";
        wrongAnswers[68] = "Picasso";
        wrongAnswers[69] = "The AngloScots"; //24th question
        wrongAnswers[70] = "Kingly Hills";
        wrongAnswers[71] = "Celtic Hills";
        wrongAnswers[72] = "Amphibole";//25th question
        wrongAnswers[73] = "Olivine";
        wrongAnswers[74] = "Pyroxene";
        wrongAnswers[75] = "Adriatic Sea"; //26th question
        wrongAnswers[76] = "Tyrrhenian Sea";
        wrongAnswers[77] = "Ionian Sea";
        wrongAnswers[78] = "Oak"; //27th question
        wrongAnswers[79] = "Birch";
        wrongAnswers[80] = "Black-willow";
        wrongAnswers[81] = "Travis Scott"; //28th question
        wrongAnswers[82] = "Lil Pump";
        wrongAnswers[83] = "Bashir";
        wrongAnswers[84] = "Thailand"; //29th question
        wrongAnswers[85] = "Phillipines";
        wrongAnswers[86] = "Japan";
        wrongAnswers[87] = "Cornea"; //30th question
        wrongAnswers[88] = "Retina";
        wrongAnswers[89] = "Diep";
        wrongAnswers[90] = "Chengism"; //31st question
        wrongAnswers[91] = "Mika";
        wrongAnswers[92] = "Harrisonoms";
        wrongAnswers[93] = "Jaimin Jaffer"; //32nd question
        wrongAnswers[94] = "Tony Joe";
        wrongAnswers[95] = "Tom Cruise";
        wrongAnswers[96] = "0"; //33rd question
        wrongAnswers[97] = "40";
        wrongAnswers[98] = "-100";
        wrongAnswers[99] = "Michael"; //34th question
        wrongAnswers[100] = "Ben";
        wrongAnswers[101] = "Gael Telosmo";
        wrongAnswers[102] = "4++++"; //last question 
        wrongAnswers[103] = "100%"; 
        wrongAnswers[104] = ">90%";
    } //fills up the array of incorrect answers
}