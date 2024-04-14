package dungeon;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import entity.Entity;
import main.GamePanel;
import monster.MON_Slime;
import object.OBJ_Dungeon_Doorway;
import object.OBJ_Health_Potion;
import object.OBJ_Teleporter;

public class Dungeon {
	GamePanel gp;
	public int rooms = 6;
	public int roomCounter = 0;
	int maxNumOfHealingRooms = 2;
	int healingRoomCounter = 0;
	
	public ArrayList<Entity> monsterList = new ArrayList<>();
	
	// Constants
	public final int starting_room = 0;
	public final int combat_room = 1;
	final int puzzle_room = 2;
	public final int healing_room = 3;
	final int treasure_room = 4;
	public final int exit_room = 5;
	int[][] mazeStartingLocations = {{27,0},{1,23},{1,3}};
	int[] mazeExit = {41,19};
	
	public int room_type = 0;
	public boolean roomDone = false;
	
	public Dungeon(GamePanel gp) {
		this.gp = gp;
	}
	
	public void loadMap() {
		roomCounter++;
		Random random = new Random();
		if (room_type == starting_room) {
			loadStartingRoom(random);
		}
		else if (room_type == combat_room) {
			loadCombatRoom(random);
		}
		else if (room_type == puzzle_room) {
			loadPuzzleRoom(random);
		}
		else if(room_type == healing_room) {
			loadHealingRoom(random);
		}
		if (roomCounter < rooms && roomDone == true) {
			generateDoorways(random);
		}
		if (roomCounter >= rooms && roomDone == true) {
			showExit();
		}
	}
	public void loadStartingRoom(Random random) {
		roomDone = true;
		gp.inDungeon = true;
		gp.player.worldX = 6 * gp.tileSize;
		gp.player.worldY = 10 * gp.tileSize;
		
		gp.currentMap = 3;
		gp.aSetter.setObject();
		gp.aSetter.setNpc();
		gp.aSetter.setMonster();
		
		gp.tileM.loadMap(gp.currentMap);
		
		gp.gameState = gp.loadingState;
		
	}
	
	public void loadCombatRoom(Random random) {
		monsterList.clear();
		roomDone = false;
		
		int maxMonsters = (gp.player.level + gp.player.attack + gp.player.defense + gp.player.dexterity) * 2;
		if (maxMonsters > 55) {
			maxMonsters = 55;
		}
		
		int minMonsters = (gp.player.level);
		maxMonsters -= minMonsters;
		
		int gridWidth = 10;	
		int gridHeight = 5;
		
		int numOfMonsters = random.nextInt(maxMonsters) + minMonsters;
		
		ArrayList<int[]> monsterLocations = createMonsterMap(gridWidth, gridHeight, numOfMonsters);
		
		for(int i = 0; i < numOfMonsters; i++) {
			int monsterType = random.nextInt(3) + 1;
			switch (monsterType) {
			case 1: monsterList.add(new MON_Slime(gp)); break;
			case 2: monsterList.add(new MON_Slime(gp)); break;
			case 3: monsterList.add(new MON_Slime(gp)); break;
			}
			
			monsterList.get(i).worldX = monsterLocations.get(i)[0]*gp.tileSize + 1 * gp.tileSize;
			monsterList.get(i).worldY = monsterLocations.get(i)[1]*gp.tileSize + 5 * gp.tileSize;
		}
		
		gp.player.worldX = 6 * gp.tileSize;
		gp.player.worldY = 11 * gp.tileSize;
		
		gp.currentMap = 3;
		gp.aSetter.setObject();
		gp.aSetter.setNpc();
		gp.aSetter.setMonster();
		
		gp.tileM.loadMap(gp.currentMap);
		
		gp.gameState = gp.loadingState;
		
	}
	
	public void loadPuzzleRoom(Random random) {
		roomDone = true;
		int mazeNum = generateRandomNumber(random, 33, 33, 34) - 1;
		gp.tileM.clearMap();
		System.out.println(mazeNum + 4);
		gp.tileM.loadMap(mazeNum + 4);
		gp.currentMap = 6;
		gp.aSetter.setObject();
		gp.aSetter.setNpc();
		gp.aSetter.setMonster();
		
		gp.player.worldX = mazeStartingLocations[mazeNum][0] * gp.tileSize;
		gp.player.worldY = mazeStartingLocations[mazeNum][1] * gp.tileSize;
		
		gp.gameState = gp.loadingState;
		
		
	}

	public void loadHealingRoom(Random random) {
		roomDone = true;
		Arrays.fill(gp.obj, null);
		
		gp.player.worldX = 6 * gp.tileSize;
		gp.player.worldY = 11 * gp.tileSize;
		
		gp.currentMap = 3;
		gp.aSetter.setNpc();
		gp.aSetter.setMonster();
		
		gp.tileM.loadMap(gp.currentMap);
		
		gp.gameState = gp.loadingState;
		
		gp.obj[9] = new OBJ_Health_Potion(gp); 
		gp.obj[9].worldX = 6 * gp.tileSize;
		gp.obj[9].worldY = 6 * gp.tileSize;
	}
	
