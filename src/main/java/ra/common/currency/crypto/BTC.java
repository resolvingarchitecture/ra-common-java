package ra.common.currency.crypto;

import ra.common.currency.StoreOfValue;

public class BTC extends Crypto implements StoreOfValue {

    protected BTC() {
        super(21000000.00000000, 8);
    }

    @Override
    public String name() {
        return "Bitcoin";
    }

}
