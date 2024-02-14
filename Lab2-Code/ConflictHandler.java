import java.util.ArrayList;
import java.util.StringTokenizer;
public class ConflictHandler {
    Student student;
    Course course;

    ConflictHandler(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public String checkConflict() {
        ArrayList vCourse = this.student.getRegisteredCourses();
        for (int i = 0; i < vCourse.size(); i++) {
            if (((Course) vCourse.get(i)).conflicts(this.course)) {
                return "Registration conflicts";
            }
        }
        return null;
    }
}
