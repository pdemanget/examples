package stackoverflow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

//@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@OperationsPerInvocation(StreamVsVanilla.N)
@Fork(value=0,warmups=1)
@Measurement(iterations=2,time=100, timeUnit=TimeUnit.MILLISECONDS)
//@Measurement(iterations=5)
@Warmup(iterations=1)
public class StreamVsVanilla {
    public static final int N = 100;

    static List<Integer> sourceList = new ArrayList<>();
    static {
        for (int i = 0; i < N; i++) {
            sourceList.add(i);
        }
    }

    @Benchmark
    public List<Double> vanilla() {
        List<Double> result = new ArrayList<>(sourceList.size() / 2 + 1);
        for (Integer i : sourceList) {
            if (i % 2 == 0){
                result.add(Math.sqrt(i));
            }
        }
        return result;
    }

    @Measurement(iterations=2,time=100, timeUnit=TimeUnit.MILLISECONDS)
    @Benchmark
    public List<Double> stream() {
        return sourceList.stream()
                .filter(i -> i % 2 == 0)
                .map(Math::sqrt)
                .collect(Collectors.toCollection(
                    () -> new ArrayList<>(sourceList.size() / 2 + 1)));
    }
}