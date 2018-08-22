package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] buckets = new int[M];
        int n = oomages.size();
        double lowerBound = n / 50.0;
        double upperBound = n / 2.5;

        for (Oomage oomage : oomages) {
            buckets[index(oomage, M)]++;
        }

        for (int bucket : buckets) {
            if (bucket < lowerBound || bucket > upperBound) {
                return false;
            }
        }

        return true;
    }

    private static int index(Oomage oomage, int M) {
        return (oomage.hashCode() & 0x7FFFFFFF) % M;
    }
}
