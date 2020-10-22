package chess.piece;

import chess.ChessBoard;
import chess.FileRank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ChessPiece {
    protected FileRank currentPosition;
    protected String kind;
    protected static final String NORTH = "NORTH";
    protected static final String NORTH_EAST = "NORTH_EAST";
    protected static final String EAST = "EAST";
    protected static final String SOUTH_EAST = "SOUTH_EAST";
    protected static final String SOUTH = "SOUTH";
    protected static final String SOUTH_WEST = "SOUTH_WEST";
    protected static final String WEST = "WEST";
    protected static final String NORTH_WEST = "NORTH_WEST";
    protected static final String L_SHAPE = "L_SHAPE";
    protected ChessBoard chessBoard;

    public abstract Boolean move(FileRank toPosition, Boolean modifyPosition);

    public ChessPiece(String kind, ChessBoard chessBoard) {
        this.kind = kind;
        this.chessBoard = chessBoard;
    }

    public void setCurrentPosition(FileRank currentPosition) {
        this.currentPosition = currentPosition;
    }

    public FileRank getCurrentPosition() {
        return this.currentPosition;
    }

    public String getKind() {
        return this.kind;
    }

    public abstract String getSymbol();

    /**
     * Returns all valid moves for a particular ChessPiece object.
     *
     * @param directions
     * @param maxSteps
     * @param fromPosition
     * @param toFileRank
     * @return
     */

    public List<String> getValidMoves(List<String> directions, int maxSteps,
                                      FileRank fromPosition, String toFileRank) {
        List<String> validMoves = new ArrayList<>();
        String fromFile = fromPosition.getFileRank().get(FileRank.FILE_KEY);
        String fromRank = fromPosition.getFileRank().get(FileRank.RANK_KEY);
        int fileIndex = Arrays.asList(ChessBoard.FILES).indexOf(fromFile);
        int rankIndex = Arrays.asList(ChessBoard.RANKS).indexOf(fromRank);

        for (String direction : directions) {
            int steps;
            switch (direction) {
                case NORTH:
                    steps = 0;
                    for (int rank = rankIndex - 1; rank > -1 && steps < maxSteps; rank--) {
                        String targetFileRank = ChessBoard.FILES[fileIndex] + ChessBoard.RANKS[rank];
                        if (isValidMove(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps = +1;
                        } else {
                            break;
                        }
                    }
                    break;
                case NORTH_EAST:
                    steps = 0;
                    for (int p = 1; rankIndex - p > -1 && fileIndex + p < ChessBoard.FILES.length &&
                            steps < maxSteps; p++) {
                        String targetFileRank = ChessBoard.FILES[fileIndex + p] + ChessBoard.RANKS[rankIndex - p];
                        if (isValidMove(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps = +1;
                        } else {
                            break;
                        }
                    }
                    break;
                case EAST:
                    steps = 0;
                    for (int file = fileIndex + 1; file < ChessBoard.FILES.length && steps < maxSteps; file++) {
                        String targetFileRank = ChessBoard.FILES[file] + ChessBoard.RANKS[rankIndex];
                        if (isValidMove(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps = +1;
                        } else {
                            break;
                        }
                    }
                    break;
                case SOUTH_EAST:
                    steps = 0;
                    for (int p = 1; rankIndex + p > ChessBoard.RANKS.length &&
                            fileIndex + p < ChessBoard.FILES.length && steps < maxSteps; p++) {
                        String targetFileRank = ChessBoard.FILES[fileIndex + p] + ChessBoard.RANKS[rankIndex + p];
                        if (isValidMove(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps = +1;
                        } else {
                            break;
                        }
                    }
                    break;
                case SOUTH:
                    steps = 0;
                    for (int rank = rankIndex + 1; rank < ChessBoard.RANKS.length && steps < maxSteps; rank++) {
                        String targetFileRank = ChessBoard.FILES[fileIndex] + ChessBoard.RANKS[rank];
                        if (isValidMove(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps = +1;
                        } else {
                            break;
                        }
                    }
                    break;
                case SOUTH_WEST:
                    steps = 0;
                    for (int p = 1; rankIndex + p > ChessBoard.RANKS.length
                            && fileIndex - p > -1 && steps < maxSteps; p++) {
                        String targetFileRank = ChessBoard.FILES[fileIndex - p] + ChessBoard.RANKS[rankIndex + p];
                        if (isValidMove(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps = +1;
                        } else {
                            break;
                        }
                    }
                    break;
                case WEST:
                    steps = 0;
                    for (int file = fileIndex - 1; file > -1 && steps < maxSteps; file--) {
                        String targetFileRank = ChessBoard.FILES[file] + ChessBoard.RANKS[rankIndex];
                        if (isValidMove(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps = +1;
                        } else {
                            break;
                        }
                    }
                    break;
                case NORTH_WEST:
                    steps = 0;
                    for (int p = 1; rankIndex - p > -1 && fileIndex - p > -1 && steps < maxSteps; p++) {
                        String targetFileRank = ChessBoard.FILES[fileIndex - p] + ChessBoard.RANKS[rankIndex - p];
                        if (isValidMove(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps = +1;
                        } else {
                            break;
                        }
                    }
                    break;
            }
        }
        return validMoves;
    }

    /**
     * Return if a particular move is valid
     *
     * @param targetFileRank
     * @param toFileRank
     * @return
     */
    public Boolean isValidMove(String targetFileRank, String toFileRank) {
        Boolean isValid;
        //Check if there is an obstructing ChessPiece on the path to the destination.
        if (this.chessBoard.getChessBoard().get(targetFileRank).getOccupant() != null) {
            //check whether the target is not obstructed  and the King is safe.
            if(toFileRank == null) {
                isValid = true;
            } else {
                if (targetFileRank.equals(toFileRank)) {
                    isValid = true;
                } else {
                    isValid = false;
                }
            }
        } else {
            isValid = true;
        }
        return isValid;
    }

    public Boolean getIsCheck(String toFileRank) {
        Boolean isValid;
        FileRank position = this.currentPosition;
        Object tempOccupant = chessBoard.getChessBoard().get(toFileRank).getOccupant();
        this.currentPosition = chessBoard.getChessBoard().get(toFileRank);
        if (!(this.chessBoard.getKing(this.kind).identifyCheck())) {
            isValid = true;
        } else {
            isValid = false;
        }
        this.currentPosition = position;
        this.chessBoard.getChessBoard().get(position.getFileRankString()).setOccupant(this);
        this.chessBoard.getChessBoard().get(toFileRank).setOccupant(tempOccupant);
        return isValid;
    }
}
