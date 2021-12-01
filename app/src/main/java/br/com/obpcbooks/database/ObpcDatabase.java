package br.com.obpcbooks.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.obpcbooks.database.daos.UserDAO;
import br.com.obpcbooks.model.User;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class ObpcDatabase extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "obpc.db";

    public abstract UserDAO getUserDAO();

    public static ObpcDatabase getInstance(Context context){
        return Room.databaseBuilder(
                context,
                ObpcDatabase.class,
                NOME_BANCO_DE_DADOS)
                .fallbackToDestructiveMigration()
                .build();
    }


}
