import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter input file name: ");
        String filePath = scanner.nextLine();

        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (!lines.isEmpty()) {
            String firstLine = lines.get(0).trim(); //number of variables in input alphabet
            String secondLine = lines.get(1).trim(); //number of variables in tape alphabet
            String thirdLine = lines.get(2).trim(); //number of states
            String fourthLine = lines.get(3); //states
            String fifthLine = lines.get(4); //startState
            String sixthLine = lines.get(5); //accept state
            String seventhLine = lines.get(6); //reject state
            String eighthLine = lines.get(7).trim(); //blank symbol
            String ninethLine = lines.get(8); //the tape alphabet
            String tenthLine = lines.get(9); //the input alphabet


            try {
                int numberOfInputAlphabet = Integer.parseInt(firstLine);
                int numberOfTapeAlphabet = Integer.parseInt(secondLine);
                int numberOfStates = Integer.parseInt(thirdLine);


                ArrayList<String> statesList = new ArrayList<>(Arrays.asList(fourthLine.split(" ")));
                ArrayList<String>  startState= new ArrayList<>(Arrays.asList(fifthLine.split(" ")));
                ArrayList<String> acceptState = new ArrayList<>(Arrays.asList(sixthLine.split(" ")));
                ArrayList<String> rejectState = new ArrayList<>(Arrays.asList(seventhLine.split(" ")));
                char blankSymbol = eighthLine.charAt(0);
                ArrayList<String> tapeAlphabet = new ArrayList<>(Arrays.asList(ninethLine.split(" ")));
                ArrayList<String> inputAlphabet = new ArrayList<>(Arrays.asList(tenthLine.split(" ")));

                //System.out.println(numberOfInputAlphabet);
                //System.out.println(numberOfTapeAlphabet);
                //System.out.println(numberOfStates);
                //System.out.println(statesList);
                //System.out.println(startState);
                //System.out.println(acceptState);
                //System.out.println(rejectState);
                //System.out.println(blankSymbol);
                //System.out.println(tapeAlphabet);
                //System.out.println(inputAlphabet);



                int transitionLineCount = 15;

                ArrayList<String> transitionLines = new ArrayList<>();
                for (int i = 10; i < 10 + transitionLineCount && i < lines.size(); i++) {
                    String transitionLine = lines.get(i).trim();
                    transitionLines.add(transitionLine);
                    //System.out.println("Transition line " + i + ": " + transitionLine);
                }

                System.out.print("Enter output file name: ");
                String outputFileName = scanner.nextLine();

                TM turingMachine = new TM(numberOfInputAlphabet, numberOfTapeAlphabet,
                        numberOfStates,
                        statesList, acceptState, rejectState, blankSymbol,
                        tapeAlphabet, inputAlphabet, transitionLines);

                ArrayList<String> inputLines = new ArrayList<>();
                int startLine = transitionLineCount + 10;
                for (int i = startLine; i < lines.size(); i++) {
                    String inputLine = lines.get(i).trim();
                    inputLines.add(inputLine);
                }

                //call TM with inputLines and outputFileName
                turingMachine.simulateTM(inputLines, outputFileName);

            } catch (NumberFormatException e) {
                System.err.println("error");
                e.printStackTrace();
            }

        }
    }
}