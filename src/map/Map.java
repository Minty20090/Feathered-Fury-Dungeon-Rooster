package map;

import java.awt.image.BufferedImage;

import tile.Tile;

public class Map {
	public int mapWidth; // in tiles
	public int mapHeight;
	public BufferedImage image;
	public int layer;
	public Tile[][] tiles;
	public int[] otherLayers;
	public String[] tileMaps;

}
