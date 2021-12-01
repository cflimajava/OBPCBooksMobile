package br.com.obpcbooks.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import br.com.obpcbooks.database.ObpcDatabase;
import br.com.obpcbooks.database.daos.UserDAO;

public class LimparUsuarioLogadosTask extends AsyncTask<Void, Void, Void> {

    private UserDAO dao;

    public LimparUsuarioLogadosTask(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.liparUsuarioLogados();
        return null;
    }

}
