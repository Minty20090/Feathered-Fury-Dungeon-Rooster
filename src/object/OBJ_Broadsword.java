package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Broadsword extends Entity {
	
	public OBJ_Broadsword (GamePanel gp) {
		super(gp);
		
		name = "Broadsword";
		type = type_sword;
		
		down1 = setup("/objects/broadsword", gp.tileSize, gp.tileSize);
		
		attackValue = 3;
		dexterityValue = -1;
		attackArea.width = 64;
		attackArea.height = 36;
		
		description = "A heavy broadsword /n+3 attack/n-1 dexterity";
	}

}
