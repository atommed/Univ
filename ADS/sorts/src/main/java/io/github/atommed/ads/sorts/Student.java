package io.github.atommed.ads.sorts;

/**
 * Created by Gregory on 30.03.2016.
 */
public class Student implements Comparable<Student> {
    private String name;
    private String surname;

    long card_id;
    long group_id;

    @Override
    public int compareTo(Student other) {
        if(this.group_id < other.group_id) return -1;
        else if(this.group_id > other.group_id) return 1;
        else return this.name.compareTo(other.name);
    }
}
