import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.Serializable;

	public class Admin extends User implements isAdmin, Serializable{

		ArrayList<Course> adminCourse;
		ArrayList<Student> studentList;
		ArrayList<Student> comprehensiveStudentArrayAdmin;
		// all the course data is currently in the main method, stored in an ArrayList called courseArray.
		// when a new admin object is initialized, courseArray is passed in as an argument to the constructor, and all its
		// data gets stored in an ArrayList in the admin class called adminCourseArray.
		public Admin (ArrayList<Course> adminCourseArray) {
			// call super/ user constructor
			super(adminCourseArray);
			this.adminCourse = adminCourseArray;			
			this.studentList = new ArrayList<Student>();
		}
		
		// view comment above previous constructor- same as idea, just for array lists comprehensiveStudentArray and arrayListStudents
		public Admin (ArrayList<Course> adminCourseArray, ArrayList<Student> comprehensiveStudentArrayAdmin) {
			// call super/ user constructor
			super(adminCourseArray);
			this.adminCourse = adminCourseArray;
			this.comprehensiveStudentArrayAdmin = comprehensiveStudentArrayAdmin;		
		}
		
		@Override
		// this function prints for the admin the list of courses that are full. 
		public void viewFullCourses() {
			// iterate through all the courses
			for (int i=0; i<adminCourse.size(); i++) {
				// check if the number of students registered for each course is equal to the maximum number of students allowed
				if (adminCourse.get(i).getMaxStudents().equals(adminCourse.get(i).getCurrentRegistered())) {
					// if so, print the course's information
					System.out.println("Course name: " + adminCourse.get(i).getName());
					System.out.println("Course ID: " + adminCourse.get(i).getID());
					System.out.println("Maximum number of students allowed: " + adminCourse.get(i).getMaxStudents());
					System.out.println("Current number of students registered: " + adminCourse.get(i).getCurrentRegistered());
					System.out.println("Students enrolled: ");	
					// since this parameter is an array list, need to iterate through
					for (int j = 0; j<adminCourse.get(i).getStudentList().size(); j++) {
						System.out.print(adminCourse.get(i).getStudentList().get(j).getFirstName() + " ");
						System.out.println(adminCourse.get(i).getStudentList().get(j).getLastName() + " ");						
					}					
					System.out.println("Instructor: " + adminCourse.get(i).getInstructorName());
					System.out.println("Section number: " + adminCourse.get(i).getCourseSectionNumber());
					System.out.println("Location: " + adminCourse.get(i).getCourseLocation());		
				}
			}
		}

		@Override
		//creates a new course
		public void createNewCourse() {
			Scanner input = new Scanner(System.in);
			System.out.println("Enter name of course you want to add: ");
			String courseName = input.nextLine();
			System.out.println("Enter ID of course you want to add: ");
			String courseID = input.nextLine();
			// validate that the user enters an integer for max number of students allowed
			String maxStudent;
			while (true) {
				System.out.println("Enter max number of students allowed in course: ");
				maxStudent = input.nextLine();
				
				try {
					Integer.parseInt(maxStudent);
					String.valueOf(maxStudent);
					break;
				}
				catch(NumberFormatException e) {
					System.out.println("Maximum students allowed must be an integer. Try again.");
				}
			}		
			// assumption that there are 0 students registered if the course is just being created now
			String currentRegistered = "0";
			System.out.println("Enter name of instructor: ");
			String instructorName = input.nextLine();
			//validate that user enters a number for course section
			String courseSectionNumber;
			while (true) {
				System.out.println("Enter course section number: ");
				courseSectionNumber = input.nextLine();
				
				try {
					Integer.parseInt(courseSectionNumber);
					String.valueOf(courseSectionNumber);
					break;
				}
				catch(NumberFormatException e) {
					System.out.println("Course section must be an integer. Try again.");
				}
				
			}
			// validate that this course does not already exist
			// assumption that no two courses can have the same ID and section number. Determining whether there is a duplicate course
			// goes based off these two fields.
			for (int i=0; i<adminCourse.size(); i++) {
				if (adminCourse.get(i).getID().equals(courseID)&& adminCourse.get(i).getCourseSectionNumber().equals(courseSectionNumber)) {
					System.out.println("A course with this ID and section number already exists; no two courses can share those two properties.");
					System.out.println("Sending you back to main menu.");
					System.out.println();
					return;
				}
			}
			
			System.out.println("Enter course location: ");
			String courseLocation = input.nextLine();
			//initialize new course object given all the data that was just entered
			Course newCourse = new Course(courseName, courseID, maxStudent, currentRegistered, new ArrayList<Student>(), instructorName, courseSectionNumber, courseLocation);
			// add this new course to adminCourse array list
			adminCourse.add(newCourse);
			System.out.println("Course successfully added");
			System.out.println();
		}

		@Override
		//deletes an existing course
		public void deleteCourse() {
			Scanner input = new Scanner (System.in);
			System.out.println("Enter course ID: ");
			String deletedCourseID = input.nextLine();
			System.out.println("Enter section number: ");
			String deletedSectionNumber = input.nextLine();
			//validate that the admin inputs a course that exists
			Boolean enteredValidCourse = false;
			for (int i=0; i<adminCourse.size(); i++) {
				// iterate through the courses, if one has a matching ID and section number...
				// (again working under assumption that every course has unique ID and section number, therefore
				// those are the fields that we iterate through in order to find the one that should be deleted.)
				if (adminCourse.get(i).getID().equals(deletedCourseID)&& adminCourse.get(i).getCourseSectionNumber().equals(deletedSectionNumber)) {
					//...remove that course object from the ArrayList
					adminCourse.remove(i);
					System.out.println("Course successfully deleted!");
					System.out.println();
					enteredValidCourse = true;
				}
			}
			// if the course does not exist, user gets sent back to main menu
			if (!enteredValidCourse) {
				System.out.println("That course does not exist. Sending you back to main menu.");
				System.out.println("");
				System.out.println("");
			}
		}

		@Override
		// allows admin to edit maximum number of students allowed in a course, instructor name, course section number,
		// course location, and the list of students registered in the course
		public void editCourse() {
			// ask the user which course they what to edit
			Scanner input = new Scanner (System.in);
			System.out.println("Enter course ID: ");
			String editedCourseID = input.nextLine();
			System.out.println("Enter section number: ");
			String editedSectionNumber = input.nextLine();
			// use this var to validate that the user entered an existent course
			Boolean enteredValidCourse = false;	
			for (int i=0; i<adminCourse.size(); i++) {
				if (adminCourse.get(i).getID().equals(editedCourseID)&& adminCourse.get(i).getCourseSectionNumber().equals(editedSectionNumber)) {
					enteredValidCourse = true;
				}
			}
			// if they entered a course that does not exist, send them back to main menu
			if (!enteredValidCourse) {
				System.out.println("That course does not exist. Sending you back to main menu.");
				System.out.println("");
				System.out.println("");
				return;
			}
			String propertyIndex;
			while (true) {		
				while (true) {
					
					System.out.println("To edit maximum number of students allowed: enter '1'");
					System.out.println("To edit instructor name: enter '2'");	
					System.out.println("To edit course section number: enter '3'");
					System.out.println("To edit course location: enter '4'");
					System.out.println("To add or remove a student from the course, enter '5'");
					
					// validate that the user input properly
					propertyIndex = input.nextLine();
					
					try {
						Integer.parseInt(propertyIndex);
						String.valueOf(propertyIndex);
						break;
					}
					catch(NumberFormatException e) {
						System.out.println("Invalid entry, try again.");
					}
					
				}
				// if the user did not enter a proper number...
				if (!propertyIndex.equals("1") && !propertyIndex.equals("2") && !propertyIndex.equals("3")
						&& !propertyIndex.equals("4") && !propertyIndex.equals("5")){
					System.out.println("Invalid entry, try again");
					System.out.println();
				}
				else {
					break;
				}
			}
			
			for (int i=0; i<adminCourse.size(); i++) {
				// iterate through the array of courses (adminCourse) to find the course that the admin wants to edit
				if (adminCourse.get(i).getID().equals(editedCourseID)&& adminCourse.get(i).getCourseSectionNumber().equals(editedSectionNumber)) {
					if (propertyIndex.equals("1")) {
						Boolean valid = false;
						while (!valid) {
							System.out.println("What would you like the new maximum number of students to be?");
							String newVersionProperty = input.nextLine();
							try {
								Integer.parseInt(newVersionProperty);
								String.valueOf(newVersionProperty);
								valid = true;
							}
							catch(NumberFormatException e) {
								System.out.println("This property must be an integer. Try again.");
							}
							// reset the maximum number of students allowed
							// if the administrator enters a number, and there are already more students registered
							// in this course than the new maximum allows, the maximum number is changed, but all students
							// are allowed to remain in the course. It is not fair to kick a student out of a course when 
							// they are already enrolled, so all students get to stay in the course, but the admin still gets to
							// change the maximum for the future.
							if (valid) {
								adminCourse.get(i).setMaxStudents(newVersionProperty); 	
								System.out.println("Course student maximum successfully edited!");
								System.out.println();
							}
						}
					}
					if (propertyIndex.equals("2")) {
						System.out.println("Who would you like the new instructor to be?");
						String newVersionProperty = input.nextLine();
						// reset the instructor name
						adminCourse.get(i).setInstructorName(newVersionProperty);	
						System.out.println("Course instructor name successfully edited!");
						System.out.println();
					}
					if (propertyIndex.equals("3")) {
						Boolean valid = false;
						while (!valid) {
							System.out.println("What would you like the new course section number to be?");
							String newVersionProperty = input.nextLine();
							// course section number must be an integer- validate that user entered an integer
							try {
								Integer.parseInt(newVersionProperty);
								String.valueOf(newVersionProperty);
								valid= true;
								
							}
							catch(NumberFormatException e) {
								System.out.println("This property must be an integer. Try again.");
							}
							
							if (valid) {
								// reset the course section number
								adminCourse.get(i).setCourseSectionNumber(newVersionProperty);		
								System.out.println("Course section number successfully edited!");
								System.out.println();
							}
						}

					}
					
					if (propertyIndex.equals("4")) {
						System.out.println("What would you like the new course location to be?");
						String newVersionProperty = input.nextLine();
						adminCourse.get(i).setCourseLocation(newVersionProperty);
						// reset the course location
						System.out.println("Course location successfully edited!");
						System.out.println();
					}
					
					if (propertyIndex.equals("5")) {
						while (true) {
					
							System.out.println("Would you like to add or remove a student from this course? Enter 'add' or 'remove', or 'return' to return to the main menu.");
							String addOrRemove = input.nextLine();
							if (addOrRemove.equals("return")) {
								// if the user input 'return'...
								System.out.println("Returning you to the main menu...");
								System.out.println();
								break;
							}
							if (addOrRemove.equals("add")) {
								// ask the admin for the information of the student they would like to register. The admin is required
								// to enter not only the students full name, but also the students username and password, since registering
								// a student for a course is a significant action, so the admin must provide all this information
								System.out.println("Enter the first name of the student you would like to add: ");
								String fName = input.nextLine();
								System.out.println("Enter the last name of the student you would like to add:  ");
								String lName = input.nextLine();
								System.out.println("Enter the user name of the student you would like to add: ");
								String uName = input.nextLine();
								System.out.println("Enter the password of the student you would like to add: ");
								String pWord = input.nextLine();			

								// create a student object with this information so that you can iterate through and make sure the student is registered in the system.
			
								Student studentByAdmin = new Student (fName, lName, uName, pWord, adminCourse, comprehensiveStudentArrayAdmin);
													
								// assumption that an admin can only add a student to a class if the student is already registered in the system.
								Boolean registered = false;
								for (int k=0; k<comprehensiveStudentArrayAdmin.size(); k++) {		
									if (((studentByAdmin.getFirstName()).equals(comprehensiveStudentArrayAdmin.get(k).getFirstName()))
											&& ((studentByAdmin.getLastName()).equals(comprehensiveStudentArrayAdmin.get(k).getLastName()))
											&&((studentByAdmin.getPassword()).equals(comprehensiveStudentArrayAdmin.get(k).getPassword()))
											&&((studentByAdmin.getUserName()).equals(comprehensiveStudentArrayAdmin.get(k).getUserName()))){
										registered = true;
									}			
								}
								
								// if the student is not yet registered in the system, the admin cannot register them here. The admin needs to go 
								// back to the registerAStudent function on the main menu, where the admin will register a student's first and last
								// name, and that student will be assigned a username and password. Then, if they wish, they can come back here and 
								// register that student for specific courses
								if (!registered) {
									System.out.println("This student is not yet registered in the system, so they can not be registered in a course. Returning"
											+ " you to the main menu... to register a student in the system, enter '5'.");
									System.out.println();
									break;
								}				
								
								// the admin cannot register a student for a course if that student is already registered in the course.
								// iterate through the student list for the given course, getting first and last name, and if they match
								// a name that is already on the list, the admin cannot add the student because the student is already in the course.
								Boolean notYetRegistered = false;
								for (int h =0; h<adminCourse.get(i).getStudentList().size(); h++) {
									if (adminCourse.get(i).getStudentList().get(h).getFirstName().equals(studentByAdmin.getFirstName()) 
											&& adminCourse.get(i).getStudentList().get(h).getLastName().equals(studentByAdmin.getLastName())) {
										System.out.println("Student is already registered in this course... Try again.");
										System.out.println();
										notYetRegistered = true;
										break;
									}
										
								}
								// if the program got to here- student is registered in system, but is not already registered
								// for course- add them to the course's student list.
								if (!notYetRegistered) {
									adminCourse.get(i).getStudentList().add(studentByAdmin);
									int intVersion = Integer.parseInt(adminCourse.get(i).getCurrentRegistered());								
									intVersion++;	
									String stringVersion = Integer.toString(intVersion);
									adminCourse.get(i).setCurrentRegistered(stringVersion);
									System.out.println("Student successfully registered for course!");	
									
								}
							
							}
							else if (addOrRemove.equals("remove")) {
								// if there are no students in the course then you can't remove any students- make sure size>0
								if (adminCourse.get(i).getStudentList().size() <= 0) {
									System.out.println("There are no students in this course so you cannot remove anyone from this course. Sending you back to main menu.");
									break;
								}
								
								else {
									int index;
									Boolean valid = false;
									// the student removal goes by number. Admin will enter a number, and the person who is that number on the list
									// will be removed. If the admin does not know the index number of the student they want to remove, they can go 
									// back to the main menu and enter 9. The reason why it goes by index number and not student name, is because
									// I am assuming that the admin is probably more likely to be removing a student because they want the course size
									// to be smaller- and not because they care which exact student is removed.
									while (!valid) {
										System.out.println("There are " + adminCourse.get(i).getStudentList().size() + " student/s on this course list. Enter the student "
												+ "you'd like to remove by number. (For example, if John Smith is the first person on this list, enter 1. If you are not sure "
												+ "what the index of the student is for this course, return to the main menu and enter '9'.");
										String studentIndex = input.nextLine();
										// validate that user enters an integer
										try {
											Integer.parseInt(studentIndex);
											String.valueOf(studentIndex);
											valid = true;
										}
										catch(NumberFormatException e) {
											System.out.println("This property must be an integer. Try again.");
										}
										// validate that user enters a valid index number- i.e. if there are
										// 3 students in the course, the user must enter a number between 1 and 3.
										
										if (valid) {
											int tempIndex = Integer.parseInt(studentIndex);
											index = tempIndex-1;									
											// if the index they entered is out of bounds... invalid
											if (index>=adminCourse.get(i).getStudentList().size() || index<0) {	
												System.out.println("That is not a valid index...");
												System.out.println();
											}
											// if they got to here- user input is an integer, that is in bounds of the student list,
											// then that student is removed from that course's student list
											else {
												adminCourse.get(i).getStudentList().remove(index);							
												int intVersion = Integer.parseInt(adminCourse.get(i).getCurrentRegistered());								
												intVersion--;	
												String stringVersion = Integer.toString(intVersion);
												adminCourse.get(i).setCurrentRegistered(stringVersion);
												System.out.println("Student successfully removed!");
												return;
											}					
										}
									}
								}
								
							}
							else {
								System.out.println("Invalid entry. Try again.");
							}
						}
				
					}
					
				}			
			}
		}

		@Override
		// since I'm writing to new file need to warn about FileNotFoundException
		// writes which courses are full
		public void logFullCourses() throws FileNotFoundException {
			// set up new file
			File file = new File("fullCourseList.txt");
			PrintWriter output = new PrintWriter(file);
			Boolean fullCoursesExist = false;
			for (int i=0; i<adminCourse.size(); i++) {
				if (adminCourse.get(i).getMaxStudents().equals(adminCourse.get(i).getCurrentRegistered())) {
					fullCoursesExist = true;
					
					output.println("Course name: " + adminCourse.get(i).getName());
					output.println("Course ID: " + adminCourse.get(i).getID());
					output.println("Maximum number of students allowed: " + adminCourse.get(i).getMaxStudents());
					output.println("Current number of students registered: " + adminCourse.get(i).getCurrentRegistered());
					output.println("Students enrolled: ");	
					// since this parameter is an array list, need to iterate through
					for (int j = 0; j<adminCourse.get(i).getStudentList().size(); j++) {
						output.print(adminCourse.get(i).getStudentList().get(j).getFirstName() + " ");
						output.println(adminCourse.get(i).getStudentList().get(j).getLastName() + " ");						
					}					
					output.println("Instructor: " + adminCourse.get(i).getInstructorName());
					output.println("Section number: " + adminCourse.get(i).getCourseSectionNumber());
					output.println("Location: " + adminCourse.get(i).getCourseLocation());
									
					System.out.println("Courses successfully logged to file 'fullCourseList.txt'!");
				}
			}
			//close the print writer
			output.close();

			// validate that there was at least one full course so that if there wasn't, we can inform the user
			if (!fullCoursesExist) {
				System.out.println("None of the courses are full. Sending you back to main menu");
				System.out.println();
				System.out.println();
			}						
		}

		@Override
		// sorts the courses based on how many students are enrolled in each course
		public void sort() {
			// use the sort function and CourseComparator method to go through num students in each course
			adminCourse.sort(new CourseComparator());
			System.out.println("Courses sorted!");
			System.out.println();
		}

		@Override
		// displays info for a given course, by Course ID
		public void displayCourseInfo() {
			// find out what course the admin wants to see the info for (goes by
			// id and section number)
			Scanner input = new Scanner (System.in);
			System.out.println("Enter course ID: ");
			String displayCourseID = input.nextLine();
			System.out.println("Enter section number: ");
			String displaySectionNumber = input.nextLine();
			for (int i=0; i<adminCourse.size(); i++) {
				// iterate through to find that course
				if (adminCourse.get(i).getID().equals(displayCourseID)&& adminCourse.get(i).getCourseSectionNumber().equals(displaySectionNumber)) {
					// print the information to the console
					System.out.println("Course name: " + adminCourse.get(i).getName());
					System.out.println("Course ID: " + adminCourse.get(i).getID());
					System.out.println("Maximum number of students allowed: " + adminCourse.get(i).getMaxStudents());
					System.out.println("Current number of students registered: " + adminCourse.get(i).getCurrentRegistered());
					System.out.print("Students enrolled: ");
					for (int j = 0; j<adminCourse.get(i).getStudentList().size(); j++) {
						System.out.print(adminCourse.get(i).getStudentList().get(j).getFirstName() + " ");
						System.out.println(adminCourse.get(i).getStudentList().get(j).getLastName() + " ");						
					}				
					System.out.println("Instructor: " + adminCourse.get(i).getInstructorName());
					System.out.println("Section number: " + adminCourse.get(i).getCourseSectionNumber());
					System.out.println("Location: " + adminCourse.get(i).getCourseLocation());
					System.out.println("");
					return;
				}
			}	
			//validation that the course exists...
			System.out.println("That course does not exist. Sending you back to main menu.");
		}

		@Override
		// allows admin to add a student to students list (without assigning
		// him/ her to a course). Assumption that in this function, all the admin
		// can do is register the student in general. If the admin wishes to add or 
		// remove students from a particular course, they can do that via the edit function.
		public void registerAStudent() {
			Scanner input = new Scanner(System.in);
			System.out.println("Enter student's first name");
			String firstName = input.nextLine();
			System.out.println("Enter student's last name");
			String lastName = input.nextLine();
			// the student is automatically assigned a username and password based on the order in which they have been registered.
			// If, for example, student John Smith was registered first, his username would be Student1 and password would be
			// Student001. Jane Doe, registered second, would be Student2; password Student002, etc. Using the array list of all the
			// registered students to keep track of what number registered each student is
			String username = "Student" + (comprehensiveStudentArrayAdmin.size()+1);
			String password = "Student00" + (comprehensiveStudentArrayAdmin.size()+1);
			// create the new student
			Student dummyStudent = new Student(firstName, lastName, username, password);
			
			for (int i=0; i<comprehensiveStudentArrayAdmin.size(); i++) {			
				// no two students can have the same first and last name. iterate through the list of all the students
				// that are registered, to make sure that the student being registered now is not already registered
				if (((dummyStudent.getFirstName()).equals(comprehensiveStudentArrayAdmin.get(i).getFirstName()))
						&& ((dummyStudent.getLastName()).equals(comprehensiveStudentArrayAdmin.get(i).getLastName()))){
					System.out.println("This student is already registered. Cannot register same student twice.");
					System.out.println("Sending you back to main menu...");
					System.out.println();
					return;			
				}			
			}
			// add this new student to the array list of all the registered students
			comprehensiveStudentArrayAdmin.add(dummyStudent);	
			System.out.println("Student successfully registered!");
			System.out.println();
		}

		@Override
		// for any given course, logs which students are enrolled in the course
		public void logRegisteredStudentsInACourse() {
			// find out what course the user want the student list for, by course ID and section
			System.out.println("Enter course ID: ");
			Scanner input = new Scanner (System.in);
			String id = input.nextLine();
			System.out.println("Enter course section: ");
			String section = input.nextLine();
			// validate that the course exists
			Boolean courseExists = false;
			for (int i=0; i<adminCourse.size(); i++) {
				if (adminCourse.get(i).getID().equals(id)&& adminCourse.get(i).getCourseSectionNumber().equals(section)) {
					courseExists = true;
					// if there are no students in the course, let the user know
					if (adminCourse.get(i).getStudentList().size() == 0){
						System.out.println("There are no students currently registered for this course.");
					}
					//iterate through the student list and print the students' names
					for (int j = 0; j<adminCourse.get(i).getStudentList().size(); j++) {
						System.out.print(adminCourse.get(i).getStudentList().get(j).getFirstName() + " ");
						System.out.println(adminCourse.get(i).getStudentList().get(j).getLastName() + " ");						
					}
				}
			}
			// if the course does not exist...
			if (!courseExists) {
				System.out.println("This course does not exist. Sending you back to main menu");
				System.out.println();
				System.out.println();
			}		
		}
		
		// I have chosen to add this method in which the admin can view the first name, last name, username, and password
		// of each student that is registered. This is because when several students are registered, it may be confusing
		// to remember which students are registered, and which names correlate with which username and password. 
		public void viewAllRegisteredStudents() {
			for (int i=0; i<comprehensiveStudentArrayAdmin.size(); i++) {
				System.out.print("Student: " + comprehensiveStudentArrayAdmin.get(i).getFirstName() + " ");
				System.out.println(comprehensiveStudentArrayAdmin.get(i).getLastName());
				System.out.println("Username: " + comprehensiveStudentArrayAdmin.get(i).getUserName());
				System.out.println("Password: " + comprehensiveStudentArrayAdmin.get(i).getPassword());
			}
		}
	}
