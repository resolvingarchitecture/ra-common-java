package ra.common.currency.wallet;

import ra.common.currency.crypto.Crypto;

public class CryptoWallet extends Wallet {

    private String publicKey; // encoded public key
    private String publicKeyType; // type of public key
    private String publicKeyEncoding; // encoding of public key

    private String privateKey; // encoded private key
    private String privateKeyType; // type of private key
    private String privateKeyEncoding; // encoding of private key

    public CryptoWallet() {
    }

    public CryptoWallet(String name) {
        super(name);
    }

    public CryptoWallet(String name, Crypto amount) {
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
}
