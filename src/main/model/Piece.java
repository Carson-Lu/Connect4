package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a connect 4 Piece
public class Piece implements Writable {

    private int posX;
    private int posY;
    private boolean player;

    // EFFECTS: creates a piece at given posX and posY and also sets player to given boolean (their piece)
    public Piece(int posX, int posY, boolean player) {
        this.posX = posX;
        this.posY = posY;
        this.player = player;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public boolean getPlayer() {
        return this.player;
    }

    // EFFECTS: converts Piece fields to json, will throw a NullPointerException if unable to convert to json
    // CITATION:
    // This class code is modeled after Thingy class in JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Override
    public JSONObject toJson() throws NullPointerException {
        JSONObject jsonPiece = new JSONObject();
        jsonPiece.put("posX", posX);
        jsonPiece.put("posY", posY);
        jsonPiece.put("player", player);
        return jsonPiece;
    }
}
