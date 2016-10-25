package ztc.JavaObjectCompare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaObjectCompare {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<Student>();
        Student s3 = new Student(3, "c");
        Student s1 = new Student(1, "a");
        Student s2 = new Student(2, "b");
        list.add(s1);
        list.add(s2);
        list.add(s3);

        System.out.println("排序前");
        for (int i = 0; i < list.size(); i++) {
            Student s = list.get(i);
            System.out.println(s.getId() + "  " + s.getName());
        }

        Collections.sort(list, new Compare());

        System.out.println("排序后");
        for (int i = 0; i < list.size(); i++) {
            Student s = list.get(i);
            System.out.println(s.getId() + "  " + s.getName());
        }
    }
}
