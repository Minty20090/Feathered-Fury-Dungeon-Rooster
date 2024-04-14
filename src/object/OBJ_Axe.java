package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
	
	public OBJ_Axe (GamePanel gp) {
		super(gp);
		
		name = "Trusty Axe";
		type = type_axe;
		
		down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
		
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		
		description = "An ordinary woodscutter's axe./nGood at hacking things. /n+1 attack";
	}

}
