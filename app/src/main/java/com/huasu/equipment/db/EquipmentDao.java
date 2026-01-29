package com.huasu.equipment.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * 设备数据访问对象
 */
@Dao
public interface EquipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EquipmentEntity equipment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EquipmentEntity> equipmentList);

    @Update
    void update(EquipmentEntity equipment);

    @Query("SELECT * FROM equipment WHERE id = :id")
    EquipmentEntity getById(int id);

    @Query("SELECT * FROM equipment ORDER BY name ASC")
    List<EquipmentEntity> getAll();

    @Query("SELECT * FROM equipment WHERE state = :state ORDER BY name ASC")
    List<EquipmentEntity> getByState(String state);

    @Query("SELECT * FROM equipment WHERE equipment_type = :type ORDER BY name ASC")
    List<EquipmentEntity> getByType(String type);

    @Query("SELECT * FROM equipment WHERE name LIKE :keyword OR equipment_name LIKE :keyword OR qc_num LIKE :keyword")
    List<EquipmentEntity> search(String keyword);

    @Query("DELETE FROM equipment WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM equipment")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM equipment")
    int count();
}
