package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Health_Potion extends Entity{
	GamePanel gp;
	int value = 5;
	
	public OBJ_Health_Potion (GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Health Potion";
		down1 = setup("/objects/health_potion", gp.tileSize, gp.tileSize);
		description = "Heals your life by/n" + value + ".";
		
	}
	public void use(Entity entity) {
		gp.ui.addMessage("+" + value + " Health!");
		entity.life += value;
		if (gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		
	}
}
