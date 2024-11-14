package com.example.daltud2.Control;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import java.util.Random;
import com.example.daltud2.Model.NguoiDung;

import java.io.File;

public class DataBaseSQLLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "webDocTruyen";
    private static final int DATABASE_VERSION = 2;

    // Constructor có tham số tên cơ sở dữ liệu
    public DataBaseSQLLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Constructor rút gọn, tự động sử dụng tên cơ sở dữ liệu mặc định
    public DataBaseSQLLite(Context context) {
        this(context, "webDocTruyen.db", null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng nguoi_dung
        String createNguoiDungTable = "CREATE TABLE IF NOT EXISTS nguoi_dung (" +
                "idUser VARCHAR(50) PRIMARY KEY," +
                "tenUser VARCHAR(255) NOT NULL," +
                "matKhau VARCHAR(255) NOT NULL," +
                "soDienThoai VARCHAR(15)," +
                "email VARCHAR(255) NOT NULL," +   // Thêm cột email
                "role TINYINT NOT NULL DEFAULT 0" + // 0: user bình thường, 1: admin
                ");";
        db.execSQL(createNguoiDungTable);

        // Tạo bảng admin
        String createAdminTable = "CREATE TABLE IF NOT EXISTS admin (" +
                "idAdmin VARCHAR(50) PRIMARY KEY," +
                "idUser VARCHAR(50)," +  // Liên kết với bảng nguoi_dung
                "FOREIGN KEY (idUser) REFERENCES nguoi_dung(idUser)" +
                ");";
        db.execSQL(createAdminTable);

        // Tạo bảng truyen
        String createTruyenTable = "CREATE TABLE IF NOT EXISTS truyen (" +
                "idTruyen VARCHAR(50) PRIMARY KEY," +
                "tenTruyen VARCHAR(255) NOT NULL," +
                "tenTacGia VARCHAR(255)," +
                "luotXem INT,"+
                "luotTheoDoi,"+
                "ngayPhatHanh DATE," +
                "moTaTruyen TEXT," +
                "urlAnhBia VARCHAR(255)" +
                ");";
        db.execSQL(createTruyenTable);

        // Tạo bảng chuong_truyen
        String createChuongTruyenTable = "CREATE TABLE IF NOT EXISTS chuong_truyen (" +
                "idChapter VARCHAR(50) PRIMARY KEY," +
                "idTruyen VARCHAR(50)," +
                "chuongSo INT NOT NULL," +
                "ngayPhatHanh DATE," +
                "FOREIGN KEY (idTruyen) REFERENCES truyen(idTruyen)" +
                ");";
        db.execSQL(createChuongTruyenTable);

        // Tạo bảng anh_chuong
        String createAnhChuongTable = "CREATE TABLE IF NOT EXISTS anh_chuong (" +
                "idAnh INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idChapter VARCHAR(50), " +
                "urlAnh VARCHAR(255) NOT NULL," +
                "FOREIGN KEY (idChapter) REFERENCES chuong_truyen(idChapter)" +
                ");";
        db.execSQL(createAnhChuongTable);

        // Tạo bảng comment
        String createCommentTable = "CREATE TABLE IF NOT EXISTS comment (" +
                "idComment VARCHAR(50) PRIMARY KEY," +
                "idChapter VARCHAR(50)," +
                "idUser VARCHAR(50)," +
                "noiDungBinhLuan TEXT NOT NULL," +
                "thoiGianBinhLuan DATE," +
                "FOREIGN KEY (idChapter) REFERENCES chuong_truyen(idChapter)," +
                "FOREIGN KEY (idUser) REFERENCES nguoi_dung(idUser)" +
                ");";
        db.execSQL(createCommentTable);

        // Tạo bảng address
        String createAddressTable = "CREATE TABLE IF NOT EXISTS address (" +
                "tenTag VARCHAR(100) PRIMARY KEY," +
                "moTaTag VARCHAR(255)" +
                ");";
        db.execSQL(createAddressTable);

        // Tạo bảng new
        String createNewTable = "CREATE TABLE IF NOT EXISTS new (" +
                "idNew VARCHAR(50) PRIMARY KEY," +
                "idAdmin VARCHAR(50)," +  // Liên kết với bảng admin
                "noiDung TEXT NOT NULL," +
                "ngayDang DATE," +
                "FOREIGN KEY (idAdmin) REFERENCES admin(idAdmin)" +
                ");";
        db.execSQL(createNewTable);

        // Tạo bảng theo_doi_truyen
        String createTheoDoiTruyenTable = "CREATE TABLE IF NOT EXISTS theo_doi_truyen (" +
                "idUser VARCHAR(50)," +
                "idTruyen VARCHAR(50)," +
                "ngayBatDau DATE," +
                "trangThaiTheoDoi TINYINT," +  // 1: đang theo dõi, 0: không theo dõi
                "PRIMARY KEY (idUser, idTruyen)," +
                "FOREIGN KEY (idUser) REFERENCES nguoi_dung(idUser)," +
                "FOREIGN KEY (idTruyen) REFERENCES truyen(idTruyen)" +
                ");";
        db.execSQL(createTheoDoiTruyenTable);

        // Tạo bảng truyen_address
        String createTruyenAddressTable = "CREATE TABLE IF NOT EXISTS truyen_address (" +
                "idTruyen VARCHAR(50)," +
                "tenTag VARCHAR(100)," +
                "PRIMARY KEY (idTruyen, tenTag)," +
                "FOREIGN KEY (idTruyen) REFERENCES truyen(idTruyen)," +
                "FOREIGN KEY (tenTag) REFERENCES address(tenTag)" +
                ");";
        db.execSQL(createTruyenAddressTable);

        // Khởi tạo dữ liệu (nếu cần)
        InitData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS truyen_address");
        db.execSQL("DROP TABLE IF EXISTS theo_doi_truyen");
        db.execSQL("DROP TABLE IF EXISTS new");
        db.execSQL("DROP TABLE IF EXISTS address");
        db.execSQL("DROP TABLE IF EXISTS comment");
        db.execSQL("DROP TABLE IF EXISTS chuong_truyen");
        db.execSQL("DROP TABLE IF EXISTS truyen");
        db.execSQL("DROP TABLE IF EXISTS admin");
        db.execSQL("DROP TABLE IF EXISTS nguoi_dung");
        onCreate(db);
    }

    private void InitData(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            // Chèn dữ liệu vào bảng nguoi_dung
            if (BangTrongko(db, "nguoi_dung")) {
                String insertNguoiDung = "INSERT INTO nguoi_dung (idUser, tenUser, matKhau, soDienThoai, email, role) VALUES " +
                        "('user01', 'Nguyen Van A', 'admin', '0900000001', 'nguyenvana@example.com', 1), " +  // user01 là admin
                        "('user02', 'Le Thi B', '234', '0900000002', 'lethib@example.com', 0), " +          // user02 là user bình thường
                        "('user03', 'Tran Van C', '345', '0900000003', 'tranvanc@example.com', 0), " +      // user03 là user bình thường
                        "('user04', 'Pham Thi D', '456', '0900000004', 'phamthid@example.com', 0), " +      // user04 là user bình thường
                        "('user05', 'Hoang Van E', '567', '0900000005', 'hoangvane@example.com', 0)";       // user05 là user bình thường
                db.execSQL(insertNguoiDung);
            }

            // Chèn dữ liệu vào bảng admin
            if (BangTrongko(db, "admin")) {
                String insertAdmin = "INSERT INTO admin (idAdmin, idUser) VALUES " +
                        "('admin01', 'user01')";  // user01 là admin
                db.execSQL(insertAdmin);
            }


            // Chèn dữ liệu vào bảng truyen
            if (BangTrongko(db, "truyen")) {
                String insertTruyen = "INSERT INTO truyen (idTruyen, tenTruyen, tenTacGia, luotXem, luotTheoDoi, ngayPhatHanh, moTaTruyen, urlAnhBia) VALUES " +
                        "('truyen01', 'Ta là tà đế', 'Nguyễn Văn A', 500, 100, '2019-11-13', 'Truyện huyền huyễn về hành trình của nhân vật chính.', '/data/data/com.example.daltud2/files/images/comics/Ta_La_Ta_De/ta-la-ta-de.jpg'), " +
                        "('truyen02', 'Lăng tiên kỳ đàm', 'Lê Thị B', 300, 50, '2021-05-09', 'Một câu chuyện phiêu lưu thú vị.', '/data/data/com.example.daltud2/files/images/comics/Ta_La_Ta_De/ta-la-ta-de.jpg'), " +
                        "('truyen03', 'Cuộc chiến với yêu ma', 'Trần Văn C', 800, 200, '2020-08-21', 'Một câu chuyện về chiến đấu với yêu ma.', '/data/data/com.example.daltud2/files/images/comics/Ta_La_Ta_De/ta-la-ta-de.jpg'), " +
                        "('truyen04', 'Hành trình kỳ ảo', 'Phạm Thị D', 1000, 300, '2018-01-10', 'Hành trình khám phá thế giới mới đầy kỳ diệu.', '/data/data/com.example.daltud2/files/images/comics/Ta_La_Ta_De/ta-la-ta-de.jpg');";
                db.execSQL(insertTruyen);
            }

            // Chèn dữ liệu vào bảng chuong_truyen
            if (BangTrongko(db, "chuong_truyen")) {
                String insertChuongTruyen = "INSERT INTO chuong_truyen (idChapter, idTruyen, chuongSo, ngayPhatHanh) VALUES " +
                        "('truyen01_chap01', 'truyen01', 1, '2019-11-14'), " +
                        "('truyen01_chap02', 'truyen01', 2, '2019-11-15'), " +
                        "('truyen02_chap01', 'truyen02', 1, '2021-05-10'), " +
                        "('truyen02_chap02', 'truyen02', 2, '2021-05-11'), " +
                        "('truyen03_chap01', 'truyen03', 1, '2020-08-22'), " +
                        "('truyen03_chap02', 'truyen03', 2, '2020-08-23'), " +
                        "('truyen04_chap01', 'truyen04', 1, '2018-01-11'), " +
                        "('truyen04_chap02', 'truyen04', 2, '2018-01-12');";
                db.execSQL(insertChuongTruyen);
            }
            // Chèn dữ liệu vào bảng anh_chuong
            insertImagesIntoDB(this, db); // Gọi hàm chèn ảnh

            // Chèn dữ liệu vào bảng comment
            if (BangTrongko(db, "comment")) {
                String insertComment = "INSERT INTO comment (idComment, idChapter, idUser, noiDungBinhLuan, thoiGianBinhLuan) VALUES " +
                        "('comment01', 'Ta_La_Ta_De_Chap_0', 'user01', 'Hay qua!', '2020-01-15'), " +
                        "('comment02', 'Ta_La_Ta_De_Chap_0', 'user02', 'Rất hấp dẫn!', '2021-02-15'), " +
                        "('comment03', 'Ta_La_Ta_De_Chap_0', 'user03', 'Tác phẩm xuất sắc', '2022-03-15'), " +
                        "('comment04', 'Lang_Tien_Ki_Dam_Chap_1', 'user04', 'Rất hay và ý nghĩa', '2023-04-15'), " +
                        "('comment05', 'Lang_Tien_Ki_Dam_Chap_1', 'user05', 'Tôi thích truyện này', '2024-05-15')";
                db.execSQL(insertComment);
            }

            // Chèn dữ liệu vào bảng address
            if (BangTrongko(db, "address")) {
                String insertAddress = "INSERT INTO address (tenTag, moTaTag) VALUES " +
                        "('Action', 'Truyện hành động'), " +
                        "('Adventure', 'Truyện phiêu lưu, hành động'), " +
                        "('Anime', 'Truyện chuyển thể từ anime'), " +
                        "('Chuyển Sinh', 'Truyện nhân vật chuyển sinh sang thế giới khác'), " +
                        "('Cổ Đại', 'Truyện lấy bối cảnh thời cổ đại'), " +
                        "('Comedy', 'Truyện hài hước'), " +
                        "('Comic', 'Truyện tranh theo phong cách comic'), " +
                        "('Demons', 'Truyện về yêu ma, quỷ dữ'), " +
                        "('Detective', 'Truyện trinh thám, điều tra'), " +
                        "('Doujinshi', 'Truyện fanmade, do fan sáng tác'), " +
                        "('Drama', 'Truyện kịch tính, nhiều cảm xúc'), " +
                        "('Fantasy', 'Truyện giả tưởng'), " +
                        "('Gender Bender', 'Truyện có yếu tố thay đổi giới tính'), " +
                        "('Harem', 'Truyện với nhiều nhân vật yêu mến nhân vật chính'), " +
                        "('Historical', 'Truyện lịch sử, có yếu tố lịch sử'), " +
                        "('Horror', 'Truyện kinh dị'), " +
                        "('Huyền Huyễn', 'Truyện huyền huyễn, phép thuật'), " +
                        "('Isekai', 'Truyện nhân vật bị dịch chuyển sang thế giới khác'), " +
                        "('Josei', 'Truyện dành cho nữ giới trưởng thành'), " +
                        "('Mafia', 'Truyện về xã hội đen, mafia'), " +
                        "('Magic', 'Truyện phép thuật'), " +
                        "('Manhua', 'Truyện tranh Trung Quốc'), " +
                        "('Manhwa', 'Truyện tranh Hàn Quốc'), " +
                        "('Martial Arts', 'Truyện về võ thuật'), " +
                        "('Military', 'Truyện có yếu tố quân sự'), " +
                        "('Mystery', 'Truyện bí ẩn, hồi hộp'), " +
                        "('Ngôn Tình', 'Truyện tình cảm'), " +
                        "('One shot', 'Truyện ngắn chỉ có một tập'), " +
                        "('Psychological', 'Truyện tâm lý'), " +
                        "('Romance', 'Truyện tình cảm, lãng mạn'), " +
                        "('School Life', 'Truyện về cuộc sống học đường'), " +
                        "('Sci-fi', 'Truyện khoa học viễn tưởng'), " +
                        "('Seinen', 'Truyện dành cho nam giới trưởng thành'), " +
                        "('Shoujo', 'Truyện dành cho nữ giới trẻ tuổi'), " +
                        "('Shoujo Ai', 'Truyện tình cảm giữa các nhân vật nữ'), " +
                        "('Shounen', 'Truyện dành cho nam giới trẻ tuổi'), " +
                        "('Shounen Ai', 'Truyện tình cảm giữa các nhân vật nam'), " +
                        "('Slice of life', 'Truyện về cuộc sống thường ngày'), " +
                        "('Sports', 'Truyện thể thao'), " +
                        "('Supernatural', 'Truyện siêu nhiên'), " +
                        "('Tragedy', 'Truyện bi kịch'), " +
                        "('Trọng Sinh', 'Truyện nhân vật sống lại hoặc tái sinh'), " +
                        "('Truyện Màu', 'Truyện tranh màu'), " +
                        "('Webtoon', 'Truyện tranh phong cách Hàn Quốc, thường được đăng trên web'), " +
                        "('Xuyên Không', 'Truyện về nhân vật xuyên không gian hoặc thời gian');";
                db.execSQL(insertAddress);
            }

            // Chèn dữ liệu vào bảng new
            if (BangTrongko(db, "new")) {
                String insertNew = "INSERT INTO new (idNew, idAdmin, noiDung, ngayDang) VALUES " +
                        "('news01', 'admin01', 'Tin mới nhất về các truyện nổi bật', '2024-10-01'), " +
                        "('news02', 'admin01', 'Cập nhật chương mới của Truyện A', '2024-10-02'), " +
                        "('news03', 'admin01', 'Sự kiện ra mắt Truyện E', '2024-10-03'), " +
                        "('news04', 'admin01', 'Review Truyện B', '2024-10-04'), " +
                        "('news05', 'admin01', 'Phân tích nội dung Truyện D', '2024-10-05')";
                db.execSQL(insertNew);
            }

            // Chèn dữ liệu vào bảng theo_doi_truyen
            if (BangTrongko(db, "theo_doi_truyen")) {
                String insertTheoDoiTruyen = "INSERT INTO theo_doi_truyen (idUser, idTruyen, ngayBatDau, trangThaiTheoDoi) VALUES " +
                        "('user01', 'truyen01', '2020-01-05', 1), " +
                        "('user02', 'truyen02', '2021-02-05', 1), " +
                        "('user03', 'truyen01', '2022-03-05', 1), " +
                        "('user04', 'truyen02', '2023-04-05', 1), " +
                        "('user05', 'truyen02', '2024-05-05', 1)";
                db.execSQL(insertTheoDoiTruyen);
            }

            // Chèn dữ liệu vào bảng truyen_address
            if (BangTrongko(db, "truyen_address")) {
                String insertTruyenAddress = "INSERT INTO truyen_address (idTruyen, tenTag) VALUES " +
                        "('truyen01', 'Action'), " +
                        "('truyen01', 'Fantasy'), " +
                        "('truyen02', 'Adventure'), " +
                        "('truyen02', 'Comedy'), " +
                        "('truyen03', 'Horror'), " +
                        "('truyen03', 'Demons'), " +
                        "('truyen04', 'Isekai'), " +
                        "('truyen04', 'Magic');";
                db.execSQL(insertTruyenAddress);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private boolean BangTrongko(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;
    }

    public void insertImagesIntoDB(DataBaseSQLLite context, SQLiteDatabase db) {
        // Đường dẫn tới thư mục chứa ảnh truyện
        String comicsPath = "/data/data/com.example.daltud2/files/images/comics";
        File comicsFolder = new File(comicsPath);

        // Chỉ chèn nếu bảng anh_chuong trống
        if (BangTrongko(db, "anh_chuong")) {
            File[] stories = comicsFolder.listFiles(); // Lấy tất cả thư mục truyện
            if (stories != null) {
                for (File story : stories) {
                    if (story.isDirectory()) { // Kiểm tra nếu là thư mục truyện
                        File[] chapters = story.listFiles(); // Lấy tất cả thư mục chương
                        if (chapters != null) {
                            for (File chapter : chapters) {
                                if (chapter.isDirectory()) { // Kiểm tra nếu là thư mục chương
                                    File[] images = chapter.listFiles(); // Lấy tất cả tệp trong thư mục chương
                                    if (images != null) {
                                        for (File image : images) {
                                            if (image.isFile() && image.getName().endsWith(".jpg")) { // Chỉ xử lý tệp ảnh .jpg
                                                String imageName = image.getName();
                                                String chapterId = story.getName() + "_" + chapter.getName(); // Tạo idChapter từ tên truyện và chương

                                                // Chèn vào bảng
                                                String insertQuery = "INSERT INTO anh_chuong (idChapter, urlAnh) VALUES (?, ?)";
                                                db.execSQL(insertQuery, new Object[]{chapterId, "file://" + image.getAbsolutePath()}); // Sử dụng file:// để chỉ đường dẫn tệp
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // select tất cả admin
    public Cursor timKiemAdmin(SQLiteDatabase db) {
        String query = "SELECT nguoi_dung.* FROM nguoi_dung " +
                "INNER JOIN admin ON nguoi_dung.idUser = admin.idUser";
        return db.rawQuery(query, null);
    }

    // select tất cả truyện
    public Cursor getAllTruyen(SQLiteDatabase db) {
        String query = "SELECT * FROM truyen";
        return db.rawQuery(query, null);
    }

    //Select truyện có từ khoá giống
    public Cursor searchComics(SQLiteDatabase db, String tv_comicSearch) {
        String query = "SELECT * FROM truyen WHERE tenTruyen LIKE '%" + tv_comicSearch + "%' OR tenTacGia LIKE '%" + tv_comicSearch + "%'";
        return db.rawQuery(query, null);
    }

    public Cursor searchComicsByTag(SQLiteDatabase db, String tv_Tag) {
        String query = "SELECT t.* " +
                "FROM truyen t " +
                "INNER JOIN truyen_address ta ON t.idTruyen = ta.idTruyen " +
                "WHERE ta.tenTag LIKE '%" + tv_Tag + "%' ";
        return db.rawQuery(query, null);
    }


    // select tài khoản
    public NguoiDung kiemTraTaiKhoanMatKhau(SQLiteDatabase db, String emaill, String password) {
        String query = "SELECT * FROM nguoi_dung WHERE email = ? AND matKhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{emaill, password});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String idUser = cursor.getString(cursor.getColumnIndex("idUser"));
            @SuppressLint("Range") String tenUser = cursor.getString(cursor.getColumnIndex("tenUser"));
            @SuppressLint("Range") String matKhau = cursor.getString(cursor.getColumnIndex("matKhau"));
            @SuppressLint("Range") String soDienThoai = cursor.getString(cursor.getColumnIndex("soDienThoai"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") int role = cursor.getInt(cursor.getColumnIndex("role"));

            cursor.close();
            return new NguoiDung(idUser, tenUser, matKhau, soDienThoai, email, role);
        } else {
            cursor.close();
            return null; // Không tìm thấy người dùng
        }
    }

    // Tạo idUser ngẫu nhiên
    private String randomIdUsers(){
        Random random = new Random();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";

        // Lấy 2 chữ cái ngẫu nhiên
        String randomLetters = "" + letters.charAt(random.nextInt(letters.length()))
                + letters.charAt(random.nextInt(letters.length()));

        // Lấy 2 chữ số ngẫu nhiên
        String randomDigits = "" + digits.charAt(random.nextInt(digits.length()))
                + digits.charAt(random.nextInt(digits.length()));

        return "User" + randomDigits + randomLetters;
    }

    //kiểm tra người dùng đã từng đăng ký chưa dựa vào email
    public boolean kiemTraEmailTonTai(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM nguoi_dung WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean exists = cursor.moveToFirst(); // Trả về true nếu có email
        cursor.close();
        db.close();
        return exists;
    }
    // Thêm người dùng mới
    public void insertNguoiDung(String email, String matKhau) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Tạo một giá trị idUser ngẫu nhiên từ User + 2 chữ số và 2 chữ cái
        String idUser = randomIdUsers();

        // Lấy tên người dùng từ email
        String tenUser = email.substring(0, email.indexOf('@'));

        // Sử dụng ContentValues để chèn dữ liệu
        ContentValues values = new ContentValues();
        values.put("idUser", idUser);
        values.put("tenUser", tenUser);
        values.put("matKhau", matKhau);
        values.put("soDienThoai", "");
        values.put("email", email);
        values.put("role", 0);

        // Sử dụng phương thức insert để chèn dữ liệu và kiểm tra kết quả
        long result = db.insert("nguoi_dung", null, values);
        db.close();

        if (result == -1) {
            Log.e("InsertUser", "Error inserting user");
        } else {
            Log.d("InsertUser", "User inserted successfully");
        }
    }

}
