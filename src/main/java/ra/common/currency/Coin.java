package ra.common.currency;

public interface Coin {
    String symbol();
    long maxSupply();
    long value();
}
