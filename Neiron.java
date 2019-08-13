import java.util.ArrayList;
import java.util.TreeSet;

public class Neiron {
    ArrayList<Double> weights;
    private TreeSet<Byte> used;
    private TreeSet<Byte> unused;

    byte pos;

    Neiron(byte pos) {
        weights =  new ArrayList<>();
        used = new TreeSet<>();
        unused = new TreeSet<>();

        this.pos = pos;
        for(int i = 0; i < 9; i++) {
            weights.add(new Double(1));
        }
    }

    double process(ArrayList<Byte> field, byte sign) {
        double res = 0;
        for(int  i = 0; i < weights.size(); i++) {
            if(i == pos) continue;
            if(field.get(i) == 0) continue;
            res += weights.get(i) * Math.abs(field.get(i));
            if(field.get(i) == sign) used.add((byte) i);
            else unused.add((byte) i);
        }
        return res;
    }


    void scale_weights(double etha, boolean is_positive) {
        for(Byte x : used) {
            weights.set(x, is_positive ? weights.get(x) * etha : weights.get(x) / etha);
        }

        for(Byte x : unused) {
            weights.set(x, is_positive ? weights.get(x) / etha : weights.get(x) * etha);
        }
    }

    void clear_used() {
        used.clear();
    }
}
