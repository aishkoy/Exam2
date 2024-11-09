import java.util.Random;
import java.util.Scanner;

public class Step2 {
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    public static void main(String[] args) {
        gameStart();
    }

    public static void gameStart(){
        printGameTitle();
        int usersResult = userTurn();
        int computersResult = computersTurn();
        printResult(usersResult, computersResult);
    }

    public static int userTurn(){
        int usersDicesSum = getUserInput();
        int dice1 = rollTheDice();
        printDice(dice1);
        int dice2 = rollTheDice();
        printDice(dice2);

        int dicesSum = dice1 + dice2;
        return pointScoring(usersDicesSum, dicesSum);
    }

    public static int computersTurn(){
        int computersDicesSum = rand.nextInt(11) + 2;
        printf("\nComputer predicted %d points.%n", computersDicesSum);
        println("Computer rolls the dices...");

        int dice1 = rollTheDice();
        printDice(dice1);
        int dice2 = rollTheDice();
        printDice(dice2);
        int dicesSum = dice1 + dice2;

        return pointScoring(computersDicesSum, dicesSum);

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

    public static int pointScoring(int usersDicesSum, int dicesSum){
        int absDiff = Math.abs(dicesSum - usersDicesSum);
        int result = dicesSum  - absDiff * 2;
        printf("On the dices fell %d points.%n", dicesSum);
        String tmpl = String.format("%d-abs(%d - %d) * 2: %d points.", dicesSum, dicesSum, usersDicesSum, result);
        println("Result is " + tmpl);

        return result;
    }

    public static void printResult(int userResult, int compResult){
        if(userResult == compResult){
            println("It's a tie! Both players scored the same. Well played!");
        } else{
            String whoWon = userResult > compResult ? "User" : "Computer";
            int diff = Math.abs(userResult - compResult);
            String pointsStr = diff == 1 ? "point" : "points";
            printf("%n%s win %d %s more. Congratulations!%n", whoWon, diff, pointsStr);
        }
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