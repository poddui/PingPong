import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class AIGamePanel extends JPanel implements ActionListener{
	
	static final int SCREEN_WIDTH = 1000;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT) / UNIT_SIZE;
    int ballSpeedY = (int)(Math.random() * 5) - 2;
    int ballSpeedX = 10;
    int ballLocationX = SCREEN_WIDTH / 2;
    int ballLocationY = SCREEN_HEIGHT / 2;
    int[] points = {0 ,0};
    long resetTime = 0;
    int pointChecker = 0;
    int playerX = SCREEN_WIDTH - UNIT_SIZE * 2;
    int playerY = UNIT_SIZE;
    int playerWidth = UNIT_SIZE;
    int playerHeight = SCREEN_HEIGHT - UNIT_SIZE * 15;
    int enemyX = UNIT_SIZE;
    int enemyY = UNIT_SIZE;
    int enemyWidth = UNIT_SIZE;
    int enemyHeight = SCREEN_HEIGHT - UNIT_SIZE * 15;
    int enemyVelocity = 0;
    int enemySpeed = 20;
	Timer timer;
    Font scoreFont = new Font("Arial", Font.BOLD, 36);
    ServerSocket serverSocket;
    Socket socket;
    BufferedReader in;
    PrintWriter out;

	
	AIGamePanel(){
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);		
        startServer();
        timer = new Timer(10, this);
        requestFocusInWindow();
        startGame();
	}
    public void startGame(){
        timer.start();
    }

    public void startServer() {
    new Thread(() -> {
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("Waiting for AI connection...");

            socket = serverSocket.accept();
            System.out.println("AI connected!");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        }).start();
    }

    public void resetGame(){
        resetTime = System.currentTimeMillis();
        ballLocationX = SCREEN_WIDTH / 2;
        ballLocationY = SCREEN_HEIGHT / 2;
        ballSpeedX = 0;
        ballSpeedY = 0;
    }

    public boolean checkPlayerCollision(){
        if(playerY <= 0){
            playerY = 0;
        }
        if(playerY >= SCREEN_HEIGHT - playerHeight){
            playerY = SCREEN_HEIGHT - playerHeight;
        }
        if(enemyY <=  0){
            enemyY = 0;
        }
        if(enemyY >= SCREEN_HEIGHT - enemyHeight){
            enemyY = SCREEN_HEIGHT - enemyHeight;
        }
        return false;
    }
	
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void setYDirection(int Direction) {
        enemyVelocity = Direction;
    }   

    public void move(){
        enemyY += enemyVelocity;
    }

    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillOval(ballLocationX, ballLocationY, UNIT_SIZE, UNIT_SIZE);
        g.setColor(Color.green);
        g.fillRoundRect(playerX, playerY, playerWidth, playerHeight, 0, 0);
        g.fillRoundRect(enemyX , enemyY, enemyWidth, enemyHeight, 0, 0);
        g.setFont(scoreFont);
        g.setColor(Color.cyan);
        g.drawString(points[0] + " - " + points[1], SCREEN_WIDTH / 2 - g.getFontMetrics().stringWidth(points[0] + " - " + points[1]) / 2 ,UNIT_SIZE * 2);  
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(out != null){
            String state = ballLocationX + "," + ballLocationY + "," + 
                            ballSpeedX + "," + ballSpeedY + "," + 
                            enemyY + ",";
            out.println(state);
        }
        try{
            if(in != null && in.ready()){
                String line = in.readLine();
                int action = Integer.parseInt(line);

                if(action == -1){
                    setYDirection(-enemySpeed);
                } else if (action == 1){
                    setYDirection(enemySpeed);
                } else {
                    setYDirection(0);
                }
            }
        } catch (Exception k){
            k.printStackTrace();
        }
        move();
        int ballCenter = ballLocationY + UNIT_SIZE / 2;
        int playerCenter = playerY + playerHeight / 2;
        int enemyCenter = enemyY + enemyHeight / 2;
        int playerDistance = ballCenter - playerCenter;
        int enemyDistance = ballCenter - enemyCenter;
        if(ballLocationX > (SCREEN_WIDTH - UNIT_SIZE*3) && ballLocationX <= (SCREEN_WIDTH - UNIT_SIZE*2)){
            if(ballLocationY >= playerY && ballLocationY <= (playerY + playerHeight) ){
                ballSpeedX = -Math.abs(ballSpeedX);
                double normalized = (double) playerDistance / (playerHeight / 2.0);
                if(normalized > 1) normalized = 1;
                if(normalized < -1) normalized = -1;
                ballSpeedY = (int)(normalized * 8);
                if(ballSpeedY == 0){
                    ballSpeedY = normalized > 0 ? 1 : -1;
                }
            }
        }
        if(ballLocationX < (UNIT_SIZE*3) && ballLocationX <= (UNIT_SIZE*2)){
            if(ballLocationY >= enemyY && ballLocationY <= (enemyY + enemyHeight) ){
                ballSpeedX = Math.abs(ballSpeedX);
                double normalized = (double) enemyDistance / (enemyHeight / 2.0);
                if(normalized > 1) normalized = 1;
                if(normalized < -1) normalized = -1;
                ballSpeedY = (int)(normalized * 8);
                if(ballSpeedY == 0){
                    ballSpeedY = normalized > 0 ? 1 : -1;
                }
            }
        }
        ballLocationY = ballLocationY + ballSpeedY;
        if(ballLocationY >= SCREEN_HEIGHT-UNIT_SIZE || ballLocationY < 0){
            ballSpeedY = ballSpeedY * -1;
        }
        if(ballLocationX >= SCREEN_WIDTH){
            resetGame();
            points[0] += 1;
            pointChecker = 1;
        }
        if(ballLocationX <= 0){
            resetGame();
            points[1] += 1;
            pointChecker = 2;
        }
        ballLocationX = ballLocationX + ballSpeedX;
        if((System.currentTimeMillis() - resetTime) >= 1000 ){
            if(pointChecker == 1){
                ballSpeedX = 10;
                ballSpeedY = (int)(Math.random() * 5) - 2;
                pointChecker = 0;
            }
            if(pointChecker == 2){
                ballSpeedX = -10;
                ballSpeedY = (int)(Math.random() * 5) - 2;
                pointChecker = 0;
            }

        }
        checkPlayerCollision();
        repaint();
    }
    
}
		


