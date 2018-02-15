package net.rb.ad.graphics.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * (C)2018- Richard Bergqvist
 * Created 15 feb. 2018
 */
public abstract class Shader {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	public Shader(String vertexFile, String fragmentFile) {
		this.programID = glCreateProgram();
		this.vertexShaderID = load(vertexFile, GL_VERTEX_SHADER);
		this.fragmentShaderID = load(fragmentFile, GL_FRAGMENT_SHADER);
		
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		glLinkProgram(programID);
		glValidateProgram(programID);
	}
	
	public void start() {
		glUseProgram(programID);
	}
	
	public void stop() {
		glUseProgram(0);
	}
	
	public void clean() {
		stop();
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}
	
	protected void bindAttribute(int attribute, String name) {
		glBindAttribLocation(programID, attribute, name);
	}
	
	protected abstract void bindAttributes();
	
	private static int load(String name, int type) {
		StringBuilder shaderSource = new StringBuilder();
		String extension = "";
		try {
			if (type == GL_VERTEX_SHADER) {
				extension = ".vs";
			} if (type == GL_FRAGMENT_SHADER) {
				extension = ".fs";
			}
			BufferedReader reader = new BufferedReader(new FileReader("res/shaders/" + name + extension));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read shader file: " + name);
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(shaderID, 500));
			System.out.println("Could not compile shader: " + name);
			System.exit(-1);
		}
		return shaderID;
	}
}