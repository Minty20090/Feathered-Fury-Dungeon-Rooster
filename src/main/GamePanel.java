package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JPanel;

import dungeon.Dungeon;
import entity.Entity;
import entity.Player;
import map.MapManager;
import object.OBJ_Key;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	// NOTES
	// fix going out of bounds of maze
	// fix monster out of bounds
	
	// screen settings
	final int originalTileSize = 16; // 16x16 tile size
	final int scale = 4; 
	
	public final int tileSize = originalTileSize * scale; // 48x48 tile size
	public final int maxScreenCol = 18;
	public final int maxScreenRow = 12; 
	public final int screenWidth = maxScreenCol * tileSize; // 768 pixels
	public final int screenHeight = maxScreenRow * tileSize; // 576 pixels
	
	// world settings
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 25;
	
	// FPS
	int FPS = 60;
	
	// SYSTEM
	public int currentMap = 0;
	public TileManager tileM = new TileManager(this);
	MapManager mapM = new MapManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public EventHandler eHandler = new EventHandler(this);
	public UI ui = new UI(this);
	Thread gameThread;
	public Dungeon dungeon = new Dungeon(this);
	Random random = new Random();
	
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[100];
	public Entity npcInteractions[] = new Entity[10];
	public int owlDialogueIndex = 0;
	
	ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> projectileList = new ArrayList<>();
	
	// GAMESTATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int loadingState = 10;
	public final int levelUpState = 11;
	public final int shopState = 12;
	public final int dungeonState = 13;
	public final int helpState = 14;
	public final int deathState = 15;
	public final int dungeonDoneState = 16; 
	
	// PLAYER TYPE
	public int playerType;
	public final int fighter = 1;
	public final int mage = 2;
	public final int berserker = 3;
	
	public boolean inDungeon = false;
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		
	}
	
	public void setUpGame() {
		aSetter.setObject();
		aSetter.setNpc();
		aSetter.setMonster();
//		playMusic(0);

		gameState = titleState;
	}

	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	
	@Override
	public void run() {
		while (gameThread != null) {
			
			double drawInterval = 1000000000/FPS;
			double nextDrawTime = System.nanoTime() + drawInterval;
			
			
			update();
				
			repaint();
			

			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			 
			
		}
		
	}
	
	public void update() {
		
		
		
		if (gameState == playState || gameState == dungeonState) {
			//player
			if(player.life < 0) {
				gameState = deathState;
			}
			player.update();
			
			//npc
			for (int i = 0; i < npc.length; i++) {
				
				if (npc[i] != null) {
					
					npc[i].update();
						
				}
			}
			for (int i = 0; i < npcInteractions.length; i++) {
				
				if (npcInteractions[i] != null) {
					
					npcInteractions[i].update();
						
				}
			}
			
			for (int i = 0; i < monster.length; i++) {
				
				if (monster[i] != null) {
					if(monster[i].alive == true && monster[i].dying == false) {
						monster[i].update();
					}
					else if (monster[i].alive == false){
						monster[i] = null;
					}
		 
				}
				
				
			}
			for (int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i) != null) {
					if (projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}
					else if (projectileList.get(i).alive == false){
						projectileList.remove(i);
					}
				}
			}
			if (gameState == dungeonState && dungeon.room_type == dungeon.combat_room) {
				boolean monstersAlive = !(isFilledWithNull(monster));
				if (monstersAlive == false) {
					if(dungeon.rooms > dungeon.roomCounter) {
						if (dungeon.roomDone == false ) {
							dungeon.generateDoorways(random);
							dungeon.roomDone = true;
						}
					}
					else {
						if (dungeon.roomDone == false ) {
							dungeon.showExit();
						}
					}
				}
			}
			
		}
		
		
		if (gameState == pauseState) {
			//nothing
		}
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// title screen
		
		if (gameState == titleState) {
			ui.draw(g2);
		}
		
		else {
			// npc interaction boxes
			for (int i = 0; i < npcInteractions.length; i++) {
				if (npcInteractions[i] != null) {
					npcInteractions[i].draw(g2);
				}
			}
			
			// base layer of map
			mapM.draw(g2, currentMap);

			// OBSTRUCTION TILES
			tileM.draw(g2);
			
			
			
			// ADD ENTITIES TO THE LIST
			entityList.add(player);
			
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			
			for (int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			
			//SORT
			Collections.sort(entityList, new Comparator<Entity> () {

				@Override
				public int compare(Entity o1, Entity o2) {
					
					return Integer.compare(o1.worldY, o2.worldY);
				}
				
			});
			
			//DRAW ENTITIES
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			
			//EMPTY ENTITY LIST
			entityList.clear();
			
			// top layers of map
			if (mapM.maps[currentMap].otherLayers != null) {
				for (int i = 0; i < mapM.maps[currentMap].otherLayers.length; i++) {
					mapM.draw(g2, mapM.maps[currentMap].otherLayers[i]);
				}
			}
			
			// UI
			ui.draw(g2);
			
			// DEBUG
			if (keyH.showDebugText == true) {
				g2.setColor(Color.white);
				int x = tileSize;
				int y = tileSize * 3;
				
				g2.drawString("World X: " + player.worldX, x, y);
				y += 20;
				g2.drawString("World Y: " + player.worldY, x, y);
				y += 20;
				g2.drawString("Col: " + (player.worldX + player.solidAreaDefaultX)/tileSize, x, y);
				y += 20;
				g2.drawString("Row: " + (player.worldY + player.solidAreaDefaultY)/tileSize, x, y);
				y += 20;
				g2.drawString("room type: " + dungeon.room_type, x, y);
				y += 20;
				g2.drawString("rooms: " + dungeon.rooms, x, y);
				y += 20;
				g2.drawString("roomsCounter: " + dungeon.roomCounter, x, y);
				y += 20;
				g2.drawString("roomDone: " + dungeon.roomDone, x, y);
				y += 20;
				g2.drawString("remaining Monsters: " + countEntries(monster), x, y);
				y += 20;
				try {
					g2.drawString("dialogueIndex: " + npcInteractions[1].dialogueIndex, x, y);
				}
				catch(NullPointerException e) {
					
				}
				
			
				
				
			    g2.setColor(Color.LIGHT_GRAY);
	            for (int row = 0; row < maxWorldRow; row++) {
	                for (int col = 0; col < maxWorldCol; col++) {
	                    x = col * tileSize;
	                    y = row * tileSize;
	                    g2.drawRect(x, y, tileSize, tileSize);
	                }
	            }
			}
			
			g2.dispose();
			
		}
		
	}
	
	public void playMusic (int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE (int i) {
		se.setFile(i);
		se.play();
	}
	public static <T> boolean isFilledWithNull(T[] array) {
        for (T element : array) {
            if (element != null) {
                return false;
            }
        }
        return true;
    }
	public static <T> int countEntries(T[] array) {
		int i = 0;
        for (T element : array) {
            if (element != null) {
            	i++;
            }
        }
        return i;
    }
	public boolean containsClass(ArrayList<Entity> inventory, Class<?> cls) {
        for (Object obj : inventory) {
            if (obj != null && cls.isInstance(obj)) {
                return true;
            }
        }
        return false;
    }
	public void removeOBJKey(ArrayList<Entity> inventory) {
        // Using Iterator to remove elements while iterating
		boolean removedKey = false;
        Iterator<Entity> iterator = inventory.iterator();
        while (removedKey == false) {
            Object obj = iterator.next();
            if (obj instanceof OBJ_Key) {
                iterator.remove();
                removedKey = true;
            }
        }
    }
}
