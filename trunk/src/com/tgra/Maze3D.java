package com.tgra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL11;


public class Maze3D implements ApplicationListener {
	

	private ArrayList<Cell> cells;
	Cell cell = new Cell();
	private Camera cam;


	@Override
	public void create() {
		
		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		Gdx.gl11.glEnable(GL11.GL_LIGHT1);
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 0.2f, 30.0f);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		
		initializeCells();
		cam = new Camera(new Point3D(3.0f, 3.0f, 3.0f), new Point3D(0.0f, 3.0f, 0.0f), new Vector3D(0.0f, 1.0f, 0.0f));
		
		
	}

	private void initializeCells() {

		cells = new ArrayList<Cell>();
		InputStreamReader reader;
		BufferedReader br;
		int x = 0, y = 0;
			
		try 
		{
			File inFile = new File("maze.txt");
			reader = new InputStreamReader(new FileInputStream(inFile));
			br = new BufferedReader(reader);			
			String inputLine = null;
			
			while ((inputLine = br.readLine()) != null)
			{	
				for(int i = 0; i< inputLine.length(); i++)
				{
					if(inputLine.charAt(i) == 'B')
					{
						cells.add(new Cell());
					}
					else if(inputLine.charAt(i) == 'S')
					{
						cells.add(new Cell(true,false));
					}
					else if(inputLine.charAt(i) == 'W')
					{
						cells.add(new Cell(false,true));
					}
					else
					{
						cells.add(new Cell(false, false));
					}
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
	
	public void printMaze()
	{

		int index = 0;
		for(float fx = 0.0f; fx < 10.0f; fx += 1.0)
		{
			for(float fz = 0.0f; fz < 10.0f; fz += 1.0, index++)
			{			
				Gdx.gl11.glPushMatrix();
				Gdx.gl11.glTranslatef(fx, 0.0f, fz);
				cells.get(index).draw();
				Gdx.gl11.glPopMatrix();
			}
		}
		
		Cell cSouthWall = new Cell(true, false);
		Cell cWestWall = new Cell(false, true);
		
		for(float i = 0.0f; i < Math.sqrt(cells.size()); i+=1.0)
		{
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(0.0f, 0.0f, i);
			cSouthWall.draw();
			Gdx.gl11.glPopMatrix();
			
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(i, 0.0f, 0.0f);
			cWestWall.draw();
			Gdx.gl11.glPopMatrix();
			
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(i, 0.0f, 10.0f);
			cWestWall.draw();
			Gdx.gl11.glPopMatrix();
			
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(10, 0.0f, i);
			cSouthWall.draw();
			Gdx.gl11.glPopMatrix();


		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		
		movements();
		display();
		printMaze();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public void display()
	{
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		cam.setModelViewMatrix();
		
		float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 0);

		float[] lightPosition = {5.0f, 10.0f, 15.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 0);

		float[] lightDiffuse1 = {0.5f, 0.0f, 0.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse1, 0);

		float[] lightPosition1 = {-5.0f, -10.0f, -15.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition1, 0);

		float[] materialDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

//		cell.draw();
		
		Gdx.gl11.glTranslatef(1.0f, 2.0f, 1.0f);
	}
	
	public void movements()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			cam.pitch(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			cam.pitch(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			cam.yaw(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			cam.yaw(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			cam.slide(0.0f, 0.0f, -10.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			cam.slide(0.0f, 0.0f, 10.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			cam.slide(-10.0f * deltaTime, 0.0f, 0.0f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			cam.slide(10.0f * deltaTime, 0.0f, 0.0f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.R))
		{
			cam.slide(0.0f, 10.0f * deltaTime, 0.0f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.F))
		{
			cam.slide(0.0f, -10.0f * deltaTime, 0.0f);
		}
	}

}
