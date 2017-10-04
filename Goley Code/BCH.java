/**
 * Created by Gasia on 4/22/2017.
 */
    public class BCH {

    public static final int[][] alpha = {
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {1, 1, 0, 0},
            {0, 1, 1, 0},
            {0, 0, 1, 1},
            {1, 1, 0, 1},
            {1, 0, 1, 0},
            {0, 1, 0, 1},
            {1, 1, 1, 0},
            {0, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 0, 1, 1},
            {1, 0, 0, 1}};

    public static int[][] syndrome(int[] recieved) {
        System.out.println("*********Syndrome*********");
        System.out.println("PS: first row repreents S1, second row S2, ...., last row S6");
        int[][] syndrome = new int[6][alpha[0].length];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < recieved.length; j++) {
                syndrome[0][i] ^= alpha[ ((1 * j) % 15) + 1][i] * recieved[j];
                syndrome[1][i] ^= alpha[ ((2 * j) % 15) + 1][i] * recieved[j];
                syndrome[2][i] ^= alpha[ ((3 * j) % 15) + 1][i] * recieved[j];
                syndrome[3][i] ^= alpha[ ((4 * j) % 15) + 1][i] * recieved[j];
                syndrome[4][i] ^= alpha[ ((5 * j) % 15) + 1][i] * recieved[j];
                syndrome[5][i] ^= alpha[ ((6 * j) % 15) + 1][i] * recieved[j];
            }
        }
        print(syndrome);
        int[][] _syndrome = new int[6][15];
        for (int j = 0; j < syndrome.length; j++) {
            try {
                int index = Alpha_degree(syndrome[j]);
                if (index >= 0)
                {
                    _syndrome[j][index] = 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _syndrome;
    }

    public static int Alpha_degree(int[] syndrome) throws Exception {
        for (int i = 0; i < alpha.length; i++) {
            int indicator = 0;

            for (int j = 0; j < alpha[0].length; j++) {
                if (syndrome[j] == alpha[i][j])
                    indicator++;
            }
            if (indicator == 4)
                return i - 1;
        }
        throw new Exception();
    }


    public static int[] divide(int[] v1, int[] v2) {
        int[] res = new int[v1.length];
        int s1_index = -1;
        int s2_index = -1;
        for (int i = 0; i < v1.length; i++) {
            if (v1[i] == 1)
                s1_index = i;
        }
        for (int i = 0; i < v2.length; i++) {
            if (v2[i] == 1)
                s2_index = i;
        }
        if (s1_index >= 0 && s2_index >= 0) {
            int indexOfResult = (s1_index - s2_index) % 15;
            if (indexOfResult < 0)
                indexOfResult += 15;
            res[indexOfResult] = 1;
        }
        return res;
    }

    public static int[] multiply(int[] s1, int[] s2) {
        int[] res = new int[s1.length];
        int s1_index = -1;
        int s2_index = -1;
        for (int i = 0; i < s1.length; i++) {
            if (s1[i] == 1)
                s1_index = i;
        }
        for (int i = 0; i < s2.length; i++) {
            if (s2[i] == 1)
                s2_index = i;
        }
        if (s1_index >= 0 && s2_index >= 0) {
            int indexOfResult = (s1_index + s2_index) % 15;
            res[indexOfResult] = 1;
        }
        return res;
    }

    public static int[] add(int[] v1, int[] v2) {
        int[] a1 = Alpha_Degree(v1);
        int[] a2 = Alpha_Degree(v2);
        int degree = -1;

        int[] temp = new int[a1.length];
        for (int i = 0; i < a1.length; i++) {
            temp[i] = a1[i] ^ a2[i];
        }
        try {
            degree = Alpha_degree(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] res = new int[v1.length];
        if (degree > -1)
            res[degree] = 1;
        return res;
    }
    
    public static int[] Alpha_Degree(int[] a) {
        int index = -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 1)
                index = i;
        }
        return alpha[index + 1];
    }

    public static int[] Alpha_add(int[] v1, int[] v2) {


        int[] res = new int[v1.length];
        for (int i = 0; i < v1.length; i++) {
            res[i] = v1[i] ^ v2[i];
        }

        return res;
    }

    public static int[][] Sigma_calculator(int[][] syndrome) {
        int[][] sigma = new int[4][15];
        sigma[0][0] = 1;
        sigma[1] = syndrome[0];

        int[] d0 = syndrome[0];
        int[] d1 = add(syndrome[2], multiply(syndrome[1], syndrome[0]));
        int[] d1Overd0 = divide(d1, d0);

        int[] indexOfX2 = new int[15];
        int[] d1Overd0ByAlphas = Alpha_Degree(d1Overd0);

        int[] sigma2Prev = d1Overd0;


        int[] firstAdding = add(syndrome[4], multiply(syndrome[3], sigma[1]));
        int[] d2 = add(firstAdding, multiply(syndrome[2], sigma2Prev));
        int[] d2Overd1 = divide(d2, d1);
        int[] d2Overd1ByAlphas = Alpha_Degree(d2Overd1);
        try {
            int degree = Alpha_degree(Alpha_add(d1Overd0ByAlphas, d2Overd1ByAlphas));
            if (degree >= 0)
                indexOfX2[degree] = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        sigma[2] = indexOfX2;
        int[] indexOfX3 = multiply(sigma[1], d2Overd1);
        sigma[3] = indexOfX3;
        System.out.println("*********Sigm*********");
        System.out.println("PS: This is the final sigma; first row represents the coefficient of x^0, second row of x,..., fourth row of x^3");
        print(sigma);
        //System.out.println("for this case: 1 + x + 0 * x^2 + alpha^5 * x^3");
        return sigma;
    }

    public static int[] Alpha_to_Degree(int[] a, int degree) {
        int[] res = new int[a.length];
        int index = 0;

        for (int i = 0; i < a.length; i++) {
            if (a[i] == 1)
                index = i;
        }
        res[index * degree % 15] = 1;
        return res;
    }

    public static boolean Root_Check(int[] v, int[][] sigma) {

        int[] res = new int[v.length];
        for (int i = 0; i < sigma.length; i++)
            res = add(res, multiply(Alpha_to_Degree(v, i), sigma[i]));
        for (int i = 0; i < res.length; i++)
            if (res[i] != 0)
                return false;
        return true;
    }

    public static int[] xor(int[] v1, int[] v2) {
        int[] res = new int[v1.length];
        for (int i = 0; i < res.length; i++)
            res[i] = v1[i] ^ v2[i];
        return res;
    }

    public static int[] decode(int[] recieved) {
        int[][] syndrome = syndrome(recieved);
        int[][] sigma = Sigma_calculator(syndrome);
        int[] error = new int[recieved.length];

        for (int i = 1; i <= 15; i++) {
            int[] temp = new int[recieved.length];
            temp[i % 15] = 1;
            if (Root_Check(temp, sigma))
                error[error.length - i] = 1;
        }
        System.out.println("*********Error*********");
        print_1D(error);
        return xor(recieved, error);

    }

    public static void print(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++)
                System.out.print(m[i][j] + " ");

            System.out.println();
        }
    }

    public static void print_1D(int[] v) {
        for (int i = 0; i < v.length; i++) {
            System.out.print(v[i] + " ");
        }
    }

    public static void main(String[] args) {

        int[] real_v   = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] recieved = {0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0};

        System.out.println("*********Sent*********");
        print_1D(real_v);
        System.out.println();

        System.out.println("*********Recieved*********");
        print_1D(recieved);
        System.out.println();

        int[] decoded = decode(recieved);
        System.out.println();
        System.out.println("*********Decoded*********");
        print_1D(decoded);
    }
}
