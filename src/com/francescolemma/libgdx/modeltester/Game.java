package com.francescolemma.libgdx.modeltester;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.SubMesh;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game implements ApplicationListener {
	
	private static final int width = 640;
	private static final int height = 360;

	private static final String MODEL_PATH = "data/ship.obj";

	private static final String MODEL_TEXTURE_PATH = "data/ship.png";
	
	private StillModel model;
	
	private Texture modelTexture;

	private InputPanel inputPanel;
	
	private PerspectiveCamera camera;
	private ShaderProgram shader;
	
	private CameraPositionJPanel cameraPanel;
	private SingleFieldJPanel fovPanel;
	private ArrayList<TranslationJPanel> translationPanels;
	private ArrayList<RotationJPanel> rotationPanels;
	
	HashMap<String, ArrayList<HashMap<String, Float>>> config = null;
	
    public Game(InputPanel inputPanel) {
    	this.inputPanel = inputPanel;
    	
    	fovPanel = inputPanel.getFovPanel();
    	cameraPanel = inputPanel.getCameraPositionPanel();
    	
    	translationPanels = inputPanel.getTranslationPanels();
    	rotationPanels = inputPanel.getRotationPanels();
    }

	public void create () {
		ObjLoader loader = new ObjLoader();
		model = loader.loadObj(Gdx.files.internal(MODEL_PATH), true);

		modelTexture = new Texture(Gdx.files.internal(MODEL_TEXTURE_PATH), Format.RGBA8888, true);

		shader = OpenGLUtils.getPlainShader();
		
//		for (SubMesh subMesh : bottomLeftModel.subMeshes) {
//			System.out.println("Found mesh " + subMesh.name);
//		}
		
		try {
			loadValues();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//		Gdx.gl20.glEnable(GL20.GL_BLEND);
		
    }

	public void render () {
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
		
    	try {
			saveValues();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		camera = new PerspectiveCamera(fovPanel.getFieldValue(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		System.out.println("fov: " + fovPanel.getFieldValue());
		
    	Matrix4 lastMatrix;
//		Gdx.gl20.glFrontFace(GL20.GL_CW);
		
		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
		camera.position.set(cameraPanel.getX_(), cameraPanel.getY_(), cameraPanel.getZ_());
//		System.out.println("Setting camera positon with values: " + cameraPanel.getX_() + " " + cameraPanel.getY_() + " " + cameraPanel.getZ_());
		
		camera.update();
		
		lastMatrix = camera.combined;
		
		for (TranslationJPanel panel : translationPanels) {
			Matrix4 transform = new Matrix4();
			transform.set(lastMatrix);
			transform.translate(panel.getX_(), panel.getY_(), panel.getZ_());
			
//			System.out.println("Translating with values: " + panel.getX_() + " " + panel.getY_() + " " + panel.getZ_());
			
			lastMatrix = transform;
		}
		
		for (RotationJPanel panel : rotationPanels) {
			Matrix4 transform = new Matrix4();
			transform.set(lastMatrix);
			transform.rotate(panel.getX_(), panel.getY_(), panel.getZ_(), panel.getAngle());
			
//			System.out.println("Rotating with values: " + panel.getX_() + " " + panel.getY_() + " " + panel.getZ_() + " " + panel.getAngle());
			
			lastMatrix = transform;
		}
		
//		System.out.println();

		
		drawWithSubMeshes(model, modelTexture, lastMatrix);

    }

	private void drawModel(StillModel model, Texture texture, Matrix4 transform) {
		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
        Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);

		shader.begin();

		shader.setUniformMatrix("u_projView", transform);
		
		model.render(shader);
		
		shader.end();
	}
	
	private void drawWithSubMeshes(StillModel model, Texture texture, Matrix4 transform) {
		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);

		shader.begin();

		int index = 0;
		
		shader.setUniformMatrix("u_projView", transform);
		
		for (SubMesh subMesh : model.subMeshes) {
            Gdx.gl20.glActiveTexture(index);
            Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
            
            texture.bind();
            
            subMesh.getMesh().render(shader, GL20.GL_TRIANGLES);
            
            index++;
		}
		
		shader.end();
	}
	
	//This method differs from the previous because here you can specify the names and order of the submeshes you want to draw
	private void drawWithSubMeshes(StillModel model, Texture texture, Matrix4 transform, String[] subMeshesNames) {
		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
    	
		shader.begin();

		int index = 0;
		
		shader.setUniformMatrix("u_projView", transform);
		
		for (String subMeshName : subMeshesNames) {
			
			SubMesh subMesh = model.getSubMesh(subMeshName);
            Gdx.gl20.glActiveTexture(index);
            Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);

            texture.bind();
            
            subMesh.getMesh().render(shader, GL20.GL_TRIANGLES);
            index++;
		}
		
		shader.end();
	}

    public void resize (int width, int height) {
    }

    public void pause () {
    }

    public void resume () {
    }

    public void dispose () {
    }

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
	

    private void loadValues() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("config.dat"));
		
		config = (HashMap<String, ArrayList<HashMap<String, Float>>>) in.readObject();
		
		if (config == null) {
			return;
		}
		
		ArrayList<HashMap<String, Float>> cameraConfigList = config.get("cameraPosition");
		if (cameraConfigList != null) {
			HashMap<String, Float> cameraConfig = cameraConfigList.get(0);
			if (cameraConfig != null) {
				cameraPanel.setX_(cameraConfig.get("x"));
				cameraPanel.setY_(cameraConfig.get("y"));
				cameraPanel.setZ_(cameraConfig.get("z"));
			}
		}
		
		ArrayList<HashMap<String, Float>> fovConfigList = config.get("fov");
		if (fovConfigList != null) {
			HashMap<String, Float> fovConfig = fovConfigList.get(0);
			if (fovConfig != null) {
				fovPanel.setFieldValue(fovConfig.get("fov"));
			}
		}
		
		ArrayList<HashMap<String, Float>> translationPanelsList = config.get("translationPanels");
		if (translationPanelsList != null) {
			int panelIndex = 0;
			for (HashMap<String, Float> translationPanelConfig : translationPanelsList) {
				if (panelIndex >= translationPanels.size()) {
					break;
				}
				
				if (translationPanelConfig != null) {
					TranslationJPanel panel = translationPanels.get(panelIndex);
					
					panel.setX_(translationPanelConfig.get("x"));
					panel.setY_(translationPanelConfig.get("y"));
					panel.setZ_(translationPanelConfig.get("z"));
				}
				
				panelIndex++;
			}
		}
		
		ArrayList<HashMap<String, Float>> rotationPanelsList = config.get("rotationPanels");
		if (rotationPanelsList != null) {
			int panelIndex = 0;
			for (HashMap<String, Float> rotationPanelConfig : rotationPanelsList) {
				if (panelIndex >= rotationPanels.size()) {
					break;
				}
				
				if (rotationPanelConfig != null) {
					RotationJPanel panel = rotationPanels.get(panelIndex);
					
					panel.setX_(rotationPanelConfig.get("x"));
					panel.setY_(rotationPanelConfig.get("y"));
					panel.setZ_(rotationPanelConfig.get("z"));
					panel.setZ_(rotationPanelConfig.get("z"));
					panel.setAngle(rotationPanelConfig.get("angle"));
				}
				
				panelIndex++;
			}
		}
		
	}
    
	private void saveValues() throws FileNotFoundException, IOException {
		config = new HashMap<String, ArrayList<HashMap<String, Float>>>();
		
		ArrayList<HashMap<String, Float>> cameraConfigList = new ArrayList<HashMap<String, Float>>();
		HashMap<String, Float> cameraConfig = new HashMap<String, Float>();
		cameraConfigList.add(cameraConfig);
		cameraConfig.put("x", cameraPanel.getX_());
		cameraConfig.put("y", cameraPanel.getY_());
		cameraConfig.put("z", cameraPanel.getZ_());
		config.put("cameraPosition", cameraConfigList);
		
		ArrayList<HashMap<String, Float>> fovConfigList = new ArrayList<HashMap<String, Float>>();
		HashMap<String, Float> fovConfig = new HashMap<String, Float>();
		fovConfigList.add(fovConfig);
		fovConfig.put("fov", fovPanel.getFieldValue());
		config.put("fov", fovConfigList);
		
		for (TranslationJPanel panel : translationPanels) {
			ArrayList<HashMap<String, Float>> panelConfigList = new ArrayList<HashMap<String, Float>>();
			HashMap<String, Float> panelConfig = new HashMap<String, Float>();
			panelConfigList.add(panelConfig);
			panelConfig.put("x", panel.getX_());
			panelConfig.put("y", panel.getY_());
			panelConfig.put("z", panel.getZ_());
			config.put("translationPanels", panelConfigList);
		}
		
		for (RotationJPanel panel : rotationPanels) {
			ArrayList<HashMap<String, Float>> panelConfigList = new ArrayList<HashMap<String, Float>>();
			HashMap<String, Float> panelConfig = new HashMap<String, Float>();
			panelConfigList.add(panelConfig);
			panelConfig.put("x", panel.getX_());
			panelConfig.put("y", panel.getY_());
			panelConfig.put("z", panel.getZ_());
			panelConfig.put("angle", panel.getAngle());
			config.put("rotationPanels", panelConfigList);
		}
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("config.dat"));
		
		out.writeObject(config);
		
		out.close();
	}
}