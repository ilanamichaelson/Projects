import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class datastructurehw1 implements Serializable{
	private static final String String = null;

	public static void main (String[] args) throws IOException{
		
		// create the file objects that hold each of the array lists that are going to be serialized
		File serializedStudents = new File("serializedStudentArrayLists.ser");
		File serializedStudents2 = new File("serializedStudentArrayList2.ser");
		File serializedCourses = new File("serializedCourses.ser");
		// These are the three array lists used in this program that are going to be serialized. Initializing
		// them before entering into if statement, so that the compiler knows that whether or not there is are 
		// existing serialized files, these lists exist in the program
		
		ArrayList<Student> comprehensiveStudentArray;
		ArrayList<Student> arrayListStudents;
		ArrayList<Course> courseArray;
			
		// enter this if statement that deserializes/ retrieves old data if the serialized
		// lists exist/ these files exist- i.e. if this is not the first time someone
		// is entering data into the system
		if (serializedStudents.exists() && serializedStudents2.exists() && serializedCourses.exists()){
			
		      comprehensiveStudentArray= new ArrayList<Student>();
		      arrayListStudents = new ArrayList<Student>();
		      courseArray = new ArrayList<Course>();
		      
		      // deserialize the comprehensive array list of every student that is registered in the system
		       try
		        {
		            FileInputStream fis = new FileInputStream("serializedStudentArrayLists.ser");
		            ObjectInputStream ois = new ObjectInputStream(fis);
		            comprehensiveStudentArray = (ArrayList) ois.readObject();
		            ois.close();
		            fis.close();
		         }catch(IOException ioe){
		             ioe.printStackTrace();
		             return;
		          }catch(ClassNotFoundException c){
		             System.out.println("Class not found");
		             c.printStackTrace();
		             return;
		          }
		    // deserialize the array list of students registered in every given course     
			try
			 {
		            FileInputStream fis = new FileInputStream("serializedStudentArrayList2.ser");
		            ObjectInputStream ois = new ObjectInputStream(fis);
		            arrayListStudents = (ArrayList) ois.readObject();
		            ois.close();
		            fis.close();
		         }catch(IOException ioe){
		             ioe.printStackTrace();
		             return;
		          }catch(ClassNotFoundException c){
		             System.out.println("Class not found");
		             c.printStackTrace();
		             return;
		          }
			// deserialize the list of all the courses.
			try
			 {
		            FileInputStream fis = new FileInputStream("serializedCourses.ser");
		            ObjectInputStream ois = new ObjectInputStream(fis);
		            courseArray = (ArrayList) ois.readObject();
		            ois.close();
		            fis.close();
		         }catch(IOException ioe){
		             ioe.printStackTrace();
		             return;
		          }catch(ClassNotFoundException c){
		             System.out.println("Class not found");
		             c.printStackTrace();
		             return;
		          }	          	
		}
		
		// if this is the first time entering into the system- i.e. the serialized files don't exist, i.e.
		// we want to read from the CSV- then enter into this else block.
		else {
			// set up buffered reader to read in from data file
			File file = new File("src/MyUniversityCourses.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			
			String fileContainer = "";
			reader.readLine();
			fileContainer = reader.readLine();
			// initialize an array list of type course that will hold all the data being read in
			courseArray = new ArrayList<Course>();
			// while the next line is not empty...
			while (fileContainer != null){
				// read through that line, split it up by commas, and pass in that
				// data as arguments to create a new course object. 
				String line = fileContainer;
				String lineSplit[] = fileContainer.split(",");
				//(for the list of students can just pass in new ArrayList bc will always start out empty)
				Course courseOne = new Course (lineSplit[0], lineSplit[1], lineSplit[2], lineSplit[3], new ArrayList<Student>(), lineSplit[5], lineSplit[6], lineSplit[7]);
				// add the new course object to the array list
				courseArray.add(courseOne);
				fileContainer = reader.readLine();
			}
			
			// create an ArrayList that will hold student objects for each given course
			arrayListStudents = new ArrayList<Student>();
			comprehensiveStudentArray = new ArrayList<Student>();
		}
		
		while (true) {
		String userType;
		// check if the user is a student or an admin
		while (true){
			System.out.println("Enter your status: 'student' or 'admin'. To exit, enter 'exit'");
			Scanner input = new Scanner(System.in);
			String userInput = input.nextLine();
			// validate that the user input 'student', 'admin', or 'exit'.
			if (userInput.equals("student") || userInput.equals("admin") || userInput.equals("exit")) {
				userType = userInput;
				break;
			}
			else {
				System.out.println("Invalid status, try again");
			}	
		}
		
		if (userType.equals("admin")) {
			// admin's username and password are/ must be Admin and Admin001 (validate this)
			while (true){
				// validate username
				System.out.println("Enter your username: ");
				Scanner input = new Scanner(System.in);
				String userName = input.nextLine();
				if (userName.equals("Admin")) {
					break;
				}
				else {
					System.out.println("Invalid username, try again");
				}
			}
			while (true) {
				// validate password
				System.out.println("Enter your password: ");
				Scanner input = new Scanner (System.in);
				String password = input.nextLine();
				if (password.equals("Admin001")) {
					System.out.println("Welcome!");
					break;
				}
				else {
					System.out.println("Invalid password, try again");
				}				
			}
			
			while (true) {
				//display menu for admin
				System.out.println("What would you like to do?");
				System.out.println("To create a class, enter '1'");
				System.out.println("To delete a class, enter '2'");
				System.out.println("To edit the property of a class, enter '3'");
				System.out.println("To display information for a given course, enter '4'");
				System.out.println("To register a student, enter '5'");
				System.out.println("To view all courses, enter '6'");
				System.out.println("To view all courses that are full, enter '7'");
				System.out.println("To write to a file the list of courses that are full, enter '8'");
				System.out.println ("To view the names of the students being registered in a specific course, enter '9'");
				System.out.println("To view the list of courses that a given student is registered in, enter '10'");
				System.out.println("To sort courses based on the current number of students registered, enter '11'");
				System.out.println("To view all the students registered in the system and their username and password, enter '12'");
				System.out.println("To exit, enter '13'");
				Scanner input = new Scanner(System.in);
				String entry = input.nextLine();
				// create new admin object (pass in courseArray and comprehensiveStudentArray as arguments so that the admin class/ 
				// object can access these array lists)
				Admin admin = new Admin(courseArray, comprehensiveStudentArray);
				// call the corresponding admin function, based on what number the admin enters/
				// what the admin said they want to do
				if (entry.equals("1")) {
					admin.createNewCourse();			
				}
				
				else if (entry.equals("2")) {
					admin.deleteCourse();
				}
				
				else if (entry.equals("3")) {
					admin.editCourse();
				}
				
				else if (entry.equals("4")) {
					admin.displayCourseInfo();
				}
				
				else if (entry.equals("5")) {
					admin.registerAStudent();
				}
				
				else if (entry.equals("6")) {
					admin.viewAllCourses();
				}
				
				else if (entry.equals("7")) {
					admin.viewFullCourses();
				}
				
				else if (entry.equals("8")) {
					admin.logFullCourses();
				}
				
				else if (entry.equals("9")) {
					admin.logRegisteredStudentsInACourse();
				}
				
				else if (entry.equals("10")) {
					admin.viewRegisteredCourseForStudent();
				}
				
				else if (entry.equals("11")) {
					admin.sort();
				}
				
				else if (entry.equals("12")) {
					admin.viewAllRegisteredStudents();
				}
				// if the admin wants to exit, they will be sent back to the original prompt that
				// asks the user if they are an admin, student, or wish to exit the program entirely
				else if (entry.equals("13")) {
					System.out.println("Goodbye!");
					break;
				}
				// if their input was not valid
				else {
					System.out.println("Not a valid entry, try again.");
					System.out.println();
					System.out.println();
				}		
			}
		}
		
		// if the user is a student
		else if (userType.equals("student")) {
			// going to initialize a student object that has these four fields
			String userName;
			String password;
			String firstName;
			String lastName;
					
			System.out.println("Enter your username: ");
			Scanner input = new Scanner(System.in);
			userName = input.nextLine();
		
			System.out.println("Enter your password: ");
			password = input.nextLine();		
			
			System.out.println("What is your first name?");
			firstName = input.nextLine();
						
			System.out.println("What is your last name?");
			lastName = input.nextLine();				
				
			// create a new student, passing in full name username and password, and the two relevant array lists
			Student student = new Student (firstName, lastName, userName, password, courseArray, comprehensiveStudentArray);
			
			// a student can only be entered into the system by an admin. Therefore, if the student is not already on the list of 
			// students created by the admin, they cannot enter the program. When the admin creates a student, each student is assigned
			// a username and password with their name. If the combination of the full name, user name, and password are not found in 
			// the list of students that the administrator has registered, the student is not allowed to enter the system.
			
			// A student's username and password is determined by the order in which the student was registered by the admin.
			// If, for example, student John Smith was registered first, his username would be Student1 and password would be
			// Student001. Jane Doe, registered second, would be Student2; password Student002, etc.
			
			Boolean registered = false;
			// check if the student is registered in the program, by iterating through the comprehensive list of all students
			// registered in the system, and looking for the corresponding username, password, first name, and last name.
			for (int i=0; i<comprehensiveStudentArray.size(); i++) {							
				if (((student.getFirstName()).equals(comprehensiveStudentArray.get(i).getFirstName()))
						&& ((student.getLastName()).equals(comprehensiveStudentArray.get(i).getLastName()))
						&&((student.getPassword()).equals(comprehensiveStudentArray.get(i).getPassword()))
						&&((student.getUserName()).equals(comprehensiveStudentArray.get(i).getUserName()))){
					System.out.println("Welcome!");
					registered = true;
				}			
			}
			
			// if the student is not registered in the system, return to original prompt
			if (!registered) {
				System.out.println("Sorry, you are not registered as a student in this system. Exiting...");
				System.out.println();
			}
			
			while (registered) {
				//display menu for student
				System.out.println("What would you like to do?");
				System.out.println("To view all courses, enter '1'");
				System.out.println("To view all courses that are available and not full, enter '2'");
				System.out.println("To register for a course, enter '3'");
				System.out.println("To withdraw from a course, enter '4'");
				System.out.println("To view all courses that you are registered in, enter '5'");
				System.out.println("To exit, enter '6'");
				String entry = input.nextLine();	
				
				// call the corresponding student function, based on what number the student enters/
				// what the student said they want to do
				if (entry.equals("1")) {
					student.viewAllCourses();
				}
				
				else if (entry.equals("2")){
					student.viewAvailableNotFullCourses();
				}
				
				else if (entry.equals("3")) {
					student.register();
				}
				
				else if (entry.equals("4")) {
					student.withdraw();
				}
				
				else if (entry.equals("5")){
					student.viewRegisteredCourseForStudent();
				}
				// if the student wants to exit, they will be sent back to the original prompt that
				// asks the user if they are an admin, student, or wish to exit the program entirely
				else if (entry.equals("6")) {
					System.out.println("Goodbye!");
					break;
				}
				
				else {
					System.out.println("Not a valid entry, try again.");
				}	
			}
		}
		
		// if the user wants to exit the system entirely...
		else {
			System.out.println("Goodbye!");
			
			// serialize the information that the user has entered before exiting the program
			 
			// the first ArrayList that needs to be serialized is the one that contains the
			// comprehensive list of all the students in the system. Store this in a1.
			ArrayList<Student> a1=new ArrayList<Student>();	
			 
			// iterate through comprehensiveStudentArray, adding each element to a1
			 for (int i=0; i<comprehensiveStudentArray.size(); i++) {
				 a1.add(comprehensiveStudentArray.get(i));
			 }
			 
			// write this information to the file serializedStudentArrayLists.ser
		    try{
		         FileOutputStream fos= new FileOutputStream("serializedStudentArrayLists.ser");
		         ObjectOutputStream oos= new ObjectOutputStream(fos);
		         oos.writeObject(a1);
		         oos.close();
		         fos.close();
		       }catch(IOException ioe){
		            ioe.printStackTrace();
		       }
			 
		    // the second ArrayList that needs to be serialized is the one that contains the
		 	// list of students registered in every given course. Store this in a2.
			 ArrayList<Student> a2 = new ArrayList<Student>();	
			 
			// iterate through arrayListStudents, adding each element to a2
			 for (int i =0; i<arrayListStudents.size(); i++) {
				a2.add(arrayListStudents.get(i));
			 }
			 
			// write this information to the file serializedStudentArrayList2.ser
			 try{
		         FileOutputStream fos= new FileOutputStream("serializedStudentArrayList2.ser");
		         ObjectOutputStream oos= new ObjectOutputStream(fos);
		         oos.writeObject(a2);
		         oos.close();
		         fos.close();
		       }catch(IOException ioe){
		            ioe.printStackTrace();
		       }		 
			
			// the third ArrayList that needs to be serialized is the one that contains
			// all the courses in the system. Store this in a3.
		     ArrayList<Course> a3 = new ArrayList<Course>();
		     
		     // iterate through the courseArray, adding each element to a3
		     for (int i=0; i<courseArray.size(); i++) {
		    	 	a3.add(courseArray.get(i));
		     }
		     
		   // write this information to the file serializedCourse.ser
		     try{
		         FileOutputStream fos= new FileOutputStream("serializedCourses.ser");
		         ObjectOutputStream oos= new ObjectOutputStream(fos);
		         oos.writeObject(a3);
		         oos.close();
		         fos.close();
		       }catch(IOException ioe){
		            ioe.printStackTrace();
		       }				     
		     
		       return;     
		   }
		}
	}
}

