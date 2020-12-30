package ra.common.currency;

public abstract class BaseCoin implements Coin {

    private long value = 0L;

    public BaseCoin() {}

    public BaseCoin(long value) {
        this.value = value;
    }

    @Override
    public String symbol() {
        return toString();
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().toUpperCase();
    }
}
