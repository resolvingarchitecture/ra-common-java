package ra.common.currency.crypto;

import ra.common.currency.BaseCoin;
import ra.common.currency.StoreOfValue;

public class BTC extends BaseCoin implements StoreOfValue {

    @Override
    public Long maxSupply() {
        return 2100000000000000L;
    }
}
