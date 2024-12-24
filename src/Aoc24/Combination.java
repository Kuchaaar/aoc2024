package Aoc24;

public record Combination(String first, String operation, String second, String result) {
    public static Combination FromString(String s){
        String[] split = s.split(" ");
        return new Combination(split[0], split[1], split[2], split[4]);
    }
}
