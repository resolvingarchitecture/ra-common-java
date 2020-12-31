package ra.common.currency;

import ra.common.JSONSerializable;

public interface Coin extends JSONSerializable {
    String symbol();
    String name();
    Long maxSupply();
    Long value();
}
