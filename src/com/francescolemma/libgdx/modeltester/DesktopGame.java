package com.francescolemma.libgdx.modeltester;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopGame {
	
    public static final int COORDINATE_FIELD_WIDTH = 30;
    public static final int COORDINATE_FIELD_HEIGHT = 22;
    
    private ArrayList<JPanel> panels;

	public static void main (String[] args) {
		JFrame frame = new JFrame("Obj Tester");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		InputPanel inputPanel = new InputPanel();
		frame.setContentPane(inputPanel);
		frame.setSize(400, 600);
		frame.setVisible(true);
	}
    
	public void setPanels(ArrayList<JPanel> panels) {
		this.panels = panels;
	}

	public ArrayList<JPanel> getPanels() {
		return panels;
	}
}