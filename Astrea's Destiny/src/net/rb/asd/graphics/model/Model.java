package net.rb.asd.graphics.model;

/**
 * (C)2018- Richard Bergqvist
 * Created 15 feb. 2018
 */
public class Model {

	private int vaoID;
	private int vertexCount;
	
	public Model(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoID() {
		return vaoID;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
}