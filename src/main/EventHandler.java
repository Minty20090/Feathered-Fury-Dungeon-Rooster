package main;

import object.OBJ_Key;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][];
	int eventRectDefaultX, eventRectDefaultY;

	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		int col = 0;
		int row = 0;
		
		while (col < gp.maxWorldCol && row < gp.maxScreenRow) {
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRectDefaultX = eventRect[col][row].x;
			eventRectDefaultY = eventRect[col][row].y;
			
			col++;
			
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
		
		
		
	}
	
	public void checkEvent() {
		
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		
		if (distance > gp.tileSize) {
			canTouchEvent = true;
		}
		
		if (canTouchEvent == true) {
			if (gp.currentMap == 0) { // main hall
				if (hit(6,5,"any") == true) { damagePit(6,5,gp.dialogueState); }
				if (hit(5,4,"any") == true) { damagePit(6,5,gp.dialogueState); }
				if (hit(4,5,"left") == true) { healingPool(4,5,gp.dialogueState); }
			}
			if(gp.currentMap == 2) { //portal room
				if (hit(6,4,"up") == true) { enterDungeon(); }
			}
			
		}
	}
	public void enterDungeon() {
		boolean hasKey = gp.containsClass(gp.player.inventory, OBJ_Key.class);
		if(hasKey == true) {
			gp.dungeon.roomCounter = 0;
			gp.dungeon.room_type = gp.dungeon.starting_room;
			gp.inDungeon = true;
			gp.dungeon.loadMap();
			gp.removeOBJKey(gp.player.inventory);
		}
		else {
			gp.ui.currentDialogue = "You need a dungeon key!!";
			gp.gameState = gp.dialogueState;
		}
	}
	
	public boolean hit(int col, int row, String reqDirection) {
		
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX  + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY  + gp.player.solidArea.y;
		
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row* gp.tileSize + eventRect[col][row].y;
		
		if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection == "any" ) {
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
				
			}
			
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRectDefaultX;
		eventRect[col][row].y = eventRectDefaultY;
		
		
		return hit;
	}
	
	public void damagePit(int col, int row, int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fell into a pit!";
		gp.player.life--;
		
//		eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	
	public void healingPool(int col, int row, int gameState) {
		if (gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.ui.currentDialogue = "You miraculously recover health /nbecause you ran into a happy fairy!";
			gp.player.life++;
			gp.player.attackCanceled = true;
		}
	}
}
