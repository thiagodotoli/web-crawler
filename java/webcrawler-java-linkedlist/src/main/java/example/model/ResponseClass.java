package example.model;

/**
 * ResponseClass
 */
public class ResponseClass {

    private int count;
    private long delay;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public ResponseClass(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }

    public ResponseClass() {
    }

    @Override
    public String toString() {
        return "ResponseClass [count=" + count + ", delay=" + delay + "]";
    }
    
}