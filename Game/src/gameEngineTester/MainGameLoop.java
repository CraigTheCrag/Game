package gameEngineTester;
 
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import entities.StaticEntity;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
 
public class MainGameLoop {
 
    public static void main(String[] args) {
 
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        TextMaster.init(loader);
        MasterRenderer renderer = new MasterRenderer(loader);
        ParticleMaster.init(loader, renderer.getProjectionMatrix());
        
    	List<Entity> entities = new ArrayList<Entity>();
    	List<Terrain> terrains = new ArrayList<Terrain>();
    	List<GuiTexture> guis = new ArrayList<GuiTexture>();
    	List<Light> lights = new ArrayList<Light>();
    	
        
        //------- FONT STUFF --------
        
        FontType font = new FontType(loader.loadFontTexture("fonts/Candara/candara"),
        		new File("res/fonts/Candara/candara.fnt"));
        GUIText text = new GUIText("Sample Text", 3, font,
        		new Vector2f(0.0f, 0.4f), 1f, true, 0.5f, 0.4f, 0.1f, 0.5f);
        text.setColour(1f, 1f, 1f);
        text.setOutlineColour(0f, 0f, 0f);
        text.setOffset(new Vector2f(0.06f, 0.06f));
        
        //---------------------------
         
        //------- TERRAIN TEXTURE PACK STUFF -------------
        
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadGameTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadGameTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadGameTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadGameTexture("path"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,
        		rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadGameTexture("blendMap"));
        
        //------------------------------------------------
        
        terrains.add(new Terrain(0, -1, loader, texturePack, blendMap, "heightmap"));
        //terrains.add(new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap"));
        
        ModelData treeData = OBJFileLoader.loadOBJ("tree");
        ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
        ModelData fernData = OBJFileLoader.loadOBJ("fern");
        ModelData lowTreeData = OBJFileLoader.loadOBJ("lowPolyTree");
        ModelData lampData = OBJFileLoader.loadOBJ("lamp");
        
        ModelData playerData = OBJFileLoader.loadOBJ("person");
        
        RawModel tree = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(),
        		treeData.getNormals(), treeData.getIndices());
        RawModel grass = loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(),
        		grassData.getNormals(), grassData.getIndices());
        RawModel fern = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(),
        		fernData.getNormals(), fernData.getIndices());
        RawModel lowPolyTree = loader.loadToVAO(lowTreeData.getVertices(), lowTreeData.getTextureCoords(),
        		lowTreeData.getNormals(), lowTreeData.getIndices());
       
        RawModel lamp = loader.loadToVAO(lampData.getVertices(), lampData.getTextureCoords(),
        		lampData.getNormals(), lampData.getIndices());
        
        RawModel person = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(),
        		playerData.getNormals(), playerData.getIndices());
        
        ModelTexture fernModelTexture = new ModelTexture(loader.loadGameTexture("fern"));
        fernModelTexture.setNumberOfRows(2);
        
        ModelTexture lowTreeModelTexture = new ModelTexture(loader.loadGameTexture("treeAtlas"));
        lowTreeModelTexture.setNumberOfRows(2);
        lowTreeModelTexture.setHasTransparency(true);
        
        TexturedModel treeModel = new TexturedModel(tree, new ModelTexture(loader.loadGameTexture("tree")));
        TexturedModel grassModel = new TexturedModel(grass, new ModelTexture(loader.loadGameTexture("grassTexture")));
        TexturedModel fernModel = new TexturedModel(fern, fernModelTexture);
        TexturedModel lowTreeModel = new TexturedModel(lowPolyTree, lowTreeModelTexture);
        TexturedModel lampModel = new TexturedModel(lamp, new ModelTexture(loader.loadGameTexture("lamp")));
        
        TexturedModel playerModel = new TexturedModel(person, new ModelTexture(loader.loadGameTexture("playerTexture")));
        
        grassModel.getTexture().setHasTransparency(true);
        grassModel.getTexture().setUseFakeLighting(true);
        fernModel.getTexture().setHasTransparency(true);
        
        Random random = new Random();
        float x,y,z;
        
        for (int i=0;i<500;i++) {
        	x = random.nextFloat()*800-400;
        	z = random.nextFloat()*-600;
        	y = Terrain.getTerrainHeight(new Vector3f(x,0,z), terrains);
        	
        	entities.add(new StaticEntity(treeModel, new Vector3f(x,y,z),0,0,0,3));
        	
        	x = random.nextFloat()*800-400;
        	z = random.nextFloat()*-600;
        	y = Terrain.getTerrainHeight(new Vector3f(x,0,z), terrains);
        	
        	entities.add(new StaticEntity(grassModel, new Vector3f(x,y,z),0,0,0,1));
        	
        	x = random.nextFloat()*800-400;
        	z = random.nextFloat()*-600;
        	y = Terrain.getTerrainHeight(new Vector3f(x,0,z), terrains);
        	
        	entities.add(new StaticEntity(fernModel, random.nextInt(4), new Vector3f(x,y,z),0,0,0,0.6f));
        	if (i % 2 == 0 | i % 3 == 0 | i % 5 == 0) {
        		
        		x = random.nextFloat()*800-400;
            	z = random.nextFloat()*-600;
            	y = Terrain.getTerrainHeight(new Vector3f(x,0,z), terrains);
        		
        		entities.add(new Entity(lowTreeModel, random.nextInt(3), new Vector3f(x,y,z),0,0,0,0.35f));
        	}
        }
        
       Player player = new Player(playerModel, new Vector3f(0,Terrain.getTerrainHeight(new Vector3f(0,0,-10), terrains)+0.1f,-10),0,0,0,0.4f);
        
       lights.add(new Light(new Vector3f(0, 20000, -7000), new Vector3f(0.6f,0.6f,0.6f)));
       /*lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
       lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
       lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));
       
       entities.add(new Entity(lampModel, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
       entities.add(new Entity(lampModel, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1));
       entities.add(new Entity(lampModel, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));*/
       
       entities.add(player);
        
        Camera camera = new Camera(player);
        
        /*guis.add(new GuiTexture(loader.loadTexture("guiTest"),
        		new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f)));*/
        
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        
        //********Water Renderer **************
        WaterShader waterShader = new WaterShader();
        WaterFrameBuffers buffers = new WaterFrameBuffers();
        WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
        List<WaterTile> waters = new ArrayList<WaterTile>();
        waters.add(new WaterTile(75, -75, -1.5f));
        
        //*************************************
        
        /*ParticleTexture texture = new ParticleTexture(loader.loadGameTexture("particleAtlas"), 4, false);
        
        ParticleSystem system = new ParticleSystem(texture, 35, 25, 0.3f, 2f, 1f);
        system.setDirection(new Vector3f(0f, 1f, 0f), 0.1f);
        system.randomizeRotation();
        system.setScaleError(0.5f);
        system.setSpeedError(0.5f);*/
        
        while(!Display.isCloseRequested()){
        	
        	player.move(terrains);
            camera.move();
            
            //system.generateParticles(player.getPosition());
            
            ParticleMaster.update(camera);
            
            /*if (player.getPreviousPosition() != player.getPosition() && 
        			WaterTile.getWaterTile(player.getPosition(), waters) != null) {
        		
        		new Particle(player.getPosition(), new Vector3f((float) Math.random()*10.0f,12f,(float) Math.random()*10.0f), 0.3f, 2f, 0f, 1f, new Vector3f(0f, 0.15f, 0.35f));
        		
        	}*/
            
            GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
            
            //render reflection texture
            buffers.bindReflectionFrameBuffer();
            float distance = 2 * (camera.getPosition().y - waters.get(0).getHeight());
            camera.getPosition().y -= distance;
            camera.invertPitch();
            renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0,1,0,-waters.get(0).getHeight()+1f));
            camera.getPosition().y += distance;
            camera.invertPitch();
            
            //render refraction texture
            buffers.bindRefractionFrameBuffer();
            renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0,-1,0,waters.get(0).getHeight()));

            //render to screen
            GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
            buffers.unbindCurrentFrameBuffer();
            renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0,-1,0,1000000000));
            waterRenderer.render(waters, camera, lights.get(0));
            
            ParticleMaster.renderPartices(camera);
            
            guiRenderer.render(guis);
            TextMaster.render();
            
            DisplayManager.updateDisplay();
            
        }
        
        //********Clean Up******
        ParticleMaster.cleanUp();
        TextMaster.cleanUp();
        buffers.cleanUp();
        waterShader.cleanUp();
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
        //***********************
    }
 
}