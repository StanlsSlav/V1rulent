package model.entities;


import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

/**
 * Representative for a sql serializable {@link model.game.Player}
 */
public class PlayerEntity implements SQLData {
    public String name;
    public int actionsLeft;
    public String typeName;

    @Override
    public String getSQLTypeName() {
        return typeName;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        this.typeName = typeName;
        name = stream.readString();
        actionsLeft = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(name);
        stream.writeInt(actionsLeft);
    }
}