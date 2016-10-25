package ztc.JavaObjectCompare;

import java.util.PriorityQueue;

public class PriorityQueueTest {
    public static void main(String[] args) {
        Student s3 = new Student(3, "c");
        Student s4 = new Student(4, "d");
        Student s1 = new Student(1, "a");
        Student s2 = new Student(2, "b");

        PriorityQueueWrapper priorityQueue = new PriorityQueueWrapper();
        priorityQueue.put(s3);
        priorityQueue.put(s4);
        priorityQueue.put(s1);
        priorityQueue.put(s2);

        for (Student student : priorityQueue.getPriorityQueue()) {
            System.out.println(student.getId() + "  " + student.getName());
        }
    }
}
