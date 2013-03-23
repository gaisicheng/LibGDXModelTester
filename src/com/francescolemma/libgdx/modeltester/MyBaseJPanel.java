package com.francescolemma.libgdx.modeltester;

import javax.swing.JPanel;

public class MyBaseJPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	protected  static final int COORDINATE_FIELD_WIDTH = 60;
	protected static final int COORDINATE_FIELD_HEIGHT = 22;
	
	protected float getFloatValue(String value) {
		if (value == null || value.equals("")) {
			return 0;
		}
		
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
