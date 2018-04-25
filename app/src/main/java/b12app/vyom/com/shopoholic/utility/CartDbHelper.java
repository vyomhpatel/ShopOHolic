package b12app.vyom.com.shopoholic.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CartDbHelper extends SQLiteOpenHelper {



    public static final String DB_NAME = "shopholicdb";
    public static final String TABLE_NAME = "cart";
    public static final String WISHLIST_TABLE = "cart";
    public static final String PRODUCT_NAME = "productname";
    public static final String PRODUCT_ID = "productid";
    public static final String PRODUCT_QUANTITY = "productquantity";
    public static final String PRODUCT_PRICE = "productprize";
    public static final String PRODUCT_IMAGE = "productimage";
    public static final String USER_ID = "userid";
    private static final int VERSION = 1;


    public CartDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

    }





    @Override
    public void onCreate(SQLiteDatabase db) {

//        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
//                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + USERNAME + " TEXT,"
//                + PASSWORD + " TEXT,"
//                + EMAIL + " TEXT"
//                + ")";
//
//        db.execSQL(CREATE_TABLE);

        db.execSQL("create table " + TABLE_NAME +" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userid TEXT," +
                "productid TEXT," +
                "productname TEXT, " +
                "productquantity TEXT, " +
                "productimage TEXT, " +
                "productprize TEXT )");

        db.execSQL("create table " + WISHLIST_TABLE +" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userid TEXT," +
                "productid TEXT," +
                "productname TEXT, " +
                "productquantity TEXT, " +
                "productimage TEXT, " +
                "productprize TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);


    }


}
