import javax.swing.*;

public class Guesser_role {

    public void display(Card[] cards, int team, String hint_Word, int guessNo) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("UI_Layout");
                frame.setContentPane(new UI_Layout(cards, 2, hint_Word, guessNo, team).Gamescreen_view);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

    }
}
