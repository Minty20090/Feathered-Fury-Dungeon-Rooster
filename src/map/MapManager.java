package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import tile.Tile;

public class MapManager {
	GamePanel gp;
	public Map[] maps;
	
	public MapManager (GamePanel gp) {
		this.gp = gp;
		maps = new Map[8];
		 
		getMapImages();
		for(int i = 0; i < maps.length; i++) {
			if (maps[i] != null) {
				convertToTiles(maps, i);
			}
			
		}
	}
	
	public void getMapImages() {
		try {
			// Main hall
			maps[0] = new Map();
			maps[0].image = ImageIO.read(getClass().getResourceAsStream("/premade_maps/full_academy_furnished_interior_27x13.png"));
			maps[0].mapWidth = 27;
			maps[0].mapHeight = 13;
			maps[0].layer = 0;
			maps[0].tiles = new Tile[maps[0].mapWidth][maps[0].mapHeight];
			maps[0].otherLayers = new int[1];
			maps[0].otherLayers[0] = 4;
			

			// shop 
			maps[1] = new Map();
			maps[1].image = ImageIO.read(getClass().getResourceAsStream("/premade_maps/shop_15x10.png"));
			maps[1].mapWidth = 15;
			maps[1].mapHeight = 10;
			maps[1].layer = 0;
			maps[1].tiles = new Tile[maps[1].mapWidth][maps[1].mapHeight];
			maps[1].otherLayers = new int[1];
			maps[1].otherLayers[0] = 5;
			
			// portal room
			maps[2] = new Map();
			maps[2].image = ImageIO.read(getClass().getResourceAsStream("/premade_maps/portal_room_13x13.png"));
			maps[2].mapWidth = 13;
			maps[2].mapHeight = 13;
			maps[2].layer = 0;
			maps[2].tiles = new Tile[maps[2].mapWidth][maps[2].mapHeight];
			maps[2].otherLayers = new int[1];
			maps[2].otherLayers[0] = 6;
			
			//dungeon
			maps[3] = new Map();
			maps[3].image = ImageIO.read(getClass().getResourceAsStream("/premade_maps/dungeon.png"));
			maps[3].mapWidth = 13;
			maps[3].mapHeight = 13;
			maps[3].tiles = new Tile[maps[3].mapWidth][maps[3].mapHeight];
			
			
			//Main hall Furniture
			maps[4] = new Map();
			maps[4].image = ImageIO.read(getClass().getResourceAsStream("/premade_maps/jank_furniture.png"));
			maps[4].mapWidth = 27;
			maps[4].mapHeight = 13;
			maps[4].layer = 1;
			maps[4].tiles = new Tile[maps[4].mapWidth][maps[4].mapHeight];
			
			// shop Desk
			maps[5] = new Map();
			maps[5].image = ImageIO.read(getClass().getResourceAsStream("/premade_maps/shop_desk_15x10.png"));
			maps[5].mapWidth = 15;
			maps[5].mapHeight = 10;
			maps[5].layer = 1;
			maps[5].tiles = new Tile[maps[5].mapWidth][maps[5].mapHeight];
			
			// portal room
			maps[6] = new Map();
			maps[6].image = ImageIO.read(getClass().getResourceAsStream("/premade_maps/top_portal_room_13x13.png"));
			maps[6].mapWidth = 13;					
			maps[6].mapHeight = 13;
			maps[6].layer = 1;
			maps[6].tiles = new Tile[maps[6].mapWidth][maps[6].mapHeight];
			
			// maze 
			maps[7] = new Map();
			maps[7].image = ImageIO.read(getClass().getResourceAsStream("/premade_maps/maze.png"));
			maps[7].mapWidth = 43;					
			maps[7].mapHeight = 25;
			maps[7].layer = 0;
			maps[7].tiles = new Tile[maps[7].mapWidth][maps[7].mapHeight];
		} 
		catch(IOException e) {
			
		}
	}
	private void convertToTiles(Map[] maps, int currentMap) { 
		try {
			maps[currentMap].image = resizeImage(maps[currentMap].image, gp.tileSize*maps[currentMap].mapWidth, gp.tileSize*maps[currentMap].mapHeight);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int y = 0; y < maps[currentMap].mapHeight * gp.tileSize; y += gp.tileSize) {
            for (int x = 0; x < maps[currentMap].mapWidth * gp.tileSize; x += gp.tileSize) {
                int tileX = x / gp.tileSize;
                int tileY = y / gp.tileSize;
                Tile tile = new Tile();
                tile.image = maps[currentMap].image.getSubimage(x, y, gp.tileSize, gp.tileSize);
                
                maps[currentMap].tiles[tileX][tileY] = tile;
            }
        }
        
    }


    public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight)
            throws IOException {

        // Create a new BufferedImage with the specified dimensions
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        // Create a Graphics2D object to draw on the new image
        Graphics2D g2 = resizedImage.createGraphics();

        // Draw the original image onto the new image with the specified dimensions
        g2.drawImage(originalImage, 0, 0, newWidth, newHeight, null);

        // Dispose of the Graphics2D object
        g2.dispose();

        return resizedImage;
    }
    

	public void draw (Graphics2D g2, int currentMap) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		
		while (worldCol < maps[currentMap].tiles.length && worldRow < maps[currentMap].tiles[0].length) {
			
			BufferedImage image = maps[currentMap].tiles[worldCol][worldRow].image;
			
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(image, screenX, screenY, null);
				
			}
			
			
			worldCol++;
			
			if (worldCol == maps[currentMap].tiles.length) {
				worldCol = 0;
				worldRow++;
			}		
		
		}
		
}
}



