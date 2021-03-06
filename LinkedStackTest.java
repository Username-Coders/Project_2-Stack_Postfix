import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

public class LinkedStackTest {

    /** Converts the input infix expression to a postfix expression.
    * @param infix The infix input to be converted to postfix.
    * @return char[] The converted postfix expression.
    */
   public static String convertToPostfix(String infix) {
        // create a new stack, whose data are of the character type
        StackInterface<Character> operatorStack = new LinkedStack<>();

        String postfix = "";
        char topOperator = ' ';
        char[] infixChar = new char[25];

        int infixLength = 0;
        int currentPosition = 0;
        int nextCharPreced = 0;
        int peekPreced = 0;

        if (infix != null && !infix.isEmpty()) {
            infixChar = infix.toCharArray();
            infixLength = infix.length();
        } else {
            System.out.println("Input string infix is either empty/null!");
        }

        while (currentPosition < infixLength) {
            //postfixChar = postfix.toCharArray();
            char nextCharacter = infixChar[currentPosition];
            
            // determine the precedence of the operator at the top of the stack
            if (!operatorStack.isEmpty()) {
                peekPreced = getPrecedence(operatorStack.peek());
            }
            // determine the precedence of nextCharacter
            nextCharPreced = getPrecedence(nextCharacter);

            // determine what operator is next, and perform actions accordingly
            if (nextCharacter >= 'a' && nextCharacter <= 'z') {
                postfix += nextCharacter;
                currentPosition++;
            } else if (nextCharacter == '^' || nextCharacter == '(') {
                operatorStack.push(nextCharacter);
                currentPosition++;
            } else if (nextCharacter == '+' || nextCharacter == '-' || nextCharacter == '*' || nextCharacter == '/') {
                while (!operatorStack.isEmpty() && nextCharPreced <= peekPreced) {
                    postfix += operatorStack.peek();
                    operatorStack.pop();
                }
                operatorStack.push(nextCharacter);
                currentPosition++;
            } else if (nextCharacter == ')') {
                    topOperator = operatorStack.pop();
                    while (topOperator != '(') {
                        postfix = postfix + topOperator;
                        topOperator = operatorStack.pop();
                    }
                    currentPosition++;
            } else {
                currentPosition++;
            }
        }

        while (!operatorStack.isEmpty()) {
            topOperator = operatorStack.pop();
            postfix = postfix + topOperator;
        }
        return postfix;
    } // end of ConvertToPostfix


    /**
     * Determines the precedence of a given operator.
     * @param operator The operator whose precedence will be determined.
     * @return Integer value of the operator's precedence.
     */
    // Unit Testing for this method is in this file, since it is private
    private static int getPrecedence(char operator) {
        int precedence = 0;
       
        switch (operator) {
          case '*':
             precedence = 2;
             break;
          case '/':
             precedence = 2;
             break;
          case '+':
             precedence = 1;
             break;
          case '-':
             precedence = 1;
             break;
          default:
             break;
       }
 
       return precedence;
     } // end of getPrecedence
    
    public static void main(String[] args) {
        /**
         * Demo the functionality of the algorithm convertToPostfix
         */

        // infix expressions to be converted to postfix for demo
        String infix1 = "a/b*(c+(d-e))"; 
        String infix2 = "a*b/(c-a)+d*e";

        String postfix1 = convertToPostfix(infix1);
        String postfix2 = convertToPostfix(infix2);

        // outputing results formated to show the starting input and the output of algorithm
        System.out.println("Coverting infix to postfix: \n");
        System.out.println("Infix: " + infix1 + " ->  Postfix: " + postfix1 + "\n");
        System.out.println("Infix: " + infix2 + " ->  Postfix: " + postfix2 + "\n");
       
    }

     /**
     * Testing getPrecedence (Private method)
     */

    // Testing getPrecedence with valid operator input
    @Test
    public void getPrecedence_ValidInput() {
        char[] operatorArray = {'+','-','*','/','(',')'};
      
        int expectedValue1 = 1;
        int expectedValue2 = 2;
        int expectedValue3 = 0;

        assertEquals(expectedValue1, LinkedStackTest.getPrecedence(operatorArray[0]));
        assertEquals(expectedValue1, LinkedStackTest.getPrecedence(operatorArray[1]));

        assertEquals(expectedValue2, LinkedStackTest.getPrecedence(operatorArray[2]));
        assertEquals(expectedValue2, LinkedStackTest.getPrecedence(operatorArray[3]));

        assertEquals(expectedValue3, LinkedStackTest.getPrecedence(operatorArray[4]));
        assertEquals(expectedValue3, LinkedStackTest.getPrecedence(operatorArray[5]));
    }

    // Testing getPrecedence with invalid input
    @Test
    public void getPrecedence_InvalidInput() {
        char invalidOperator1 = '=';
        char invalidOperator2 = '`';
        char invalidOperator3 = 'b';
        int expectedResult = 0;

        assertEquals(expectedResult,getPrecedence(invalidOperator1));
        assertEquals(expectedResult,getPrecedence(invalidOperator2));
        assertEquals(expectedResult,getPrecedence(invalidOperator3));

    }

}
