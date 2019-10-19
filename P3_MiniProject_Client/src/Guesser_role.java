import javax.swing.*;

public class Guesser_role {

    public void display(Word[] words) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("UI_Layout");
                frame.setContentPane(new UI_Layout(words, 2).Gamescreen_view);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

    }
}
