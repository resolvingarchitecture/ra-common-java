package ra.common.currency.crypto;

import ra.common.currency.StoreOfValue;

public class BTC extends Crypto implements StoreOfValue {

    @Override
    public String name() {
        return "Bitcoin";
    }

    @Override
    public Long maxSupply() {
        return 2100000000000000L;
    }
}
