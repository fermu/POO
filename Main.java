package juego;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import juego.graphics.Screen;
import juego.input.KeyBoard;

//canvas represents a blank rectangular area of the screen
//onto which the application can draw or from which the application can trap input events from the user 

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
    private static int width = 300;
    private static int height = 168;
    private static int scale = 3;
    public static String title = "POO´S BEST GAME";

    private Thread thread;
    private JFrame frame;
    private KeyBoard key;
    private boolean running = false;
    
    private Screen screen;
    
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
     
    public Main() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);

        screen = new Screen(width, height);
        frame = new JFrame();//
        key = new KeyBoard();
        addKeyListener(key);
    }
    
    public static int getWindowWidth(){
    	return width * scale;
    }
    
    public static int getWindowHeight(){
    	return height * scale;
    }
    
    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }
    
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } 
    
    public void run() {
        // Para contar FPS // when we stop the first thread this gonna run
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;

        requestFocus();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            
            render();
            frames++;

            // Contador de FPS
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(title + " | " + updates + " ups " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
       stop();
    }
	int x= 0, y =0;
	
	public void update() {
        key.update();
        if (key.up)y--;
        if (key.down) y++;
        if (key.left) x--;
        if (key.right) x++;
       
    }
	
	 public void render() {
		 //help to run the pixels faster one behind the other
        BufferStrategy bs = getBufferStrategy();
	        
        if (bs == null) {
            // Triple buffering so we don't wait to display the next img 
            // This starts buffering the next image; simply it's faster
            createBufferStrategy(3);
            return;
        }
        
        screen.clear();
        screen.render();
        
        Graphics g = bs.getDrawGraphics();
        
        // Copy the pixels we have in Screen.java to the pixels array here/*
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }
        
        // Next comes all the graphics that should be displayed
        g.drawImage(image, 0, 0, width * scale, height * scale, null);
        
        // Dispose of all the graphics that aren't gonna be used
        g.dispose();
        // Buffer swapping or "blitting", shows the next buffer visible
        bs.show();
	 }
	 
	 public static void main(String[] args) {
	        Main game = new Main();

	        // To avoid graphical errors
	        game.frame.setResizable(false);

	        // Title of our game
	        game.frame.setTitle(Main.title);

	        // It fills the window with something(parameter)
	        game.frame.add(game);

	        // Sizes the frame to be of our desired size
	        game.frame.pack();

	        // Closes the app when you click the close button
	        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        // Centers our window
	        game.frame.setLocationRelativeTo(null);

	        // Shows our window
	        game.frame.setVisible(true);

	        // Starts our game
	        game.start();
	    }
}