package com.BI183.artawan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bh183.deryhendra.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_film";
    private final static String KEY_JUDUl = "Judul";
    private final static String KEY_TAHUN = "Tahun";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_PEMAIN = "Pemain";
    private final static String KEY_SIPNOSIS= "SIPNOSIS";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    private Context context;


    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " +  TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUl + " TEXT, " + KEY_TAHUN + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_PEMAIN + " TEXT, " + KEY_SIPNOSIS + " TEXT);";
        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());

        db.insert(TABLE_FILM, null, cv);

    }

    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                }
                catch (ParseException er){
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }
        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        // Film 1
        try{
            tempDate = sdFormat.parse("2020");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film1 = new Film(
                idFilm,
                "Dilan1991",
                tempDate,
                storeImageFile(R.drawable.film1),
                "Romantic",
                "Iqbaal Ramadhan sebagai Dilan,Vanesha Prescilla sebagai Milea,Ira Wibowo sebagai ibu Dilan,Bucek Depp sebagai ayah Dilan,Happy Salma sebagai ibu Milea,Farhan sebagai ayah Milea,Adhisty Zara sebagai Disa,Yoriko Angeline sebagai Wati,Debo Andryos sebagai Nandan,Zulfa Maharani sebagai Rani,Gusti Rayhan sebagai Akew,Omara Esteghlal sebagai Piyan,Giulio Parengkuan sebagai Anhar,Andovi da Lopez sebagai Verdi,Jerome Kurnia sebagai Yugo,Tike Priyatna sebagai Eem,Bima Azriel sebagai Dilan kecil",
                "PADA 22 Desember 1990, Dilan (Iqbaal Ramadhan) mendeklarasikan hubungannya dengan Milea (Vanesha Prescilla). Milea bahagia karena dia dan Dilan akhirnya berpacaran. Masa indah pun dilalui dua insan yang sedang dimabuk cinta ini. Saat ditanya, apa cita-cita Dilan, dia ingin menikahi Milea. Begitu pun Milea yang tak mungkin menolak Dilan.\n" +
                        "\n" +
                        "Akan tetapi, hubungan Dilan dan Milea tak selamanya manis. Suatu hari Dilan dikeroyok sekelompok orang tak dikenal. Milea cemas, dia tahu ini ada hubungannya dengan posisi Dilan sebagai panglima perang salah satu geng motor di Bandung. Untuk itulah Milea ingin agar Dilan tak ikut-ikutan geng motor lagi. \n" +
                        "\n" +
                        "Ketakutan Milea terbukti saat tahu kalau Dilan akan balas dendam kepada yang memukulinya. Belakangan, Dilan tahu siapa yang mengeroyok dia tempo hari. Untuk itulah, Dilan ingin balas dendam dengan menyerang kelompok yang memukulinya.\n" +
                        "\n" +
                        "Ditemani sepupu jauh Milea, Yugo (Jerome Kurnia), Milea mendatangi Dilan yang sedang menyusun strategi penyerangan. Sayangnya, kehadiran Yugo malah memperkeruh suasana. Akibat terlalu emosi, Milea juga sampai memberi ultimatum kepada Dilan, jika tetap menyerang, mereka putus.\n" +
                        "\n" +
                        "Malam itu, Milea menyesal telah mengatakan kata \"putus\" kepada Dilan. Penyesalan Milea bertambah besar saat tahu kalau Dilan ditahan polisi karena melakukan penyerangan. Namun, masalah Milea dan Dilan bisa diatasi saat Milea bertemu Bunda Hara (Ira Wibowo), ibunya Dilan. \n" +
                        "\n" +
                        "Hubungan manis Dilan dan Milea yang kembali manis ternyata tak berlangsung lama. Dilan berada di persimpangan saat temannya Akew (Gusti Rayhan) meninggal akibat dikeroyok geng tak dikenal. Dilan harus memilih, Milea atau geng motornya."
        );

        tambahFilm(film1, db);
        idFilm++;

        // Film 2
        try{
            tempDate = sdFormat.parse("2020");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film2 = new Film(
                idFilm,
                "Ayat Ayat Cinta 2",
                tempDate,
                storeImageFile(R.drawable.film2),
                "Drama",
                "Fedi Nuril\n" +
                        "Tatjana Saphira\n" +
                        "Chelsea Islan\n" +
                        "Dewi Sandra\n" +
                        "Nur Fazura\n" +
                        "Pandji Pragiwaksono",
                "Hari - hari dalam hidup FAHRI dijalani dengan duka dan usaha pencarian istri yang sangat dicintainya, AISHA. FAHRI (Fedi Nuril) memilih tinggal di Edinburgh, Skotlandia. Kota yang sangat disukai AISHA. FAHRI bekerja menjadi dosen serta peneliti terhormat di universitas ternama kota tersebut. Dalam menjalani kehidupan sehari-harinya FAHRI hanya ditemani HULUSI (Pandji Pragiwaksono), asisten rumah tangganya yang berdarah Turki. Kesantunan dan keramahan sikapnya membuat FAHRI disukai banyak orang, seperti Nenek Catarina (Dewi Irawan), wanita Yahudi yang tinggal tak jauh dari rumahnya. Namun ada pula yang menentang bahkan membenci dirinya, seperti KEIRA (Chelsea Islan), gadis kelahiran Skotlandia yang berobsesi menjadi pemain biola terkenal. Suatu saat, FAHRI bertemu dengan HULYA (Tajtana Saphira), gadis berkebangsaan Turki-Jerman yang sedang mengambil S2 di Edinburgh yang masih memiliki hubungan keluarga dengan AISHA. Kedatangan HULYA justru memicu kenangan sedih FAHRI. Mampukah FAHRI mencapai tekadnya untuk memperbaiki citra Islam dan muslim di negeri dunia pertama itu." );
        tambahFilm(film2, db);
        idFilm++;

        //  Film 3

        try{
            tempDate = sdFormat.parse("2018");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film3 = new Film(
                idFilm,
                "Hit & Run",
                tempDate,
                storeImageFile(R.drawable.film3),
                "Action",
                "Joe Taslim\n" +
                        "Jefri Nichol\n" +
                        "Tatjana Saphira\n" +
                        "Yayan Ruhian\n" +
                        "Chandra Liow\n" +
                        "Nadya Arina",
                "Tegar Saputra, seorang polisi selebriti yang kemana-mana selalu diikuti kamera karena memiliki acara reality shownya sendiri. Tegar ditugaskan untuk menangkap Coki (Yayan Ruhian), seorang gembong narkoba yang baru kabur dari penjara. Sayangnya, di misi kali ini Tegar yang individualis harus dipasangkan dengan Lio, seorang tukang tipu. Tegar yang terbiasa beraksi sendirian kini harus berusaha menyelesaikan misinya bersama Lio yang justru membuat susah. Aksi Tegar dan Lio mencari Coki ditemanin Meisa seorang penyanyi dan Jefri.");
        tambahFilm(film3, db);


    }



}