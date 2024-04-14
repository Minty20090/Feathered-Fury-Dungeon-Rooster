package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import map.Map;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	int maxMapCol;
	int maxMapRow;
	
	public TileManager (GamePanel gp) {
		
		this.gp = gp;
		tile = new Tile[51];
		mapTileNum = new int[gp.maxWorldCol] [gp.maxWorldRow];
		
		getTileImage();
		loadMap(gp.currentMap);
	}
	
	public void getTileImage() {
		
		setup(0, "transparent_tile", false);
		setup(1, "transparent_tile", true);
		setup(2, "black", false);
		setup(3, "grass", false);
		setup(4, "grass1", false);
		setup(5, "grass2", false);
		setup(6, "grass_flowers1", false);
		setup(7, "grass_flowers2", false);
		setup(8, "grass_flowers3", false);
		setup(9, "grass_flowers4", false);
		setup(10, "trees1", true);
		setup(11, "stone_wall", true);
		setup(12, "sand1", false);
		setup(13, "stone_tile", false);
		
		
	}
	
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
	
	public void loadMap(int map) {
		String[] maps = {"/maps/map1.txt", "/maps/shop.txt", "/maps/portal_room.txt", "/maps/dungeon.txt", 
						 "/maps/Inside_Wall_Maze.txt","/maps/Outside_Wall_Maze.txt","/maps/Outside_Tree_Maze.txt"};
		clearMap();
		try {
			InputStream is = getClass().getResourceAsStream(maps[map]);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				String numbers[] = line.split(" ");
				
				while (col < numbers.length) {
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					
					col++;
				}
				
				if (col == numbers.length) {
					
					col = 0;
					row++;
					
				}
			}
			
			br.close();
		}
		catch (Exception e) {
			
		}
	}
	public void draw (Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		
		while (worldCol < mapTileNum.length && worldRow < mapTileNum[0].length) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				if (tileNum != 0) {
					g2.drawImage(tile[tileNum].image, screenX, screenY, null);
				}
				
				
			}
			
			
			worldCol++;
			
			if (worldCol == mapTileNum.length) {
				worldCol = 0;
				
				worldRow++;
				
			}
			
			
		}
		
	}
	public void clearMap() {
        for (int col = 0; col < gp.maxWorldCol; col++) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
                mapTileNum[col][row] = 0;
            }
        }
    }
}
