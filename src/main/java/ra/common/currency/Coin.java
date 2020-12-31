package ra.common.currency;

import ra.common.JSONSerializable;

public interface Coin extends JSONSerializable {
    String symbol();
    Long maxSupply();
    Long value();
    Long getLowFee();
    Long getMediumFee();
    Long getHighFee();
}
