package ra.common.currency.commodity;

import ra.common.currency.BaseCoin;

public abstract class Commodity extends BaseCoin {

    @Override
    public Long maxSupply() {
        return -1L;
    }
}
