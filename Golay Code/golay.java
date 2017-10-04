/**
 * Created by Gasia on 3/12/2017.
 */
import java.util.Random;
public class golay {

    public static final int[][] G={
            {1,0,0,0,1,1,1,0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,1,1,1,0,1,1,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,1,0,1,1,0,1,0,1,0,0,1,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,0,1,1,0,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0},
            {1,1,1,0,1,1,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0},
            {1,1,0,1,1,0,1,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0},
            {1,0,1,1,0,1,0,0,0,1,1,1,0,0,0,0,0,0,1,0,0,0,0,0},
            {0,1,1,0,1,0,0,0,1,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0},
            {1,1,0,1,0,0,0,1,1,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0},
            {1,0,1,0,0,0,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,1,0,0},
            {0,1,0,0,0,1,1,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,1,0},
            {1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1}
    };

    public static final int[][] H_transpose={
            {1,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0},
            {0,0,0,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,1,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,1},

            {1,0,0,0,1,1,1,0,1,1,0,1},
            {0,0,0,1,1,1,0,1,1,0,1,1},
            {0,0,1,1,1,0,1,1,0,1,0,1},
            {0,1,1,1,0,1,1,0,1,0,0,1},
            {1,1,1,0,1,1,0,1,0,0,0,1},
            {1,1,0,1,1,0,1,0,0,0,1,1},
            {1,0,1,1,0,1,0,0,0,1,1,1},
            {0,1,1,0,1,0,0,0,1,1,1,1},
            {1,1,0,1,0,0,0,1,1,1,0,1},
            {1,0,1,0,0,0,1,1,1,0,1,1},
            {0,1,0,0,0,1,1,1,0,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,0}
    };

    public static final int[][] P= {
            {1,0,0,0,1,1,1,0,1,1,0,1},
            {0,0,0,1,1,1,0,1,1,0,1,1},
            {0,0,1,1,1,0,1,1,0,1,0,1},
            {0,1,1,1,0,1,1,0,1,0,0,1},
            {1,1,1,0,1,1,0,1,0,0,0,1},
            {1,1,0,1,1,0,1,0,0,0,1,1},
            {1,0,1,1,0,1,0,0,0,1,1,1},
            {0,1,1,0,1,0,0,0,1,1,1,1},
            {1,1,0,1,0,0,0,1,1,1,0,1},
            {1,0,1,0,0,0,1,1,1,0,1,1},
            {0,1,0,0,0,1,1,1,0,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,0}
    };

    public static void print (int[] vec){
        for(int i = 0; i < vec.length; i++)
            System.out.print(vec[i]);
        System.out.println();
    }

    public static int[] mult(int []vect, int[][] mat )
    {
        int[] result= new int[mat[0].length];
        for(int i=0; i< mat[0].length;  i++) {
            for (int j = 0; j < vect.length; j++)
                result[i] ^= vect[j] & mat[j][i];
        }
        return result;
    }

    public static int[] syndrome(int []received){
        System.out.println("*******Syndrome*******");
        return mult(received, H_transpose);
    }

    public static int weight(int []vec){
        int count = 0;
        for(int i=0; i< vec.length;i++){
            if(vec[i]==1)
                count++;
        }
        return count;
    }

    public static int[] set(int []vec1, int []vec2){
        int[] result=new int[vec1.length+vec2.length];
        for(int i=0;i<vec1.length; i++) {
            result[i] = vec1[i];
            result[vec1.length+i]=vec2[i];
        }
        return result;
    }

    public static int[] sum(int []vec1, int []vec2) {
        int[] result = new int[vec1.length];
        for (int i = 0; i < vec1.length; i++) {
            result[i] = vec1[i] ^ vec2[i];
        }
        return result;
    }

    public static int[] decoder(int[] r)
        {
            int[] e = new int[r.length];
            int[] s = new int[H_transpose[0].length];

            //step1: compute the syndrome
            s = syndrome(r);
            print(s);

            int count_weight = weight(s);

            System.out.println("weight of the syndrome is " + count_weight);

            //step2:
            if (count_weight <= 3) {
                System.out.println("Step2 is satisfied");
                int[] vec0 = new int[s.length];
                e = set(s, vec0);
                return sum(r, e);
            }

            //step3:
            int[] temp=new int[s.length];
            for (int i = 0; i < P.length; i++) {
                temp = sum(s, P[i]);
                if (weight(temp) <= 2) {
                    System.out.println("Step3 is satisfied");
                    int[] u=new int[s.length];
                    u[i]=1;
                    e = set(temp, u);
                    return sum(r, e);
                }
            }

            //step4
            int[] SP = mult(s,P);

            //step5
            if(weight(SP)==2 || weight(SP)==3){
                System.out.println("Step5 is satisfied");
                int[] vec0 = new int[s.length];
                e=set(vec0, SP);
                return sum(r, e);
            }

            //step6
            int[] temp2=new int[s.length];
            for(int i = 0; i <P.length; i++){
                temp2 = sum(SP,P[i]);
                if(weight(temp2) == 2) {
                    System.out.println("Step6 is satisfied");
                    System.out.println("with i= "+ i);
                    int[] u2=new int[s.length];
                    u2[i]=1;
                    e = set(u2, sum(SP ,P[i]));
                    return sum(r,e);
                }
            }

            //step7
            System.out.println("The syndrome doesn't correspond to a correctable pattern");
            int[] step7= new int [r.length];
            step7[0]=-1;
            return step7;
    }

    public static int[] encode(int[] v){
        //System.out.println("*******Message Encoder*******");
        return mult(v, G);
    }


    //this function randomly generates error and randomly chooses how many errors to make (1,2 or 3 errors)
    public static int[] randerr(int[] v){

        Random rand = new Random();

        int errnum= rand.nextInt(3) +1;
        System.out.print("The noisy channel made "+ errnum+" errors in places: ");

        int  n = rand.nextInt(v.length);
        System.out.print(n);
        v[n]=v[n]^1;


        if(errnum>1) {
            int n2=0;
            do{
                n2 = rand.nextInt(v.length) ;
            }while(n2 ==n);
            System.out.print(" ,"+n2);
            v[n2]=v[n2]^1;

            if(errnum>2) {
                int n3;
                do{
                    n3 = rand.nextInt(v.length);
                }while(n3 ==n || n3==n2);
                System.out.print(" ,"+n3);

                v[n3]=v[n3]^1;
            }
        }
        System.out.println();

        return v;
    }



    public static void main(String[] args) {
        //int[] received = {1,0,0,0,0,0,1,1,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0,1};
        //int[] received = {0,0,0,0,0,0,1,1,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0,1};
        //int[] received = {1,0,1,1,1,1,1,0,1,1,1,1,0,1,0,0,1,0,0,1,0,0,1,0};

        int[] codeword = {1,1,0,0,1,0,1,0,0,0,1,1};
        System.out.println("*******Codeword*******");
        print(codeword);

        int[] enc= encode(codeword);
        System.out.println("*******Encoded Message*******");
        print(enc);

        int[] received= randerr(enc);
        System.out.println("*******Received Message with noise*******");
        print(received);

        int[] dec;
        dec= decoder(received);
        if(dec[0]!=-1) {
            System.out.println("*******Decoded Message*******");
            print(dec);
            System.out.println("*******Error pattern*******");
            print(sum(dec, received));
        }
    }
}
