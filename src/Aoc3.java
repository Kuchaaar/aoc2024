import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aoc3 {
    public long part1(String input){
        long value =0;
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(input);
        while(matcher.find()) {
            value = value + (Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2)));
        }
        return value;
    }
    public long part2(String input){
        Pattern pattern = Pattern.compile("(?<=do\\(\\))(.*?)(?=don't\\(\\))", Pattern.DOTALL);
        Matcher matcher = pattern.matcher("do()" + input + "don't()");
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            sb.append(matcher.group());
        }
        return part1(sb.toString());
    }
}
