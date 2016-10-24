package app.umf.mziks1;

/**
 * Created by UMF on 10.10.2016.
 */

public class HuffmanLeaf extends HuffmanTree   {
    public final char value;

    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }


}

