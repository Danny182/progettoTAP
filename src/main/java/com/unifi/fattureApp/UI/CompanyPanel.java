package com.unifi.fattureApp.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.unifi.fattureApp.App.MongoUiComunication;

public class CompanyPanel extends JPanel implements AddPanel {
	private static final long serialVersionUID = 6387743798709513734L;

	private static final Logger LOGGER = Logger.getLogger(CompanyPanel.class);

	private MongoUiComunication myMongoUiComunication;

	private JTextField companyNameTF;
	private JTextField companyVatTF;
	private JTextField companyAddressTF;
	private JTextField companyCityTF;
	private JTextField companyProvinceTF;
	private JTextField companyZipTF;
	private JTextField companyCountryTF;
	private JTextField companyPhoneTF;
	private JTextField companyEmailTF;
	private CompanyPanel addCompanyPanel;

	private LinkedList<JTextField> textFields;

	private Color layerColor = new java.awt.Color(216, 245, 255);

	private boolean isSaving;

	public CompanyPanel(JLayeredPane outerPanel, int buttonWidth, int buttonHeight, MongoUiComunication mongoUiCom) {
		addCompanyPanel = this;
		myMongoUiComunication = mongoUiCom;

		this.setVisible(false);

		addCompanyPanel.setName("AddCompanyPanel");
		addCompanyPanel.setBackground(layerColor);
		addCompanyPanel.setBorder(BorderFactory.createLineBorder(Color.white, 3));
		int insets = 22;
		int width = outerPanel.getWidth() - insets - insets;
		int height = outerPanel.getHeight() - insets - insets;
		addCompanyPanel.setBounds(insets, insets, width, height);
		outerPanel.add(addCompanyPanel);
		addCompanyPanel.setLayout(null);
		outerPanel.setLayer(addCompanyPanel, 2);

		textFields = new LinkedList<>();
		
		initLabelsTextFields();

		JButton cancelButton = new JButton();
		cancelButton.setName("CancelButton");
		cancelButton.setText("Cancel");
		cancelButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		cancelButton.setBounds((addCompanyPanel.getWidth() / 2) - buttonWidth - 24,
				addCompanyPanel.getHeight() - 20 - addCompanyPanel.getY(), buttonWidth, buttonHeight);
		addCompanyPanel.add(cancelButton);
		cancelButton.addActionListener(e ->	addCompanyPanel.setVisible(false));

		JButton saveButton = new JButton();
		saveButton.setName("SaveButton");
		saveButton.setEnabled(false);
		saveButton.setText("Save");
		saveButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		saveButton.setBounds((addCompanyPanel.getWidth() / 2) + 24,
				addCompanyPanel.getHeight() - 20 - addCompanyPanel.getY(), buttonWidth, buttonHeight);
		addCompanyPanel.add(saveButton);
		saveButton.addActionListener(e -> {
			if(addCompanyPanel.isSaving()) {
				boolean saved = myMongoUiComunication.addCompanyToDatabase(companyNameTF.getText(),
						companyVatTF.getText(), companyAddressTF.getText(), companyCityTF.getText(),
						companyProvinceTF.getText(), companyZipTF.getText(), companyCountryTF.getText(),
						companyPhoneTF.getText(), companyEmailTF.getText());

				if (saved) {
					myMongoUiComunication.setCurrentSelectedCompany(myMongoUiComunication.getSavedCompanies().get(myMongoUiComunication.getSavedCompanies().size()-1));
					myMongoUiComunication.enableEditCompanyButton();
				} else {
					LOGGER.error("Error: Company was not saved!!!");
				}
			}else {
				boolean saved = myMongoUiComunication.editCompanyFromDatabase(companyNameTF.getText(),
						companyVatTF.getText(), companyAddressTF.getText(), companyCityTF.getText(),
						companyProvinceTF.getText(), companyZipTF.getText(), companyCountryTF.getText(),
						companyPhoneTF.getText(), companyEmailTF.getText());
				if (saved) {
					LOGGER.info("Modificata con successo Yay");
				} else {
					LOGGER.error("Error: non modificata!!!");
				}
			}

			addCompanyPanel.setVisible(false);
			resetTextFields();
			myMongoUiComunication.updateCompanyReference();
		});

		Component[] components = addCompanyPanel.getComponents();
		for (Component component : components) {
			if (component.getClass().equals(JTextField.class) && !((JTextField) component).getName().equals("companyPhoneTextField")
					&& !((JTextField) component).getName().equals("companyEmailTextField")) {
				textFields.add((JTextField) component);
			}
		}

		for (JTextField tf : textFields) {
			tf.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					changed();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					changed();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					changed();
				}

				public void changed() {
					boolean shouldActivate = true;
					for (JTextField tf : textFields) {
						if (tf.getText().equals("")) {
							shouldActivate = false;
							break;
						}
					}
					saveButton.setEnabled(shouldActivate);
				}
			});
		}
	}
	
	private void initLabelsTextFields() {
		int addPanelY = addCompanyPanel.getY();
		int insets = 8;
		int insetsMiddle = 80;

		JLabel addCompanyTitleLabel = new JLabel("Company");
		addCompanyTitleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		int width = (int) addCompanyTitleLabel.getPreferredSize().getWidth();
		int height = (int) addCompanyTitleLabel.getPreferredSize().getHeight();
		addCompanyTitleLabel.setBounds((addCompanyPanel.getWidth() / 2) - (width / 2), addPanelY - 10, width, height);
		addCompanyPanel.add(addCompanyTitleLabel);

		JLabel companyNameLabel = new JLabel("Company Name:");
		setUpLabelInThePanel(companyNameLabel, 0, addCompanyTitleLabel);

		companyNameTF = new JTextField();
		setUpTextFieldInThePanel(companyNameTF, 300, "companyNameTextField", 0, insets, companyNameLabel);

		JLabel companyVatLabel = new JLabel("VAT Number:");
		setUpLabelInThePanel(companyVatLabel, 0, companyNameTF);

		companyVatTF = new JTextField();
		setUpTextFieldInThePanel(companyVatTF, 200, "companyVatTextField", 0, insets, companyVatLabel);

		JLabel companyAddressLabel = new JLabel("Address:");
		setUpLabelInThePanel(companyAddressLabel, 0, companyVatTF);

		companyAddressTF = new JTextField();
		setUpTextFieldInThePanel(companyAddressTF, 300, "companyAddressTextField", 0, insets, companyAddressLabel);

		JLabel companyCityLabel = new JLabel("City:");
		setUpLabelInThePanel(companyCityLabel, - insetsMiddle, companyAddressTF);

		companyCityTF = new JTextField();
		setUpTextFieldInThePanel(companyCityTF, 130, "companyCityTextField", -insetsMiddle, insets, companyCityLabel);

		JLabel companyProvinceLabel = new JLabel("Province:");
		setUpLabelInThePanel(companyProvinceLabel, insetsMiddle, companyAddressTF);

		companyProvinceTF = new JTextField();
		setUpTextFieldInThePanel(companyProvinceTF, 130, "companyProvinceTextField", insetsMiddle, insets, companyProvinceLabel);

		JLabel companyZipLabel = new JLabel("ZIP Code:");
		setUpLabelInThePanel(companyZipLabel, -insetsMiddle, companyProvinceTF);

		companyZipTF = new JTextField();
		setUpTextFieldInThePanel(companyZipTF, 80, "companyZipTextField", -insetsMiddle, insets, companyZipLabel);

		JLabel companyCountryLabel = new JLabel("Country:");
		setUpLabelInThePanel(companyCountryLabel, insetsMiddle, companyProvinceTF);
		
		companyCountryTF = new JTextField();
		setUpTextFieldInThePanel(companyCountryTF, 100, "companyCountryTextField", insetsMiddle, insets, companyCountryLabel);

		JLabel companyPhoneLabel = new JLabel("Phone:");
		setUpLabelInThePanel(companyPhoneLabel, 0, companyCountryTF);

		companyPhoneTF = new JTextField();
		setUpTextFieldInThePanel(companyPhoneTF, 150, "companyPhoneTextField", 0, insets, companyPhoneLabel);

		JLabel companyEmailLabel = new JLabel("Email:");
		setUpLabelInThePanel(companyEmailLabel, 0, companyPhoneTF);
		
		companyEmailTF = new JTextField();
		setUpTextFieldInThePanel(companyEmailTF, 190, "companyEmailTextField", 0, insets, companyEmailLabel);
		
	}
	
	private void setUpLabelInThePanel(JLabel label, int insetsMiddle, JComponent relatedComponent) {
		int width = (int) label.getPreferredSize().getWidth();
		int height = (int) label.getPreferredSize().getHeight();
		label.setBounds((addCompanyPanel.getWidth() / 2) - (width / 2)+insetsMiddle, relatedComponent.getY() + relatedComponent.getHeight() + 23, width, height);
		addCompanyPanel.add(label);
	}
	
	private void setUpTextFieldInThePanel(JTextField textField, int width, String name, int insetsWidth, int insets, JLabel linkedLabel) {
		textField.setName(name);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBounds((addCompanyPanel.getWidth() / 2) - (width / 2)+insetsWidth, linkedLabel.getY() + linkedLabel.getHeight() + insets, width, 28);
		addCompanyPanel.add(textField);
	}

	private void resetTextFields() {
		companyNameTF.setText("");
		companyVatTF.setText("");
		companyAddressTF.setText("");
		companyCityTF.setText("");
		companyProvinceTF.setText("");
		companyZipTF.setText("");
		companyCountryTF.setText("");
		companyPhoneTF.setText("");
		companyEmailTF.setText("");
	}

	public boolean isSaving() {
		return isSaving;
	}

	@Override
	public void setAddingMode(boolean isSaving) {
		this.isSaving = isSaving;
		if(!isSaving) {
			companyNameTF.setText(myMongoUiComunication.getCurrentSelectedCompany().getName());
			companyVatTF.setText(myMongoUiComunication.getCurrentSelectedCompany().getVatCode());
			companyAddressTF.setText(myMongoUiComunication.getCurrentSelectedCompany().getAddress());
			companyCityTF.setText(myMongoUiComunication.getCurrentSelectedCompany().getCity());
			companyProvinceTF.setText(myMongoUiComunication.getCurrentSelectedCompany().getProvince());
			companyZipTF.setText(myMongoUiComunication.getCurrentSelectedCompany().getZipCode());
			companyCountryTF.setText(myMongoUiComunication.getCurrentSelectedCompany().getCountry());
			companyPhoneTF.setText(myMongoUiComunication.getCurrentSelectedCompany().getPhone());
			companyEmailTF.setText(myMongoUiComunication.getCurrentSelectedCompany().getEmail());
		}
		this.setVisible(true);
	}
}