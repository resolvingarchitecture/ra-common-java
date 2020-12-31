package ra.common.currency.crypto;

public class LTN extends Crypto {

    protected LTN() {
        super(0.00, 0);
    }

    @Override
    public String name() {
        return "Lightning";
    }

}
