import java.io.FileNotFoundException;
// interface for the Admin class
//for details about each function, see comments in Admin Class
public interface isAdmin {		
		public void createNewCourse();
		public void deleteCourse();
		public void editCourse();		
		public void displayCourseInfo();
		public void registerAStudent();
		public void viewFullCourses();
		public void logFullCourses() throws FileNotFoundException;
		public void logRegisteredStudentsInACourse();
		public void viewAllRegisteredStudents();
		public void sort();
}
