package net.rb.asd.resource;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import net.rb.asd.graphics.model.Model;

/**
 * (C)2018- Richard Bergqvist
 * Created 15 feb. 2018
 */
public class ResourceLoader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	public Model loadToVAO(float[] vertices, float[] textureCoordinates, float[] normals, int[] indices) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, vertices);
		storeDataInAttributeList(1, 2, textureCoordinates);
		storeDataInAttributeList(2, 3, normals);
		bindIndicesBuffer(indices);
		unbindVAO();
		return new Model(vaoID, indices.length);
	}
	
	public int loadTexture(String name) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/textures/" + name + ".png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}
	
	public Model loadOBJModel(String name, ResourceLoader loader) {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(new File("res/models/" + name + ".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("Could not find model file: " + name);
			System.err.println("Do not include the file extension.");
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textureCoordinates = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] vertexArray = null;
		float[] normalArray = null;
		float[] textureCoordinateArray = null;
		int[] indexArray = null;
		try {
			while (true) {
				line = bufferedReader.readLine();
				String[] currentLine = line.split(" ");
				if (line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				} else if (line.startsWith("vt ")) {
					Vector2f textureCoordinate = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textureCoordinates.add(textureCoordinate);
				} else if (line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				} else if (line.startsWith("f ")) {
					textureCoordinateArray = new float[vertices.size() * 2];
					normalArray = new float[vertices.size() * 3];
					break;
				}
			}
			
			while (line != null) {
				if (!line.startsWith("f ")) {
					line = bufferedReader.readLine();
					continue;
				}
				
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1, indices, textureCoordinates, normals, textureCoordinateArray, normalArray);
				processVertex(vertex2, indices, textureCoordinates, normals, textureCoordinateArray, normalArray);
				processVertex(vertex3, indices, textureCoordinates, normals, textureCoordinateArray, normalArray);
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		vertexArray = new float[vertices.size() * 3];
		indexArray = new int[indices.size()];
		
		int vertexPointer = 0;
		for (Vector3f vertex : vertices) {
			vertexArray[vertexPointer++] = vertex.x;
			vertexArray[vertexPointer++] = vertex.y;
			vertexArray[vertexPointer++] = vertex.z;
		}
		
		for (int i = 0; i < indices.size(); i++) {
			indexArray[i] = indices.get(i);
		}
		
		return loader.loadToVAO(vertexArray, textureCoordinateArray, normalArray, indexArray);
	}
	
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textureCoordinates, List<Vector3f> normals, float[] textureCoordinateArray, float[] normalArray) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTextureCoordinate = textureCoordinates.get(Integer.parseInt(vertexData[1]) - 1);
		textureCoordinateArray[currentVertexPointer * 2] = currentTextureCoordinate.x;
		textureCoordinateArray[currentVertexPointer * 2 + 1] = 1 - currentTextureCoordinate.y;
		Vector3f currentNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalArray[currentVertexPointer * 3] = currentNormal.x;
		normalArray[currentVertexPointer * 3 + 1] = currentNormal.y;
		normalArray[currentVertexPointer * 3 + 2] = currentNormal.z;
	}
	
	private int createVAO() {
		int vaoID = glGenVertexArrays();
		vaos.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, coordinateSize, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private void bindIndicesBuffer(int[] indices) {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		
		IntBuffer buffer = storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
	}
	
	private void unbindVAO() {
		glBindVertexArray(0);
	}
	
	public void clean() {
		for (int vao : vaos) {
			glDeleteVertexArrays(vao);
		}
		
		for (int vbo : vbos) {
			glDeleteBuffers(vbo);
		}
		
		for (int texture : textures) {
			glDeleteTextures(texture);
		}
	}
}