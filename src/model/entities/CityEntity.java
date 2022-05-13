package model.entities;


import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

/**
 * Representative for a sql serializable {@link model.game.City}
 */
public class CityEntity implements SQLData {
    public String name;
    public int viruses;
    public String typeName;

    @Override
    public String getSQLTypeName() {
        return typeName;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        this.typeName = typeName;
        name = stream.readString();
        viruses = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(name);
        stream.writeInt(viruses);
    }
}