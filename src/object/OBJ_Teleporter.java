package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Teleporter extends Entity {
	
	
	public OBJ_Teleporter (GamePanel gp, int destination, int playerX, int playerY) {
		
		super(gp);
		
		name = "Teleporter";
		
		this.destination = destination;
		this.playerX = playerX;
		this.playerY = playerY;
		
		down1 = setup("/objects/teleport", gp.tileSize, gp.tileSize);
		
		collision = false;
	}
	
}
