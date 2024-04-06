import javax.swing.JFrame; // Creates GUI windows

public class GameFrame extends JFrame {
    GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false); // Prevents user from resizing the frame.
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); // Centers the frame.
    }
}
