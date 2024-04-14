package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Basic_Sword extends Entity{

	public OBJ_Basic_Sword(GamePanel gp) {
		super(gp);
		name = "Basic Sword";
		type = type_sword;
		down1 = setup("/objects/basic_sword", gp.tileSize, gp.tileSize);
		
		attackValue = 1;
		dexterity = 0;
		
		attackArea.width = 36;
		attackArea.height = 36;
		
		description = "An old sword... /n+" + attackValue + " Attack";
		
	}
	
}
