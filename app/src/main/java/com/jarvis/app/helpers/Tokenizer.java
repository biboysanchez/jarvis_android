package com.jarvis.app.helpers;

public class Tokenizer {
    public static final String TAG = Tokenizer.class.getSimpleName();
    public String dlk_code;
    public String token;
    public String sequance;

    /**
     * Tokenizer constructor required sequance and hash
     * @param sequance A varible type of string mostly merchant ID
     * @param hash A varible string results from public static String hash(String sequance, String dlk)
     */
    public Tokenizer(String sequance, String hash) {
        this.sequance = sequance;
        buildToken(hash);
    }

    private void buildToken(String hash) {
        token = HashHelper.getSHA256(hash);
    }

    public String getToken() {
        return token;
    }

    public static String hash(String sequance, String dlk){
        return "merchant_label" + "%|%" +
                "merchant_id" + "%|%" +
                dlk + "%|%" +
                "merchant_key" + "%|%" +
                sequance;
    }
}
