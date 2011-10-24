package com.tgra;

import com.badlogic.gdx.Gdx;

public class Cell {

	public boolean southWall;
	public boolean westWall;
	private Wall[] walls = new Wall[3]; 
	
	
	public Cell(){
		this.southWall = true;
		this.westWall = true;
		
		this.walls[0] = new Wall();
		this.walls[1] = new Wall();
		this.walls[2] = new Wall();

	}
	public Cell(boolean southWall, boolean westWall) {
		this.southWall = southWall;
		this.westWall = westWall;

		this.walls[0] = new Wall();
		this.walls[1] = new Wall();
		this.walls[2] = new Wall();
	}
	
	
	public void draw(){
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(0.0f, -0.5f, 0.0f);
		Gdx.gl11.glScalef(1.0f, 0.05f, 1.0f);
		walls[0].draw();
		Gdx.gl11.glPopMatrix();
		
		if(southWall){
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(-0.5f, 0.0f, 0.0f);
			Gdx.gl11.glScalef(0.15f, 1.0f, 1.0f);
			walls[1].draw();
			Gdx.gl11.glPopMatrix();
		}
		if(westWall){
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(0.0f, 0.0f, -0.5f);
			Gdx.gl11.glScalef(1.0f, 1.0f, 0.15f);
			walls[2].draw();
			Gdx.gl11.glPopMatrix();
		}
		
	}
	
	
}
