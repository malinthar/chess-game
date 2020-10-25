package chess.piece;

import chess.ChessBoard;
import chess.FileRank;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pawn chess piece.
 */
public class Pawn extends ChessPiece {
    private static final String SYMBOL = "p";
    private static final List<String> WHITE_DIRECTIONS = Arrays.asList(NORTH);
    private static final List<String> BLACK_DIRECTIONS = Arrays.asList(SOUTH);
    private static final Map<String, List<String>> DIRECTIONS = new HashMap<>();
    private String promotion = "Q";

    //assign directions based on the kind.
    static {
        DIRECTIONS.put(ChessBoard.WHITE_KIND, WHITE_DIRECTIONS);
        DIRECTIONS.put(ChessBoard.BLACK_KIND, BLACK_DIRECTIONS);
    }

    public Pawn(String kind, ChessBoard chessBoard) {
        super(kind, chessBoard);
    }

    /**
     * Ensures that a chess piece of opponent kind is available to move diagonally,
     * and returns if the move is valid.
     * @param toPosition
     * @return
     */
    public Boolean moveDiagonal(FileRank toPosition) {
        String fromFile = this.currentPosition.getFileRank().get(FileRank.FILE_KEY);
        String fromRank = this.currentPosition.getFileRank().get(FileRank.RANK_KEY);
        int fromFileIndex = Arrays.asList(ChessBoard.FILES).indexOf(fromFile);
        int fromRankIndex = Arrays.asList(ChessBoard.RANKS).indexOf(fromRank);
        if (this.kind.equals(ChessBoard.WHITE_KIND)) {
            if (fromFileIndex + 1 < ChessBoard.FILES.length && fromRankIndex - 1 > -1) {
                if (toPosition.getFileRankString().equals(ChessBoard.FILES[fromFileIndex + 1] + ChessBoard.RANKS[fromRankIndex - 1])) {
                    return true;
                }
            }
            if (fromFileIndex - 1 > -1 && fromRankIndex - 1 > -1) {
                if (toPosition.getFileRankString().equals(ChessBoard.FILES[fromFileIndex - 1] + ChessBoard.RANKS[fromRankIndex - 1])) {
                    return true;
                }
            }
        } else {
            if (fromFileIndex + 1 < ChessBoard.FILES.length && fromRankIndex + 1 < ChessBoard.RANKS.length) {
                if (toPosition.getFileRankString().equals(ChessBoard.FILES[fromFileIndex + 1] + ChessBoard.RANKS[fromRankIndex + 1])) {
                    return true;
                }
            }
            if (fromFileIndex - 1 > -1 && fromRankIndex + 1 < ChessBoard.RANKS.length) {
                if (toPosition.getFileRankString().equals(ChessBoard.FILES[fromFileIndex - 1] + ChessBoard.RANKS[fromRankIndex + 1])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the MaxSteps for Pawn depending on the current position.
     * @return
     */
    public int getMaxSteps() {
        int maxSteps;
        //Enpassant move
        if ((this.kind.equals(ChessBoard.WHITE_KIND) &&
                this.currentPosition.getFileRank().get(FileRank.RANK_KEY).equals(ChessBoard.RANKS[6])) |
                (this.kind.equals(ChessBoard.BLACK_KIND)
                        && this.currentPosition.getFileRank().get(FileRank.RANK_KEY).equals(ChessBoard.RANKS[1]))) {
            maxSteps = 2;
        } else {
            maxSteps = 1;
        }
        return maxSteps;
    }

    @Override
    public Boolean move(FileRank toPosition, Boolean modifyPosition) {
        Boolean isValid;
        String toFileRank = toPosition.getFileRank().get(FileRank.FILE_KEY) +
                toPosition.getFileRank().get(FileRank.RANK_KEY);
        int maxSteps = getMaxSteps();
        if (toPosition.getOccupant() == null) {
            return validateMoveAndUpdatePosition(
                    getValidMoves(DIRECTIONS.get(this.kind),
                    maxSteps,this.currentPosition,toFileRank),
                    toFileRank,modifyPosition,toPosition);
        } else {
            if (moveDiagonal(toPosition) && !getIsKingChecked(toFileRank)) {
                isValid = true;
                if(modifyPosition) {
                    this.currentPosition = toPosition;
                }
            } else {
                isValid = false;
            }
        }
        return isValid;
    }

    @Override
    public String getSymbol() {
        if ("BLACK".equals(kind)) {
            return "b" + SYMBOL;
        } else {
            return "w" + SYMBOL;
        }
    }

    /**
     * Set the promotion type.
     * @param promotion
     */
    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    /**
     * Returns the current promotion type.
     * @return
     */
    public String getPromotion() {
        return this.promotion;
    }
}
