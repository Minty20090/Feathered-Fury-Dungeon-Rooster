package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Normal_Shield extends Entity {

	public OBJ_Normal_Shield(GamePanel gp) {
		super(gp);
		name = "Standard Shield";
		type = type_shield;
		down1 = setup("/objects/rare_shield", gp.tileSize, gp.tileSize);
		defenseValue = 2;
		dexterityValue = 0;
		
		description = "A standard shield issued to/nAcademy students... /n+" + defenseValue + " Defense";
	}

}
