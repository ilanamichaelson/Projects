import java.util.Comparator;

public class CourseComparator implements Comparator<Course> {
	@Override
	// uses the compareTo method set up in Course class
	public int compare(Course c1, Course c2) {
		return c1.compareTo(c2);
	}
}
