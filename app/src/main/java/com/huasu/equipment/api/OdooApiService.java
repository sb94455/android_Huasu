package com.huasu.equipment.api;

import com.huasu.equipment.api.request.InspectionSubmitRequest;
import com.huasu.equipment.api.request.LoginRequest;
import com.huasu.equipment.api.request.RepairCreateRequest;
import com.huasu.equipment.api.response.BaseResponse;
import com.huasu.equipment.api.response.DashboardResponse;
import com.huasu.equipment.api.response.EquipmentListResponse;
import com.huasu.equipment.api.response.EquipmentResponse;
import com.huasu.equipment.api.response.InspectionListResponse;
import com.huasu.equipment.api.response.InspectionResponse;
import com.huasu.equipment.api.response.RepairListResponse;
import com.huasu.equipment.api.response.RepairResponse;
import com.huasu.equipment.api.response.UserResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Odoo API 服务接口
 * 定义所有 API 端点
 */
public interface OdooApiService {

    // ==================== 认证相关 ====================

    /**
     * 用户登录
     */
    @POST("api/mobile/login")
    Call<BaseResponse<LoginRequest.LoginData>> login(@Body LoginRequest request);

    /**
     * 用户登出
     */
    @POST("api/mobile/logout")
    Call<BaseResponse<Void>> logout();

    /**
     * 获取当前用户信息
     */
    @GET("api/mobile/user/current")
    Call<BaseResponse<UserResponse>> getCurrentUser();

    // ==================== 设备相关 ====================

    /**
     * 获取设备列表
     */
    @GET("api/mobile/equipment/list")
    Call<EquipmentListResponse> getEquipmentList(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("state") String state,
            @Query("equipment_type") String equipmentType,
            @Query("search") String search
    );

    /**
     * 获取设备详情
     */
    @GET("api/mobile/equipment/{id}")
    Call<BaseResponse<EquipmentResponse>> getEquipmentDetail(@Path("id") int equipmentId);

    /**
     * 搜索设备
     */
    @GET("api/mobile/equipment/search")
    Call<EquipmentListResponse> searchEquipment(@Query("keyword") String keyword);

    // ==================== 点检相关 ====================

    /**
     * 获取点检任务列表
     */
    @GET("api/mobile/inspection/tasks")
    Call<InspectionListResponse> getInspectionTasks(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("state") String state
    );

    /**
     * 获取点检任务详情
     */
    @GET("api/mobile/inspection/{id}")
    Call<BaseResponse<InspectionResponse>> getInspectionDetail(@Path("id") int taskId);

    /**
     * 提交点检结果
     */
    @POST("api/mobile/inspection/submit")
    Call<BaseResponse<Void>> submitInspection(@Body InspectionSubmitRequest request);

    // ==================== 报修相关 ====================

    /**
     * 获取报修单列表
     */
    @GET("api/mobile/repair/list")
    Call<RepairListResponse> getRepairList(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("state") String state
    );

    /**
     * 创建报修单
     */
    @POST("api/mobile/repair/create")
    Call<BaseResponse<Map<String, Object>>> createRepair(@Body RepairCreateRequest request);

    /**
     * 获取报修单详情
     */
    @GET("api/mobile/repair/{id}")
    Call<BaseResponse<RepairResponse>> getRepairDetail(@Path("id") int repairId);

    // ==================== 仪表盘相关 ====================

    /**
     * 获取仪表盘统计数据
     */
    @GET("api/mobile/dashboard")
    Call<BaseResponse<DashboardResponse>> getDashboardStats();

    // ==================== 文件上传 ====================

    /**
     * 上传图片
     */
    @Multipart
    @POST("api/mobile/upload/image")
    Call<BaseResponse<Map<String, Object>>> uploadImage(
            @Part MultipartBody.Part image,
            @Part("file_name") RequestBody fileName,
            @Part("res_model") RequestBody resModel,
            @Part("res_id") RequestBody resId
    );

    // ==================== 字典数据 ====================

    /**
     * 获取故障类型列表
     */
    @GET("api/mobile/dict/fault_types")
    Call<BaseResponse<List<Map<String, Object>>>> getFaultTypes();

    /**
     * 获取设备类型列表
     */
    @GET("api/mobile/dict/equipment_types")
    Call<BaseResponse<List<Map<String, Object>>>> getEquipmentTypes();
}
