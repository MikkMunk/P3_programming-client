import javax.swing.*;

public class Instructor_role {

    public void display(Cards[] cards) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("UI_Layout");
                frame.setContentPane(new UI_Layout(cards, 1).Gamescreen_view);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

    }
}
