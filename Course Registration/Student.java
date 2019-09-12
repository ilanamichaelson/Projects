import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User implements isStudent, Serializable{
	//initialize variables as private (getters and setters below)
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private ArrayList<Course> courses;
	public ArrayList<Student> comprehensiveStudentArrayStudent;
	
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	public Student() {}
	
	ArrayList<Course> studentCourse; 
	
	// set up necessary constructors
	
	public Student (ArrayList<Course> studentCourseArray) {
		// when I initialize a Student object in main, the ArrayList of data that is stored in 
		// courseArray gets passed in as an argument, and initialized in a new ArrayList for 
		// student called studentCourse
		super(studentCourseArray);
		this.studentCourse = studentCourseArray;
	}
	
	public Student(String firstNameConstructor, String lastNameConstructor) {
		this.firstName = firstNameConstructor;
		this.lastName = lastNameConstructor;
	}
	
	public Student(String firstName, String lastName, String userName, String Password, ArrayList<Course> studentCourseArray, ArrayList<Student> comprehensiveStudentArrayStudent) {
		super(studentCourseArray);
		this.studentCourse = studentCourseArray;
		this.comprehensiveStudentArrayStudent = comprehensiveStudentArrayStudent;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = Password;		
	}
	
	public Student(String username) {
		this.userName = username;
	}
	
	public Student(String firstName, String lastName, String userName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
	}
	
	@Override
	//displays all courses that are not already full
	public void viewAvailableNotFullCourses() {
		// iterate through each course, check if the number registered is equal to the maximum number allowed,
		// if they are not equal, print that course info
		for (int i=0; i<studentCourse.size(); i++) {
			if (studentCourse.get(i).getMaxStudents().equals(studentCourse.get(i).getCurrentRegistered())== false) {
				System.out.println(studentCourse.get(i).getName() + "," + studentCourse.get(i).getID() + "," + studentCourse.get(i).getCurrentRegistered() + "," + studentCourse.get(i).getMaxStudents());	
			}
		}
	}

	@Override
	// this function allows the student to register for a course
	public void register() {
		Scanner input = new Scanner (System.in);
		System.out.println("Enter course name: ");
		String courseName = input.nextLine();
		System.out.println("Enter course section: ");
		String courseSection = input.nextLine();
		// validate that student enters his/ her own name
		while (true) {
			System.out.println("Enter your first name: ");
			String firstName = input.nextLine();		
			System.out.println("Enter your last name: ");
			String lastName = input.nextLine();		
			if (!firstName.equals(this.firstName) || !lastName.equals(this.lastName)) {
				System.out.println("You can only register yourself. Please enter your own name.");
			}
			else {
				break;
			}
		}		
		Boolean courseExists = false;
		for (int i = 0; i<studentCourse.size(); i++) {
			if (studentCourse.get(i).getName().equals(courseName)&& studentCourse.get(i).getCourseSectionNumber().equals(courseSection)) {
				courseExists = true;
				//validate that the course is not already full
				if ((studentCourse.get(i).getMaxStudents()).equals(studentCourse.get(i).getCurrentRegistered())) {
					System.out.println("Course is full, so you cannot enter it. Sending you back to main menu.");
					System.out.println();
					System.out.println();
					break;
				}			
				else {			
					for (int j=0; j<studentCourse.get(i).getStudentList().size(); j++) {
						// validate that the student is not already enrolled in this course
						if (firstName.equals(studentCourse.get(i).getStudentList().get(j).getFirstName()) && lastName.equals(studentCourse.get(i).getStudentList().get(j).getLastName())) {
							System.out.println("You are already enrolled in this course. Sending you back to main menu.");
							return;
						}
					}				
					//student passed all validation, add name to student list
					studentCourse.get(i).getStudentList().add(new Student(firstName, lastName));
					// when you add a student to a course, need to also update the number of students registered
					// in addition to the actual list. (it is in the ArrayList as a string, so get the string, parse
					// to int, update the number, and change back to string)
					int intVersion = Integer.parseInt(studentCourse.get(i).getCurrentRegistered());				
					intVersion++;
					String stringVersion = Integer.toString(intVersion);
					studentCourse.get(i).setCurrentRegistered(stringVersion);
					
					System.out.println("Course successfully added!");
					System.out.println();
					System.out.println();
					break;
				}							
			}		
		}	
		// if the course does not exist...
		if (!courseExists) {
			System.out.println("Course does not exist. Sending you back to main menu.");
			System.out.println();
			System.out.println();
		}
	}
	// this function allows student to withdraw from a course
	public void withdraw() {
		Scanner input = new Scanner (System.in);
		System.out.println("Enter course name: ");
		String courseName = input.nextLine();
		System.out.println("Enter course section: ");
		String courseSection = input.nextLine();
		// validate that student is entering his/her own name
		while (true) {
			System.out.println("Enter your first name: ");
			String firstName = input.nextLine();
			System.out.println("Enter your last name: ");
			String lastName = input.nextLine();
			if (!firstName.equals(this.firstName) || !lastName.equals(this.lastName)) {
				System.out.println("You can only withraw yourself. Please enter your own name.");
			}
			else {
				break;
			}
		}
		Boolean courseExists = false;
		for (int i = 0; i<studentCourse.size(); i++) {
			if (studentCourse.get(i).getName().equals(courseName)&& studentCourse.get(i).getCourseSectionNumber().equals(courseSection)) {
				courseExists = true;
				boolean studentEnrolled = false;
				for (int j=0; j<studentCourse.get(i).getStudentList().size(); j++) {
					// if the students first and last name matches a name on the list... 
					if (firstName.equals(studentCourse.get(i).getStudentList().get(j).getFirstName()) && lastName.equals(studentCourse.get(i).getStudentList().get(j).getLastName())) {
						studentEnrolled = true;
						// ...then remove this student's name from the list
						studentCourse.get(i).getStudentList().remove(j);
						// when you remove a student from a course, need to also update the number of students registered
						// in addition to the actual list. (it is in the ArrayList as a string, so get the string, parse
						// to int, update the number, and change back to string)
						int intVersion = Integer.parseInt(studentCourse.get(i).getCurrentRegistered());					
						intVersion--;
						String stringVersion = Integer.toString(intVersion);
						studentCourse.get(i).setCurrentRegistered(stringVersion);
						
						System.out.println("Successfully withdrawn from course!");
						break;
					}
				}
				// validate that student is enrolled in the course
				if (!studentEnrolled) {
					System.out.println("You are not enrolled in this course so you cannot withdraw.");
				}
				break;
			}
		}
		// if the course does not exist...
		if (!courseExists) {
			System.out.println("Course does not exist. Sending you back to main menu.");
			System.out.println();
			System.out.println();
		}
	}
	
	//set up getters and setters for all the private variables that were initialized at the top of this list	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
}
