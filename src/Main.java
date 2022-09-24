import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static final BlockingQueue<String> forThread1 = new ArrayBlockingQueue<>(100);
    private static final BlockingQueue<String> forThread2 = new ArrayBlockingQueue<>(100);
    private static final BlockingQueue<String> forThread3 = new ArrayBlockingQueue<>(100);
    private static final int NUMBER_OF_WORDS = 10_000;
    private static final int WORDS_LENGTH = 100_000;

    public static void main(String[] args) {
        new Thread(()->{
            String[] texts = new String[NUMBER_OF_WORDS];
            for (int i = 0; i < texts.length; i++) {
                texts[i] = generateText("abc", WORDS_LENGTH);
                try {
                    forThread1.put(texts[i]);
                    forThread2.put(texts[i]);
                    forThread3.put(texts[i]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();



        new Thread(() -> {
            int count = 0;
            for (int i = 0; i < NUMBER_OF_WORDS; i++) {
                try {
                    if (whatIsTheLetter(forThread1.take()).equals("a")){
                        count++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Слов с максимальным содержанием символа а: " + count );
        }).start();

        new Thread(() -> {
            int count = 0;
            for (int i = 0; i < NUMBER_OF_WORDS; i++) {
                try {
                    if (whatIsTheLetter(forThread2.take()).equals("b")){
                        count++;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Слов с максимальным содержанием символа b: " + count );
        }).start();

        new Thread(() -> {
            int count = 0;
            for (int i = 0; i < NUMBER_OF_WORDS; i++) {
                try {
                    if (whatIsTheLetter(forThread3.take()).equals("c")){
                        count++;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Слов с максимальным содержанием символа c: " + count );
        }).start();
    }

    public static String whatIsTheLetter(String word) {
        int a = 0;
        int b = 0;
        int c = 0;
        String letter;
        for (int j = 0; j < word.length(); j++) {
            if (Character.toString(word.charAt(j)).equals("a")) {
                a++;
            } else if (Character.toString(word.charAt(j)).equals("b")) {
                b++;
            } else {
                c++;
            }
        }
        if (b > a && b > c) {
            letter = "b";
        } else if (c > a && c > b) {
            letter = "c";
        } else if (a > b && a > c) {
            letter = "a";
        } else letter = "z";
        return letter;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
