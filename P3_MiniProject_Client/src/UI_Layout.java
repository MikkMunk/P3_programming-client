import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UI_Layout{
    Card [] cards;
    int role = 0;
    String hint_word = "nothing";
    int guessNo = 0;
    int teamNo = 0;


//Creating the different colors that will be applied to the text cards
    Color red = new Color(220, 130, 130);
    Color blue = new Color(130, 150, 220);
    Color grey = new Color(230, 240, 220);
    Color black = new Color(120, 120, 120);

    Color strong_red = new Color(220, 70, 70);
    Color strong_blue = new Color(70, 90, 220);
    Color strong_grey = new Color(170, 170, 100);
    Color strong_black = new Color(50, 50, 50);

//auto generated code initializing all the different elements of the UI
    public JPanel Gamescreen_view;
    private JTextField role_display;
    private JTextField team_display;
    private JTextField hint_display;
    private JTextField guessNo_display;
    private JButton text_card1;
    private JButton text_card2;
    private JButton text_card3;
    private JButton text_card4;
    private JButton text_card5;
    private JButton text_card6;
    private JButton text_card7;
    private JButton text_card8;
    private JButton text_card9;
    private JButton text_card10;
    private JButton text_card11;
    private JButton text_card12;
    private JButton text_card13;
    private JButton text_card14;
    private JButton text_card15;
    private JButton text_card16;
    private JButton text_card17;
    private JButton text_card18;
    private JButton text_card19;
    private JButton text_card20;
    private JButton text_card21;
    private JButton text_card22;
    private JButton text_card23;
    private JButton text_card24;
    private JButton text_card25;
    private JButton Submit;

    //compiling all the text cards/buttons into an array, so we can cycle through it later
    private JButton[] text_cards = {text_card1, text_card2, text_card3, text_card4, text_card5, text_card6, text_card7,
            text_card8, text_card9, text_card10, text_card11, text_card12, text_card13, text_card14, text_card15, text_card16,
            text_card17, text_card18, text_card19, text_card20, text_card21, text_card22, text_card23, text_card24, text_card25};

    //IN the constructor we set up the visual representation of the current game state
    UI_Layout( Card[] cards, int role, String hint_Word, int guessNo, int teamNo) {
        this.cards = cards;
        this.role = role;
        this.hint_word = hint_Word;
        this.guessNo = guessNo;
        this.teamNo = teamNo;

        hint_display.setText(hint_word); //displaying the current hint word
        guessNo_display.setText(Integer.toString(guessNo)); //displaying the amount of possible guesses

        //Displaying the number and color of your team
        if (teamNo == 1){
            team_display.setText("Team 1");
            team_display.setBackground(strong_red);
        } else if (teamNo == 2){
            team_display.setText("Team 2");
            team_display.setBackground(strong_blue);
        }

        //applying the generated list of words to the text cards
        for (int i = 0; i < 25; i++) {
            text_cards[i].setText(cards[i].getName());

         //setting the strong background color on cards that have been chosen
            if(cards[i].isPlayed()){
                selectCard(text_cards[i], cards[i].getNumber());
            }
        }

        //setting the display for the instructor role
        if (role == 1) {
            hint_display.setEditable(true); //allowing the player to edit the hint text
            guessNo_display.setEditable(true); //allowing the player to edit the guess text
            role_display.setText("Instructor");  //displaying the players role

            //adding an action listener to the submit button
            Submit.addActionListener(new ReadHint(hint_display, guessNo_display));

            for (int i = 0; i < 25; i++) {
                //applying colors to the cards that weren't chosen
                if (cards[i].isPlayed() == false) {
                    //Choosing color based on the cards number (see card class for number overview)
                    if (cards[i].getNumber() == 0) {
                        text_cards[i].setBackground(black);
                    } else if (cards[i].getNumber() == 1) {
                        text_cards[i].setBackground(grey);
                    } else if (cards[i].getNumber() == 2) {
                        text_cards[i].setBackground(blue);
                    } else if (cards[i].getNumber() == 3) {
                        text_cards[i].setBackground(red);
                    }
                }
            }
        }

        //setting the display for the guesser role
        if(role == 2){
            role_display.setText("Guesser"); //displaying the players role

            //Adding action listeners to all the text cards
            for (int i = 0; i < text_cards.length; i++) {
                text_cards[i].addActionListener(new cardChosen(cards[i], text_cards[i], i));
            }
        }
    }



    class cardChosen implements ActionListener { //This class is called when a guesser clicks a text card
        Card card;
        JButton text_card;
        int card_number;

        cardChosen (Card card, JButton text_card, int card_number){
            this.card = card;
            this.text_card = text_card;
            this.card_number = card_number;
        }

        public void actionPerformed(ActionEvent e) { //this is called when the action listener activates the class
            try {
                Main.changedColor(card_number); //letting the main function know which card was selected
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }

            selectCard(text_card, card.getNumber()); //calls function to change the color of the chosen card
        }
    }


    class ReadHint implements ActionListener{ //This class is called when an instructor click the submit button
        JTextField hint;
        JTextField number;

        ReadHint(JTextField hint, JTextField number){
            this.hint = hint;
            this.number = number;
        }
        @Override
        public void actionPerformed(ActionEvent e) { //this is called when the action listener activates the class
            try {
                //sending the inputted hint and guess number to the main (the number is first translated from string to int)
                Main.submittedHint(hint.getText(), Integer.parseInt(number.getText()));
            } catch (IOException ex){}
        }
    }

    //This function assigns a strong color to a card
    void selectCard (JButton text_card, int color){
        if (color == 0){
            text_card.setBackground(strong_black);
        }
        else if(color == 1){
            text_card.setBackground(strong_grey);
        }
        else if(color == 2){
            text_card.setBackground(strong_blue);
        }
        else if(color == 3){
            text_card.setBackground(strong_red);
        }
    }

}
