package Utils;

public final class Pair <T, U> {
    public T getFirst() {
        return First;
    }

    public void setFirst(T first) {
        this.First = first;
    }

    public U getSencond() {
        return Sencond;
    }

    public void setSencond(U sencond) {
        this.Sencond = sencond;
    }

    private T First;
    private U Sencond;

    public Pair(T t, U u) {
        this.First = t;
        this.Sencond = u;
    }
}
