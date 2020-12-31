package ra.common.currency.crypto;

public class XMR extends Crypto {

    @Override
    public String name() {
        return "Monero";
    }

    @Override
    public Long maxSupply() {
        return -1L;
    }
}
