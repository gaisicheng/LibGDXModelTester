package com.francescolemma.libgdx.modeltester;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class InputPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private SingleFieldJPanel fovPanel = new SingleFieldJPanel("fov");
	private CameraPositionJPanel cameraPositionPanel = new CameraPositionJPanel();
	
	private ArrayList<TranslationJPanel> translationPanels = new ArrayList<TranslationJPanel>();
	private ArrayList<RotationJPanel> rotationPanels = new ArrayList<RotationJPanel>();

	public InputPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		translationPanels.clear();
		translationPanels.add(new TranslationJPanel());
		
		rotationPanels.clear();
		rotationPanels.add(new RotationJPanel());
		
		add(fovPanel);
		add(cameraPositionPanel);
		
		for (JPanel panel : translationPanels) {
			add(panel);
		}
		
		for (JPanel panel : rotationPanels) {
			add(panel);
		}
//
//		button.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String tutorialName = (String) list.getSelectedValue();
//				Tutorial tutorial = Tutorials.newTutorial(
//						"com.karimamin.tutorials", tutorialName);
////				new JoglApplication(tutorial, tutorialName, 480, 320, false);
//				LwjglApplication(new Game(), "Game", 1280, 720, false);
//			}
//		});
		
		new LwjglApplication(new Game(this), "Game", Game.getWidth(), Game.getHeight(), true);
	}

	public void setFovPanel(SingleFieldJPanel fovPanel) {
		this.fovPanel = fovPanel;
	}

	public SingleFieldJPanel getFovPanel() {
		return fovPanel;
	}

	public void setCameraPositionPanel(CameraPositionJPanel cameraPositionPanel) {
		this.cameraPositionPanel = cameraPositionPanel;
	}

	public CameraPositionJPanel getCameraPositionPanel() {
		return cameraPositionPanel;
	}

	public void setTranslationPanels(ArrayList<TranslationJPanel> translationPanels) {
		this.translationPanels = translationPanels;
	}

	public ArrayList<TranslationJPanel> getTranslationPanels() {
		return translationPanels;
	}

	public void setRotationPanels(ArrayList<RotationJPanel> rotationPanels) {
		this.rotationPanels = rotationPanels;
	}

	public ArrayList<RotationJPanel> getRotationPanels() {
		return rotationPanels;
	}
}