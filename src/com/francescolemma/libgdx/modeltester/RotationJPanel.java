package com.francescolemma.libgdx.modeltester;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class RotationJPanel extends TranslationJPanel {

	private static final long serialVersionUID = 3793686668665993439L;

	private JTextField angleText;

	
	public RotationJPanel() {
		super("Rotation");
		JLabel angleLabel = new JLabel("angle:");
		angleText = new JTextField();
		angleText.setPreferredSize(new Dimension(COORDINATE_FIELD_WIDTH, COORDINATE_FIELD_HEIGHT));
		this.add(angleLabel);
		this.add(angleText);
	}

	public float getAngle() {
		return getFloatValue(angleText.getText());
	}

	public void setAngle(Float value) {
		angleText.setText(String.valueOf(value));
	}
}