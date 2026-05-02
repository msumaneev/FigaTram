package com.figatram.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.figatram.data.local.dao.TramDao;
import com.figatram.data.local.dao.TramDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FigaTramDatabase_Impl extends FigaTramDatabase {
  private volatile TramDao _tramDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `arrival_stats` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `vehicleId` TEXT NOT NULL, `stationId` TEXT NOT NULL, `timestampArrived` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tram_info` (`vehicleId` TEXT NOT NULL, `model` TEXT, `description` TEXT, `photoUri` TEXT, `isFavorite` INTEGER NOT NULL, PRIMARY KEY(`vehicleId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '0ac6ea12c79b24e35faadb837567473d')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `arrival_stats`");
        db.execSQL("DROP TABLE IF EXISTS `tram_info`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsArrivalStats = new HashMap<String, TableInfo.Column>(4);
        _columnsArrivalStats.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivalStats.put("vehicleId", new TableInfo.Column("vehicleId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivalStats.put("stationId", new TableInfo.Column("stationId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivalStats.put("timestampArrived", new TableInfo.Column("timestampArrived", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysArrivalStats = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesArrivalStats = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoArrivalStats = new TableInfo("arrival_stats", _columnsArrivalStats, _foreignKeysArrivalStats, _indicesArrivalStats);
        final TableInfo _existingArrivalStats = TableInfo.read(db, "arrival_stats");
        if (!_infoArrivalStats.equals(_existingArrivalStats)) {
          return new RoomOpenHelper.ValidationResult(false, "arrival_stats(com.figatram.data.local.entity.ArrivalStatsEntity).\n"
                  + " Expected:\n" + _infoArrivalStats + "\n"
                  + " Found:\n" + _existingArrivalStats);
        }
        final HashMap<String, TableInfo.Column> _columnsTramInfo = new HashMap<String, TableInfo.Column>(5);
        _columnsTramInfo.put("vehicleId", new TableInfo.Column("vehicleId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTramInfo.put("model", new TableInfo.Column("model", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTramInfo.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTramInfo.put("photoUri", new TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTramInfo.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTramInfo = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTramInfo = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTramInfo = new TableInfo("tram_info", _columnsTramInfo, _foreignKeysTramInfo, _indicesTramInfo);
        final TableInfo _existingTramInfo = TableInfo.read(db, "tram_info");
        if (!_infoTramInfo.equals(_existingTramInfo)) {
          return new RoomOpenHelper.ValidationResult(false, "tram_info(com.figatram.data.local.entity.TramInfoEntity).\n"
                  + " Expected:\n" + _infoTramInfo + "\n"
                  + " Found:\n" + _existingTramInfo);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "0ac6ea12c79b24e35faadb837567473d", "d8ef0a86dc5d374cdaeb661b71145387");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "arrival_stats","tram_info");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `arrival_stats`");
      _db.execSQL("DELETE FROM `tram_info`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TramDao.class, TramDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TramDao tramDao() {
    if (_tramDao != null) {
      return _tramDao;
    } else {
      synchronized(this) {
        if(_tramDao == null) {
          _tramDao = new TramDao_Impl(this);
        }
        return _tramDao;
      }
    }
  }
}
