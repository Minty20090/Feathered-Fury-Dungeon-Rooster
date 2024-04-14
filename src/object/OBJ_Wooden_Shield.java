package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Wooden_Shield extends Entity {

	public OBJ_Wooden_Shield(GamePanel gp) {
		super(gp);
		name = "Wooden Shield";
		type = type_shield;
		down1 = setup("/objects/wooden_shield", gp.tileSize, gp.tileSize);
		defenseValue = 1;
		
		description = "An old shield... /n+" + defenseValue + " Defense";
	}

}
