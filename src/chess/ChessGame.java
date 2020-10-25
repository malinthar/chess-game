package chess;

import java.util.Scanner;

/**
 * Chess game controller.
 */
public class ChessGame {
    private static final String WHITE_TURN = ChessBoard.WHITE_KIND;
    private static final String BLACK_TURN = ChessBoard.BLACK_KIND;
    private static final String WHITES_TURN_MESSAGE = "White's move: ";
    private static final String BLACKS_TURN_MESSAGE = "Black's move: ";
    private static final String WHITE_WIN = "White wins";
    private static final String BLACK_WIN = "Black wins";
    private static final String ILLEGAL_MOVE = "Illegal move, try again";
    private static final String RESIGN = "resign";
    private static final String DRAW_PROMPT = "draw?";
    private static final String DRAW = "draw";
    private static final String CHECK = "Check";
    private static final String CHECKMATE = "Checkmate";
    private ChessBoard chessBoard;
    private Boolean gameOver;
    private String winner;
    private String turn;
    private Boolean draw_prompt = false;

    public ChessGame() {
        this.chessBoard = new ChessBoard();
        this.gameOver = false;
        this.turn = WHITE_TURN;
    }

    /**
     * Validate user input and performs actions based on the input.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            //display the chessboard
            System.out.println("\n");
            chessBoard.display();
            if (WHITE_TURN.equals(turn)) {
                System.out.print("\n" + WHITES_TURN_MESSAGE);
            } else {
                System.out.print("\n" + BLACKS_TURN_MESSAGE);
            }

            //get the user input for stdin
            String input = scanner.nextLine();
            String[] inArr = input.split("\\s+");

            //Resign
            if (inArr.length == 1 && RESIGN.equals(inArr[0])) {
                if (turn.equals(WHITE_TURN)) {
                    System.out.println(BLACK_WIN);
                } else {
                    System.out.println(WHITE_WIN);
                }

                //Draw prompt
            } else if (inArr.length == 3 && DRAW_PROMPT.equals(inArr[2])) {
                draw_prompt = true;

                //Accept draw
            } else if (draw_prompt && inArr.length == 1 && DRAW.equals(inArr[0])) {
                break;

                //move chess piece
            }  else if (inArr.length == 2 | inArr.length == 3) {
                Boolean isValid;
                //perform regular move and get validity of the move
                if(inArr.length == 2) {
                    isValid = chessBoard.move(inArr[0], inArr[1], turn);

                //move with  promotion and get validity
                } else {
                    isValid = chessBoard.promotePawn(inArr[0], inArr[1], turn, inArr[2]);
                }

                //if a valid move is performed, evaluate the status of the game
                if (isValid) {
                    if (WHITE_TURN.equals(turn)) {
                        // check if opponent's king is check | game is checkmate
                        if (chessBoard.getKing(ChessBoard.BLACK_KIND).identifyCheck()) {
                            if (chessBoard.getKing(ChessBoard.BLACK_KIND).getIsCheckMate()) {
                                System.out.print(CHECKMATE + "\n");
                                System.out.print(WHITE_WIN);
                                break;
                            } else {
                                System.out.print(CHECK);
                            }
                        }
                        turn = BLACK_TURN;
                    } else {
                        // check if opponent's king is check | game is checkmate
                        if (chessBoard.getKing(ChessBoard.WHITE_KIND).identifyCheck()) {
                            if (chessBoard.getKing(ChessBoard.WHITE_KIND).getIsCheckMate()) {
                                System.out.print(CHECKMATE + "\n");
                                System.out.print(BLACK_WIN);
                                break;
                            } else {
                                System.out.print(CHECK);
                            }
                        }
                        turn = WHITE_TURN;
                    }
                } else {
                    System.out.println(ILLEGAL_MOVE);
                }
            } else {
                System.out.println(ILLEGAL_MOVE);
            }
        }

    }
}
