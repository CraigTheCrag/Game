package entities;
 
import models.TexturedModel;
import terrains.Terrain;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;
 
public class Entity {
 
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;
    
    private Terrain currentTerrain;
    
    private int textureIndex = 0;
 
    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
            float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }
    
    public Entity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ,
            float scale) {
    	this.textureIndex = textureIndex;
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }
    
    public float getTextureXOffset() {
    	int column = textureIndex % model.getTexture().getNumberOfRows();
    	return (float)column / (float)model.getTexture().getNumberOfRows();
    }
    
    public float getTextureYOffset() {
    	int row = textureIndex / model.getTexture().getNumberOfRows();
    	return (float)row / (float)model.getTexture().getNumberOfRows();
    }
 
    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }
 
    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }
    
	private Terrain findCurrentTerrain(List<Terrain> terrains) {
		for (Terrain terrain : terrains) {
			if (this.getPosition().x >= terrain.getX() && this.getPosition().z >= terrain.getZ() &&
					this.getPosition().x < terrain.getX() + terrain.getTerrainSize() &&
					this.getPosition().z < terrain.getZ() + terrain.getTerrainSize()) {
				return terrain;
			}
		}
		return null;
	}
	
	protected float getTerrainHeight(List<Terrain> terrains) {
		this.currentTerrain = this.findCurrentTerrain(terrains);
		float xPos = this.getPosition().x;
		float zPos = this.getPosition().z;
		
		float terrainHeight;
		if (this.currentTerrain != null) {
			terrainHeight = this.currentTerrain.getHeightOfTerrain(xPos, zPos);
		} else {
			terrainHeight = 0;
		}
		return terrainHeight;
	}
	
	public void setCorrectPosition(List<Terrain> terrains) {
		float newY = this.getTerrainHeight(terrains);
		this.setPosition(new Vector3f(this.getPosition().x, newY, this.getPosition().z));
	}
 
    public TexturedModel getModel() {
        return model;
    }
 
    public void setModel(TexturedModel model) {
        this.model = model;
    }
 
    public Vector3f getPosition() {
        return position;
    }
 
    public void setPosition(Vector3f position) {
        this.position = position;
    }
 
    public float getRotX() {
        return rotX;
    }
 
    public void setRotX(float rotX) {
        this.rotX = rotX;
    }
 
    public float getRotY() {
        return rotY;
    }
 
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }
 
    public float getRotZ() {
        return rotZ;
    }
 
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }
 
    public float getScale() {
        return scale;
    }
 
    public void setScale(float scale) {
        this.scale = scale;
    }
 
}