import java.util.Map;

public interface PageRank<T1, T2> {

    void evaluate();

    Map<String, Double> getScores();

}
