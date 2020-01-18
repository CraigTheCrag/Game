package entities;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import terrains.Terrain;

public abstract class NonStaticEntity extends Entity {

	public NonStaticEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale, boolean useFakeLighting, boolean hasTransparency) {
		super(model, position, rotX, rotY, rotZ, scale, useFakeLighting, hasTransparency);
	}
	
	public NonStaticEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		super(model, position, rotX, rotY, rotZ, scale, false, false);
	}
	
	public NonStaticEntity(TexturedModel model, int numberOfRows, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ,
            float scale, boolean useFakeLighting, boolean hasTransparency) {
    	super(model, numberOfRows, textureIndex, position, rotX, rotY, rotZ, scale, useFakeLighting, hasTransparency);
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
