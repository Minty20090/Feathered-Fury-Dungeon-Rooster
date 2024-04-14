package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Slime extends Entity {
	
	GamePanel gp;

	public MON_Slime(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		
		name = "Slime";
		type = type_monster;
		movement = true;
		speed = 1;
		maxLife = 4;
		life = maxLife;
		attack = 2;
		defense = 0;
		exp = 2;
		
		solidArea.x = 0;
		solidArea.y = 24;
		solidArea.width = 64;
		solidArea.height = 40;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
		
	}
	
	public void getImage() {
		up1 = setup("/monster/slime_up", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/slime_down", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/slime_up", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/slime_down", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/slime_up", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/slime_down", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/slime_up", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/slime_down", gp.tileSize, gp.tileSize);
		
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1; // pick num from 1-100
			
			if (i <= 25) {
				direction = "up";
			}
			
			if (25 < i && i <= 50) {
				direction = "left";
			}
			
			if (50 < i && i <= 75) {
				direction = "right";
			}
			
			if (75 < i && i <= 100) {
				direction = "down";
			}
			
			actionLockCounter = 0;
		}
		
	}	
	public void damageReaction() {
		
		actionLockCounter = 0;
		direction = gp.player.direction;
		
	}

}
