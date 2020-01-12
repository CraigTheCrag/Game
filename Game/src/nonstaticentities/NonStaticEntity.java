package nonstaticentities;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.TexturedModel;
import renderEngine.Loader;
import terrains.Terrain;

public abstract class NonStaticEntity extends Entity {

	public NonStaticEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public NonStaticEntity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ,
            float scale) {
    	super(model, textureIndex, position, rotX, rotY, rotZ, scale);
    }
	
	public NonStaticEntity(Loader loader, String OBJFile, String textureFile, int numberOfRows, int textureIndex,
			Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(loader, OBJFile, textureFile, numberOfRows, textureIndex, position, rotX, rotY, rotZ, scale);
	}
	
	public NonStaticEntity(Loader loader, String OBJFile, String textureFile, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		super(loader, OBJFile, textureFile, position, rotX, rotY, rotZ, scale);
	}
	
	public abstract void move(List<Terrain> terrains);
	
    public void increasePosition(float dx, float dy, float dz) {
        this.setPosition(new Vector3f(this.getPosition().x+dx,
        		this.getPosition().y+dy, this.getPosition().z+dz));
    }
 
    public void increaseRotation(float dx, float dy, float dz) {
        this.setRotX(this.getRotX()+dx);
        this.setRotY(this.getRotY()+dy);
        this.setRotZ(this.getRotZ()+dz);
    }
}
