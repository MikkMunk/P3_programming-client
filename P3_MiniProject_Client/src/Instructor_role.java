import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Instructor_role {
    UI_Layout UI;
    JFrame frame;

    Instructor_role(Card[] cards, int team, String hint_Word, int guessNo){
        UI = new UI_Layout(cards, 1, hint_Word, guessNo, team);
    }

    public void display() throws InvocationTargetException, InterruptedException {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("UI_Layout");
                frame.setContentPane(UI.Gamescreen_view);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    void displayHint(String hint, int num){
        UI.setHint(hint, num);
    }

    void updateCards(Card[] cards){
        UI.changeCard(cards);
    }

    void closeUI (){
        frame.setVisible(false); //you can't see me!
        frame.dispose();
    }
}
