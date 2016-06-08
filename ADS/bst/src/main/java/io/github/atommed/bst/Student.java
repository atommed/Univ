package io.github.atommed.bst;

/**
 * Created by gregory on 08.06.16.
 */
public class Student implements Comparable<Student> {
    String surname;
    int course;
    Long id;
    double avgMark;
    String country;

    public int compareTo(Student student) {
        return id.compareTo(student.id);
    }
}
