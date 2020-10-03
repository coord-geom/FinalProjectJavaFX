package sample.Model.Graphs.Animation;

import java.util.Comparator;

public class PQComparator implements Comparator<int[]> {
    @Override
    public int compare(int[] o1, int[] o2) {
        if(o1[0] == o2[0]) return o1[1] - o2[1];
        return o1[0] - o2[0];
    }
}