	public void generateDoorways(Random random) {
		int doorways = generateRandomNumber(random, 10,30,60);
		ArrayList<int[]> availableDoorways = new ArrayList<>(Arrays.asList(new int[]{6, 2, 6, 3}, new int[]{0, 7, 1, 7}, new int[]{12, 7, 11, 7}));
		
		if (room_type == puzzle_room) { 
			doorways = 1;
		}
		
		for(int i = 0; i < doorways; i++) {
			float healingRoomProbability = (float) 100.0 * (float) (maxNumOfHealingRooms - healingRoomCounter) / (float) (rooms - roomCounter);
			int combatRoomProbability = (int) (100-healingRoomProbability)/5*4;
			int puzzleRoomProbability = (int) (100-healingRoomProbability)/5;
			int gen_room_type = generateRandomNumber(random, combatRoomProbability, puzzleRoomProbability, (int) healingRoomProbability);
			
			if(gen_room_type == healing_room) {
				healingRoomCounter++;
			}
			
			int selectedDoorway = random.nextInt(availableDoorways.size());
			
			gp.obj[i] = new OBJ_Dungeon_Doorway(gp);
			
			BufferedImage img = gp.obj[i].image4;
			
			if (gen_room_type == puzzle_room) {img = gp.obj[i].image;}
			else if (gen_room_type == combat_room) {img = gp.obj[i].image3;}
			else if (gen_room_type == healing_room) {img = gp.obj[i].image2;}
			
			if (room_type == puzzle_room) {
				gp.obj[i].down1 = img;
				gp.obj[i].worldX = mazeExit[0]* gp.tileSize;
				gp.obj[i].worldY = mazeExit[1]* gp.tileSize;
				gp.obj[i].icon = true;
				gp.obj[i].destination = 999;
				
				img = gp.obj[i].image4;
				gp.obj[i+3] = new OBJ_Dungeon_Doorway(gp);
				gp.obj[i+3].down1 = img;
				gp.obj[i+3].worldX = (mazeExit[0] + 1) * gp.tileSize;
				gp.obj[i+3].worldY = mazeExit[1]* gp.tileSize;
				gp.obj[i+3].destination = gen_room_type;
			}
			else {
				gp.obj[i].down1 = img;
				gp.obj[i].worldX = availableDoorways.get(selectedDoorway)[2] * gp.tileSize;
				gp.obj[i].worldY = availableDoorways.get(selectedDoorway)[3]* gp.tileSize;
				gp.obj[i].icon = true;
				gp.obj[i].destination = 999;
				
				img = gp.obj[i].image4;
				gp.obj[i+3] = new OBJ_Dungeon_Doorway(gp);
				gp.obj[i+3].down1 = img;
				gp.obj[i+3].worldX = availableDoorways.get(selectedDoorway)[0]* gp.tileSize;
				gp.obj[i+3].worldY = availableDoorways.get(selectedDoorway)[1]* gp.tileSize;
				gp.obj[i+3].destination = gen_room_type;
			}
			
			availableDoorways.remove(selectedDoorway);
			
		}
		
	}
	
    public void showExit() {
    	gp.inDungeon = false;
    	
		roomDone = true;
		room_type = exit_room;
		
		gp.player.worldX = 6 * gp.tileSize;
		gp.player.worldY = 10 * gp.tileSize;
		
		gp.currentMap = 3;
		gp.aSetter.setObject();
		gp.aSetter.setNpc();
		gp.aSetter.setMonster();
		
		gp.tileM.loadMap(gp.currentMap);
		
		gp.gameState = gp.loadingState;
		gp.gameState= gp.dungeonDoneState;
		
		
    }
    public ArrayList<int[]> createMonsterMap(int gridWidth, int gridHeight, int numOfMonsters) {
        ArrayList<int[]> monsters = new ArrayList<>();
        Set<String> occupiedTiles = new HashSet<>();  // Keep track of occupied tiles

        Random random = new Random();

        // Function to generate a random coordinate without overlapping
        while (monsters.size() < numOfMonsters) {
            int x = random.nextInt(gridWidth);
            int y = random.nextInt(gridHeight);
            String tileKey = x + "," + y;

            if (!occupiedTiles.contains(tileKey)) {
                monsters.add(new int[]{x, y});
                occupiedTiles.add(tileKey);
            }
        }
        
        return monsters;
    }

    
	public static int generateRandomNumber(Random random, int probability1, int probability2, int probability3) {
		System.out.println("probability1: " + probability1);
		System.out.println("probability1: " + probability2);
		System.out.println("probability1: " + probability3);
		
        int randomNumber = random.nextInt(probability1 + probability2 + probability3) + 1; // Generate a random number between 1 and 100
        if (randomNumber <= probability1) {
            return 1; // 1 - 10% of the time
        } else if (randomNumber <= probability1 + probability2) {
            return 2; // 2 - 30% of the time (10% + 30%)
        } else if (randomNumber <= probability1 + probability2 + probability3){
            return 3; // 3 - 60% of the time (10% + 30% + 60%)
        }
        
        return 1;
       
    }
}
