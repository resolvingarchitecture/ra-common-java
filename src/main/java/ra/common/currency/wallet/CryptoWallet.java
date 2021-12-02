package ra.common.currency.wallet;

import java.math.BigInteger;

public class CryptoWallet extends Wallet {

    protected String publicKey; // encoded public key
    protected String publicKeyType; // type of public key
    protected String publicKeyEncoding; // encoding of public key

    protected String privateKey; // encoded private key
    protected String privateKeyType; // type of private key
    protected String privateKeyEncoding; // encoding of private key

    protected BigInteger unconfirmedBalance; // the total unconfirmed balance of the wallet
    protected BigInteger immatureBalance; // the total immature balance of the wallet
    protected BigInteger txCount; // the total number of transactions in the wallet

    public CryptoWallet() {
    }

    public CryptoWallet(String name) {
        super(name);
    }

    public CryptoWallet(String name, BigInteger amount) {
        super(name, amount);
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKeyType() {
        return publicKeyType;
    }

    public void setPublicKeyType(String publicKeyType) {
        this.publicKeyType = publicKeyType;
    }

    public String getPublicKeyEncoding() {
        return publicKeyEncoding;
    }

    public void setPublicKeyEncoding(String publicKeyEncoding) {
        this.publicKeyEncoding = publicKeyEncoding;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPrivateKeyType() {
        return privateKeyType;
    }

    public void setPrivateKeyType(String privateKeyType) {
        this.privateKeyType = privateKeyType;
    }

    public String getPrivateKeyEncoding() {
        return privateKeyEncoding;
    }

    public void setPrivateKeyEncoding(String privateKeyEncoding) {
        this.privateKeyEncoding = privateKeyEncoding;
    }

    public BigInteger getUnconfirmedBalance() {
        return unconfirmedBalance;
    }

    public void setUnconfirmedBalance(BigInteger unconfirmedBalance) {
        this.unconfirmedBalance = unconfirmedBalance;
    }

    public BigInteger getImmatureBalance() {
        return immatureBalance;
    }

    public void setImmatureBalance(BigInteger immatureBalance) {
        this.immatureBalance = immatureBalance;
    }

    public BigInteger getTxCount() {
        return txCount;
    }

    public void setTxCount(BigInteger txCount) {
        this.txCount = txCount;
    }
}
