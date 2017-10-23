package com.unifi.fattureApp.App;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.mongodb.MongoClient;
import com.unifi.fattureApp.mongoWrapper.MongoWrapper;

public class MongoUiComunication {
	Database database;
	String mongoHost = "localhost";
	CompanyController myCompanyController;
	
	private Company currentSelectedCompany;
	private Client currentSelectedClient;
	private Invoice currentSelectedInvoice;
	
	private JComboBox clientsList;
	private JComboBox invoicesList;
	private JLabel companyInfo;
	
	private int companyCounter=0;
	

	
	
	public MongoUiComunication(){
//		if (args.length > 0)
//			mongoHost = args[0];
//		Database database = null;
//		try {
//			database = new MongoWrapper(new MongoClient(mongoHost));
//		} catch (UnknownHostException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		myCompanyController = new CompanyController(database);
		
		try{
			database = new MongoWrapper(new MongoClient(mongoHost, 27017));
		}catch(Exception e){
			System.out.println("Error while connecting to mongoHost");
			e.printStackTrace();
		}
	
		myCompanyController = new CompanyController(database);
	}
	
	public boolean addClientToDatabase(String name, String fiscalCode, String residence,String city,String province,String zip,String country, String phone, String email){
		String currentId=String.valueOf(this.getClientsCount()+1);
		return myCompanyController.addClient(new Client(currentId,name,fiscalCode,residence,city,province,zip,country,phone,email));
	}
	
	public boolean addCompanyToDatabase(String name, String vat, String address, String city, String province, String zip, String country, String phone, String email){
		String currentId=String.valueOf(this.getCompaniesCount()+1);
		return myCompanyController.addCompany
		(new Company(currentId, name, vat, address, city, province, zip,
				country, phone, email));
	}
	
	public boolean addInvoiceToDatabase(String name, String price,String description){
		String currentId=String.valueOf(this.getInvoicesCount()+1);
		return myCompanyController.addInvoice(new Invoice(currentId,name,price,description));
	}
	
	public int getCompaniesCount(){
		List<Company> companies = myCompanyController.getAllCompany();
		return companies.size();
	}
	
	public int getClientsCount(){
		List<Client> clients = myCompanyController.getAllClients();
		return clients.size();
	}
	
	public int getInvoicesCount(){
		List<Invoice> invoices = myCompanyController.getAllInvoices();
		return invoices.size();
	}
	
	
	public List<Company> getSavedCompanies(){
		return myCompanyController.getAllCompany();
	}
	
	public List<Client> getSavedClients(){
		return myCompanyController.getAllClients();
	}
	
	public List<Invoice> getSavedInvoices(){
		return myCompanyController.getAllInvoices();
	}
	
	//   Just console prints!!!
	
	public void printAllClients(){
		System.out.println("In the database Clients:");
		List<Client> clients = myCompanyController.getAllClients();
		clients.
			stream().
			forEach(
				client -> System.out.println
					("Client Name : " + client.getId() + " - " + client.getName())
			);
		System.out.println("--------/Clients---------");
	}
	
	public void printAllCompanies(){
		System.out.println("In the database Companies:");
		List<Company> companies = myCompanyController.getAllCompany();
		companies.
			stream().
			forEach(
				company -> System.out.println
					("Company Name : " + company.getId() + " - " + company.getName())
			);
		System.out.println("--------/Companies---------");
	}
	
	
	public void printSelected() {
		System.out.println(" Selected: ");
		System.out.println(" Company : "+currentSelectedCompany.getName());
		System.out.println(currentSelectedCompany.getAddress());
		System.out.println("");
		System.out.println(" Client : "+currentSelectedClient.getName());
		System.out.println(currentSelectedClient.getCityResidence());
		System.out.println("");
		System.out.println(" Invoice : "+currentSelectedInvoice.getName());
		System.out.println(currentSelectedInvoice.getPrice());
		System.out.println(" -------/Selected---------- ");

	}

	
	// end prints !!!
	
	public Company getCurrentSelectedCompany() {
		return currentSelectedCompany;
	}

	public void setCurrentSelectedCompany(Company currentSelectedCompany) {
		this.currentSelectedCompany = currentSelectedCompany;
	}

	public Invoice getCurrentSelectedInvoice() {
		return currentSelectedInvoice;
	}

	public void setCurrentSelectedInvoice(Invoice currentSelectedInvoice) {
		this.currentSelectedInvoice = currentSelectedInvoice;
	}

	public Client getCurrentSelectedClient() {
		return currentSelectedClient;
	}

	public void setCurrentSelectedClient(Client currentSelectedClient) {
		this.currentSelectedClient = currentSelectedClient;
	}
	
	public void updateAllReferences() {
		updateCompanyReference();
		updateClientsReferences();
		updateInvoicesReferences();
	}
	
	
	
	
	private void updateCompanyReference() {
		if(companyInfo.getText().equals("info")) {
			if(this.getCompaniesCount()>0){
				companyInfo.setText(this.getSavedCompanies().get(0).getName());
			}
		}else {
			companyInfo.setText(this.getSavedCompanies().get(companyCounter).getName());
		}
		currentSelectedCompany=this.getSavedCompanies().get(companyCounter);
	}

	public void updateClientsReferences() {
		clientsList.removeAllItems();
		for(int i=0;i<this.getSavedClients().size();i++) {
			clientsList.addItem(this.getSavedClients().get(i).getName());
		}
	}
	
	public void updateInvoicesReferences() {
		invoicesList.removeAllItems();
		for(int i=0;i<this.getSavedInvoices().size();i++) {
			invoicesList.addItem(this.getSavedInvoices().get(i).getName());
		}
	}


	public void setClientsList(JComboBox clientsList) {
		this.clientsList = clientsList;
	}


	public void setInvoicesList(JComboBox invoicesList) {
		this.invoicesList = invoicesList;
	}


	public void setCompanyInfo(JLabel companyInfo) {
		this.companyInfo = companyInfo;
	}

	public int getCompanyCounter() {
		return companyCounter;
	}

	public void setCompanyCounter(int companyCounter) {
		this.companyCounter = companyCounter;
		this.updateCompanyReference();
	}

	
	
	
	
}