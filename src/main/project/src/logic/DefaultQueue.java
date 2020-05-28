package src.logic;

/**
 * Class of realisation of queue
 */

public class DefaultQueue {
    private String[] queue;
    private int maxAmount;
    private int currentAmount;
    private int head;
    private int tail;

    /**
     * Constructor
     * @param maxAmount - the max number of queue
     */

    public DefaultQueue(int maxAmount) {
        this.maxAmount = maxAmount;
        queue = new String[maxAmount];
        currentAmount = 0;
        head = 0;
        tail = -1;
    }

    /**
     * Method for inserting an element to queue
     * @param element - the element of queue
     */

    public void insert(String element) {
        if (tail < maxAmount - 1) {
            queue[++tail] = element;
            currentAmount++;
        } else {
            for (int i = 0; i < maxAmount - 1; i++) {
                queue[i] = queue[i + 1];
            }
            queue[maxAmount - 1] = element;
        }
    }

    public String getElement(int index) {
        return queue[index];
    }

    public String getHead() {
        return queue[head];
    }

    public String getTail() {
        return queue[tail];
    }

    public boolean isFull() {
        return (currentAmount == maxAmount - 1);
    }

    public boolean isEmpty() {
        return (currentAmount == 0);
    }

    public int getSize() {
        return currentAmount;
    }
}
