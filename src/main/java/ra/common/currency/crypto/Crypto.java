package ra.common.currency.crypto;

import ra.common.currency.BaseCoin;

public abstract class Crypto extends BaseCoin {

    protected final int precision;
    protected final double maxSupply;
    private double highFee = 0.00;
    private double mediumFee = 0.00;
    private double lowFee = 0.00;

    protected Crypto(double maxSupply, int precision) {
        this.maxSupply = maxSupply;
        this.precision = precision;
    }

    public Integer getPrecision() {
        return precision;
    }

    public Double getMaxSupply() {
        return maxSupply;
    }

    public Double getHighFee() {
        return highFee;
    }

    public void setHighFee(Double highFee) {
        this.highFee = highFee;
    }

    public Double getMediumFee() {
        return mediumFee;
    }

    public void setMediumFee(Double mediumFee) {
        this.mediumFee = mediumFee;
    }

    public Double getLowFee() {
        return lowFee;
    }

    public void setLowFee(Double lowFee) {
        this.lowFee = lowFee;
    }

}
