package net.rb.asd.entity;

import org.lwjgl.util.vector.Vector3f;

/**
 * (C)2018- Richard Bergqvist
 * Created 16 feb. 2018
 */
public class Light {

	private Vector3f position;
	private Vector3f color;
	
	public Light(Vector3f position, Vector3f color) {
		this.position = position;
		this.color = color;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	public Vector3f getColor() {
		return color;
	}
}