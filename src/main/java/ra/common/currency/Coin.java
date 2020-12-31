package ra.common.currency;

import ra.common.JSONSerializable;

public interface Coin extends JSONSerializable {
    String symbol();
    String name();
    Double value();
}
