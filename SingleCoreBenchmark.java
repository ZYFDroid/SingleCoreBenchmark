import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class SingleCoreBenchmark {

    public static void main(String[] args) {

		System.out.println("Begin benchmark...");

        if(args.length>0 && args[0].equals("dynamic")){
            for (;;) {
                long time = (profilePi(4096));
                try {
                    Thread.sleep(time >= 1000 ? 1 : (1001 - time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                System.out.println("Single core performance："+(int)(10000000d / (time)));
            }
        }
        else{
            for (int i = 0; i < 3; i++) {
                profilePi(4096);
            }

            long min = Long.MAX_VALUE;
            long max = Long.MIN_VALUE;
            long total = 0;
            for (int i = 0; i < 8; i++) {
                long time = (profilePi(4096));
                total+=time;
                min = Math.min(min,time);
                max = Math.max(max,time);
            }
            total-=max;total-=min;
            System.out.println("Single core performance："+(int)(10000000d / (total / 6d)));
        }

    }

    static long profilePi(int digit){
        long a = System.currentTimeMillis();
        computePi(digit);
        return System.currentTimeMillis() - a;
    }

    static String computePi(int digits){
        MathContext mc = new MathContext(digits+1);
        BigDecimal PI = new BigDecimal(2),tmep = new BigDecimal(2);
        BigDecimal n = new BigDecimal(1),m = new BigDecimal(3);
        BigDecimal precies = new BigDecimal("1e-"+digits);
        BigDecimal two = new BigDecimal(2);
        while(tmep.compareTo(precies) > 0)
        {
            tmep = tmep.multiply(n).divide(m,mc); // tmep * n / m;
            PI =PI.add(tmep);
            n=n.add(BigDecimal.ONE);
            m = m.add(two);
        }
        return PI.toString();
    }
}
