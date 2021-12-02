package ra.common.currency.crypto;

import ra.common.currency.StoreOfValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class BTC extends Crypto implements StoreOfValue {

    public final static Double MAX_SUPPLY = 21000000.00000000D;
    public final static Integer PRECISION = 8;
    public final static Integer SATS_PER_BITCOIN = 100000000;

    public BTC() {
        super(MAX_SUPPLY, PRECISION);
    }

    public BTC(String valueInBTC) {
        super(MAX_SUPPLY, PRECISION);
        int numberTrailingZeroes = (valueInBTC.length()-1) - valueInBTC.indexOf(".");
        int numberToAdd = PRECISION - numberTrailingZeroes;
        for(int i=0; i<numberToAdd; i++) {
            valueInBTC+="0";
        }
        valueInBTC = valueInBTC.replace(".","");
        long sats = Long.parseLong(valueInBTC);
        super.setValue(BigInteger.valueOf(sats));
    }

    public BTC(Double valueInBTC) {
        super(MAX_SUPPLY, PRECISION);
        String tempValue = valueInBTC.toString();
        int numberTrailingZeroes = (tempValue.length()-1) - tempValue.indexOf(".");
        int numberToAdd = PRECISION - numberTrailingZeroes;
        for(int i=0; i<numberToAdd; i++) {
            tempValue+="0";
        }
        tempValue = tempValue.replace(".","");
        long sats = Long.parseLong(tempValue);
        super.setValue(BigInteger.valueOf(sats));
    }

    public BigDecimal valueInBitcoin() {
        if(value()==null || value().equals(BigInteger.ZERO)) return BigDecimal.ZERO;
        BigDecimal bitcoin = new BigDecimal(value().longValue());
        BigDecimal sats = new BigDecimal(SATS_PER_BITCOIN);
        return bitcoin.divide(sats);
    }

    @Override
    public String name() {
        return "Bitcoin";
    }

}
