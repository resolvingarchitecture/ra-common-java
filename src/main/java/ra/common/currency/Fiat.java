package ra.common.currency;

public class Fiat extends BaseCoin {
    @Override
    public long maxSupply() {
        return -1;
    }
}
