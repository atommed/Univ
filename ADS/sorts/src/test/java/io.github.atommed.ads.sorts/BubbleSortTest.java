package io.github.atommed.ads.sorts;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class BubbleSortTest{
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
    @BeforeClass
    public static void standartSort(){
        Arrays.sort(students);
    }

    private static void assertSorted(Student[] s, int start_index){
        for(int i = 0; i < s.length; i++) {
            assertEquals(s[i], students[start_index + i]);
        }
    }

    @Test
    public void smallArraysSort(){
        Student[] sma1 = new Student[1];
        sma1[0] = students[0];
        BubbleSort.sort(sma1);
        assertSorted(sma1, 0);

        Student[] sma2 = new Student[2];
        sma2[0] = students[2];
        sma2[1] = students[1];
        BubbleSort.sort(sma2);
        assertSorted(sma2, 1);

        Student[] sma3 = new Student[3];
        sma3[0] = students[5];
        sma3[1] = students[3];
        sma3[2] = students[4];
        BubbleSort.sort(sma3);
        assertSorted(sma3, 3);
    }

    @Test
    public void reversedSortTest(){
        Student[] s = new Student[students.length];
        for(int i = 0 ; i < s.length; i++)
            s[i] = students[students.length - i -1];
        BubbleSort.sort(s);
        assertSorted(s, 0);
    }

    @Test
    public void shuffledTest(){
        Random r = new Random();
        Student[] s = new Student[students.length];
        System.arraycopy(students, 0, s, 0, students.length);
        for(int i = 1; i < s.length -1; i++){
            int swap_index = r.nextInt(s.length - i) + i;
            Student t = s[i];
            s[i] = s[swap_index];
            s[swap_index] = t;
        }
        BubbleSort.sort(s);
        assertSorted(s, 0);
    }
}