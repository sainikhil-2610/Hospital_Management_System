package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	 private Connection connection;
     private Scanner scanner;
     public Patient(Connection connection,Scanner scanner) {
    	 this.connection=connection;
    	 this.scanner=scanner;
     }
     
     //AddPatient() Method 
     public void AddPatient() {
    	 System.out.println("Enter Patient Name:");
    	 String  name = scanner.nextLine();
    	 System.out.println("Enter the Patient Age:");
    	 int age = scanner.nextInt();
    	 System.out.println("Enter the Patient Gender:");
    	 String gender = scanner.next();
    	 
    	 
    	 try{
    		String query="INSERT INTO patients (name,age,gender) VALUES (?, ?, ?)";
    		PreparedStatement preparedStatement = connection.prepareStatement(query);
    		preparedStatement.setString(1,name);
    		preparedStatement.setInt(2,age);
    		preparedStatement.setString(3,gender);
    		int affectedRows = preparedStatement.executeUpdate();
    		if(affectedRows>0)
    			 System.out.print("Patient Added Successfully!!");
    		else
    			 System.out.print("Fail to add patient");
    		
    	 }
    	 catch(SQLException e) {
    		 e.printStackTrace();}
    	 
    	 }
     
     //Adding ViewPatientsMethod()
       public void ViewPatients() {
    	   try {
    		   String query ="SELECT *FROM patients";
    		   PreparedStatement preparedStatement = connection.prepareStatement(query);
    		   ResultSet result=preparedStatement.executeQuery();
    		   System.out.println("Patients:");
    		   System.out.println("+------------+--------------------------------+-----+----------+");
    		   System.out.println("| Patient ID | Name                           | Age | Gender   |");
    		   System.out.println("+------------+--------------------------------+-----+----------+");
    		   while(result.next()) {
    			   int id=result.getInt("id");
    			   String Name=result.getString("name");
    			   int age=result.getInt("age");
    			   String Gender=result.getString("gender");
    			   System.out.printf("| %-10s | %-30s | %-3s | %-8s |\n",id,Name,age,Gender);
    			   System.out.println("+------------+--------------------------------+-----+----------+");
    		   }
    		   
       }
    	   catch(SQLException e) {
                   e.printStackTrace();    		   
    	   }
       }
       //Checking Patient By Creating getPatientMethod() By Using PatientId
       public boolean getPatientById(int id) {
    	   String query="SELECT * FROM Patients WHERE id=?";
    	   boolean flag = false;
    	   try {
    	   PreparedStatement prepareStatement = connection.prepareStatement(query);
    	   prepareStatement.setInt(1,id);
    	   ResultSet result =prepareStatement.executeQuery();
    	   //ResultSet is an interface and created result instance will hold the row which is matched with the id
              if(result.next()) 
            	    flag= true;
              else 
            	   return flag;
            
       }
    	   catch(SQLException e) {
    		e.printStackTrace();   
    	   }
		return flag;
       }
     
	

}
