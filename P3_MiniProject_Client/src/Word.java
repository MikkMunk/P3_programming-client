import java.io.Serializable;

public class Word implements Serializable {
    String text = "hej";
    int number = 1;
    boolean hah = true;

    Word() {
    }

    public void setHah(boolean hah) {
        this.hah = hah;
    }

    public String getText() {
        return text;
    }
}
