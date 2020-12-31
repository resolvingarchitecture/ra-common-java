package ra.common.currency.crypto;

import ra.common.currency.BaseCoin;
import ra.common.currency.StoreOfValue;

public class BTC extends BaseCoin implements StoreOfValue {

    private long fastestFee;
    private long halfHourFee;
    private long hourFee;

    public long getFastestFee() {
        return fastestFee;
    }

    public void setFastestFee(long fastestFee) {
        this.fastestFee = fastestFee;
    }

    public long getHalfHourFee() {
        return halfHourFee;
    }

    public void setHalfHourFee(long halfHourFee) {
        this.halfHourFee = halfHourFee;
    }

    public long getHourFee() {
        return hourFee;
    }

    public void setHourFee(long hourFee) {
        this.hourFee = hourFee;
    }

    @Override
    public Long maxSupply() {
        return 2100000000000000L;
    }
}
