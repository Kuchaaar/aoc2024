package Aoc22;

import java.util.*;

public class Aoc22 {
    List<Long> numbers;

    public Aoc22(List<String> input) {
        numbers = new ArrayList<>();
        for (String s : input) {
            numbers.add(Long.parseLong(s));
        }
    }

    public String part(boolean part2) {
        if (part2) {
            return String.valueOf(getBestSequencePrice());
        }
        return String.valueOf(numbers.stream()
                .mapToLong(s -> getSecret(s))
                .sum());
    }

    public int getBestSequencePrice() {
        Map<List<Integer>, Integer> secretSequences = new HashMap<>();
        int[] prices = new int[2000];
        int[] changes = new int[2001];

        for (Long number : numbers) {
            getPricesAndChanges(number, prices, changes);
            Map<List<Integer>, Integer> priceSequences = getPriceSequences(prices, changes);
            for (Map.Entry<List<Integer>, Integer> entry : priceSequences.entrySet()) {
                List<Integer> sequence = entry.getKey();
                int price = entry.getValue();
                secretSequences.put(sequence, secretSequences.getOrDefault(sequence, 0) + price);
            }
        }

        return secretSequences.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getValue();
    }

    private static void getPricesAndChanges(long secret, int[] prices, int[] changes) {
        for (int i = 0; i < 2000 + 1; i++) {
            secret = codingSecret(secret);
            long price = secret % 10;
            if (i != 2000) prices[i] = (int) price;
            if (i != 0) changes[i] = (int) (price - prices[i - 1]);
        }
    }

    private static Map<List<Integer>, Integer> getPriceSequences(int[] prices, int[] changes) {
        Map<List<Integer>, Integer> priceSequences = new HashMap<>();
        for (int i = 1; i < prices.length - 4; i++) {
            List<Integer> sequence = Arrays.asList(changes[i], changes[i + 1], changes[i + 2], changes[i + 3]);
            if (!priceSequences.containsKey(sequence)) {
                priceSequences.put(sequence, prices[i + 3]);
            }
        }
        return priceSequences;
    }

    private static long getSecret(long secret) {
        for (int i = 0; i < 2000; i++) {
            secret = codingSecret(secret);
        }
        return secret;
    }

    private static long codingSecret(long secret) {
        long secret_a = secret * 64;
        secret = secret ^ secret_a;
        secret = secret % 16777216;
        secret_a = secret / 32;
        secret = secret ^ secret_a;
        secret = secret % 16777216;
        secret_a = secret * 2048;
        secret = secret ^ secret_a;
        secret = secret % 16777216;
        return secret;
    }
}
