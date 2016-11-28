package pdem.stackoverflow;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAccumulator;

/**
 */
public class SOStreams {
  public static void main(String[] args) {
    List<Double> list = Arrays.asList(2.0d,3.0d,4.0d);

    DoubleAccumulator acc = new DoubleAccumulator(Double::sum,0.d);
    AtomicInteger atomic = new AtomicInteger();

    list.forEach(sla -> {
        acc.accumulate(sla);
        int a = (int) sla.doubleValue();
        atomic.addAndGet(a);

    });
    System.out.println(acc);
    System.out.println(atomic);
  }

}
