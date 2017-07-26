package com.unifi.fattureApp.UI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

public class MainWindowUI {

	private JFrame fattureApp_Frame;
	private JLayeredPane outer_Panel;
	private JPanel myCompany_Panel;

	//private int buttonPadding = 8;
	private int buttonHeight = 30;
	private int buttonWidth = 80;
	
	private Color outerColor = new java.awt.Color(232, 246, 250);
	private Color layer1Color = new java.awt.Color(226, 244, 252);

	private Dimension windowDimension = new Dimension(500, 700);
	private JPanel addCompany_Panel;
	private JPanel addClient_Panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MainWindowUI window = new MainWindowUI();
					window.fattureApp_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					window.fattureApp_Frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindowUI() {
		initialize();
	}

	private void initialize() {
		fattureApp_Frame = new JFrame();
		fattureApp_Frame.setTitle("Fatture App");
		fattureApp_Frame.setPreferredSize(windowDimension);
		fattureApp_Frame.setResizable(false);
		fattureApp_Frame.pack();
		fattureApp_Frame.setBackground(java.awt.Color.LIGHT_GRAY);
		fattureApp_Frame.getContentPane().setLayout(null);
		fattureApp_Frame.setLocationRelativeTo(null);
		
		
		outer_Panel = new JLayeredPane();
		outer_Panel.setName("OuterPanel");
		outer_Panel.setBackground(outerColor);
		outer_Panel.setBounds(0, 0, fattureApp_Frame.getContentPane().getWidth(), fattureApp_Frame.getContentPane().getHeight() );
		fattureApp_Frame.getContentPane().add(outer_Panel);
		outer_Panel.setLayout(null);

		int outerInsets = 8;
		int topPanelHeight = 80;
		
		myCompany_Panel = new JPanel();
		myCompany_Panel.setName("CompanyPanel");
		myCompany_Panel.setBorder(BorderFactory.createLineBorder(Color.white));
		myCompany_Panel.setBounds(outerInsets, outerInsets, outer_Panel.getWidth() - outerInsets * 2, topPanelHeight);
		outer_Panel.add(myCompany_Panel);
		myCompany_Panel.setBackground(layer1Color);
		myCompany_Panel.setLayout(null);
		
		int innerInsets = 12;
		String myCompanyName_String = "My Company";
		JLabel myCompany_Label = new JLabel(myCompanyName_String);
		myCompany_Label.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		double width = myCompany_Label.getPreferredSize().getWidth();
		double height = myCompany_Label.getPreferredSize().getHeight();
		myCompany_Label.setBounds(innerInsets, innerInsets, (int) width, (int) height);
		myCompany_Panel.add(myCompany_Label);
		
		JLabel myCompanyInfo_Label = new JLabel("info");
		width = myCompany_Label.getPreferredSize().getWidth();
		height = myCompany_Label.getPreferredSize().getHeight();
		myCompany_Label.setBounds(innerInsets, innerInsets, (int) width, (int) height);
		myCompanyInfo_Label.setBounds(innerInsets, myCompany_Label.getY() +  innerInsets + myCompany_Label.getHeight(), (int)width, (int)height);
		myCompany_Panel.add(myCompanyInfo_Label);
		
		//BUTTON TO ADD NEW COMPANY
		JButton addMyCompany_Button = new JButton("Add");
		addMyCompany_Button.setName("AddCompanyButton");
		addMyCompany_Button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		addMyCompany_Button.setBounds(myCompany_Panel.getWidth() - innerInsets - buttonWidth, (myCompany_Panel.getHeight() / 2) - (buttonHeight / 2), buttonWidth, buttonHeight);
		myCompany_Panel.add(addMyCompany_Button);
		outer_Panel.setLayer(addMyCompany_Button, 1);
		addMyCompany_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAddCompanyPanel();
			}
		});
		
		JPanel invoice_Panel = new JPanel();
		invoice_Panel.setName("InvoicePanel");
		invoice_Panel.setBorder(BorderFactory.createLineBorder(Color.white));
		int invoicePanelY = (outerInsets * 2) + myCompany_Panel.getHeight();
		int invoicePanelWidth = outer_Panel.getWidth() - (outerInsets * 2);
		int invoicePanelHeight = outer_Panel.getHeight() - (invoicePanelY + outerInsets);
		invoice_Panel.setBounds(outerInsets, invoicePanelY, invoicePanelWidth, invoicePanelHeight);
		outer_Panel.add(invoice_Panel);
		invoice_Panel.setBackground(layer1Color);
		invoice_Panel.setLayout(null);
	
		
		outer_Panel.setLayer(invoice_Panel, 1);
		
		JPanel invoiceProvisionPanel = new JPanel();
		invoiceProvisionPanel.setBackground(Color.RED);
		invoiceProvisionPanel.setBounds(6, 152, 472, 103);
		invoice_Panel.add(invoiceProvisionPanel);
		invoiceProvisionPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Invoice provision");
		lblNewLabel_1.setBounds(6, 6, 207, 16);
		invoiceProvisionPanel.add(lblNewLabel_1);
		
		JComboBox invoiceListcomboBox = new JComboBox();
		invoiceListcomboBox.setBounds(6, 36, 333, 27);
		invoiceProvisionPanel.add(invoiceListcomboBox);
		
		JButton addInvoiceProvision = new JButton("Add");
		addInvoiceProvision.setBounds(341, 35, 54, 29);
		invoiceProvisionPanel.add(addInvoiceProvision);
		
		JButton editInvoiceProvision = new JButton("Edit");
		editInvoiceProvision.setBounds(407, 35, 59, 29);
		invoiceProvisionPanel.add(editInvoiceProvision);
		
		JPanel clientPanel = new JPanel();
		clientPanel.setName("ClientPanel");
		clientPanel.setBackground(Color.ORANGE);
		clientPanel.setBounds(6, 30, 472, 92);
		invoice_Panel.add(clientPanel);
		clientPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Client");
		lblNewLabel.setBounds(6, 6, 37, 16);
		clientPanel.add(lblNewLabel);
		
		JComboBox clientListComboBox = new JComboBox();
		clientListComboBox.setBounds(6, 35, 335, 27);
		clientPanel.add(clientListComboBox);
		
		JButton addClient = new JButton("Add");
		addClient.setName("AddClientButton");
		addClient.setBounds(336, 33, 61, 29);
		clientPanel.add(addClient);
		addClient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddClientPanel();
			}
		});
		
		JButton editClient = new JButton("Edit");
		editClient.setBounds(398, 32, 68, 29);
		clientPanel.add(editClient);
		outer_Panel.setLayer(myCompany_Panel, 1);
		
		createAddRecordsPanels();
		
		fattureApp_Frame.pack();
	}
	
	private void createAddRecordsPanels(){
		addCompany_Panel = new CompanyPanel(outer_Panel, buttonWidth, buttonHeight);
		addCompany_Panel.setVisible(false);
		
		addClient_Panel=new ClientPanel(outer_Panel, buttonWidth, buttonHeight);
		addClient_Panel.setVisible(false);
	}
	
	

	private void showAddCompanyPanel() {
		// SHOW PANEL TO ADD CUSTOMER
		addCompany_Panel.setVisible(true);
	}
	
	private void showAddClientPanel() {
		// SHOW PANEL TO ADD CUSTOMER
		addClient_Panel.setVisible(true);
	}

	public JFrame getMainFrame() {
		// TODO Auto-generated method stub
		return fattureApp_Frame;
	}
}
