/*
 * Author: Alex L
 * Date: January 13th, 2019
 * Description: A Class that extends the JButton class to give the answer buttons in the game a number
 * so it is possible to cross-reference the button's number and the integers in the correct answers array
 * while simultaneously giving it text (with possible answers) on each button rather than setting text to 
 * each button and creating 60 images to lay over the text.
 */

/**
 *
 * @author Alex!
 */

import javax.swing.JButton;

public class Button extends JButton {
    private int buttonNum; //button numbers
    
    public void setNum(int i) { //gives each JButton a number
        buttonNum = i; //sets it to i
    }
    
    public int getNum() {
        return buttonNum; //returns the button number
    }
       
}
