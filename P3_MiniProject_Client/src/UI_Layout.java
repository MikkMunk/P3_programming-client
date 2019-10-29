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



    Color red = new Color(220, 130, 130);
    Color blue = new Color(130, 150, 220);
    Color grey = new Color(230, 240, 220);
    Color black = new Color(120, 120, 120);

    Color strong_red = new Color(220, 70, 70);
    Color strong_blue = new Color(70, 90, 220);
    Color strong_grey = new Color(170, 170, 100);
    Color strong_black = new Color(50, 50, 50);


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

    private JButton[] text_cards = {text_card1, text_card2, text_card3, text_card4, text_card5, text_card6, text_card7,
            text_card8, text_card9, text_card10, text_card11, text_card12, text_card13, text_card14, text_card15, text_card16,
            text_card17, text_card18, text_card19, text_card20, text_card21, text_card22, text_card23, text_card24, text_card25};

    UI_Layout( Card[] cards, int role, String hint_Word, int guessNo, int teamNo) {
        this.cards = cards;
        this.role = role;
        this.hint_word = hint_Word;
        this.guessNo = guessNo;
        this.teamNo = teamNo;

        hint_display.setText(hint_word);
        guessNo_display.setText(Integer.toString(guessNo));

        if (teamNo == 1){
            team_display.setText("Team 1");
            team_display.setBackground(strong_red);
        } else if (teamNo == 2){
            team_display.setText("Team 2");
            team_display.setBackground(strong_blue);
        }

        String[] button_text = new String[25];
        for (int i = 0; i < 25; i++) {
            //cards[i] = new Card();
            button_text[i] = cards[i].getName();
            text_cards[i].setText(button_text[i]);

            if(cards[i].isPlayed()){
                if (cards[i].getNumber() == 0){
                    text_cards[i].setBackground(strong_black);
                }
                else if(cards[i].getNumber() == 1){
                    text_cards[i].setBackground(strong_grey);
                }
                else if(cards[i].getNumber() == 2){
                    text_cards[i].setBackground(strong_blue);
                }
                else if(cards[i].getNumber() == 3){
                    text_cards[i].setBackground(strong_red);
                }
            }
        }

        if (role == 1) {
            int[] colors = new int[25];
            hint_display.setEditable(true);
            guessNo_display.setEditable(true);
            role_display.setText("Instructor");
            Submit.addActionListener(new ReadHint(hint_display, guessNo_display));
            for (int i = 0; i < 25; i++) {
                if (cards[i].isPlayed() == false) {
                    colors[i] = cards[i].getNumber();
                    if (colors[i] == 0) {
                        text_cards[i].setBackground(black);
                    } else if (colors[i] == 1) {
                        text_cards[i].setBackground(grey);
                    } else if (colors[i] == 2) {
                        text_cards[i].setBackground(blue);
                    } else if (colors[i] == 3) {
                        text_cards[i].setBackground(red);
                    }
                }
            }
        }
        if(role == 2){
            role_display.setText("Guesser");
            for (int i = 0; i < text_cards.length; i++) {
                text_cards[i].addActionListener(new cardChosen(cards[i], text_cards[i], i));
            }
        }
    }



    class cardChosen implements ActionListener {

        Card card;
        JButton text_card;
        int card_number;

        cardChosen (Card card, JButton text_card, int card_number){
            this.card = card;
            this.text_card = text_card;
            this.card_number = card_number;
        }

        public void actionPerformed(ActionEvent e) { //this is called when the action listener activates the class
            Main.changedColor(card_number);

            selectCard(card, text_card, card.getNumber());
        }
    }

    class ReadHint implements ActionListener{
        JTextField hint;
        JTextField number;

        ReadHint(JTextField hint, JTextField number){
            this.hint = hint;
            this.number = number;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("action listener activated");
            String hintWord = hint.getText();
            int hintNumber = Integer.parseInt(number.getText());
            System.out.println(hintWord + " " + hintNumber);
            try {
                Main.submittedHint(hint.getText(), Integer.parseInt(number.getText()));
            } catch (IOException ex){}
        }
    }

    void selectCard (Card card, JButton text_card, int color){
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

    public void setHint (String newHint, int newNum){
        hint_display.setText(newHint);
        guessNo_display.setText(Integer.toString(newNum));
    }

    public void changeCard (Card[] cards){
        for(int i = 0; i < 25; i++) {
            if (cards[i].isPlayed()) {
                selectCard(cards[i], text_cards[i], cards[i].getNumber());
            }
        }
    }
}
