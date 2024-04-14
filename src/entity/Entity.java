package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	GamePanel gp;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, up, down, left, right;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage image, image2, image3, image4;
	public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	
	public int solidAreaDefaultX, solidAreaDefaultY;
	
	public Boolean collision = false;
	String[] dialogues = new String[20];
	
	// STATE
	public int worldX, worldY;
	public String direction = "down";
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public boolean movement = false;
	public boolean collisionOn = false;
	public boolean invincible = false;
	boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
	public boolean icon;
	
	//COUNTER
	public int actionLockCounter;
	public int invincibleCounter = 0;
	public int invincibleTime = 30;
	int hpBarCounter = 0;
	int dyingCounter = 0;
	
	
	// CHARACTER ATTRIBUTES
	public String name;
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int speed;
	public int dialogueIndex = 0;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
	
	// ITEM ATTRIBUTES
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int inventorySize = 20;
	public int attackValue = 0;
	public int defenseValue = 0;
	public int dexterityValue = 0;
	public String description;
	public int useCost;
	
	// TYPE 
	public int type; // 0 = player, 1 = npc, 2 = monster
	public int type_player = 0;
	public int type_npc = 1;
	public int type_monster = 2;
	public int type_sword = 3;
	public int type_axe = 4;
	public int type_shield = 5;
	public int type_consumable = 6;
	
	// TELEPORTER
	public int playerX;
	public int playerY;
	public int destination;
	
	
	public Entity (GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {}
	public void damageReaction() {}
	public void update() {
		setAction();
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if (this.type == type_monster && contactPlayer == true) {
			if (gp.player.invincible == false) {
				boolean dodged = gp.player.calculateDodgeProbability(gp.player.dexterity);
				if (dodged == true) {
					int damage = attack - gp.player.defense;
					if (damage < 0) {
						damage = 0;
					}
					gp.player.life -= damage;
					gp.ui.addMessage("-" + damage + " Health!");
					gp.player.invincible = true;
				}
				else {
					gp.ui.addMessage("Dodged!");
					gp.player.invincible = true;
				}
				
			}
		}
		
		if (movement == true) {
			if (collisionOn == false) {
				switch (direction) {
				case "up": 
					worldY -= speed; 
					break;
				case "down": 
					worldY += speed; 
					break;
				case "left": 
					worldX -= speed; 
					break;	
				case "right": 
					worldX += speed; 
					break;
					
				}
					
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
			
			if (invincible == true) {
				invincibleCounter++;
				if (invincibleCounter > 40) {
					invincible = false;
					invincibleCounter = 0;
				}
			}
		}
		
	
		
	}
	
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		BufferedImage image = null;
		
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
						
			switch (direction) {
			case "up":
				if (spriteNum == 1) { image = up1; }
				if (spriteNum == 2) { image = up2; }
				break;
				
			case "down":
				if (spriteNum == 1) { image = down1; }
				if (spriteNum == 2) { image = down2; }
				break;
				
			case "left":
				if (spriteNum == 1) { image = left1; }
				if (spriteNum == 2) { image = left2; }
				break;
				
			case "right":
				if (spriteNum == 1) { image = right1; }
				if (spriteNum == 2) { image = right2; }
				break;
			}
			
			//MONSTER HP BAR
			if (type == type_monster && hpBarOn== true) {
				double oneScale = (double) gp.tileSize/maxLife;
				double hpBarValue = oneScale * life;
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect(screenX-1, screenY-1, gp.tileSize +2, 7);
				g2.setColor(new Color(255, 0, 55));
				g2.fillRect(screenX, screenY, (int) hpBarValue, 5);
				
				hpBarCounter++;
				
				if (hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			if (invincible == true) {
				hpBarCounter = 0;
				hpBarOn = true;
				changeAlpha(g2, 0.4f);
			}
			
			if (dying == true && alive == true) {
				dyingAnimation(g2);
			}
			
			g2.drawImage(image, screenX, screenY, null);

			changeAlpha(g2, 1f);
		}
	}
	
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		
		int i = 5;
		if (dyingCounter <= i) { changeAlpha(g2, 0f);}
		if (i < dyingCounter && dyingCounter <= i*2) { changeAlpha(g2, 1f);}
		if (i*2 < dyingCounter && dyingCounter <= i*3) { changeAlpha(g2, 0f);}
		if (i*3 < dyingCounter && dyingCounter <= i*4) { changeAlpha(g2, 1f); }
		if (i*4 < dyingCounter && dyingCounter <= i*5) { changeAlpha(g2, 0f); }
		if (i*5 < dyingCounter && dyingCounter <= i*6) { changeAlpha(g2, 1f); }
		if (dyingCounter > i*6) {
			alive = false;
			gp.ui.addMessage("+" + exp + " exp!");
			gp.player.exp += exp;
			gp.player.checkLevelUp();
		}
		
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	public BufferedImage setup(String imagePath, int width, int height) {
	    UtilityTool uTool = new UtilityTool();
	    BufferedImage image = null;
	    try {
	        image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
	        image = uTool.scaleImage(image, width, height);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return image;
	}
	public void use(Entity entity) {}
	public void speak() {
		if (dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch (gp.player.direction) {
		case "up":
			direction = "down";
		case "down":
			direction = "up";
		case "right":
			direction = "left";
		case "left":
			direction = "right";
			
		}
	}
	
	
}	
