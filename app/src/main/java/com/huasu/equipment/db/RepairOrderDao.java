package com.huasu.equipment.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * 报修单数据访问对象
 */
@Dao
public interface RepairOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RepairOrderEntity order);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RepairOrderEntity> orderList);

    @Update
    void update(RepairOrderEntity order);

    @Query("SELECT * FROM repair_orders WHERE id = :id")
    RepairOrderEntity getById(int id);

    @Query("SELECT * FROM repair_orders ORDER BY report_time DESC")
    List<RepairOrderEntity> getAll();

    @Query("SELECT * FROM repair_orders WHERE state = :state ORDER BY report_time DESC")
    List<RepairOrderEntity> getByState(String state);

    @Query("SELECT * FROM repair_orders WHERE state IN (:states) ORDER BY report_time DESC")
    List<RepairOrderEntity> getByStates(List<String> states);

    @Query("DELETE FROM repair_orders WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM repair_orders")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM repair_orders")
    int count();
}
