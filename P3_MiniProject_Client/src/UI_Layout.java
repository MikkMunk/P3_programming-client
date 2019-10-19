import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_Layout {
    Word [] words;
    int role = 0;
    Color red = new Color(220, 130, 130);
    Color blue = new Color(130, 150, 220);
    Color grey = new Color(230, 240, 220);
    Color black = new Color(120, 120, 120);

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

    private JButton[] text_cards = {text_card1, text_card2, text_card3, text_card4, text_card5, text_card6, text_card7,
            text_card8, text_card9, text_card10, text_card11, text_card12, text_card13, text_card14, text_card15, text_card16,
            text_card17, text_card18, text_card19, text_card20, text_card21, text_card22, text_card23, text_card24, text_card25};

    UI_Layout( Word[] words, int role) {
        this.words = words;
        this.role = role;
        text_card1.addActionListener(new gameStarted());
    }

    /*public static void main(String[] args) {
        Word [] words = new Word[25];


        JFrame frame = new JFrame("UI_Layout");
        frame.setContentPane(new UI_Layout(words).Gamescreen_view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }*/



    class gameStarted implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String[] button_text = new String[25];
            for (int i = 0; i < 25; i++){
                words[i] = new Word();
                button_text[i] = words[i].getText();
                text_cards[i].setText(button_text[i]);
            }

            if (role == 1){
                int[] colors = new int[25];
                for (int i = 0; i < 25; i++){
                    colors[i] = words[i].number;
                    if (colors[i] == 0){
                        text_cards[i].setBackground(black);
                    }
                    else if(colors[i] == 1){
                        text_cards[i].setBackground(grey);
                    }
                    else if(colors[i] == 2){
                        text_cards[i].setBackground(blue);
                    }
                    else if(colors[i] == 3){
                        text_cards[i].setBackground(red);
                    }
                }
            }
        }


    }
}

