import java.util.ArrayList;
import java.util.Scanner;

public class User {
	private ArrayList<Course> courses;
	public User() {
		ArrayList<Student> arrayListStudents = new ArrayList<Student>();
	}
	
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	ArrayList<Course> studentCourse; 
	// all the course data is currently in the main method, stored in an ArrayList called courseArray.
	// when a new user object is initialized, courseArray is passed in as an argument, and all its
	// data gets stored in an ArrayList in the user class called student course. (Array List of type course)
	public User (ArrayList<Course> studentCourseArray) {
		this.studentCourse = studentCourseArray;
	}
	
	// returns list of courses that any given student is registered for.
	// this function is in user so that it can be accessible to both
	// students and administrators.
	public void viewRegisteredCourseForStudent() {
		System.out.println("Enter your/student's first name");
		Scanner input = new Scanner(System.in);
		String firstName = input.nextLine();
		System.out.println("Enter your/ student's last name");
		String lastName = input.nextLine();
		// for this particular function, even if a student is calling the function, and they do not 
		// enter their own name, they can still see if the name they enter is registered for any courses.
		// this is because as long as they are not doing any actions- like actually registering or
		// withdrawing- there is no harm in them having access to this information.
		Boolean studentValidation = false;
		for (int i = 0; i<studentCourse.size(); i++) {
			for (int j = 0; j<studentCourse.get(i).getStudentList().size(); j++) {
				// iterate through every student and every course list and if there is a match to the name of the student that was
				// input, print the name of that course
				if (firstName.equals(studentCourse.get(i).getStudentList().get(j).getFirstName()) && lastName.equals(studentCourse.get(i).getStudentList().get(j).getLastName())) {
					System.out.println(studentCourse.get(i).getName());
					studentValidation = true;
				}
			}
		}
		// validate that student is registered for any courses at all
		if (!studentValidation) {
			System.out.println(firstName + " " + lastName +  " is not registered for any courses.");
		}
	}	
	// this function is in user so that it can be accessible to both
	// students and administrators.
	// displays all courses
	public void viewAllCourses(){
		for (int i = 0; i<studentCourse.size(); i++)
			System.out.println(studentCourse.get(i).getName() + "," + studentCourse.get(i).getID() + "," + studentCourse.get(i).getCurrentRegistered() + "," + studentCourse.get(i).getMaxStudents());	
	}
}
