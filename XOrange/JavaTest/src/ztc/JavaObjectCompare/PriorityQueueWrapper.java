package ztc.JavaObjectCompare;

import java.util.PriorityQueue;

public class PriorityQueueWrapper {
    private PriorityQueue<Student> priorityQueue = null;

    public PriorityQueueWrapper() {
        priorityQueue = new PriorityQueue<Student>(new Compare());
    }

    public void put(Student student) {
        priorityQueue.add(student);
        if (priorityQueue.size() > 3) {
            priorityQueue.poll();
        }
    }

    public PriorityQueue<Student> getPriorityQueue() {
        return priorityQueue;
    }
}
