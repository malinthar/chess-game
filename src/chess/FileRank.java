package chess;

import chess.piece.ChessPiece;

import java.util.HashMap;
import java.util.Map;

public class FileRank {
    public static final String FILE_KEY = "file";
    public static final String RANK_KEY = "rank";
    private Map<String,String> filerank = new HashMap<>();
    private Object occupant;
    private String id;

    public FileRank(String file, String rank, Object initialOccupant) {
        this.id = file + rank;
        filerank.put(FILE_KEY, file);
        filerank.put(RANK_KEY, rank);
        if(initialOccupant != null && initialOccupant instanceof ChessPiece) {
            ((ChessPiece) initialOccupant).setCurrentPosition(this);
        }
        this.occupant = initialOccupant;
    }

    public Map<String, String> getFileRank() {
        return filerank;
    }

    public String getFileRankString() {
        return filerank.get(FILE_KEY) + filerank.get(RANK_KEY);
    }

    public void setOccupant(Object occupant) {
        this.occupant = occupant;
    }

    public Object getOccupant() {
        return this.occupant;
    }
}
