package ra.common.currency.crypto;

import ra.common.currency.BaseCoin;

public abstract class Crypto extends BaseCoin {

    private long highFee = 0L;
    private long mediumFee = 0L;
    private long lowFee = 0L;

    public Long getHighFee() {
        return highFee;
    }

    public void setHighFee(long highFee) {
        this.highFee = highFee;
    }

    public Long getMediumFee() {
        return mediumFee;
    }

    public void setMediumFee(long mediumFee) {
        this.mediumFee = mediumFee;
    }

    public Long getLowFee() {
        return lowFee;
    }

    public void setLowFee(long lowFee) {
        this.lowFee = lowFee;
    }
}
