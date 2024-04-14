package entity;

import main.GamePanel;
import object.OBJ_Key;

public class NPC_Owl extends Entity{
	
	public NPC_Owl(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0] = "Hello! Welcome to the Academy.";
		dialogues[1] = "Over the next few days, you'll level up your skills./nby clearing dungeons and fighing monsters to level up.";
		dialogues[2] = "Here's a dungeon key! The dungeon portal room is on your right.";
		dialogues[3] = "Come back for more keys once you've fought your first dungeon!";
		dialogues[4] = "Please complete a dungeon first before coming back!";
		dialogues[5] = "Congrats on finishing your first dungeon! Here's another key./nKeep completing dungeons and I'll continue giving you keys";
		dialogues[6] = "Here's another key!";
	}
	
	public void setAction() {
		
//		actionLockCounter++;
//		
//		if (actionLockCounter == 120) {
//		Random random = new Random();
//			int i = random.nextInt(100) + 1; // pick num from 1-100
//			
//			if (i <= 25) {
//				direction = "up";
//			}
//			
//			if (25 < i && i <= 50) {
//				direction = "left";
//			}
//			
//			if (50 < i && i <= 75) {
//				direction = "right";
//			}
//			
//			if (75 < i && i <= 100) {
//				direction = "down";
//			}
//			
//			actionLockCounter = 0;
//		}
	}	
	
	public void getImage() {
		
//		up1 = setup("mc_v1_walking_up1");
//		up2 = setup("mc_v1_walking_up2");
//		right1 = setup("mc_v1_walking_right1");
//		right2 = setup("mc_v1_walking_right2");
		down1 = setup("/npc/owl", gp.tileSize, gp.tileSize); 
		down2 = setup("/npc/owl", gp.tileSize, gp.tileSize);
//		left1 = setup("mc_v1_walking_left1");
//		left2 = setup("mc_v1_walking_left2");
//		up = setup("mc_v1_standing_back");
//		down = setup("/npc/owl");
//		left = setup("mc_v1_standing_left");
//		right = setup("mc_v1_standing_right");
		
	}
	public void speak() {
//		if (dialogues[dialogueIndex] == null || dialogueIndex > dialogues.length) {
//			dialogueIndex = 0;
//		}
		
		if (dialogueIndex == 2) {
			gp.player.inventory.add(new OBJ_Key(gp));
			gp.ui.addMessage("+1 Key!");
			gp.ui.currentDialogue = dialogues[dialogueIndex];
			dialogueIndex++;
		}
		
		else if (dialogueIndex == 4) {
			boolean hasKey = gp.containsClass(gp.player.inventory, OBJ_Key.class);
			if(hasKey == true) {
				dialogueIndex = 4;
				gp.ui.currentDialogue = dialogues[dialogueIndex];
			}
			else {
				dialogueIndex = 5;
				gp.ui.currentDialogue = dialogues[dialogueIndex];
				dialogueIndex++;
			}
		}
		else if (dialogueIndex == 6) {
			gp.ui.currentDialogue = dialogues[dialogueIndex];
			gp.player.inventory.add(new OBJ_Key(gp));
		}
		else {
			gp.ui.currentDialogue = dialogues[dialogueIndex];

			if(dialogueIndex < 6) {
				dialogueIndex++;
			}
			
		}
		
		if (dialogueIndex == 6) {
			gp.ui.currentDialogue = dialogues[dialogueIndex];
			gp.player.inventory.add(new OBJ_Key(gp));
		}
		
		gp.owlDialogueIndex = dialogueIndex;
		
		
		
		
	}
	

}
