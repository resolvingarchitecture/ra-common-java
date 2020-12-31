package ra.common.currency;

public abstract class Fiat extends BaseCoin {

    @Override
    public Long maxSupply() {
        return -1L;
    }
}
