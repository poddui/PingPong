import javax.swing.JFrame;

public class GameFrame extends JFrame {
    
    GameFrame(){
        this.add(new AIGamePanel());
        this.setTitle("Ping Pong Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
