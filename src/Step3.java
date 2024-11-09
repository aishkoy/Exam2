import java.util.Random;
import java.util.Scanner;

public class Step3 {
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();
    static int toursNum = 3;
    static int[] userPredicted = new int[toursNum];
    static int[] userDice = new int[toursNum];
    static int[] userResults = new int[toursNum];
    static int[] computersPredicted = new int[toursNum];
    static int[] computersDice = new int[toursNum];
    static int[] computersResults = new int[toursNum];

    public static void main(String[] args) {
        gameStart();
    }

    public static void gameStart(){
        for (int i = 0; i < toursNum; i++){
            println("Tour №" + (i+1));
            tourLaunch(i);
        }
        showFinalScore();
    }

    public static void tourLaunch(int tourNumber){
        printGameTitle();

        int usersResult = userTurn(tourNumber);
        userResults[tourNumber] = usersResult;

        int computersResult = computersTurn(tourNumber);
        computersResults[tourNumber] = computersResult;

        int diff = Math.abs(usersResult - computersResult);
        printCurrentResult(usersResult, computersResult, diff);

        showCurrentScore(usersResult, computersResult, diff);
    }

    public static int userTurn(int tourNumber){
        int usersDicesSum = getUserInput();
        userPredicted[tourNumber] = usersDicesSum;

        int dice1 = rollTheDice();
        printDice(dice1);
        int dice2 = rollTheDice();
        printDice(dice2);

        int dicesSum = dice1 + dice2;
        userDice[tourNumber] = dicesSum;

        return pointScoring(usersDicesSum, dicesSum);
    }

    public static int computersTurn(int tourNumber){
        int computersDicesSum = rand.nextInt(11) + 2;
        computersPredicted[tourNumber] = computersDicesSum;

        printf("\nComputer predicted %d points.%n", computersDicesSum);
        println("Computer rolls the dices...");

        int dice1 = rollTheDice();
        printDice(dice1);
        int dice2 = rollTheDice();
        printDice(dice2);

        int dicesSum = dice1 + dice2;
        computersDice[tourNumber] = dicesSum;

        return pointScoring(computersDicesSum, dicesSum);

    }

    public static int getUserInput(){
        int sumOfDices;

        while(true){
            print("\nPredict amount of points (2.. 12): ");
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

    public static void printCurrentResult(int userResult, int compResult, int diff){
        if(userResult == compResult){
            println("It's a tie! Both players have the same score.");
        }
        else{
            String whoWon = userResult > compResult ? "User" : "Computer";
            String pointsStr = diff == 1 ? "point" : "points";
            printf("%n%s wins %d %s more. Congratulations!%n%n", whoWon, diff, pointsStr);
        }
    }

    public static void showCurrentScore(int userResult, int compResult, int diff){
        String userPointsStr = userResult == 1 ? "point" : "points";
        String compPointsStr = compResult == 1 ? "point" : "points";

        println("--------- Current Score ---------");
        printf("User: %d %s.%n", userResult, userPointsStr);
        printf("Computer: %d %s.%n", compResult, compPointsStr);

        if (userResult == compResult) {
            println("It's a tie! Both players have the same score.");

        } else {
            String diffPointsStr = diff == 1 ? "point" : "points";
            String whoIsAhead = userResult > compResult ? "User" : "Computer";
            printf("%s is ahead by %d %s.%n", whoIsAhead, diff, diffPointsStr);
        }
        println("---------------------------------\n");
    }

    public static void showFinalScore(){
        int finalUserResult = 0;
        for(int userResult : userResults){
            finalUserResult += userResult;
        }

        int finalComputerResult = 0;
        for(int computersResult : computersResults){
            finalComputerResult += computersResult;
        }

        int finalDiff = Math.abs(finalUserResult - finalComputerResult);
        String whoWon = finalUserResult > finalComputerResult ? "User" : "Computer";
        String pointsStr = finalDiff == 1 ? "point" : "points";

        String[] rounds = {"- 1 -", "- 2 -", "- 3 -"};
        String lineSeparator = "|";
        println("----------------------- Finish Game -----------------------");
        printf("%8s%4s%12s%12s%15s%n", "Round", lineSeparator, "User", lineSeparator, "Computer");
        println("-----------+-----------------------+-----------------------");
        for(int i = 0; i < 3; i++){
            printf("%12s%12s%9d%3s%12s%9d%n", lineSeparator, "Predicted:", userPredicted[i], lineSeparator,"Predicted:", computersPredicted[i]);
            printf("%8s%4s%7s%14d%3s%7s%14d%n",rounds[i], lineSeparator, "Dice:", userDice[i], lineSeparator,"Dice:", computersDice[i]);
            printf("%12s%9s%12d%3s%9s%12d%n", lineSeparator, "Result:", userResults[i], lineSeparator,"Result:", computersResults[i]);
            println("-----------+-----------------------+-----------------------");
        }
        printf("%8s%4s%9s%12d%3s%9s%12d%n","Total", lineSeparator, "Points:", finalUserResult, lineSeparator,"Points:", finalComputerResult);
        printf("%s wins %d %s more. Congratulations!%n", whoWon, finalDiff, pointsStr);
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