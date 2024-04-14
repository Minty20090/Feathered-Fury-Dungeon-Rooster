package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	public Boolean upPressed = false, downPressed = false, rightPressed = false, leftPressed = false, 
			enterPressed = false, spacePressed = false, shotKeyPressed = false;
	
	// DEBUG
	public Boolean showDebugText = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			titleState(code);
		}
		
		// PLAY STATE
		else if (gp.gameState == gp.playState || gp.gameState == gp.dungeonState) {
			playState(code);
		}
		// pause state
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		
		// dialogue state
		else if (gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}
		
		// CHARACTER STATE
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}
		
		// LEVEL UP STATE
		else if (gp.gameState == gp.levelUpState) {
			levelUpState(code);
		}
		
		// HELP STATE
		else if (gp.gameState == gp.helpState) {
			helpState(code);
		}
		
		// DEATH STATE 
		else if (gp.gameState == gp.deathState) {
			deathState(code);
		}
		// DUNGEON DONE STATE
		else if (gp.gameState == gp.dungeonDoneState) {
			dungeonDoneState(code);
		}
	}
	
	public void playState(int code) {
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if(code == KeyEvent.VK_H) {
			gp.gameState = gp.helpState;
		}
		if(code == KeyEvent.VK_ENTER ) {
			enterPressed = true;
		}
		
		if(code == KeyEvent.VK_SPACE && gp.playerType != gp.mage) {
			spacePressed = true;
		}
		
		if(code == KeyEvent.VK_F && gp.playerType == gp.mage) {
			shotKeyPressed = true;
		}
		
		if(code == KeyEvent.VK_R) {
			gp.dungeon.room_type++;
		}
		
		// Debug
		if(code == KeyEvent.VK_T) {
			if (showDebugText == true) {
				showDebugText = false;
			}
			else {
				showDebugText = true;
			}
		}
	}
	public void dungeonDoneState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}
	public void levelUpState(int code) {
		
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 3;
			}
		}
		
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 3) {
				gp.ui.commandNum = 0;
			}
		}
		
		if(code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.player.strength++;
				gp.player.attack = gp.player.getAttack();
			}
			if (gp.ui.commandNum == 1) {
				gp.player.dexterity++;
			}
			if (gp.ui.commandNum == 2) {
				gp.player.defense++;
			}
			if (gp.ui.commandNum == 3) {
				gp.player.maxLife += 2;
				gp.player.life += 2;
			}
			gp.ui.commandNum = 0;
			if(gp.inDungeon == true) {
				gp.gameState = gp.dungeonState;
			}
			else {
				gp.gameState = gp.playState;
			}
			
		}
	}
	public void titleState(int code) {
		
		if (gp.ui.titleScreenState == 0) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
				}
				if (gp.ui.commandNum == 1) {
					
				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
		}
		else if (gp.ui.titleScreenState == 1) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}
			}
			
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 3) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.playerType = gp.fighter;
					gp.player.classSelection();
					gp.gameState = gp.helpState;
					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 1) {
					gp.playerType = gp.mage;
					gp.player.classSelection();
					gp.gameState = gp.helpState;
					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 2) {
					gp.playerType = gp.berserker;
					gp.player.classSelection();
					gp.gameState = gp.helpState;
					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 3) {
					gp.ui.titleScreenState = 0;
					gp.ui.commandNum = 0;
				}
				
				
			}
		}
		
	}
	
	public void dialogueState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}
	
	public void pauseState(int code) {
		if(code == KeyEvent.VK_P) {
			if (gp.inDungeon == true) {
				gp.gameState = gp.dungeonState;
			}
			else {
				gp.gameState = gp.playState;
			}
			
		}
	}

	public void characterState(int code) {
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_W) {
			if (gp.ui.slotRow != 0) {
				gp.ui.slotRow--;
			}	
		}
		
		if(code == KeyEvent.VK_S) {
			if (gp.ui.slotRow != gp.ui.maxSlotRow) {
				gp.ui.slotRow++;
			}
		}
		
		if(code == KeyEvent.VK_A) {
			if (gp.ui.slotCol != 0) {
				gp.ui.slotCol--;
			}	
		}
		
		if(code == KeyEvent.VK_D) {
			if (gp.ui.slotCol != gp.ui.maxSlotCol) {
				gp.ui.slotCol++;
			}
		}
		if(code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
			
		}
	}
	
	public void shopState() {
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
		
		if(code == KeyEvent.VK_F) {
			shotKeyPressed = false;
		}
		
		
	}
	
	public void helpState(int code) {
		if(code == KeyEvent.VK_H) {
			gp.gameState = gp.playState;
		}
	}

	public void deathState(int code) {
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
		}
		
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
		}
		if(code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.player.setDefaultValues();
				gp.currentMap = 0;
				gp.tileM.loadMap(gp.currentMap);
				gp.aSetter.setNpc();
				gp.aSetter.setObject();
				gp.inDungeon = false;
				gp.player.invincible = false;
				Arrays.fill(gp.monster, null);
				gp.gameState = gp.playState;
			}
			if (gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
			}			
			
		}
	}
}

