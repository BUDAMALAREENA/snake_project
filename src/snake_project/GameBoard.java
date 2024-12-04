package snake_project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
public class GameBoard extends JPanel {
    private static final int TILE_SIZE = 20;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private ArrayList<Point> snake;
    private Point food;
    private String direction = "RIGHT";
    private boolean isGameOver = false;
    
    public GameBoard() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && !direction.equals("RIGHT")) {
                    direction = "LEFT";
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && !direction.equals("LEFT")) {
                    direction = "RIGHT";
                }
                if (e.getKeyCode() == KeyEvent.VK_UP && !direction.equals("DOWN")) {
                    direction = "UP";
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN && !direction.equals("UP")) {
                    direction = "DOWN";
                }
            }
        });
    }

    public void startGame() {
        // Initialize snake and food
        snake = new ArrayList<>();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        spawnFood();

        // Game loop
        Timer timer = new Timer(100, e -> {
            if (!isGameOver) {
                moveSnake();
                checkCollisions();
                repaint();
            }
        });
        timer.start();
    }

    public void moveSnake() {
        Point head = snake.get(0);
        Point newHead = null;

        // Determine the new head based on current direction
        switch (direction) {
            case "LEFT":
                newHead = new Point(head.x - 1, head.y);
                break;
            case "RIGHT":
                newHead = new Point(head.x + 1, head.y);
                break;
            case "UP":
                newHead = new Point(head.x, head.y - 1);
                break;
            case "DOWN":
                newHead = new Point(head.x, head.y + 1);
                break;
        }

        // Add new head to the snake
        snake.add(0, newHead);

        // Check if snake eats the food
        if (newHead.equals(food)) {
            spawnFood();
        } else {
            snake.remove(snake.size() - 1); // Remove tail
        }
    }

    public void checkCollisions() {
        Point head = snake.get(0);

        // Check wall collisions
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            isGameOver = true;
        }

        // Check self-collisions
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                isGameOver = true;
            }
        }
    }

    public void spawnFood() {
        Random random = new Random();
        food = new Point(random.nextInt(WIDTH), random.nextInt(HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw the snake
        g.setColor(Color.GREEN);
        for (Point point : snake) {
            g.fillRect(point.x * TILE_SIZE, point.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw the food
        g.setColor(Color.RED);
        g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Game Over text
        if (isGameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over!", WIDTH * TILE_SIZE / 4, HEIGHT * TILE_SIZE / 2);
        }
    }
}
