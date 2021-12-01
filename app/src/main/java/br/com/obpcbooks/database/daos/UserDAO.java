package br.com.obpcbooks.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import br.com.obpcbooks.model.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long salva(User user);

    @Update
    void atualiza(User user);

    @Query("SELECT * FROM User WHERE id = :id")
    User buscaUsuarioPeloId(long id);

    @Query("SELECT * FROM User")
    User bucarUsuarioLogado();

    @Query("DELETE FROM User")
    void liparUsuarioLogados();

}
