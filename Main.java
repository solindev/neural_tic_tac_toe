import java.util.ArrayList;

public class Main {

    static boolean check_win(ArrayList<Byte> field) {
        ArrayList<ArrayList<Byte>> verts = new ArrayList<>();
        ArrayList<ArrayList<Byte>> hors = new ArrayList<>();
        ArrayList<ArrayList<Byte>> diags = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            verts.add(new ArrayList<Byte>(3));
            hors.add(new ArrayList<Byte>(3));
        }
        for(int i = 0; i < field.size(); i++) {
            verts.get(i % 3).add(field.get(i));
            hors.get(i / 3).add(field.get(i));
        }

        diags.add(new ArrayList<>());
        diags.get(0).add(field.get(0));
        diags.get(0).add(field.get(4));
        diags.get(0).add(field.get(8));
        diags.add(new ArrayList<>());
        diags.get(1).add(field.get(2));
        diags.get(1).add(field.get(4));
        diags.get(1).add(field.get(6));

        boolean check;

        ArrayList<ArrayList<Byte>>  full = new ArrayList<>();
        full.addAll(verts);
        full.addAll(hors);
        full.addAll(diags);

        for(ArrayList<Byte> x : full) {
            check = true;
            for(int i = 0; i < x.size() - 1; i++) {
                //System.out.println(x);
                if(x.get(i) == 0) { check = false; break; }
                if (x.get(i) != x.get(i + 1)) {
                    check = false;
                    break;
                }
            }
            if(check) return true;
        }

        return false;
    }

    public static void main(String args[]) {
        NeuralNetwork pl1 = new NeuralNetwork((byte) 1);
        NeuralNetwork pl2 = new NeuralNetwork((byte) -1);

        ArrayList<Byte> field = new ArrayList<>();

        boolean turn = true;
        boolean win;

        for(int i = 0; i < 9; i++) {
            field.add((byte) 0);
        }

        for(int j = 0; j < 1000; j++) {
            System.out.printf("\nGame: %d\n", j);
            System.out.println("------------------------------------------------------------------");

            win = false;
            for(int i = 0; i < field.size() && !win; i++) {
                byte pos;
                if(turn) { turn = !turn; pos = pl1.predict(field); field.set(pos, (byte) 1); }
                else { turn = !turn; pos = pl2.get_random(field); field.set(pos, (byte) -1); }
                win = check_win(field);
                for(int k = 0; k < field.size(); k++) {
                    if((k % 3) == 0) System.out.println();
                    char c = '_';
                    if (field.get(k) == 1) c = 'o';
                    else if(field.get(k) == -1) c = 'x';
                    System.out.printf("%c ", c);
                }
                System.out.println(win);
            }



            pl1.scale_neirons(turn);
            pl2.scale_neirons(!turn);

            for(int i = 0; i < 9; i++) {
                field.set(i, (byte) 0);
            }
        }
    }
}
