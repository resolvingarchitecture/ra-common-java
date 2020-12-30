package ra.common.currency.crypto;

import ra.common.currency.BaseCoin;

public class LTN extends BaseCoin {
    @Override
    public Long maxSupply() {
        return -1L;
    }
}
