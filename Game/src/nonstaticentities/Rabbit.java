package nonstaticentities;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import terrains.Terrain;
import textures.ModelTexture;

public class Rabbit extends NonStaticEntity {
	
	private static final String OBJ_FILE = "pine";
	private static final String TEXTURE_FILE = "pine";
	
	
	public Rabbit(Loader loader, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(loader, OBJ_FILE, TEXTURE_FILE, position, rotX, rotY, rotZ, scale);
	}
	
	@Override
	public void move(List<Terrain> terrains) {
		// TODO Auto-generated method stub
	}
}
