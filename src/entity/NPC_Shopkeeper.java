package entity;

import main.GamePanel;

public class NPC_Shopkeeper extends Entity{
		
		public NPC_Shopkeeper(GamePanel gp) {
			super(gp);
			
			direction = "down";
			speed = 1;
			
			getImage();
			setDialogue();
		}
		
		public void setDialogue() {
			dialogues[0] = "Welcome to the shop!";
		}
		
		public void getImage() {
			down1 = setup("/npc/shopkeeper", gp.tileSize, gp.tileSize); 
			down2 = setup("/npc/shopkeeper", gp.tileSize, gp.tileSize);
			
		}
		public void speak() {
			if (dialogues[dialogueIndex] == null || dialogueIndex > dialogues.length) {
				gp.gameState = gp.shopState;
				dialogueIndex = 0;
			}
			gp.ui.currentDialogue = dialogues[dialogueIndex];
			dialogueIndex++;
		}
		
}
