package ra.common.currency;

public interface Coin {
    String symbol();
    boolean limitedSupply();
    long maxSupply();
    long value();
}
