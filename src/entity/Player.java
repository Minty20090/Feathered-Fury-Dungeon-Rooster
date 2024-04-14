package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Basic_Sword;
import object.OBJ_Broadsword;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Staff;
import object.OBJ_Wooden_Shield;

public class Player extends Entity{
	
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public boolean attackCanceled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int inventorySize = 20;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		
		// SET PLAYER COLLISION AREA
		solidArea = new Rectangle(12, 27, 43, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
		
	}
	
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
//		inventory.add(new OBJ_Key(gp));
//		inventory.add(new OBJ_Key(gp));
//		inventory.add(new OBJ_Key(gp));
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 13;
		worldY = gp.tileSize * 8;
		speed = 4;
		direction = "down";
		
		// PLAYER STATUS
		currentWeapon = new OBJ_Basic_Sword(gp);
		currentShield = new OBJ_Wooden_Shield(gp);
		
		maxLife = 6;
		life = maxLife;
		level = 1;
		strength = 1; // scales attack
		dexterity = currentShield.dexterityValue + currentWeapon.dexterityValue; // scales defense
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		attack = getAttack();
		defense = currentShield.defenseValue;
		
	}
	
	public void classSelection() {
		inventory.clear();
		
		if (gp.playerType == gp.fighter) {
			currentWeapon = new OBJ_Basic_Sword(gp);
			currentShield = new OBJ_Wooden_Shield(gp);
			
		}
		if (gp.playerType == gp.mage) {
			projectile = new OBJ_Fireball(gp);
			currentWeapon = new OBJ_Staff(gp);
			currentShield = new OBJ_Wooden_Shield(gp);
		}
		if(gp.playerType == gp.berserker) {
			currentWeapon = new OBJ_Broadsword(gp);
			currentShield = new OBJ_Wooden_Shield(gp);
		}
		
		inventory.add(currentWeapon);
		inventory.add(currentShield);
	}
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		if(gp.playerType == gp.mage) {
			projectile.attack = strength * currentWeapon.attackValue;
		}
		return strength * currentWeapon.attackValue;
	}
	
	public void getPlayerImage() {
		up1 = setup("/Player/mc_v1_walking_up1", gp.tileSize, gp.tileSize);
		up2 = setup("/Player/mc_v1_walking_up2", gp.tileSize, gp.tileSize);
		right1 = setup("/Player/mc_v1_walking_right1", gp.tileSize, gp.tileSize);
		right2 = setup("/Player/mc_v1_walking_right2", gp.tileSize, gp.tileSize);
		down1 = setup("/Player/mc_v1_walking_down1", gp.tileSize, gp.tileSize); 
		down2 = setup("/Player/mc_v1_walking_down2", gp.tileSize, gp.tileSize);
		left1 = setup("/Player/mc_v1_walking_left1", gp.tileSize, gp.tileSize);
		left2 = setup("/Player/mc_v1_walking_left2", gp.tileSize, gp.tileSize);
		up = setup("/Player/mc_v1_standing_back", gp.tileSize, gp.tileSize);
		down = setup("/Player/mc_v1_standing_front", gp.tileSize, gp.tileSize);
		left = setup("/Player/mc_v1_standing_left", gp.tileSize, gp.tileSize);
		right = setup("/Player/mc_v1_standing_right", gp.tileSize, gp.tileSize);
		
	}
	
	public void getPlayerAttackImage() {
		if (currentWeapon.type == type_sword) {
			attackUp1 = setup("/Player/mc_attack_up_1", gp.tileSize, gp.tileSize * 2);
		    attackUp2 = setup("/Player/mc_attack_up_2", gp.tileSize, gp.tileSize * 2);
		    attackDown1 = setup("/Player/mc_attack_down_1", gp.tileSize, gp.tileSize * 2);
		    attackDown2 = setup("/Player/mc_attack_down_2", gp.tileSize, gp.tileSize * 2);
		    attackLeft1 = setup("/Player/mc_attack_left_1", gp.tileSize * 2, gp.tileSize);
		    attackLeft2 = setup("/Player/mc_attack_left_2", gp.tileSize * 2, gp.tileSize);
		    attackRight1 = setup("/Player/mc_attack_right_1", gp.tileSize * 2, gp.tileSize);
		    attackRight2 = setup("/Player/mc_attack_right_2", gp.tileSize * 2, gp.tileSize);
		}
		if (currentWeapon.type == type_axe) {
			attackUp1 = setup("/Player/mc_axe_attack_up_1", gp.tileSize, gp.tileSize * 2);
		    attackUp2 = setup("/Player/mc_axe_attack_up_2", gp.tileSize, gp.tileSize * 2);
		    attackDown1 = setup("/Player/mc_axe_attack_down_1", gp.tileSize, gp.tileSize * 2);
		    attackDown2 = setup("/Player/mc_axe_attack_down_2", gp.tileSize, gp.tileSize * 2);
		    attackLeft1 = setup("/Player/mc_axe_attack_left_1", gp.tileSize * 2, gp.tileSize);
		    attackLeft2 = setup("/Player/mc_axe_attack_left_2", gp.tileSize * 2, gp.tileSize);
		    attackRight1 = setup("/Player/mc_axe_attack_right_1", gp.tileSize * 2, gp.tileSize);
		    attackRight2 = setup("/Player/mc_axe_attack_right_2", gp.tileSize * 2, gp.tileSize);
		}
		
	}
	
	public void update() {
		
		if (attacking == true && gp.playerType != gp.mage) {
			attacking();
		}
		else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || 
				keyH.enterPressed == true || keyH.spacePressed == true || keyH.shotKeyPressed == true) {
			if (keyH.upPressed == true) {
				direction = "up";
			}
			else if (keyH.downPressed == true) {
				direction = "down";
			}
			
			else if (keyH.leftPressed == true) {
				direction = "left";
			}
			else if (keyH.rightPressed == true) {
				direction = "right";
			}
			
			if (keyH.spacePressed == true) {
				attacking = true;
			}
			
			// check tile collision
			
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// check object collision
			int objIndex = gp.cChecker.checkObject(this, true);
			interactObject(objIndex);
			if (keyH.enterPressed) {
				System.out.println("ENTER PRESSED");
			}
			// check npc collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// check npc interaction
			int npcInteractionIndex = gp.cChecker.checkEntity(this, gp.npcInteractions);
			interactNPCInteraction(npcInteractionIndex);
			
			// check monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// check event
			gp.eHandler.checkEvent();
			
			if (collisionOn == false && keyH.enterPressed == false && keyH.spacePressed == false && keyH.shotKeyPressed == false) {
				switch (direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;	
				case "right": worldX += speed; break;
					
				}
				spriteCounter++;
				if (spriteCounter > 12) {
					if (spriteNum == 1) {
						spriteNum = 2;
					}
					
					else if (spriteNum == 2) {
						spriteNum = 1;
					}
					
					spriteCounter = 0;
				}
				
			}
			
			if ((keyH.enterPressed == true || keyH.spacePressed == true) && attackCanceled == false && gp.playerType != gp.mage) {
				attacking = true;
				spriteCounter = 0;
			}
			
			if (keyH.shotKeyPressed == true) {
				projectile.set(worldX, worldY, direction, true, this);
				gp.projectileList.add(projectile);
			}
			
			if (invincible == true) {
				invincibleCounter++;
				if (invincibleCounter > invincibleTime) {
					invincible = false;
					invincibleCounter = 0;
				}
					
			}
			
			attackCanceled = false;
			keyH.enterPressed = false;
			keyH.spacePressed = false;
			
		}
		else {
			spriteCounter = 100;
		}
		
		
		
	}
	
	public void attacking() {
		spriteCounter++;
		if (spriteCounter <= 10) {
			spriteNum = 1;
		}
		if (spriteCounter < 10 && spriteCounter <=30) {
			spriteNum = 2;
			
			// store player current position
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//adjust player position for the attack area
			
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			
			//attack area becomes solid area
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			// check monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack);
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
		}
		if (spriteCounter > 25 ) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	public void interactObject(int i) {
		if (i != 999) {
			String text;
			if (gp.obj[i].name == "Teleporter") {
				if(gp.obj[i].destination == 2) {
					gp.inDungeon = false;
				}
				worldX = gp.obj[i].playerX * gp.tileSize;
				worldY = gp.obj[i].playerY * gp.tileSize;
				
				gp.currentMap = gp.obj[i].destination;
				gp.aSetter.setObject();
				gp.aSetter.setNpc();
				gp.aSetter.setMonster();
				
				gp.tileM.loadMap(gp.currentMap);
				
				gp.gameState = gp.loadingState;
				
				
			}
			else if (gp.obj[i].name == "Doorway" && icon == false) {
				if(gp.obj[i].destination != 999) {
					
					gp.dungeon.room_type = gp.obj[i].destination;
					gp.dungeon.loadMap();
					System.out.println(icon);
				}
			}
			else if(gp.obj[i].name == "Doorway" && icon == true) {
				System.out.println("icon");
			}
			else {
				if (inventory.size() != inventorySize) {
					inventory.add(gp.obj[i]);
					text = "Picked up a " + gp.obj[i].name + "!";
					gp.obj[i] = null;
				}
				else {
					text = "Inventory Full!";
				}
				gp.ui.addMessage(text);
			}
			
		}
		
	}
	
	public void interactNPC(int i) {
		if(keyH.enterPressed == true) {
			if (i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
	}
	
	public void interactNPCInteraction(int i) {
		if(keyH.enterPressed == true) {
			if (i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npcInteractions[i].speak();
				
			}
		}
		
	}
	
	public void contactMonster(int i) {
		if (i != 999) {
			if (invincible == false && gp.monster[i].dying != true) {
				boolean dodged = calculateDodgeProbability(dexterity);
				if (dodged == false) {
					int damage = gp.monster[i].attack - defense;
					if (damage < 0) {
						damage = 0;
					}
					life -= damage;
					gp.ui.addMessage("-" + damage + " Health!");
					invincible = true;
				}
				else {
					gp.ui.addMessage("Dodged!");
					gp.player.invincible = true;
				}
				
			}
			
		}
	}
	
	public void damageMonster(int i, int attack) {
		if (i != 999) {
			
			if (gp.monster[i].invincible == false) {
				int damage = attack - gp.monster[i].defense;
				if (damage < 0) {
					damage = 0;
				}
				gp.monster[i].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
			}
			if(gp.monster[i].life <= 0) {
				gp.monster[i].dying = true;
				
			}
			
			
		}
	}
	
	public void checkLevelUp() {
		if (exp > nextLevelExp) {
			exp -= nextLevelExp;
			level++;
			nextLevelExp = 2 * nextLevelExp;
			attack = getAttack();
			gp.ui.addMessage("Level up!");
			gp.gameState = gp.levelUpState;
		}
	}

	public static boolean calculateDodgeProbability(int dexterity) {

		int MAX_DEXTERITY = 100;
	    double MAX_DODGE_PROBABILITY = 0.25; // Maximum dodge probability (25%)

        double scaledDexterity = (double) dexterity / MAX_DEXTERITY;

        // Using logarithmic scaling for dodge probability calculation
        double dodgeProbability = Math.log(scaledDexterity + 1) / Math.log(2);

        // Limiting dodge probability between 0 and the maximum allowed value
        dodgeProbability = Math.min(MAX_DODGE_PROBABILITY, dodgeProbability);
        
        Random random = new Random();
        double randomValue = random.nextDouble(); // Random value between 0 and 1

        // Compare random value with dodge probability to determine if dodge occurs
        return randomValue <= dodgeProbability;
    }
	
	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if (itemIndex < gp.player.inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
				int previousDex = currentWeapon.dexterityValue;
				System.out.println(previousDex);
				currentWeapon = selectedItem;
				attack = getAttack();
				dexterity -= previousDex;
				dexterity = dexterity + currentWeapon.dexterityValue;
				getPlayerAttackImage();
			}
			if (selectedItem.type == type_shield) {
				int previousDef = currentShield.defenseValue;
				int previousDex = currentShield.dexterityValue;
				currentShield = selectedItem;
				defense -= previousDef;
				defense += currentShield.defenseValue;
				dexterity = dexterity + currentShield.dexterityValue - previousDex;
			}
			if (selectedItem.type == type_consumable) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
			
		}
		
	}
	
	public void draw(Graphics2D g2) {
		int tempScreenX = screenX;
		int tempScreenY = screenY; 
		
		BufferedImage image = null;
		if (spriteCounter == 100) {
			switch (direction) {
			case "up": image = up; break;
			case "down": image = down; break;
			case "left": image = left; break;
			case "right": image = right; break;
			}
		}
		else {
			switch (direction) {
		    case "up":
		        if (attacking == false) { 
		        	if (spriteNum == 1) { image = up1;} 
		        	else { image = up2; }
		        	
		        } else {
		        	tempScreenY -= gp.tileSize;
		            if (spriteNum == 1) { image = attackUp1;} 
		            else { image = attackUp2; }
		        }
		        break;
		    case "down":
		        if (attacking == false) {
		            if (spriteNum == 1) {
		                image = down1;
		            } else {
		                image = down2;
		            }
		        } else {
		            if (spriteNum == 1) {
		                image = attackDown1;
		            } else {
		                image = attackDown2;
		            }
		        }
		        break;
		    case "left":
		        if (attacking == false) {
		            if (spriteNum == 1) {
		                image = left1;
		            } else {
		                image = left2;
		            }
		        } else {
		        	tempScreenX -= gp.tileSize;
		            if (spriteNum == 1) {
		                image = attackLeft1;
		            } else {
		                image = attackLeft2;
		            }
		        }
		        break;
		    case "right":
		        if (attacking == false) {
		            if (spriteNum == 1) {
		                image = right1;
		            } else {
		                image = right2;
		            }
		        } else {
		            if (spriteNum == 1) {
		                image = attackRight1;
		            } else {
		                image = attackRight2;
		            }
		        }
		        break;
		
			}

		}
		
		if (invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
	}
	
	public void setPlayerLocation(int x, int y) {
		worldX = x * gp.tileSize;
		worldY = y * gp.tileSize;
	}
	
}
