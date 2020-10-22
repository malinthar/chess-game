package chess.piece;

import chess.ChessBoard;
import chess.FileRank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight extends ChessPiece {

    public static final List<String> INITIAL_POSITIONS = Arrays.asList("b", "g");
    private static final String SYMBOL = "N";

    public Knight(String kind, ChessBoard chessBoard) {
        super(kind, chessBoard);
    }

    public List<String> generateToPositions(FileRank fromPosition) {
        List<String> toPositions = new ArrayList<>();
        String fromFile = fromPosition.getFileRank().get(FileRank.FILE_KEY);
        String fromRank = fromPosition.getFileRank().get(FileRank.RANK_KEY);
        int fileIndex = Arrays.asList(ChessBoard.FILES).indexOf(fromFile);
        int rankIndex = Arrays.asList(ChessBoard.RANKS).indexOf(fromRank);
        int filesLength = ChessBoard.FILES.length;
        int ranksLength = ChessBoard.RANKS.length;
        if (fileIndex + 2 < filesLength && rankIndex + 1 < ranksLength) {
            toPositions.add(ChessBoard.FILES[fileIndex + 2] + ChessBoard.RANKS[rankIndex + 1]);
        }
        if (fileIndex + 1 < filesLength && rankIndex + 2 < ranksLength) {
            toPositions.add(ChessBoard.FILES[fileIndex + 1] + ChessBoard.RANKS[rankIndex + 2]);
        }
        if (fileIndex - 1 > -1 && rankIndex + 2 < ranksLength) {
            toPositions.add(ChessBoard.FILES[fileIndex - 1] + ChessBoard.RANKS[rankIndex + 2]);
        }
        if (fileIndex - 2 > -1 && rankIndex + 1 < ranksLength) {
            toPositions.add(ChessBoard.FILES[fileIndex - 2] + ChessBoard.RANKS[rankIndex + 1]);
        }
        if (fileIndex - 2 > -1 && rankIndex - 1 > -1) {
            toPositions.add(ChessBoard.FILES[fileIndex - 2] + ChessBoard.RANKS[rankIndex - 1]);
        }
        if (fileIndex - 1 > -1 && rankIndex - 2 > -1) {
            toPositions.add(ChessBoard.FILES[fileIndex - 1] + ChessBoard.RANKS[rankIndex - 2]);
        }
        if (fileIndex + 1 < filesLength && rankIndex - 2 > -1) {
            toPositions.add(ChessBoard.FILES[fileIndex + 1] + ChessBoard.RANKS[rankIndex - 2]);
        }
        if (fileIndex + 2 < filesLength && rankIndex - 1 > -1) {
            toPositions.add(ChessBoard.FILES[fileIndex + 2] + ChessBoard.RANKS[rankIndex - 1]);
        }
        if (fileIndex + 2 < filesLength && rankIndex + 1 > filesLength) {
            toPositions.add(ChessBoard.FILES[fileIndex + 2] + ChessBoard.RANKS[rankIndex + 1]);
        }
        return toPositions;
    }

    @Override
    public String getSymbol() {
        if ("BLACK".equals(kind)) {
            return "b" + SYMBOL;
        } else {
            return "w" + SYMBOL;
        }
    }

    @Override
    public Boolean move(FileRank toPosition, Boolean modifyPosition) {
        Boolean isValid = false;
        String toFileRank = toPosition.getFileRank().get(FileRank.FILE_KEY) + toPosition.getFileRank().get(FileRank.RANK_KEY);
        for (String position : generateToPositions(this.currentPosition)) {
            if (toFileRank.equals(position)) {
                if(modifyPosition && getIsCheck(toFileRank)) {
                    isValid = true;
                    this.currentPosition = toPosition;
                } else if (modifyPosition){
                    isValid = false;
                } else{
                    isValid = true;
                }
                break;
            }
        }
        return isValid;
    }
}
