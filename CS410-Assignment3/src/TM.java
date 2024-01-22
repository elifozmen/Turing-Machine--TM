import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TM {
    private int numberOfInputAlphabet;
    private int numberOfTapeAlphabet;
    private int numberOfStates;
    private ArrayList<String> statesList;
    private String startState;
    private ArrayList<String> acceptState;
    private ArrayList<String> rejectState;
    private char blankSymbol;
    private ArrayList<String> tapeAlphabet;
    private ArrayList<String> inputAlphabet;
    private ArrayList<String> transitions;

    private String currentState;
    private int tapeHead;
    private char[] tape;
    public StringBuilder routeStringBuilder;

    public TM(int numberOfInputAlphabet, int numberOfTapeAlphabet, int numberOfStates,
              ArrayList<String> statesList, ArrayList<String> acceptState, ArrayList<String> rejectState,
              char blankSymbol, ArrayList<String> tapeAlphabet, ArrayList<String> inputAlphabet,
              ArrayList<String> transitionLines) {
        this.numberOfInputAlphabet = numberOfInputAlphabet;
        this.numberOfTapeAlphabet = numberOfTapeAlphabet;
        this.numberOfStates = numberOfStates;
        this.statesList = statesList;
        this.startState = statesList.get(0);
        this.acceptState = acceptState;
        this.rejectState = rejectState;
        this.blankSymbol = blankSymbol;
        this.tapeAlphabet = tapeAlphabet;
        this.inputAlphabet = inputAlphabet;
        this.transitions = transitionLines;
    }


    public void simulateTM(ArrayList<String> inputLines, String outputFileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (String inputLine : inputLines) {
                System.out.println("Input: " + inputLine);
                String result = processInput(inputLine);
                writer.write("Input: " + inputLine);
                writer.newLine();


                if ("accepted".equals(result)) {
                    writer.write(routeStringBuilder.toString());
                    writer.newLine();
                    writer.write("String " + inputLine + " accepted");
                    writer.newLine();
                    System.out.println("String " + inputLine + " accepted");
                } else if ("rejected".equals(result)) {
                    writer.write(routeStringBuilder.toString());
                    writer.newLine();
                    writer.write("String " + inputLine + " rejected");
                    writer.newLine();
                    System.out.println("String " + inputLine + " rejected");
                } else if ("looped".equals(result)) {
                    writer.write(routeStringBuilder.toString());
                    writer.newLine();
                    System.out.println("String " + inputLine + " Looped");
                    writer.write("String " + inputLine + " looped");
                    writer.newLine();
                }
                writer.newLine();

                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String processInput(String inputLine) {
        currentState = startState;
        tapeHead = 1; // start from the first position of the tape
        tape = new char[inputLine.length() + 2];
        Arrays.fill(tape, blankSymbol);
        for (int i = 0; i < inputLine.length(); i++) {
            tape[i + 1] = inputLine.charAt(i);
        }

        ArrayList<String> route = new ArrayList<>();
        route.add(currentState);

        while (true) {
           String transition = findTransition(currentState + " " + String.valueOf(tape[tapeHead]));
            if (transition == null) {
                System.out.println("Transition not found for key: " + currentState + " " + String.valueOf(tape[tapeHead]));
                return "rejected";
            }

            String[] parts = transition.split(" ");
            currentState = parts[4];


            tape[tapeHead] = parts[2].charAt(0); //update tape

            // move tape head
            if (parts[3].equals("R")) {
                tapeHead++;
            } else if (parts[3].equals("L")) {
                tapeHead--;
            }


            route.add(currentState);


            if (acceptState.contains(currentState)) {
                //System.out.println("Accepted!");
                printRoute(route);
                //route.add(currentState);
                return "accepted";
            }


            if (rejectState.contains(currentState)) {
                //System.out.println("Rejected!");
                printRoute(route);
                //route.add(currentState);
                return "rejected";
            }

            // check if the tape head goes out of bounds
            if (tapeHead < 0 || tapeHead >= tape.length) {
                //System.out.println("Looped!");
                printRoute(route);
                return "looped";
            }



        }
    }

    private void printRoute(ArrayList<String> route) {
        routeStringBuilder = new StringBuilder("Route taken: ");
        for (String state : route) {
            routeStringBuilder.append(state).append(" ");
        }
        System.out.println(routeStringBuilder.toString());
    }




    private String findTransition(String transitionKey) {
        for (String transition : transitions) {
            if (transition.startsWith(transitionKey)) {
                return transition;
            }
        }
        System.out.println("No transition found for key: " + transitionKey);
        return null;
    }

}
