package staticentities;

import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;

public class LowPolyTree extends StaticEntity {
	
	private static final String OBJ_FILE = "lowPolyTree";
	private static final String TEXTURE_FILE = "treeAtlas";
	
	private static final int NUMBER_OF_ROWS = 2;

	public LowPolyTree(Loader loader, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(loader, OBJ_FILE, TEXTURE_FILE, NUMBER_OF_ROWS, textureIndex, position, rotX, rotY, rotZ, scale);
	}
}
