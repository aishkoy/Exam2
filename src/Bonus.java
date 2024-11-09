import java.util.Random;
import java.util.Scanner;

public class Bonus {
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    static int toursNum = 3;

    static int[] userPredicted;
    static int[] userDice;
    static int[] userResults;

    static int[] computersPredicted;
    static int[] computersDice;
    static int[] computersResults;

    static int scoreDifference = 0;

    public static void main(String[] args) {
        do {
            gameReset();
            gameStart();
        } while (isGameRestarted());
    }

    public static void gameStart() {
        for (int i = 0; i < toursNum; i++) {
            println("Tour №" + (i + 1));
            tourLaunch(i);
        }
        showFinalScore();
    }

    public static void gameReset() {
        userPredicted = new int[toursNum];
        userDice = new int[toursNum];
        userResults = new int[toursNum];
        computersPredicted = new int[toursNum];
        computersDice = new int[toursNum];
        computersResults = new int[toursNum];
    }

    public static void tourLaunch(int tourNumber) {
        printGameTitle();

        int usersResult = userTurn(tourNumber);
        userResults[tourNumber] = usersResult;

        int computersResult = computersTurn(tourNumber);
        computersResults[tourNumber] = computersResult;

        int diff = Math.abs(usersResult - computersResult);
        scoreDifference = usersResult - computersResult;

        printCurrentResult(usersResult, computersResult, diff);
        showCurrentScore(usersResult, computersResult, diff);
    }

    public static int userTurn(int tourNumber) {
        int usersDicesSum = getUserInput();
        userPredicted[tourNumber] = usersDicesSum;

        int dice1, dice2;

        boolean isCheating = isUserCheating();
        boolean isCheatFailed = false;

        if (isCheating) {

            isCheating = playersChanceToCheat(tourNumber);
            if (isCheating) {
                println("User successfully cheated!");
                println("User rolls the dices...\n");
                int[][] variables = breakNumIntoTwoSummands(usersDicesSum);
                int[] randomPair = variables[rand.nextInt(variables.length)];
                dice1 = randomPair[0];
                printDice(dice1);
                dice2 = randomPair[1];
                printDice(dice2);
            } else {
                println("User failed to cheat!");
                isCheatFailed = true;
                println("User rolls the dices...\n");
                dice1 = rollTheDice();
                printDice(dice1);
                dice2 = rollTheDice();
                printDice(dice2);
            }
        } else {
            println("User decided not to cheat.");
            println("User rolls the dices...\n");
            dice1 = rollTheDice();
            printDice(dice1);
            dice2 = rollTheDice();
            printDice(dice2);
        }

        int dicesSum = dice1 + dice2;
        userDice[tourNumber] = dicesSum;

        return pointScoring(usersDicesSum, dicesSum, isCheatFailed);
    }

    public static int computersTurn(int tourNumber) {
        int computersDicesSum = rand.nextInt(11) + 2;
        computersPredicted[tourNumber] = computersDicesSum;

        printf("\nComputer predicted %d points.%n", computersDicesSum);

        boolean isCheating = isComputerCheating(tourNumber, scoreDifference);
        boolean isCheatFailed = false;
        int dice1, dice2;

        if (isCheating) {
            isCheating = playersChanceToCheat(tourNumber);
            if (isCheating) {
                println("Computer successfully cheated!");
                println("Computer rolls the dices...\n");
                int[][] variables = breakNumIntoTwoSummands(computersDicesSum);
                int[] randomPair = variables[rand.nextInt(variables.length)];
                dice1 = randomPair[0];
                printDice(dice1);
                dice2 = randomPair[1];
                printDice(dice2);
            } else {
                println("Computer failed to cheat!");
                isCheatFailed = true;
                println("Computer rolls the dices...\n");
                dice1 = rollTheDice();
                printDice(dice1);
                dice2 = rollTheDice();
                printDice(dice2);
            }
        } else {
            println("Computer decided not to cheat.");
            println("Computer rolls the dices...");
            dice1 = rollTheDice();
            printDice(dice1);
            dice2 = rollTheDice();
            printDice(dice2);
        }

        int dicesSum = dice1 + dice2;
        computersDice[tourNumber] = dicesSum;

        return pointScoring(computersDicesSum, dicesSum, isCheatFailed);

    }

    public static int getUserInput() {
        int sumOfDices;

        while (true) {
            print("\nPredict amount of points (2.. 12): ");
            String inputStr = sc.nextLine().strip();

            String regex = "\\b(12|[2-9]|1[0-1])\\b";

            if (inputStr.isBlank() || !inputStr.matches(regex)) {
                println("Invalid input, please try again");
                continue;
            }

            sumOfDices = Integer.parseInt(inputStr);
            break;
        }
        return sumOfDices;
    }

    public static int rollTheDice() {
        return rand.nextInt(6) + 1;
    }

    public static void printDice(int dice) {
        String[] dicePatterns = {"|         |\n|    #    |\n|         |",
                "| #       |\n|         |\n|       # |",
                "| #       |\n|    #    |\n|       # |",
                "| #     # |\n|         |\n| #     # |",
                "| #     # |\n|    #    |\n| #     # |",
                "| #     # |\n| #     # |\n| #     # |"
        };

        String border = "+---------+";
        println(border);
        println(dicePatterns[dice - 1]);
        println(border);
    }

    public static void printGameTitle() {
        println("---   Start game    ---");
    }

