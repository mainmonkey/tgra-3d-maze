package com.tgra;

//import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;


public class DesktopStarter {
	public static void main(String[] args){
		new LwjglApplication(new Maze3D(), "3DMaze", 1024, 860, false);
	}

}
