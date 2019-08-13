import java.util.ArrayList;
import java.util.TreeSet;

public class NeuralNetwork {

        ArrayList<Neiron> inps;

        TreeSet<Byte> used;
        byte sign;

        NeuralNetwork(byte sign) {
                this.sign = sign;
                inps = new ArrayList<>();
                used = new TreeSet<>();

                for(byte i = 0; i < 9; i++) {
                        inps.add(new Neiron(i));
                }
        }

        byte predict(ArrayList<Byte> field) {
                return activation(field);
        }

        byte activation(ArrayList<Byte> field) {
                Double max = inps.get(0).process(field, sign);
                byte max_pos = 0;
                boolean fst = true;

                for(byte i = 0; i < inps.size(); i++) {
                        if(field.get(i) == 1 || field.get(i) == -1) continue;
                        if(fst) { inps.get(i).process(field, sign); max_pos = i; continue; }
                        if(inps.get(i).process(field, sign) > max) {
                                max = inps.get(i).process(field, sign);
                                max_pos = i;
                        }
                }


                used.add(max_pos);
                return max_pos;
        }

        byte get_random(ArrayList<Byte> field) {
                byte pos;
                do {
                        pos = (byte) Math.floor(Math.random() * 9);
                } while (field.get(pos) == -1 || field.get(pos) == 1);
                return pos;
        }

        void scale_neirons(boolean is_positive) {
                for(Byte x : used) {
                        inps.get(x).scale_weights(1.1, is_positive);
                }
        }

        void clear_used() {
                used.clear();
        }
}
