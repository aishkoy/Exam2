import java.util.Arrays;

public class Chernovik {
    public static void main(String[] args) {
//        int[][] numbers = breakNumIntoTwoSummands(5);
//        for (int i = 0; i < numbers.length; i++) {
//            println(numbers[i][0] + " " + numbers[i][1]);
//        }

//        showFinalScore();
    }

//    public static void showFinalScore(){
//        String[] rounds = {"- 1 -", "- 2 -", "- 3 -"};
//        String lineSeparator = "|";
//        println("----------------------- Finish Game -----------------------");
//        printf("%8s%4s%12s%12s%15s%n", "Round", lineSeparator, "User", lineSeparator, "Computer");
//        println("-----------+-----------------------+-----------------------");
//        for(int i = 0; i < 3; i++){
//            printf("%12s%12s%9d%3s%12s%9d%n", lineSeparator, "Predicted:", -11, lineSeparator,"Predicted:", 11);
//            printf("%8s%4s%7s%14d%3s%7s%14d%n",rounds[i], lineSeparator, "Dice:", 11, lineSeparator,"Dice:", 11);
//            printf("%12s%9s%12d%3s%9s%12d%n", lineSeparator, "Result:", 11, lineSeparator,"Result:", 11);
//            println("-----------+-----------------------+-----------------------");
//        }
//        printf("%8s%4s%9s%12d%3s%9s%12d%n","Total", lineSeparator, "Points:", 11, lineSeparator,"Points:", 11);
//
//    }

//    public static int[][] breakNumIntoTwoSummands(int userDicesSum){
//        int count = 0;
//        for (int i = 1; i <= 6; i++) {
//            int secondSummand = userDicesSum - i;
//            if (secondSummand >= 1 && secondSummand <= 6) {
//                count++;
//            }
//        }
//
//        int[][] numbers = new int[count][2];
//        int index = 0;
//
//        for(int i = 1; i <= 6; i++){
//            int secondSummand = userDicesSum - i;
//            if (secondSummand >= 1 && secondSummand <= 6) {
//                numbers[index][0] = i;
//                numbers[index][1] = secondSummand;
//                index++;
//            }
//        }
//        return numbers ;
//    }

    public static void printf(String format, Object... args){
        System.out.printf(format, args);
    }

    public static void println(String format){
        System.out.println(format);
    }
}
