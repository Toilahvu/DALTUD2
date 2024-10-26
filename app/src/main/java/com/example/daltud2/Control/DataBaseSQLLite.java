package com.example.daltud2.Control;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseSQLLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "webDocTruyen";
    private static final int DATABASE_VERSION = 1;

    public DataBaseSQLLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng nguoi_dung
        String createNguoiDungTable = "CREATE TABLE IF NOT EXISTS nguoi_dung (" +
                "idUser VARCHAR(50) PRIMARY KEY," +
                "tenUser VARCHAR(255) NOT NULL," +
                "matKhau VARCHAR(255) NOT NULL," +
                "soDienThoai VARCHAR(15)," +
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
                "anhTruyen VARCHAR(255)," +
                "FOREIGN KEY (idTruyen) REFERENCES truyen(idTruyen)" +
                ");";
        db.execSQL(createChuongTruyenTable);

        // Tạo bảng comment
        String createCommentTable = "CREATE TABLE IF NOT EXISTS comment (" +
                "idComment VARCHAR(50) PRIMARY KEY," +
                "idTruyen VARCHAR(50)," +
                "idUser VARCHAR(50)," +
                "noiDungBinhLuan TEXT NOT NULL," +
                "thoiGianBinhLuan DATE," +
                "FOREIGN KEY (idTruyen) REFERENCES truyen(idTruyen)," +
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
                "trangThaiTheoDoi TINYINT," +  // 1: đang theo dõi, 0: đã hủy theo dõi
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
        // Xóa các bảng nếu chúng tồn tại
        db.execSQL("DROP TABLE IF EXISTS truyen_address");
        db.execSQL("DROP TABLE IF EXISTS theo_doi_truyen");
        db.execSQL("DROP TABLE IF EXISTS new");
        db.execSQL("DROP TABLE IF EXISTS address");
        db.execSQL("DROP TABLE IF EXISTS comment");
        db.execSQL("DROP TABLE IF EXISTS chuong_truyen");
        db.execSQL("DROP TABLE IF EXISTS truyen");
        db.execSQL("DROP TABLE IF EXISTS admin");
        db.execSQL("DROP TABLE IF EXISTS nguoi_dung");
        // Tạo lại các bảng
        onCreate(db);
    }

    private void InitData(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            // Chèn dữ liệu vào bảng nguoi_dung
            if (BangTrongko(db, "nguoi_dung")) {
                String insertNguoiDung = "INSERT INTO nguoi_dung (idUser, tenUser, matKhau, soDienThoai, role) VALUES " +
                        "('user01', 'Nguyen Van A', 'admin', '0900000001', 1), " +  // user01 là admin
                        "('user02', 'Le Thi B', '234', '0900000002', 0), " +       // user02 là user bình thường
                        "('user03', 'Tran Van C', '345', '0900000003', 0), " +    // user03 là user bình thường
                        "('user04', 'Pham Thi D', '456', '0900000004', 0), " +     // user04 là user bình thường
                        "('user05', 'Hoang Van E', '567', '0900000005', 0)";        // user05 là user bình thường
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
                String insertTruyen = "INSERT INTO truyen (idTruyen, tenTruyen, tenTacGia, ngayPhatHanh, moTaTruyen, urlAnhBia) VALUES " +
                        "('truyen01', 'Truyen A', 'Tac Gia A', '2020-01-01', 'Mo ta ve truyen A', 'http://anhbia.com/a.jpg'), " +
                        "('truyen02', 'Truyen B', 'Tac Gia B', '2021-02-02', 'Mo ta ve truyen B', 'http://anhbia.com/b.jpg'), " +
                        "('truyen03', 'Truyen C', 'Tac Gia C', '2022-03-03', 'Mo ta ve truyen C', 'http://anhbia.com/c.jpg'), " +
                        "('truyen04', 'Truyen D', 'Tac Gia D', '2023-04-04', 'Mo ta ve truyen D', 'http://anhbia.com/d.jpg'), " +
                        "('truyen05', 'Truyen E', 'Tac Gia E', '2024-05-05', 'Mo ta ve truyen E', 'http://anhbia.com/e.jpg')";
                db.execSQL(insertTruyen);
            }

            // Chèn dữ liệu vào bảng chuong_truyen
            if (BangTrongko(db, "chuong_truyen")) {
                String insertChuongTruyen = "INSERT INTO chuong_truyen (idChapter, idTruyen, chuongSo, ngayPhatHanh, anhTruyen) VALUES " +
                        "('chap01', 'truyen01', 1, '2020-01-10', 'http://anhtruyen.com/a1.jpg'), " +
                        "('chap02', 'truyen02', 1, '2021-02-10', 'http://anhtruyen.com/b1.jpg'), " +
                        "('chap03', 'truyen03', 1, '2022-03-10', 'http://anhtruyen.com/c1.jpg'), " +
                        "('chap04', 'truyen04', 1, '2023-04-10', 'http://anhtruyen.com/d1.jpg'), " +
                        "('chap05', 'truyen05', 1, '2024-05-10', 'http://anhtruyen.com/e1.jpg')";
                db.execSQL(insertChuongTruyen);
            }

            // Chèn dữ liệu vào bảng comment
            if (BangTrongko(db, "comment")) {
                String insertComment = "INSERT INTO comment (idComment, idTruyen, idUser, noiDungBinhLuan, thoiGianBinhLuan) VALUES " +
                        "('comment01', 'truyen01', 'user01', 'Hay qua!', '2020-01-15'), " +
                        "('comment02', 'truyen02', 'user02', 'Rất hấp dẫn!', '2021-02-15'), " +
                        "('comment03', 'truyen03', 'user03', 'Tác phẩm xuất sắc', '2022-03-15'), " +
                        "('comment04', 'truyen04', 'user04', 'Rất hay và ý nghĩa', '2023-04-15'), " +
                        "('comment05', 'truyen05', 'user05', 'Tôi thích truyện này', '2024-05-15')";
                db.execSQL(insertComment);
            }

            // Chèn dữ liệu vào bảng address
            if (BangTrongko(db, "address")) {
                String insertAddress = "INSERT INTO address (tenTag, moTaTag) VALUES " +
                        "('Adventure', 'Truyện phiêu lưu, hành động'), " +
                        "('Romance', 'Truyện tình cảm, lãng mạn'), " +
                        "('Comedy', 'Truyện hài hước'), " +
                        "('Fantasy', 'Truyện giả tưởng'), " +
                        "('Horror', 'Truyện kinh dị')";
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
                        "('user03', 'truyen03', '2022-03-05', 1), " +
                        "('user04', 'truyen04', '2023-04-05', 1), " +
                        "('user05', 'truyen05', '2024-05-05', 1)";
                db.execSQL(insertTheoDoiTruyen);
            }

            // Chèn dữ liệu vào bảng truyen_address
            if (BangTrongko(db, "truyen_address")) {
                String insertTruyenAddress = "INSERT INTO truyen_address (idTruyen, tenTag) VALUES " +
                        "('truyen01', 'Adventure'), " +
                        "('truyen02', 'Romance'), " +
                        "('truyen03', 'Comedy'), " +
                        "('truyen04', 'Fantasy'), " +
                        "('truyen05', 'Horror')";
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

    public Cursor timKiemAdmin(SQLiteDatabase db) {
        String query = "SELECT nguoi_dung.* FROM nguoi_dung " +
                "INNER JOIN admin ON nguoi_dung.idUser = admin.idUser";
        return db.rawQuery(query, null);
    }

}
