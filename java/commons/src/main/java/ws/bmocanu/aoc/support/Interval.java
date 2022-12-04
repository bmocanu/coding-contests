package ws.bmocanu.aoc.support;

@SuppressWarnings("unused")
public class Interval {

    public int left;

    public int right;

    public Interval() {
    }

    public Interval(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public boolean contains(int otherIntervalLeft, int otherIntervalRight) {
        return (this.left <= otherIntervalLeft && this.right >= otherIntervalRight);
    }

    public boolean contains(Interval otherInterval) {
        return (this.left <= otherInterval.left && this.right >= otherInterval.right);
    }

    public boolean overlaps(Interval otherInterval) {
        return (this.left >= otherInterval.left && this.left <= otherInterval.right) ||
                (this.right >= otherInterval.left && this.right <= otherInterval.right) ||
                (otherInterval.left >= this.left && otherInterval.left <= this.right) ||
                (otherInterval.right >= this.left && otherInterval.right <= this.right);
    }

}
