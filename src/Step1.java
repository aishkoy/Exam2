import java.util.Random;
import java.util.Scanner;

public class Step1 {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        gameStart();
    }

    public static void gameStart(){
        printGameTitle();
        int usersDicesSum = getUserInput();
        int dice1 = rollTheDice();
        printDice(dice1);
        int dice2 = rollTheDice();
        printDice(dice2);

        int dicesSum = dice1 + dice2;
        printResult(usersDicesSum, dicesSum);
    }

    public static int getUserInput(){
        int sumOfDices;

        while(true){
            print("Predict amount of points (2.. 12): ");
            String inputStr = sc.nextLine().strip();

            String regex = "\\b(12|[2-9]|1[0-1])\\b";

            if(inputStr.isBlank() || !inputStr.matches(regex)){
                println("Invalid input, please try again");
                continue;
            }

            sumOfDices = Integer.parseInt(inputStr);
            break;
        }

        println("User rolls the dices...\n");
        return sumOfDices;
    }

    public static int rollTheDice(){
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public static void printDice(int dice){
        String border = "+---------+";
        String[] dicePatterns = {   "|         |\n|    #    |\n|         |",
                                    "| #       |\n|         |\n|       # |",
                                    "| #       |\n|    #    |\n|       # |",
                                    "| #     # |\n|         |\n| #     # |",
                                    "| #     # |\n|    #    |\n| #     # |",
                                    "| #     # |\n| #     # |\n| #     # |"
                                };
        println(border);
        println(dicePatterns[dice-1]);
        println(border);
    }

    public static void printGameTitle(){
        println("---   Start game    ---");
    }

    public static void printResult(int usersDicesSum, int dicesSum){
        int absDiff = Math.abs(dicesSum - usersDicesSum);
        int result = dicesSum  - absDiff * 2;
        printf("On the dices fell %d points.%n", dicesSum);
        String tmpl = String.format("%d-abs(%d - %d) * 2: %d points.", dicesSum, dicesSum, usersDicesSum, result);
        println("Result is " + tmpl);

        String winOrLose = result > 0 ? "wins" : "lost";
        printf("User %s!", winOrLose);
    }

    public static void println(String str){
        System.out.println(str);
    }

    public static void print(String str){
        System.out.print(str);
    }

    public static void printf(String str, Object... args){
        System.out.printf(str, args);
    }
}