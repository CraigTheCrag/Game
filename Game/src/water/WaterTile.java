package water;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class WaterTile {
	
	public static final float TILE_SIZE = 60;
	
	private float height;
	private float x,z;
	
	public WaterTile(float centerX, float centerZ, float height){
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
	}

	public float getHeight() {
		return height;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public static WaterTile getWaterTile(Vector3f position, List<WaterTile> waters) {
		float tileX, tileZ;
		for (WaterTile water : waters) {
			tileX = water.getX() - (TILE_SIZE);
			tileZ = water.getZ() - (TILE_SIZE);
			
			if ((position.x < tileX + TILE_SIZE*2f && position.x >= tileX) &&
					(position.z < tileZ + TILE_SIZE*2f && position.z > tileZ) && (position.y <= water.getHeight())) {
				return water;
			}
		}
		return null;
	}

}
