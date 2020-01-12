package staticentities;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.TexturedModel;
import renderEngine.Loader;

public class StaticEntity extends Entity {

	public StaticEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public StaticEntity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ,
            float scale) {
    	super(model, textureIndex, position, rotX, rotY, rotZ, scale);
    }
	
	public StaticEntity(Loader loader, String OBJFile, String textureFile, int numberOfRows, int textureIndex, Vector3f position,
    		float rotX, float rotY, float rotZ, float scale) {
		super(loader, OBJFile, textureFile, numberOfRows, textureIndex, position, rotX, rotY, rotZ, scale);
	}

	public StaticEntity(Loader loader, String OBJFile, String textureFile, Vector3f position,
    		float rotX, float rotY, float rotZ, float scale) {
		super(loader, OBJFile, textureFile, position, rotX, rotY, rotZ, scale);
	}
	
}
