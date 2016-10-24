package app.umf.mziks1;

/**
 * Created by UMF on 10.10.2016.
 */

public abstract class HuffmanTree  implements Comparable<HuffmanTree> {
    public final int frequency;
    public HuffmanTree(int freq) { frequency = freq; }

    @Override
    public int compareTo(HuffmanTree huffmanTree) {
        return frequency-huffmanTree.frequency;
    }
// compares on the frequency

}
