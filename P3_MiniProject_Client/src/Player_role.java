import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Player_role {
    UI_Layout UI;
    JFrame frame;

    Player_role(Card[] cards, int team, int role, String hint_Word, int guessNo){
        UI = new UI_Layout(cards, role, hint_Word, guessNo, team); //creating a UI layout for the player
    }

    public void display() throws InvocationTargetException, InterruptedException { //loads and displays the visual UI layout

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("UI_Layout"); //creating the frame/window for the UI
                frame.setContentPane(UI.Gamescreen_view); //loading the content from the UI object
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //setting the default ability to close the window
                frame.pack();
                frame.setVisible(true); //showing the UI on the screen
            }
        });
    }

    void closeUI (){
        frame.setVisible(false); //making the UI invisible
        frame.dispose(); //closing the UI down completely
    }
}
