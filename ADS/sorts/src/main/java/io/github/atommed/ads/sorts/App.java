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


    public static void printSquareArr(Student[][] arr){
      for(int i = 0; i < arr.length; i++){
	System.out.println("Row " + (i+1));
	printArray(arr[i]);
      }
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

	System.out.println("INSERTION SORT\n" + 
	       "####################################");

	Student[][] insSort = new Student[5][5];
	for(int i = 0; i < 5; i++){
	  for(int j = 0; j < 5; j++)
	    insSort[i][j] = students[i*5+j];
	}
	System.out.println("Unsorted");
	printSquareArr(insSort);

	Student[] tmpInsSort = new Student[25];
	System.arraycopy(students,0,tmpInsSort, 0, students.length);
        
	InsertionSort.sort(tmpInsSort, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if(o1.group_id < o2.group_id) return -1;
                else if(o1.group_id > o2.group_id) return 1;
                else return o1.getName().compareTo(o2.getName());
            }
        });

	for(int i =0; i < 5; i++){
	  int j = 0;
	  while(tmpInsSort[i*5+j].group_id == i+1){
	    insSort[i][j] = tmpInsSort[i*5+j];
	    j++;
	    if(j==5) break;
	  }
	}
	System.out.println("Sorted");
	printSquareArr(insSort);
    }
}
