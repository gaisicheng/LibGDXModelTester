package com.francescolemma.libgdx.modeltester;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class TranslationJPanel extends MyBaseJPanel {

	private static final long serialVersionUID = -8283343511273683109L;
	private JTextField xText;
	private JTextField yText;
	private JTextField zText;

	protected String type = "Translation";
	
	public TranslationJPanel() {
		setupPanel();
	}

	public TranslationJPanel(String type) {
		this.type = type;
		setupPanel();
	}
	
	private void setupPanel() {
		this.setLayout(new FlowLayout());
		JLabel typeLabel = new JLabel(type + " ");
		JLabel xLabel = new JLabel("x:");
		xText = new JTextField();
		xText.setPreferredSize(new Dimension(COORDINATE_FIELD_WIDTH, COORDINATE_FIELD_HEIGHT));
		JLabel yLabel = new JLabel("y:");
		yText = new JTextField();
		yText.setPreferredSize(new Dimension(COORDINATE_FIELD_WIDTH, COORDINATE_FIELD_HEIGHT));
		JLabel zLabel = new JLabel("z:");
		zText = new JTextField();
		zText.setPreferredSize(new Dimension(COORDINATE_FIELD_WIDTH, COORDINATE_FIELD_HEIGHT));
		this.add(typeLabel);
		this.add(xLabel);
		this.add(xText);
		this.add(yLabel);
		this.add(yText);
		this.add(zLabel);
		this.add(zText);
	}

	public float getX_() {
		return getFloatValue(xText.getText());
	}

	public void setX_(float x) {
		xText.setText(String.valueOf(x));
	}

	public float getY_() {
		return getFloatValue(yText.getText());
	}

	public void setY_(float y) {
		yText.setText(String.valueOf(y));
	}

	public float getZ_() {
		return getFloatValue(zText.getText());
	}

	public void setZ_(float z) {
		zText.setText(String.valueOf(z));
	}
}