    public static int pointScoring(int usersDicesSum, int dicesSum, boolean isCheatFailed) {
        int absDiff = Math.abs(dicesSum - usersDicesSum);
        int result = dicesSum - absDiff * 2;

        String resultStr = result == 1 ? "point" : "points";

        printf("On the dices fell %d points.%n", dicesSum);
        String formula = String.format("%d-abs(%d - %d) * 2", dicesSum, dicesSum, usersDicesSum);
        if (isCheatFailed) {
            result -= 10;
            formula = String.format("( %d-abs(%d - %d) * 2 ) - 10", dicesSum, dicesSum, usersDicesSum);
            println("Since you tried to cheat, the result is calculated using the following formula: " + formula);
            printf("Result is %s: %d %s.%n%n", formula, result, resultStr);
        } else {
            printf("Result is %s: %d %s.%n%n", formula, result, resultStr);
        }
        return result;
    }

    public static void printCurrentResult(int userResult, int compResult, int diff) {
        if (userResult == compResult) {
            println("It's a tie! Both players have the same score.");
        } else {
            String whoWon = userResult > compResult ? "User" : "Computer";
            String pointsStr = diff == 1 ? "point" : "points";
            printf("%n%s wins %d %s more. Congratulations!%n%n", whoWon, diff, pointsStr);
        }
    }

    public static void showCurrentScore(int userResult, int compResult, int diff) {
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

    public static void showFinalScore() {
        int finalUserResult = 0;
        for (int userResult : userResults) {
            finalUserResult += userResult;
        }

        int finalComputerResult = 0;
        for (int computersResult : computersResults) {
            finalComputerResult += computersResult;
        }

        String[] rounds = {"- 1 -", "- 2 -", "- 3 -"};
        String lineSeparator = "|";
        println("----------------------- Finish Game -----------------------");
        printf("%8s%4s%12s%12s%15s%n", "Round", lineSeparator, "User", lineSeparator, "Computer");
        println("-----------+-----------------------+-----------------------");
        for (int i = 0; i < 3; i++) {
            printf("%12s%12s%9d%3s%12s%9d%n", lineSeparator, "Predicted:", userPredicted[i], lineSeparator, "Predicted:", computersPredicted[i]);
            printf("%8s%4s%7s%14d%3s%7s%14d%n", rounds[i], lineSeparator, "Dice:", userDice[i], lineSeparator, "Dice:", computersDice[i]);
            printf("%12s%9s%12d%3s%9s%12d%n", lineSeparator, "Result:", userResults[i], lineSeparator, "Result:", computersResults[i]);
            println("-----------+-----------------------+-----------------------");
        }
        printf("%8s%4s%9s%12d%3s%9s%12d%n", "Total", lineSeparator, "Points:", finalUserResult, lineSeparator, "Points:", finalComputerResult);

        if (finalUserResult == finalComputerResult) {
            println("It's a tie! Both players have the same total score.");
        } else {
            int finalDiff = Math.abs(finalUserResult - finalComputerResult);
            String whoWon = finalUserResult > finalComputerResult ? "User" : "Computer";
            String pointsStr = finalDiff == 1 ? "point" : "points";
            printf("%s wins %d %s more. Congratulations!%n", whoWon, finalDiff, pointsStr);
        }
    }

    public static boolean isGameRestarted() {
        while (true) {
            print("\nDo you want to play one more time? (Yes/No): ");

            String answer = sc.nextLine().strip().toLowerCase();
            if (answer.equals("yes")) {
                println("");
                return true;
            } else if (answer.equals("no")) {
                println("Thank you for playing!");
                return false;
            } else {
                println("Invalid answer. Try again.");
            }

        }
    }

    public static boolean isUserCheating() {
        while (true) {
            print("Do  you want to cheat? (Yes/No): ");
            String answer = sc.nextLine().strip().toLowerCase();
            if (answer.equals("yes")) {
                return true;
            } else if (answer.equals("no")) {
                return false;
            } else {
                println("Invalid answer. Try again.");
            }
        }
    }

    public static boolean playersChanceToCheat(int tourNumber) {
        double chance = 0.0;

        if (tourNumber == 0) {
            chance = 0.5;
        } else if (tourNumber == 1) {
            chance = 0.25;
        } else if (tourNumber == 2) {
            chance = 1.0 / 6.0;
        }

        return rand.nextDouble() < chance;
    }

    public static boolean computersChanceToCheat(int maxNum, int midNum) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(maxNum) + 1;
        return randomIndex <= midNum;
    }

    public static boolean isComputerCheating(int tourNumber, int scoreDifference) {
        boolean isCheating = false;
        if (tourNumber == 0) {
            isCheating = computersChanceToCheat(5, 1);
        } else if (tourNumber >= 1) {
            if (scoreDifference > 5) {
                if (scoreDifference > 15) {
                    isCheating = computersChanceToCheat(5, 3);
                } else {
                    isCheating = computersChanceToCheat(5, 2);
                }
            } else {
                isCheating = computersChanceToCheat(5, 1);
            }
        }
        return isCheating;
    }

    public static int[][] breakNumIntoTwoSummands(int userDicesSum){
        int count = 0;
        for (int i = 1; i <= 6; i++) {
            int secondSummand = userDicesSum - i;
            if (secondSummand >= 1 && secondSummand <= 6) {
                count++;
            }
        }

        int[][] numbers = new int[count][2];
        int index = 0;

        for(int i = 1; i <= 6; i++){
            int secondSummand = userDicesSum - i;
            if (secondSummand >= 1 && secondSummand <= 6) {
                numbers[index][0] = i;
                numbers[index][1] = secondSummand;
                index++;
            }
        }
        return numbers ;
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