package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static final String DB_URL="jdbc:mysql://localhost:3306/hospital";
    private static final String USER="root";
    private static final String PASSWORD="26Thocto2003!";

	public static void main(String[] args) throws ClassNotFoundException{
		// TODO Auto-generated method stub
		//This try is For to check whether our java code i connected to MySql or not?
         try {
        	 Class.forName("com.mysql.cj.jdbc.Driver");
        	 //System.out.println("DataBase Connection Sucessful");
         }
         catch(ClassNotFoundException e) {
        	 e.printStackTrace();
         }
         Scanner sc = new Scanner(System.in);
         
         //This 2 try block to get coonected to Database through DriverManager.getConnection() with "URL","User","Password"
         //And we need to Create objects for Both Patient and Doctor Class
         try {
        	 Connection connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);
        	 Patient patient = new Patient(connection,sc);
        	 Doctor doctor = new Doctor(connection); 
        	 
        	 //To View the Main Menu and to be in Main Menu we need to use While(true)-always true because Uder must view the Main Menu
        	 while(true) {
        		 System.out.println(" \nHOSPITAL MANAGEMENT SYSTEM ");
        		 System.out.println("1.Add Patient");
        		 System.out.println("2.View Patient");
        		 System.out.println("3.View Doctor");
        		 System.out.println("4.Book Appointment");
        		 System.out.println("5.Exit");
        		 System.out.println("Enter Your Choice:");
        		 int ch=sc.nextInt();
        		 switch(ch) {
        		 case 1:
        			 //Add Patient
        			 patient.AddPatient();
        			 System.out.println();
        			 break;
        		 case 2:
        			 //View Patient
        			 patient.ViewPatients();
        			 System.out.println();
        			 break;
        		 case 3:
        			 //View Doctor
        			 doctor.ViewDoctor();
        			 System.out.println();
        			 break;
        		 case 4:
        			 //Book Appointment
        			 BookAppointment(patient,doctor,connection,sc);
        			 System.out.println();
        			 break;
        		 case 5:
        			 System.out.println("THANK YOU!! FOR USING HOSPITAL MANAGEMENT SYSTEM :) :)");
        			  return;
        		 default:
        			  System.out.print("Enter A Valid Choice :( :(");
        		 }
        		 
        	 }
         }
         catch(SQLException e) {
        	 e.printStackTrace();
         }
	}
	//This method is to Book Appointment with patient to doctor on patient wanted date,
	//Here we need to check the doctor and patient exists in Database or whether doctor or patient has already Appointment
	//Thats why we need to create the method with parameters to be passed that Connection And Scanner.
	//To check whether a paticular patient exists or not.With the help of getPatientId and it is in PatientClass.
	//So,we need to pass both patient and doctor classes objects as an arguments to Book Appointment
	public static void BookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner) {
                  System.out.println("Enter the PatientId:");
                  int patientid=scanner.nextInt();
                  System.out.println("Enter the DoctorId:");
                  int doctorid=scanner.nextInt();
                  System.out.println("Enter the Appointment Date(YYYY-MM-DD):");
                  String appointmentDate =scanner.next();
                  if(patient.getPatientById(patientid) && doctor.getDoctorById(doctorid)) {
                	 if(checkDoctorAvailibility(doctorid,appointmentDate,connection)) {
                		 String appointmentQuery = "INSERT INTO appointments (patient_id,doctor_id,appointment_Date) VALUES (?, ?, ?)";
                		 try {
                			 PreparedStatement preparedstatement=connection.prepareStatement(appointmentQuery);
                			 preparedstatement.setInt(1,patientid);
                			 preparedstatement.setInt(2,doctorid);
                			 preparedstatement.setString(3,appointmentDate);
                			 int rowsAffected=preparedstatement.executeUpdate();
                			 if(rowsAffected>0) {
                				 System.out.println("Appointment Booked :) :)");
                			 }
                			 else {
                				 System.out.println("Sorry,failed to book appointment :( :(");
                			 }
                		 }
                		 catch(SQLException e) {
                			 e.printStackTrace();
                		 }
                	 }
                	 else {
                		 System.out.println("Doctor is not available on this day :( :(");
                	 }
                	 
                  }
                  else {
                	  System.out.println("Either Doctor or Patient Does not Exists :( :( ");
                  }
      
	}
	public  static boolean checkDoctorAvailibility(int doctorId,String appointmentDate,Connection connection) {
		boolean flag=false;
		//Here SELECT COUNT(*) is to count No.of rows which meets the condition by Using WHERE
		String query="SELECT COUNT(*) FROM appointments WHERE doctor_Id=? AND appointment_Date=?";
		try {
			PreparedStatement preparestatement = connection.prepareStatement(query);
			preparestatement.setInt(1, doctorId);
			preparestatement.setString(2, appointmentDate);
			ResultSet result = preparestatement.executeQuery();
			if(result.next()) {
				int count=result.getInt(1);
				if(count==0) {
					flag=true;
				}
				else
					 return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
