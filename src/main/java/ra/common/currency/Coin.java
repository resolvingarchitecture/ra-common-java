package ra.common.currency;

import ra.common.JSONSerializable;

import java.math.BigInteger;

public interface Coin extends JSONSerializable {
    String symbol();
    String name();
    BigInteger value();
    String valueWithCommas();
}
