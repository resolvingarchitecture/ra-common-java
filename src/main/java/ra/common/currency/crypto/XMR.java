package ra.common.currency.crypto;

public class XMR extends Crypto {


    protected XMR() {
        super(0.00, 12);
    }

    @Override
    public String name() {
        return "Monero";
    }

}
