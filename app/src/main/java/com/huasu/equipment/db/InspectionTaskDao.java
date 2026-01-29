package com.huasu.equipment.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * 点检任务数据访问对象
 */
@Dao
public interface InspectionTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(InspectionTaskEntity task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<InspectionTaskEntity> taskList);

    @Update
    void update(InspectionTaskEntity task);

    @Query("SELECT * FROM inspection_tasks WHERE id = :id")
    InspectionTaskEntity getById(int id);

    @Query("SELECT * FROM inspection_tasks ORDER BY planned_start_date DESC")
    List<InspectionTaskEntity> getAll();

    @Query("SELECT * FROM inspection_tasks WHERE state = :state ORDER BY planned_start_date DESC")
    List<InspectionTaskEntity> getByState(String state);

    @Query("SELECT * FROM inspection_tasks WHERE state IN (:states) ORDER BY planned_start_date ASC")
    List<InspectionTaskEntity> getByStates(List<String> states);

    @Query("DELETE FROM inspection_tasks WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM inspection_tasks")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM inspection_tasks")
    int count();
}
