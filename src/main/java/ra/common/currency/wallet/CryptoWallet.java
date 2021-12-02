package ra.common.currency.wallet;

import ra.common.currency.crypto.Crypto;

public class CryptoWallet extends Wallet {

    protected String publicKey; // encoded public key
    protected String publicKeyType; // type of public key
    protected String publicKeyEncoding; // encoding of public key

    protected String privateKey; // encoded private key
    protected String privateKeyType; // type of private key
    protected String privateKeyEncoding; // encoding of private key

    protected Crypto unconfirmedBalance; // the total unconfirmed balance of the wallet
    protected Crypto immatureBalance; // the total immature balance of the wallet
    protected Integer txCount; // the total number of transactions in the wallet

    public CryptoWallet() {
    }

    public CryptoWallet(String name) {
        super(name);
    }

    public CryptoWallet(String name, Integer amount) {
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

    public Crypto getUnconfirmedBalance() {
        return unconfirmedBalance;
    }

    public void setUnconfirmedBalance(Crypto unconfirmedBalance) {
        this.unconfirmedBalance = unconfirmedBalance;
    }

    public Crypto getImmatureBalance() {
        return immatureBalance;
    }

    public void setImmatureBalance(Crypto immatureBalance) {
        this.immatureBalance = immatureBalance;
    }

    public Integer getTxCount() {
        return txCount;
    }

    public void setTxCount(Integer txCount) {
        this.txCount = txCount;
    }
}
