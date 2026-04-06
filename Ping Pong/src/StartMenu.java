import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartMenu extends JPanel implements ActionListener, MouseListener, MouseMotionListener{

    static final int SCREEN_WIDTH = 1000;
	static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    int rLocX = SCREEN_WIDTH / 2 - 200 / 2;
    Timer timer;
    Rectangle rect0 = new Rectangle(rLocX, 100, 200, 80);
    Rectangle rect1 = new Rectangle(rLocX, 200, 200, 80);
    Rectangle rect2 = new Rectangle(rLocX, 300, 200, 80);
    Rectangle rect3 = new Rectangle(rLocX, 400, 200, 80);
    boolean rect0Red = false;
    boolean rect1Red = false;
    boolean rect2Red = false;
    boolean rect3Red = false;
    boolean str0Black = false;
    boolean str1Black = false;
    boolean str2Black = false;
    boolean str3Black = false;

    StartMenu(){
    	this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
        this.setFocusable(true);
        addMouseMotionListener(this);
        addMouseListener(this);
        timer = new Timer(25, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Arial",Font.BOLD, 55));
        g.drawString("PING PONG GAME", rLocX - 125, 70);

        g.setColor(rect0Red ? Color.RED: Color.BLACK);
        g.fillRect(rect0.x, rect0.y, rect0.width, rect0.height);
        g.setColor(rect1Red ? Color.RED: Color.BLACK);
        g.fillRect(rect1.x, rect1.y, rect1.width, rect1.height);
        g.setColor(rect2Red ? Color.RED: Color.BLACK);
        g.fillRect(rect2.x, rect2.y, rect2.width, rect2.height);
        g.setColor(rect3Red ? Color.RED: Color.BLACK);
        g.fillRect(rect3.x, rect3.y, rect3.width, rect3.height);

        g.setColor(Color.red);
        g.drawRect(rect0.x, rect0.y, rect0.width, rect0.height);
        g.drawRect(rect1.x, rect1.y, rect1.width, rect1.height);
        g.drawRect(rect2.x, rect2.y, rect2.width, rect2.height);
        g.drawRect(rect3.x, rect3.y, rect3.width, rect3.height);
        g.setFont(new Font("Arial", Font.BOLD, 36));

        g.setColor(str0Black ? Color.BLACK: Color.RED);
        g.drawString("Play", rLocX + 65, 152);
        g.setColor(str1Black ? Color.BLACK: Color.RED);
        g.drawString("Multiplayer", rLocX + 10, 252);
        g.setColor(str2Black ? Color.BLACK: Color.RED);
        g.drawString("Settings", rLocX + 30, 352);
        g.setColor(str3Black ? Color.BLACK: Color.RED);
        g.drawString("Quit", rLocX + 65, 452);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e){
        if(rect0.contains(e.getPoint())){
            timer.stop();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().removeAll();
            GamePanel gamePanel = new GamePanel();
            frame.add(gamePanel);
            frame.revalidate();;
            frame.repaint();
            gamePanel.requestFocusInWindow();
        }
        if(rect1.contains(e.getPoint())){
            System.out.println("multiplayer");
        }
        if(rect2.contains(e.getPoint())){
            System.out.println("settings");
        }
        if (rect3.contains(e.getPoint())){
            System.exit(0);
        }
    }
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        if(rect0.contains(e.getPoint())){
            rect0Red = true;
            str0Black = true;
        }
        else{
            rect0Red = false;
            str0Black = false;
        }
        if(rect1.contains(e.getPoint())){
            rect1Red = true;
            str1Black = true;
        }
        else{
            rect1Red = false;
            str1Black = false;
        }
        if(rect2.contains(e.getPoint())){
            rect2Red = true;
            str2Black = true;
        }
        else{
            rect2Red = false;
            str2Black = false;
        }
        if(rect3.contains(e.getPoint())){
            rect3Red = true;
            str3Black = true;
        }
        else{
            rect3Red = false;
            str3Black = false;
        }
    }
}

