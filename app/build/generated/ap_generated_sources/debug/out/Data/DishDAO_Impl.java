package Data;

import Model.Dish;
import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DishDAO_Impl implements DishDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Dish> __insertionAdapterOfDish;

  private final EntityDeletionOrUpdateAdapter<Dish> __deletionAdapterOfDish;

  private final EntityDeletionOrUpdateAdapter<Dish> __updateAdapterOfDish;

  public DishDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDish = new EntityInsertionAdapter<Dish>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Dishes` (`Dish_id`,`Dish_name`,`Dish_price`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Dish value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getPrice() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPrice());
        }
      }
    };
    this.__deletionAdapterOfDish = new EntityDeletionOrUpdateAdapter<Dish>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Dishes` WHERE `Dish_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Dish value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfDish = new EntityDeletionOrUpdateAdapter<Dish>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Dishes` SET `Dish_id` = ?,`Dish_name` = ?,`Dish_price` = ? WHERE `Dish_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Dish value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getPrice() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPrice());
        }
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public long addDish(final Dish Dish) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfDish.insertAndReturnId(Dish);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteDish(final Dish Dish) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfDish.handle(Dish);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateDish(final Dish Dish) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfDish.handle(Dish);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Dish> getAllDishes() {
    final String _sql = "select * from Dishes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "Dish_id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "Dish_name");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "Dish_price");
      final List<Dish> _result = new ArrayList<Dish>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Dish _item;
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpPrice;
        _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
        _item = new Dish(_tmpId,_tmpName,_tmpPrice);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Dish getDish(final long DishId) {
    final String _sql = "select * from Dishes where Dish_id ==? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, DishId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "Dish_id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "Dish_name");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "Dish_price");
      final Dish _result;
      if(_cursor.moveToFirst()) {
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpPrice;
        _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
        _result = new Dish(_tmpId,_tmpName,_tmpPrice);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
