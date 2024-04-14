package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Staff extends Entity{

	public OBJ_Staff(GamePanel gp) {
		super(gp);
		name = "Wooden Staff";
		type = type_sword;
		down1 = setup("/objects/staff", gp.tileSize, gp.tileSize);
		
		attackValue = 1;
		dexterity = 0;
		
		attackArea.width = 36;
		attackArea.height = 36;
		
		description = "Feels vaguely magical. Does/nno damage.";
		
	}
	
}
