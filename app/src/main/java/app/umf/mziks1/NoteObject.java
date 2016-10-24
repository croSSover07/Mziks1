package app.umf.mziks1;

/**
 * Created by UMF on 10.10.2016.
 */

public class NoteObject {
    public char value;
    public int frequency;
    public String code;

    public NoteObject(char value, int freequency, String code) {
        this.value = value;
        this.frequency = freequency;
        this.code = code;
    }

    @Override
    public String toString() {
        return value + "  " + frequency + "  " + code;
    }
}


