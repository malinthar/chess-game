package chess;

import chess.piece.Bishop;
import chess.piece.ChessPiece;
import chess.piece.King;
import chess.piece.Knight;
import chess.piece.Pawn;
import chess.piece.Queen;
import chess.piece.Rook;

import java.util.HashMap;
import java.util.Map;

public class ChessBoard {
    public static final String[] FILES = {"a", "b", "c", "d", "e", "f", "g", "h"};
    public static final String[] RANKS = {"8", "7", "6", "5", "4", "3", "2", "1"};
    private static final String WHITE_SQUARE = "  ";
    private static final String BLACK_SQUARE = "##";
    public static final String WHITE_KIND = "WHITE";
    public static final String BLACK_KIND = "BLACK";

    private Map<String, FileRank> chessBoard = new HashMap<>();
    private King whiteKing;
    private King blackKing;

    public ChessBoard() {
        initialize();
    }

    public void display() {
        for (int i = 0; i < FILES.length; i++) {
            System.out.print(FILES[i] + "  ");
        }
        System.out.print("\n");
        for (int rank = 0; rank < RANKS.length; rank++) {
            for (int file = 0; file < FILES.length; file++) {
                String filerank = FILES[file] + RANKS[rank];
                Object occupant = chessBoard.get(filerank).getOccupant();
                if (occupant == null) {
                    if (((file) % 2 == 1 && (rank) % 2 == 1) | ((file) % 2 == 0 && (rank) % 2 == 0)) {
                        System.out.print(BLACK_SQUARE + " ");
                    } else {
                        System.out.print(WHITE_SQUARE + " ");
                    }
                } else {
                    System.out.print(((ChessPiece) occupant).getSymbol() + " ");
                }
            }
            System.out.print(RANKS[rank]);
            System.out.print("\n");
        }
    }

    public void initialize() {
        for (int rank = 0; rank < RANKS.length; rank++) {
            for (int file = 0; file < FILES.length; file++) {
                String filerank = FILES[file] + RANKS[rank];
                if ("1".equals(RANKS[rank]) | "8".equals(RANKS[rank])) {
                    String kind;
                    if ("1".equals(RANKS[rank])) {
                        kind = WHITE_KIND;
                    } else {
                        kind = BLACK_KIND;
                    }
                    if (King.INITIAL_POSITIONS.contains(FILES[file])) {
                        chessBoard.put(filerank, new FileRank(FILES[file], RANKS[rank], new King(kind, this)));
                        if (WHITE_KIND.equals(kind)) {
                            whiteKing = (King) chessBoard.get(filerank).getOccupant();
                        } else {
                            blackKing = (King) chessBoard.get(filerank).getOccupant();
                        }
                    } else if (Knight.INITIAL_POSITIONS.contains(FILES[file])) {
                        chessBoard.put(filerank, new FileRank(FILES[file], RANKS[rank], new Knight(kind, this)));
                    } else if (Queen.INITIAL_POSITIONS.contains(FILES[file])) {
                        chessBoard.put(filerank, new FileRank(FILES[file], RANKS[rank], new Queen(kind, this)));
                    } else if (Bishop.INITIAL_POSITIONS.contains(FILES[file])) {
                        chessBoard.put(filerank, new FileRank(FILES[file], RANKS[rank], new Bishop(kind, this)));
                    } else if (Rook.INITIAL_POSITIONS.contains(FILES[file])) {
                        chessBoard.put(filerank, new FileRank(FILES[file], RANKS[rank], new Rook(kind, this)));
                    }
                } else if ("2".equals(RANKS[rank])) {
                    chessBoard.put(filerank, new FileRank(FILES[file], RANKS[rank], new Pawn(WHITE_KIND, this)));
                } else if ("7".equals(RANKS[rank])) {
                    chessBoard.put(filerank, new FileRank(FILES[file], RANKS[rank], new Pawn(BLACK_KIND, this)));
                } else {
                    chessBoard.put(filerank, new FileRank(FILES[file], RANKS[rank], null));
                }
            }
        }
    }

    public Boolean move(String from, String to, String turn) {
        Boolean isValid;
        FileRank fromPosition = chessBoard.get(from);
        FileRank toPosition = chessBoard.get(to);
        if (fromPosition.getOccupant() != null) {
            ChessPiece movingPiece = (ChessPiece) fromPosition.getOccupant();
            if (toPosition.getOccupant() != null && movingPiece.getKind().equals(turn)) {
                ChessPiece restingPiece = (ChessPiece) toPosition.getOccupant();
                if (restingPiece.getKind().equals(movingPiece.getKind())) {
                    isValid = false;
                } else {
                    isValid = movingPiece.move(toPosition,true);
                }
            } else if(movingPiece.getKind().equals(turn)) {
                isValid = movingPiece.move(toPosition,true);
            }
            else {
                isValid = false;
            }
            if(isValid) {
                chessBoard.get(to).setOccupant(movingPiece);
                chessBoard.get(from).setOccupant(null);
            }
        } else {
            isValid = false;
        }

        return isValid;
    }

    public King getKing(String kind) {
        if(kind.equals(WHITE_KIND)) {
            return whiteKing;
        } else {
            return blackKing;
        }
    }

    public Map<String,FileRank> getChessBoard() {
        return this.chessBoard;
    }

}
