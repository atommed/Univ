package io.github.atommed.ads.sorts;

/**
 * Created by Gregory on 30.03.2016.
 */
public class Student implements Comparable<Student> {
    private String name;

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    private String surname;

    long card_id;
    long group_id;

    public Student(String name, String surname, long card_id, long group_id) {
        this.name = name;
        this.surname = surname;
        this.card_id = card_id;
        this.group_id = group_id;
    }

    @Override
    public String toString(){
        return String.format("{#%3$d %1$s %2$s, group %4$d}", name, surname, card_id, group_id);
    }

    @Override
    public int compareTo(Student other) {
        if(this.group_id < other.group_id) return -1;
        else if(this.group_id > other.group_id) return 1;
        else return this.name.compareTo(other.name);
    }
}
