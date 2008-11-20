package org.encog.workbench.dialogs.common;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.encog.workbench.EncogWorkBench;

abstract public class EncogCommonDialog extends JDialog implements
		ActionListener {

	private JButton ctrlOK;
	private JButton ctrlCancel;
	private JPanel bodyPanel;
	private JPanel buttonPanel;
	private boolean shouldProcess;

	public EncogCommonDialog(Frame owner) {
		super(owner, true);

		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());

		this.bodyPanel = new JPanel();
		this.buttonPanel = new JPanel();

		this.buttonPanel.add(this.ctrlOK = new JButton("OK"));
		this.buttonPanel.add(this.ctrlCancel = new JButton("Cancel"));

		this.ctrlCancel.addActionListener(this);
		this.ctrlOK.addActionListener(this);

		content.add(this.bodyPanel, BorderLayout.CENTER);
		content.add(this.buttonPanel, BorderLayout.SOUTH);
	}

	public double validateFieldNumeric(String name, JTextField field)
			throws ValidationException {
		try {
			double d = Double.parseDouble(field.getText());
			return d;
		} catch (NumberFormatException e) {
			throw new ValidationException("Must enter a valid number for: "
					+ name);
		}
	}

	public double validateFieldNumeric(String name, JTextField field,
			double low, double high) throws ValidationException {
		double d = validateFieldNumeric(name, field);
		if (d < low)
			throw new ValidationException("Must enter a value above " + low
					+ " for: " + name);
		if (d > high)
			throw new ValidationException("Must enter a value below " + low
					+ " for: " + name);

		return d;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ctrlOK) {
			if (collect()) {
				dispose();
				this.shouldProcess = true;
			}
		} else if (e.getSource() == this.ctrlCancel) {
			dispose();
			this.shouldProcess = false;
		}
	}

	public boolean process() {
		this.setVisible(true);
		return this.shouldProcess;
	}

	public String validateFieldString(String name, JComboBox field,
			boolean required) throws ValidationException {
		String result = (String) field.getSelectedItem();
		if (result == null && required) {
			throw new ValidationException("The " + name + " field is required.");
		}

		result = result.trim();

		return result;
	}

	public String validateFieldString(String name, JTextField field,
			boolean required) throws ValidationException {
		String result = field.getText().trim();
		if (result.length() < 1 && required) {
			throw new ValidationException("The " + name + " field is required.");
		}
		return result;
	}

	/**
	 * @return the bodyPanel
	 */
	public JPanel getBodyPanel() {
		return bodyPanel;
	}

	/**
	 * @return the buttonPanel
	 */
	public JPanel getButtonPanel() {
		return buttonPanel;
	}

	public boolean collect() {
		try {
			collectFields();
			return true;
		} catch (ValidationException e) {
			EncogWorkBench.displayError("Validation Error", e.getMessage());
			return false;
		}
	}

	abstract public void collectFields() throws ValidationException;

	abstract public void setFields();

}
