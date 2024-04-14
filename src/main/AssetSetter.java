package main;

import java.util.Arrays;

import entity.Entity;
import entity.NPC_Owl;
import object.OBJ_Health_Potion;
import object.OBJ_Teleporter;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		
		this.gp = gp;
		
	}
	
	public void setObject() {
		if(gp.currentMap == 0) { // main hall
			Arrays.fill(gp.obj, null);
			int i = 0;
			gp.obj[i] = new OBJ_Teleporter(gp, 1, 11, 7); // to shop
			gp.obj[i].worldX = 0 * gp.tileSize;
			gp.obj[i].worldY = 8 * gp.tileSize;
			
			i++;
			gp.obj[i] = new OBJ_Teleporter(gp, 2, 1, 7); // to portal room
			gp.obj[i].worldX = 26 * gp.tileSize;
			gp.obj[i].worldY = 8 * gp.tileSize;
			
//			i++;
//			gp.obj[i] = new OBJ_Axe(gp); 
//			gp.obj[i].worldX = 25 * gp.tileSize;
//			gp.obj[i].worldY = 8 * gp.tileSize;
//			
//			i++;
//			gp.obj[i] = new OBJ_Normal_Shield(gp); 
//			gp.obj[i].worldX = 24 * gp.tileSize;
//			gp.obj[i].worldY = 8 * gp.tileSize;
//			
//			i++;
//			gp.obj[i] = new OBJ_Health_Potion(gp); 
//			gp.obj[i].worldX = 23 * gp.tileSize;
//			gp.obj[i].worldY = 8 * gp.tileSize;
		}
		
		else if (gp.currentMap == 1) { // shop
			Arrays.fill(gp.obj, null);
			gp.obj[0] = new OBJ_Teleporter(gp, 0, 1, 8); // to main hall
			gp.obj[0].worldX = 13 * gp.tileSize;
			gp.obj[0].worldY = 7 * gp.tileSize;
		}
		
		else if (gp.currentMap == 2) { // portal_room
			Arrays.fill(gp.obj, null);
			gp.obj[0] = new OBJ_Teleporter(gp, 0, 25, 8); // to main hall
			gp.obj[0].worldX = 0 * gp.tileSize;
			gp.obj[0].worldY = 8 * gp.tileSize;
			
//			gp.obj[1] = new OBJ_Teleporter(gp, 3, 5, 5); // to dungeon
//			gp.obj[1].worldX = 6 * gp.tileSize;
//			gp.obj[1].worldY = 4 * gp.tileSize;
		}
		else if (gp.currentMap == 3 && gp.dungeon.room_type == gp.dungeon.healing_room) {
			gp.obj[0] = new OBJ_Health_Potion(gp); 
			gp.obj[0].worldX = 6 * gp.tileSize;
			gp.obj[0].worldY = 6 * gp.tileSize;
			
		}
		else if (gp.currentMap == 3 && (gp.dungeon.room_type == gp.dungeon.starting_room || gp.dungeon.room_type == gp.dungeon.exit_room)) { //dungeon
			Arrays.fill(gp.obj, null);
			gp.obj[7] = new OBJ_Teleporter(gp, 2, 7, 6); // to portal room
			gp.obj[7].worldX = 6 * gp.tileSize;
			gp.obj[7].worldY = 11 * gp.tileSize;
			
		}
		else if (gp.currentMap == 3) {
			Arrays.fill(gp.obj, null);
		}
		else {
			Arrays.fill(gp.obj, null);
		}
		
		if (gp.currentMap == 3 && gp.dungeon.room_type == gp.dungeon.healing_room) {
			gp.obj[0] = new OBJ_Health_Potion(gp); 
			gp.obj[0].worldX = 6 * gp.tileSize;
			gp.obj[0].worldY = 6 * gp.tileSize;
			
		}
		
		
		
	}
	public void setNpc() {
		if(gp.currentMap == 0) { // main hall
			Arrays.fill(gp.npc, null);
			Arrays.fill(gp.npcInteractions, null);
			
//			gp.npc[0] = new NPC_OldMan(gp);
//			gp.npc[0].worldX = 13 * gp.tileSize;
//			gp.npc[0].worldY = 8 * gp.tileSize;
			
			gp.npc[1] = new NPC_Owl(gp);
			gp.npc[1].worldX = 13 * gp.tileSize;
			gp.npc[1].worldY = 5 * gp.tileSize;
			gp.npc[1].movement = false;
			gp.npc[1].dialogueIndex = gp.owlDialogueIndex;
			
			gp.npcInteractions[1] = new NPC_Owl(gp);
			gp.npcInteractions[1].worldX = 13 * gp.tileSize;
			gp.npcInteractions[1].worldY = 6 * gp.tileSize;
			gp.npcInteractions[1].movement = false;
			gp.npcInteractions[1].collisionOn = false;
			gp.npcInteractions[1].dialogueIndex = gp.owlDialogueIndex;
		}
		else {
			Arrays.fill(gp.npc, null);
			Arrays.fill(gp.npcInteractions, null);
			
		}
		
	}
	
	public void setMonster() {
		if (gp.currentMap == 2) { // portal_room
			Arrays.fill(gp.monster, null);
//			gp.monster[0] = new MON_Slime(gp);
//			gp.monster[0].worldX = 5 * gp.tileSize;
//			gp.monster[0].worldY = 5 * gp.tileSize;
//			gp.monster[1] = new MON_Slime(gp);
//			gp.monster[1].worldX = 6 * gp.tileSize;
//			gp.monster[1].worldY = 6 * gp.tileSize;
//			gp.monster[2] = new MON_Slime(gp);
//			gp.monster[2].worldX = 6 * gp.tileSize;
//			gp.monster[2].worldY = 7 * gp.tileSize;
		}
		else if(gp.currentMap == 3 && gp.dungeon.room_type == gp.dungeon.combat_room) {
			for(int i = 0; i < gp.dungeon.monsterList.size(); i++) {
				Entity monster = gp.dungeon.monsterList.get(i);
				if(monster.worldY < gp.tileSize * 4) {
					monster.worldY += gp.tileSize * 4;
					monster.worldX += gp.tileSize;
				}
//				monster.worldY += gp.tileSize * 4;
//				monster.worldX += gp.tileSize;
					
				gp.monster[i] = monster;
			}
		}
		else {
			Arrays.fill(gp.monster, null);
		}
		
	}
	
	
	
}
