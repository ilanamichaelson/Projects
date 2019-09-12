import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Comparable<Course>, Serializable{
	// initialize all eight fields (to private)
	private String name;
	private String id;
	private String maxStudents;
	private String currentRegistered;	
	private ArrayList<Student> studentList;
	private String instructorName;
	private String courseSectionNumber;
	private String courseLocation; 
	
	// constructor for creating new course object
	public Course(String name, String id, String maxStudents, String currentRegistered, ArrayList<Student> studentList, String instructorName, String courseSectionNumber, String courseLocation){
		this.name = name;
		this.id = id;
		this.maxStudents = maxStudents;
		this.currentRegistered = currentRegistered;
		this.studentList = studentList;
		this.instructorName = instructorName;
		this.courseSectionNumber = courseSectionNumber;
		this.courseLocation = courseLocation;	
	}
	
	public String toString() {
		String s = String.format("\n %s,%s, %s, %s, %s, %s, %s, %s", name, id, maxStudents, currentRegistered, studentList, instructorName, courseSectionNumber, courseLocation );
		return s;
	}

	// getters and setters for each field
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getMaxStudents() {
		return maxStudents;
	}
	
	public void setMaxStudents(String maxStudents) {
		this.maxStudents = maxStudents;
	}
	
	public String getCurrentRegistered() {
		return currentRegistered;
	}
	
	public void setCurrentRegistered(String currentRegistered) {
		this.currentRegistered = currentRegistered;
	}
	
	public ArrayList<Student> getStudentList() {
		return studentList;
	}
	
	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}
	
	public String getInstructorName() {
		return instructorName;
	}
	
	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}
	
	public String getCourseSectionNumber() {
		return courseSectionNumber;
	}
	
	public void setCourseSectionNumber(String courseSectionNumber) {
		this.courseSectionNumber = courseSectionNumber;
	}
	
	public String getCourseLocation() {
		return courseLocation;
	}
	
	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
	}
	
	// compare size of every student list, returning 1, 0, or -1 (will need this for sort method)
	@Override
	public int compareTo (Course course) {
		if (this.getStudentList().size() > course.getStudentList().size()){
			return 1;
		}
		else if (this.getStudentList().size() < course.getStudentList().size()) {
			return -1;
		}
		else {
			return 0;
		}
	}
}