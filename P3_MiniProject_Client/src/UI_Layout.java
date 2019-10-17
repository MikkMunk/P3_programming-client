import javax.swing.*;
import java.awt.*;

public class UI_Layout {
    private JPanel Gamescreen_view;
    private JTextField text_card1;
    private JTextField text_card2;
    private JTextField text_card3;
    private JTextField text_card4;
    private JTextField text_card5;
    private JTextField text_card6;
    private JTextField text_card7;
    private JTextField text_card8;
    private JTextField text_card9;
    private JTextField text_card10;
    private JTextField text_card11;
    private JTextField text_card12;
    private JTextField text_card13;
    private JTextField text_card14;
    private JTextField text_card15;
    private JTextField text_card16;
    private JTextField text_card17;
    private JTextField text_card18;
    private JTextField text_card19;
    private JTextField text_card20;
    private JTextField text_card21;
    private JTextField text_card22;
    private JTextField text_card23;
    private JTextField text_card24;
    private JTextField text_card25;
    private JTextField role_display;
    private JTextField team_display;
    private JTextField hint_display;
    private JTextField guessNo_display;

    private JTextField[] text_cards = {text_card1,text_card2,text_card3,text_card4,text_card5, text_card6,text_card7,
            text_card8,text_card9,text_card10,text_card11,text_card12,text_card13,text_card14,text_card15,text_card16,
            text_card17,text_card18,text_card19,text_card20,text_card21,text_card22,text_card23,text_card24,text_card25};

    UI_Layout(Word [] words){
        for (int i = 0; i > 25; i++){
            text_cards[i].setText(words[i].getText());
        }
    }

    public static void main(String[] args) {
        Word[] words = new Word[25];

        JFrame frame = new JFrame("UI_Layout");
        frame.setContentPane(new UI_Layout(words).Gamescreen_view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
