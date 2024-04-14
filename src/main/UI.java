package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font MrPixel;
	Font Lightning;
	BufferedImage heart_full, heart_half, heart_empty;
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int slotCol = 0;
	public int slotRow = 0;
	public int maxSlotCol = 4;
	public int maxSlotRow = 3;
	
	public int titleScreenState = 0;
	
	// LOADING SCREEN
	int counter = 0;
	int i = 0;
	String loadingText = "Loading";
	
	Color backgroundColor = new Color(255, 213, 128);
	
	
	
//	double playTime; 
//	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	
	
	public UI (GamePanel gp) {
		
		this.gp = gp;
		try {
			InputStream is = getClass().getResourceAsStream("/font/MP16REG.ttf");
			MrPixel = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/fighting spirit TBS.ttf");
			Lightning = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_empty = heart.image3;
		
	}
	
	public void addMessage(String text) {
		
		message.add(text);
		messageCounter.add(0);
		
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(MrPixel);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		// HELP STATE
		if (gp.gameState == gp.helpState) {
			drawHelpScreen();
		}
		
		// PLAY STATE
		if (gp.gameState == gp.playState || gp.gameState == gp.dungeonState) {
			drawPlayerLife();
			drawMessage();
		}
		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		
		// DIALOGUE STATE
		if (gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		
		// LOADING STATE
		if (gp.gameState == gp.loadingState) {
			drawLoadingScreen();
		}
		
		// CHARACTER STATE
		if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();
		}
		
		// LEVEL UP STATE
		if (gp.gameState == gp.levelUpState) {
			drawLevelUpScreen();
		}
		
		// SHOP STATE
		if (gp.gameState == gp.shopState) {
			drawShopScreen();
		}
		
		if (gp.gameState == gp.deathState) {
			drawDeathScreen();
		}
		if (gp.gameState == gp.dungeonDoneState) {
			drawDungeonDoneScreen();
		}
	}
	
	public void drawDungeonDoneScreen() {
		int frameX = gp.tileSize * 3;
		int frameY = gp.tileSize * 2;
		int frameWidth = gp.tileSize * 12;
		int frameHeight = gp.tileSize * 4;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(80F));
		String text = "Dungeon Complete!";
		
		int textX = getXForCenteredText(text);
		int textY = frameY + gp.tileSize * 2;
		
		g2.drawString(text, textX, textY);
		textY += 85;
		textX = (int) (frameX + gp.tileSize * .5);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(50F));
		final int lineHeight = 48;
		
		text = "Return to the Portal Room!";
		textX = getXForCenteredText(text);
		g2.drawString(text, textX, textY);
		
	}
	
	public void drawDeathScreen() {
		int frameX = gp.tileSize * 3;
		int frameY = gp.tileSize * 2;
		int frameWidth = gp.tileSize * 12;
		int frameHeight = gp.tileSize * 6;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(80F));
		String text = "You Died!";
		
		int textX = getXForCenteredText(text);
		int textY = frameY + gp.tileSize * 2;
		
		g2.drawString(text, textX, textY);
		textY += 85;
		textX = (int) (frameX + gp.tileSize * .5);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(40F));
		final int lineHeight = 48;
		
		text = "Respawn";
		textX = getXForCenteredText(text);
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX - gp.tileSize, textY);
		}
		textY += lineHeight;
		
		text = "Return to title screen";
		textX = getXForCenteredText(text);
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX - gp.tileSize, textY);
		}
		textY += lineHeight;
		
	}
	
	public void drawHelpScreen() {
		// CREATE A FRAME
		int frameX = gp.tileSize * 3;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 12;
		int frameHeight = gp.tileSize * 8;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(60F));
		
		int textX = getXForCenteredText("Controls");
		int textY = frameY + gp.tileSize * 3/2;
		
		g2.drawString("Controls", textX, textY);
		textY += 65;
		textX = (int) (frameX + gp.tileSize * .5);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(40F));
		final int lineHeight = 48;
		
		// NAMES
		g2.drawString("Movement", textX, textY);
		textY += lineHeight;
		g2.drawString("Character/Inventory Screen", textX, textY);
		textY += lineHeight;
		g2.drawString("Interact/Dialogue", textX, textY);
		textY += lineHeight;
		g2.drawString("Fire fireball", textX, textY);
		textY += lineHeight;
		g2.drawString("Help Screen", textX, textY);
		textY += lineHeight;
		g2.drawString("Pause Game", textX, textY);
		textY += lineHeight;
		
		// VALUES
		int tailX = frameX + frameWidth - 30;
		textY = frameY + gp.tileSize * 3/2 + 65;
		String value;
		
		value = "WASD";
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = "C";
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = "Enter";
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = "F";
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = "H";
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = "P";
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = "Press H to start the game!";
		textX = getXForAlignedToRightText(value, tailX);
		textY = frameY + frameHeight - lineHeight/2;
		g2.drawString(value, textX, textY);
		
	}
	public void drawShopScreen() {
		// CREATE A FRAME
		int frameX = gp.tileSize * 2;
		int frameY = gp.tileSize * 4;
		int frameWidth = gp.screenWidth - frameX * 2;
		int frameHeight = gp.screenWidth - frameY * 2;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// HEADING
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
		int x = frameX + gp.tileSize/2;
		int y = frameY + gp.tileSize/2;
		g2.drawString("SHOP", frameX, frameY);
		g2.setStroke(new BasicStroke(3));
		y += 7;
		int itemNameWidth = g2.getFontMetrics().stringWidth("SHOP");
        g2.drawLine(x, y, x + itemNameWidth, y);
		
		// SLOTS
		final int slotXStart = frameX + y + (int)(gp.tileSize * .375);
		final int slotYStart = frameY + y + (int)(gp.tileSize * .375);
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = gp.tileSize + 3;
		
		// CURSOR
		int cursorX = slotXStart + slotSize * slotCol;
		int cursorY = slotYStart + slotSize * slotRow;
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		// DRAW CURSOR
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 5, 5);
		
		// DRAW ITEMS
		for (int i = 0; i < gp.player.inventory.size(); i++) {
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			slotX += slotSize;
			if (i == 10 || i == 21 || i == 32) {
				slotX = slotXStart;
				slotY += slotSize;
			}
		}
		
		// CREATE A FRAME
		int dFrameX = frameX;
		int dFrameY = frameY + gp.tileSize/2 + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize * 4;
		drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
		
		// DRAW DESCRIPTION TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		int itemIndex = getItemIndexOnSlot();
		
		if (itemIndex < gp.player.inventory.size()) {
			// ITEM NAME
			String text = gp.player.inventory.get(itemIndex).name;
			x = dFrameX + gp.tileSize/2;
			y = dFrameY + gp.tileSize*3/4;
			g2.drawString("["+ text + "]", x, y);
			
			g2.setStroke(new BasicStroke(3));
			y += 7;
			itemNameWidth = g2.getFontMetrics().stringWidth(text);
	        g2.drawLine(x, y, x + itemNameWidth, y);
			
			// ITEM DESCRIPTION
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
			text = gp.player.inventory.get(itemIndex).description;
			String[] lines = text.split("/n");
			for (int i = 0; i < lines.length; i++) {
				y += 32;
				text = lines[i];
				g2.drawString(text, x, y);
			}
		}
		
	}
	
	public void drawInventory() {
		// CREATE A FRAME
		int frameX = gp.tileSize * 11;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 6;
		int frameHeight = gp.tileSize * 5;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// SLOTS
		final int slotXStart = frameX + (int)(gp.tileSize * .375);
		final int slotYStart = frameY + (int)(gp.tileSize * .375);
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = gp.tileSize + 3;
		
		// CURSOR
		int cursorX = slotXStart + slotSize * slotCol;
		int cursorY = slotYStart + slotSize * slotRow;
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		// DRAW CURSOR
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 5, 5);
		
		// DRAW ITEMS
		for (int i = 0; i < gp.player.inventory.size(); i++) {
			
			// equipped items icon
			if (gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(new Color(255, 210, 110));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			// image
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			slotX += slotSize;
			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXStart;
				slotY += slotSize;
			}
		}
		
		
		
		int itemIndex = getItemIndexOnSlot();
		
		if (itemIndex < gp.player.inventory.size()) {

			// CREATE A FRAME
			int dFrameX = frameX;
			int dFrameY = frameY + gp.tileSize/2 + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize * 4;
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
			
			// DRAW DESCRIPTION TEXT
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
			
			// ITEM NAME
			String text = gp.player.inventory.get(itemIndex).name;
			int x = dFrameX + gp.tileSize/2;
			int y = dFrameY + gp.tileSize*3/4;
			g2.drawString("["+ text + "]", x, y);
			
			g2.setStroke(new BasicStroke(3));
			y += 7;
			int itemNameWidth = g2.getFontMetrics().stringWidth(text);
	        g2.drawLine(x, y, x + itemNameWidth, y);
			
			// ITEM DESCRIPTION
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
			text = gp.player.inventory.get(itemIndex).description;
			String[] lines = text.split("/n");
			for (int i = 0; i < lines.length; i++) {
				y += 32;
				text = lines[i];
				g2.drawString(text, x, y);
			}
		}
		
	}
	
	public void drawLevelUpScreen() {
		
		int x = gp.tileSize;
		int y = (int) (gp.tileSize * 1.5);
		int width = gp.maxScreenCol * gp.tileSize - gp.tileSize*2;
		int height = gp.maxScreenRow * gp.tileSize - gp.tileSize*3;
		
		drawSubWindow(x, y, width, height);
		
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
		String text = "You leveled up!";
		y += 96;
		x = getXForCenteredText(text);
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
		text = "Choose a skill to level up!";
		y += 70;
		x = getXForCenteredText(text);
		g2.drawString(text, x, y);
		
		String[] texts = {"+1 Attack", "+1 Dexterity", "+2 Health"};
		int longestText = 1000;
		for(int i = 0; i < texts.length; i++) {
			int len = getXForCenteredText(texts[i]);
			if (len < longestText) {
				longestText = len;
			}
		}
		
		x = longestText;
		y += gp.tileSize/2;
		
		text = "+1 Strength";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize/2, y);
		}
		
		text = "+1 Dexterity";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize/2, y);
		}
		
		text = "+1 Defense";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize/2, y);
		}
		
		text = "+2 Health";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 3) {
			g2.drawString(">", x - gp.tileSize/2, y);
		}
		
		
	}
	
	public void drawMessage() {
	    int messageX = gp.player.screenX - gp.tileSize / 2;
	    int messageY = gp.player.screenY;
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));

	    // Calculate the total height of all messages
	    int totalHeight = message.size() * 20;

	    // Determine the starting Y position for the first message
	    int startY = messageY;

	    // Iterate through the messages list in reverse order
	    for (int i = message.size() - 1; i >= 0; i--) {
	        if (message.get(i) != null) {
	            g2.setColor(Color.white);
	            g2.drawString(message.get(i), messageX, startY);
	            startY -= 20;
	            int counter = messageCounter.get(i) + 1;
	            messageCounter.set(i, counter);

	            // Remove messages that have scrolled out of view
	            if (messageCounter.get(i) > 180) {
	                message.remove(i);
	                messageCounter.remove(i);
	            }
	            
	        }
	    }
	}

	public void drawCharacterScreen() {
		// CREATE A FRAME
		int frameX = gp.tileSize;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 6;
		int frameHeight = gp.tileSize * 10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(40F));
		
		int textX = (int) (frameX + gp.tileSize * .5);
		int textY = frameY + gp.tileSize;
		final int lineHeight = 48;
		
		// NAMES
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight + gp.tileSize/4;
		g2.drawString("Weapon", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;
		
		// VALUES
		int tailX = frameX + frameWidth - 30;
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.exp);
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.coin);
		textX = getXForAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		
		textY += gp.tileSize/4;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);
	}
	
	public void drawLoadingScreen() {
		
		g2.setColor(backgroundColor);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setColor(Color.white);
		g2.setFont(MrPixel);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
	    int textWidth = g2.getFontMetrics().stringWidth(loadingText);
	    int textHeight = g2.getFontMetrics().getHeight();
	    int textX = (gp.screenWidth - textWidth) / 2;
	    int textY = (gp.screenHeight + textHeight) / 2;
	    g2.drawString(loadingText, textX, textY);

	    counter += 5; // Update counter for animation
	    if (counter == 120 || counter == 240 || counter == 360) {
	    	loadingText += ".";
	    }
	    // Switch to playState after a few seconds
	    if (counter >= 480) { // Adjust the value to control the duration of the loading screen
	    	counter = 0;
	    	if (gp.inDungeon == true) {
	    		gp.gameState = gp.dungeonState;
	    	}
	    	else {
	    		gp.gameState = gp.playState;
	    	}
	        
	        loadingText = "Loading";
	    }
		
		
	}
	
	public void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		while (i < gp.player.maxLife/2) {
			g2.drawImage(heart_empty, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		while (i < gp.player.life/2) {
			g2.drawImage(heart_full, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		if (i * 2 + 1 == gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
		}
		
		
	}
	
	public void drawTitleScreen() {
		g2.setColor(backgroundColor);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		if (titleScreenState == 0) {
			
			// TITLE NAME
			g2.setFont(Lightning);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
			String text = "Feathered Fury";
			int x = getXForCenteredText(text);
			int y = gp.tileSize * 5/2;
			
			// TEXT SHADOW
			g2.setColor(new Color(228, 155, 15));
			g2.drawString(text, x+5, y+5);
			
			// TITLE TEXT
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
			text = "Dungeon Rooster";
			x = getXForCenteredText(text);
			y += 85;
			
			g2.setColor(new Color(228, 155, 15));
			g2.drawString(text, x+5, y+5);
			
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			
			// MAIN CHARACTER IMAGE
			x = gp.screenWidth/2 - gp.tileSize * 3/2;
			y += gp.tileSize*3/4;
			g2.drawImage(gp.player.down, x, y, gp.tileSize*3, gp.tileSize*3, null);
			
			// MENU
			g2.setFont(MrPixel);
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
			text = "NEW GAME";
			x = getXForCenteredText(text);
			y += gp.tileSize * 4;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			text = "LOAD GAME";
			x = getXForCenteredText(text);
			y += gp.tileSize * 1;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			text = "QUIT";
			x = getXForCenteredText(text);
			y += gp.tileSize * 1;
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		}
		else if (titleScreenState == 1) {
			g2.setFont(Lightning);
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
			String text = "Select your class!";
			int x = getXForCenteredText(text);
			int y = gp.tileSize *3;
			g2.drawString(text, x, y);
			
			g2.setFont(MrPixel);
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
			text = "Fighter";
			x = getXForCenteredText(text);
			y += gp.tileSize * 3/2;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			text = "Mage";
			x = getXForCenteredText(text);
			y += gp.tileSize * 1;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			text = "Berserker";
			x = getXForCenteredText(text);
			y += gp.tileSize * 1;
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			text = "Back";
			x = getXForCenteredText(text);
			y += gp.tileSize * 2;
			g2.drawString(text, x, y);
			if (commandNum == 3) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		}
		
		
	}
	public void drawDialogueScreen() {
		// Window
		int x = gp.tileSize * 2;
		int y = gp.tileSize * 8;
		int width = gp.screenWidth - x * 2;
		int height = gp.tileSize * 3;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
		x += gp.tileSize*3/4;
		y += gp.tileSize;
		
		for (String line : currentDialogue.split("/n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0, 200);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";
		
		int x = getXForCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
				
	}
	
	public int getXForCenteredText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	public int getXForAlignedToRightText(String text, int tailX) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + slotRow * 5;
		return itemIndex;
	}

}
