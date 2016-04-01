package io.github.atommed.ads.sorts;

import java.util.Arrays;
import java.util.Comparator;

public class App{
    static Student[] students = {
            new Student("Ora", "Oliver", 1 ,1),
            new Student("Norma", "Dixon",2,2),
            new Student("Everett", "Rodriguez",3,3),
            new Student("Doug", "Aguilar",4,4),
            new Student("Gordon", "Davidson",5,5),
            new Student("Rosa", "Hughes",6,1),
            new Student("Julie", "Massey",7,2),
            new Student("Terrence", "Harris",8,3),
            new Student("Laura", "Ferguson",9,4),
            new Student("Elisa", "Rivera",10,5),
            new Student("Jeanette", "Scott",11,1),
            new Student("Larry", "Collier",12,2),
            new Student("Angelina", "Moran",13,3),
            new Student("Perry", "Steele",14,4),
            new Student("Stuart", "Bradley",15,5),
            new Student("Muriel", "Curtis",16,1),
            new Student("Todd", "Brown",17,2),
            new Student("Leo", "Gutierrez",18,3),
            new Student("Hubert", "Nichols",19,4),
            new Student("Katherine", "Snyder",20,5),
            new Student("Delia", "Ball",21,1),
            new Student("Raymond", "Olson",22,2),
            new Student("Joanne", "Gordon",23,3),
            new Student("Randolph", "Houston",24,4),
            new Student("Rudy", "Welch",25,5),

    };

    public static void printArray(Student... arr){
        for(Student s: arr)
            System.out.println(s);
    }

    public static Student[] copyArray(Student... arr){
        Student[] s = new Student[arr.length];
        System.arraycopy(arr, 0, s, 0, arr.length);
        return s;
    }

    public static void main(String... args){
        System.out.println("UNSORTED:\n" +
                "####################################");
        printArray(students);
        Student[] bubble_res = copyArray(students);
        Student[] insert_res = copyArray(students);

        BubbleSort.sort(bubble_res, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if(o1.group_id < o2.group_id) return -1;
                else if(o1.group_id > o2.group_id) return 1;
                else return o1.getName().compareTo(o2.getName());
            }
        });
        System.out.println("BUBBLE SORT:\n" +
                "###################################");
        printArray(bubble_res);

        Integer[][] deb = {
                {3,2,1},
                {1,2,3}
        };
        Integer[] t = deb[0];
        deb[0] = deb[1];
        deb[1] = t;
        System.out.println(deb[0][0]);
        /*
        BubbleSort.sort(deb, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o1[0] - o2[0];
            }
        });
        System.out.println(deb[0][0]);
        */
    }
}
