import net.jini.core.entry.*;

public class Sobj implements Entry {
    public String contents;

    public Sobj() {
        // No Arg constructor
    }

    public Sobj ( String s) {
        contents = s;
    }
}
