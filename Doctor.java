package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {
	 private Connection connection;
     public Doctor(Connection connection) {
    	 this.connection=connection;
     }
     
     
     //Adding ViewPatientsMethod()
       public void ViewDoctor() {
    	   try {
    		   String query ="SELECT *FROM doctors";
    		   PreparedStatement preparedStatement = connection.prepareStatement(query);
    		   ResultSet result=preparedStatement.executeQuery();
    		   System.out.println("Doctors:");
    		   System.out.println("+------------+--------------------------------+----------------+");
    		   System.out.println("| Doctor ID  | Name                           | Specialization |");
    		   System.out.println("+------------+--------------------------------+----------------+");
    		   while(result.next()) {
    			   int id=result.getInt("id");
    			   String Name=result.getString("name");
    			   String Specialization=result.getString("specialization");
    			   System.out.printf("| %-10s | %-30s | %-14s |\n",id,Name,Specialization);
    			   System.out.println("+------------+--------------------------------+----------------+");
    		   }
    		   
       }
    	   catch(SQLException e) {
                   e.printStackTrace();    		   
    	   }
    	   
       }
     //Check doctor by Using getDoctorBtId() Method
       public boolean getDoctorById(int id) {
    	   String query="SELECT * FROM doctors WHERE id=?";
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
