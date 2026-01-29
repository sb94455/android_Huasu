package com.huasu.equipment.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * 应用数据库
 * 用于本地缓存数据
 */
@Database(
    entities = {
        EquipmentEntity.class,
        InspectionTaskEntity.class,
        RepairOrderEntity.class
    },
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract EquipmentDao equipmentDao();
    public abstract InspectionTaskDao inspectionTaskDao();
    public abstract RepairOrderDao repairOrderDao();

    /**
     * 获取数据库实例
     */
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "equipment_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 关闭数据库
     */
    public static void destroyInstance() {
        if (INSTANCE != null && INSTANCE.isOpen()) {
            INSTANCE.close();
        }
        INSTANCE = null;
    }
}
