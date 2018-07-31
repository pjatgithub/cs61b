import edu.princeton.cs.algs4.StdAudio;
import synthesizer.GuitarString;

import java.util.HashMap;
import java.util.Map;

/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
public class GuitarHero {
    private static final double BASE_FREQUENCY = 440.0;
    private static final int FREQUENCY_OFFSET = 24;
    private static final double FREQUENCY_DIVISOR = 12.0;
    private static final String KEYBOARD ="q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private  GuitarString[] strings;
    private  Map<Character, Integer> indices;

    public GuitarHero() {
        int length = KEYBOARD.length();
        strings = new GuitarString[length];
        indices = new HashMap<>(KEYBOARD.length());

        for (int i = 0; i < length; i++) {
            double f = frequency(i);
            GuitarString string = new GuitarString(f);
            char key = KEYBOARD.charAt(i);

            strings[i] = string;
            indices.put(key, i);
        }
    }

    public static void main(String[] args) {
        GuitarHero guitarHero = new GuitarHero();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                guitarHero.pluck(key);
            }

            double s = guitarHero.sample();
            StdAudio.play(s);
            guitarHero.tic();
        }
    }

    private void pluck(char key) {
        if (!indices.containsKey(key)) {
            return;
        }

        int index = indices.get(key);
        strings[index].pluck();
    }

    private double sample() {
        double s = 0.0;

        for (GuitarString string : strings) {
            s += string.sample();
        }

        return s;
    }

    private void tic() {
        for (GuitarString string : strings) {
            string.tic();
        }
    }

    private static double frequency(int index) {
        return BASE_FREQUENCY * Math.pow(2, (index - FREQUENCY_OFFSET) / FREQUENCY_DIVISOR);
    }
}

