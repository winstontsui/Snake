import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent; // Handles events in the window.
import java.awt.event.ActionListener; // Typically for mouse click events.
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25; // How big we want objects to be
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 150; // Higher number = slower game
    final int x[] = new int[GAME_UNITS]; // Holds x coordinates of snake. x[0] holds head position of snake
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 1; // Number of body parts in snake
    int applesEaten; // Score
    int appleX; // Coordinate of apple
    int appleY;
    char direction = 'R'; // Snake will go right initally
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            //     g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            //     g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            // }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    // Head of snake
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        // Generates a new apple that is NOT on the snake's body part.
        boolean appleOnSnake = true;
        while (appleOnSnake) {
            // Generates new random coordinates for the apple
            appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

            // Check if the apple is on the snake's body
            for (int i = 0; i < bodyParts; i++) {
                if (appleX == x[i] && appleY == y[i]) {
                    appleOnSnake = true;
                    break;
                } else {
                    appleOnSnake = false;
                }
            }
        }
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            // y[0] is the y coordinate of the head of the snake
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        // Checks if head collides with body.
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // Checks if head touches borders
        if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT)
            running = false;
        if (!running)
            timer.stop();

    }

    public void gameOver(Graphics g) {
        // Display the score on the screen
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 35));
        FontMetrics scoreMetrics = getFontMetrics(g.getFont());
        g.drawString("Final score: " + applesEaten,
                (SCREEN_WIDTH - scoreMetrics.stringWidth("Final score: " + applesEaten)) / 2,
                g.getFont().getSize());

        // Write game over text on the screen
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 70));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (SCREEN_WIDTH - metrics.stringWidth("Game Over!")) / 2, SCREEN_HEIGHT / 2);
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        metrics = getFontMetrics(g.getFont());
        g.drawString("You lost, better luck next time! Muahahaha",
                (SCREEN_WIDTH - metrics.stringWidth("You lost, better luck next time! Muahahaha")) / 2,
                SCREEN_HEIGHT / 2 + 50);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        metrics = getFontMetrics(g.getFont());
        g.drawString("MELANIE LORAINNE SALAS YOU SUCK AT THIS SNAKE GAME, HAHAHAHA!!",
                (SCREEN_WIDTH - metrics.stringWidth("MELANIE LORAINNE SALAS YOU SUCK AT THIS SNAKE GAME, HAHAHAHA!!"))
                        / 2,
                SCREEN_HEIGHT / 2 + 100);
        g.drawString("SALAS YOU ARE UNACCEPTABLE. YOU MUST BE PUNISHED",
                (SCREEN_WIDTH - metrics.stringWidth("SALAS YOU ARE UNACCEPTABLE. YOU MUST BE PUNISHED")) / 2,
                SCREEN_HEIGHT / 2 + 150);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        // Handles received keyboard events.
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L')
                        direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U')
                        direction = 'D';
                    break;
            }
        }
    }

}
