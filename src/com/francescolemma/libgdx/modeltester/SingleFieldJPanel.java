package com.francescolemma.libgdx.modeltester;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class SingleFieldJPanel extends MyBaseJPanel {

	private static final long serialVersionUID = -8283343511273683109L;
	private JTextField fieldText;

	protected String type = "Translation";
	
	public SingleFieldJPanel(String type) {
		this.type = type;
		this.setLayout(new FlowLayout());
		JLabel fieldLabel = new JLabel(type + ":");
		fieldText = new JTextField();
		fieldText.setPreferredSize(new Dimension(COORDINATE_FIELD_WIDTH, COORDINATE_FIELD_HEIGHT));
		this.add(fieldLabel);
		this.add(fieldText);
	}
	
	public float getFieldValue() {
		return getFloatValue(fieldText.getText());
	}

	public void setFieldValue(Float value) {
		fieldText.setText(String.valueOf(value));
	}
}