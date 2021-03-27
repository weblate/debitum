package org.ebur.debitum.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO set exportSchema to true and define export location
@Database(entities = {Transaction.class, Person.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TransactionDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
    public abstract PersonDao personDao();

    // define a singleton TransactionDatabase to prevent having multiple instances of the database opened at the same time
    private static volatile TransactionDatabase INSTANCE;
    // create an ExecutorService with a fixed thread pool that will be used to run database operations asynchronously on a background thread
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /* returns the singleton. It'll create the database the first time it's accessed, using Room's
     * database builder to create a RoomDatabase object
     */
    static TransactionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TransactionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TransactionDatabase.class, "transaction_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // THIS IS FOR TESTING ONLY !!!
    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database
                TransactionDao transactionDao = INSTANCE.transactionDao();
                PersonDao personDao = INSTANCE.personDao();

                transactionDao.deleteAll();
                personDao.deleteAll();

                personDao.insert(new Person("Haushaltskasse"));
                personDao.insert(new Person("Natalie"));
                transactionDao.insert(new Transaction(personDao.getId("Haushaltskasse"), 799, true, "Netflix", new Date(1616493107)));
                transactionDao.insert(new Transaction(personDao.getId("Natalie"), -1000, true, "Pralinen", new Date(1610293082)));
                transactionDao.insert(new Transaction(personDao.getId("Natalie"), 1, false, "Buch", new Date(1609293082)));
            });
        }
    };

}
