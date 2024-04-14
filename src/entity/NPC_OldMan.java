package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{
	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0] = "Hello";
		dialogues[1] = "Second Dialogue";
		dialogues[2] = "3rd";
		dialogues[3] = "4th";
		
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
	
	public void getImage() {
		
		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize); 
		down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		
	}
	
	public void speak() {
		super.speak();
		
	}

}
