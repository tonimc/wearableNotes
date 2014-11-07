package com.toniousli.wearablenotes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "wearable_notes.db";
    private static final int DATABASE_VERSION = 4;

    // the DAO object we use to access the SimpleData table
    private Dao<Note, Integer> notes = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Note.class);

            this.notes = getNotesDao();
            this.notes.create(new Note("Nota 1", "svsdfgsfdsfsfsfsdfsdfwrekrwlkrjw -P-rwelkrj welkrj ewlk jrlwe jrwelk rw lkerlkwjerlkjwer -P- sdfsdkflsd jfkls jdflskd jfklsfklds flkdsj fsd kjf"));
            this.notes.create(new Note("Nota 2", "kjfelsrkfj-P- sdfsdkflsd jfkls jdflskd jfklsfklds flkdsj fsd kjf"));
            this.notes.create(new Note("Nota 3", "reklltnerltkjelrjkt-P- sdfsdkflsd jfkls jdflskd jfklsfklds flkdsj fsd kjf"));
            this.notes.create(new Note("Nota 4", "sswrkjlwkerlkjweklr-P- sdfsdkflsd jfkls jdflskd jfklsfklds flkdsj fsd kjf"));
            this.notes.create(new Note("Nota 5", "lkmsdlkmsdklmfssss-P- sdfsdkflsd jfkls jdflskd jfklsfklds flkdsj fsd kjf"));
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Note.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<Note, Integer> getNotesDao() throws SQLException {
        if (notes == null) {
            notes = getDao(Note.class);
        }
        return notes;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        notes = null;
    }
}
