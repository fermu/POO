package juego.graphics;

import java.util.Random;

public class Screen {

    private int width, height;
    public int[] pixels;
    public final int MAP_SIZE = 64;
    public final int MAP_SIZE_MASK = MAP_SIZE - 1;
    
    public int xOffset, yOffset;
    
    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    private Random random = new Random();
    
    // private final int ALPHA_COL = 0xff00ff50;
    
    public Screen(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        
        for (int i= 0; i<MAP_SIZE * MAP_SIZE; i++){
        	tiles[i] = random.nextInt(0xffffff);
        	tiles[0] = 0;
        }
        
   }
    
    public void clear(){
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }
    public void render(int xOffset, int yOffset){
    	for(int y = 0; y < height; y++){
    		int yp = y + yOffset;
    		if(yp < 0 || yp >= height) continue;
    		for(int x = 0; x < width; x++){
    			int xp = x + xOffset;
    			if(xp < 0 || xp >= width) continue;
    			pixels[(x + xOffset) + (y + yOffset) * width] = Sprite.grass.pixels[(x & 15) + (y & 15) * Sprite.grass.SIZE];
    		}
    	}
    }
    
   

	
}
