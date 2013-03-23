package com.francescolemma.libgdx.modeltester;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class OpenGLUtils {

	private static final String PLAIN_VERTEX_SHADER = "data/shaders/plain/vertex.glsl";
	private static final String PLAIN_FRAGMENT_SHADER = "data/shaders/plain/fragment.glsl";
	
	private static ShaderProgram plainShader;

	public static ShaderProgram getPlainShader() {
		if (plainShader == null) {
			plainShader = createShader(PLAIN_VERTEX_SHADER, PLAIN_FRAGMENT_SHADER);
		}
		
		return plainShader;
	}
	
	private static ShaderProgram createShader(String vertexPath, String fragmentPath) {
		return createShader(Gdx.files.internal(vertexPath), Gdx.files.internal(fragmentPath));
	}
	
	private static ShaderProgram createShader(FileHandle vertex, FileHandle fragment) {
		ShaderProgram shader = new ShaderProgram(vertex, fragment);
		
        if (!shader.isCompiled())
            throw new IllegalStateException(shader.getLog());
        
        return shader;
    }
}
