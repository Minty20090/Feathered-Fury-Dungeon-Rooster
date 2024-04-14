package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Dungeon_Doorway extends Entity{
	
	public OBJ_Dungeon_Doorway (GamePanel gp) {
        super(gp);
        
        name = "Doorway";
        icon = true;
		
        image = setup("/objects/puzzle", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/healing", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/combat", gp.tileSize, gp.tileSize);
		image4 = setup("/tiles/black", gp.tileSize, gp.tileSize);
		
		
	}
		
	
}
