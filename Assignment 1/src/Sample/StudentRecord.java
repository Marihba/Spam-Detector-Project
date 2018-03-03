package Sample;
public class StudentRecord {

    private String sid;
    private float midterm, assignment, exam, finalGrade;
    private char letterGrade;


    public StudentRecord(String sid, float assignment, float midterm, float exam) {
        this.sid = sid;
        this.assignment = assignment;
        this.midterm = midterm;
        this.exam = exam;
        this.finalGrade = calculateFinalGrade();
        this.letterGrade = calculateLetterGrade();
    }

    public String getSid() {
        return this.sid;
    }

    public float getAssignment() {
        return this.assignment;
    }

    public float getMidterm() {
        return this.midterm;
    }

    public float getExam() {
        return this.exam;
    }

    public float getFinalGrade() {
        return this.finalGrade;
    }

    public char getLetterGrade() {
        return this.letterGrade;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setAssignment(float assignment) {
        this.assignment = assignment;
    }

    public void setMidterm(float midterm) {
        this.midterm = midterm;
    }

    public void setExam(float exam) {
        this.exam = exam;
    }
    public float calculateFinalGrade() {
        return ((this.assignment * 0.20f) + (this.midterm * 0.30f) + (this.exam * 0.50f));
    }

    public char calculateLetterGrade() {
        float grade = this.finalGrade;
        char letter;

        if (grade >= 80.0f && grade <= 100.0f)     { letter = 'A'; }
        else if (grade >= 70.0f && grade <= 79.99f) { letter = 'B'; }
        else if (grade >= 60.0f && grade <= 69.99f) { letter = 'C'; }
        else if (grade >= 50.0f && grade <= 59.99f) { letter = 'D'; }
        else if (grade >= 0.0f && grade <= 49.00f)  { letter = 'F'; }
        else {
            System.err.println("Error in determining Letter Grade....");
            letter = '-';
        }
        return letter;
    }

}