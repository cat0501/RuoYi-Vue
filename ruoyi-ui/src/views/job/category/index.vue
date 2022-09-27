<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分类级别" prop="categoryLevel">
        <el-input
          v-model="queryParams.categoryLevel"
          placeholder="请输入分类级别(1,2,3)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父分类id" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入父分类id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类名称" prop="categoryName">
        <el-input
          v-model="queryParams.categoryName"
          placeholder="请输入分类名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="排序值" prop="categoryRank">
        <el-input
          v-model="queryParams.categoryRank"
          placeholder="请输入排序值(字段越大越靠前)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否删除" prop="isDeleted">
        <el-input
          v-model="queryParams.isDeleted"
          placeholder="请输入删除标识字段(0-未删除 1-已删除)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建者id" prop="createUser">
        <el-input
          v-model="queryParams.createUser"
          placeholder="请输入创建者id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="修改者id" prop="updateUser">
        <el-input
          v-model="queryParams.updateUser"
          placeholder="请输入修改者id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类图标" prop="img">
        <el-input
          v-model="queryParams.img"
          placeholder="请输入分类图标"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['job:category:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['job:category:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['job:category:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['job:category:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="categoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="分类id" align="center" prop="categoryId" />
      <el-table-column label="分类级别" align="center" prop="categoryLevel" />
      <el-table-column label="父分类id" align="center" prop="parentId" />
      <el-table-column label="分类名称" align="center" prop="categoryName" />
      <el-table-column label="排序值(字段越大越靠前)" align="center" prop="categoryRank" />
      <el-table-column label="删除标识字段(0-未删除 1-已删除)" align="center" prop="isDeleted" />
      <el-table-column label="创建者id" align="center" prop="createUser" />
      <el-table-column label="修改者id" align="center" prop="updateUser" />
      <el-table-column label="分类图标" align="center" prop="img" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['job:category:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['job:category:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改三级分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类级别" prop="categoryLevel">
          <el-input v-model="form.categoryLevel" placeholder="请输入分类级别(1-一级分类 2-二级分类 3-三级分类)" />
        </el-form-item>
        <el-form-item label="父分类id" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父分类id" />
        </el-form-item>
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="排序值(字段越大越靠前)" prop="categoryRank">
          <el-input v-model="form.categoryRank" placeholder="请输入排序值(字段越大越靠前)" />
        </el-form-item>
        <el-form-item label="删除标识字段(0-未删除 1-已删除)" prop="isDeleted">
          <el-input v-model="form.isDeleted" placeholder="请输入删除标识字段(0-未删除 1-已删除)" />
        </el-form-item>
        <el-form-item label="创建者id" prop="createUser">
          <el-input v-model="form.createUser" placeholder="请输入创建者id" />
        </el-form-item>
        <el-form-item label="修改者id" prop="updateUser">
          <el-input v-model="form.updateUser" placeholder="请输入修改者id" />
        </el-form-item>
        <el-form-item label="分类图标" prop="img">
          <el-input v-model="form.img" placeholder="请输入分类图标" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>



<script>
import { listCategory, getCategory, delCategory, addCategory, updateCategory } from "@/api/job/category";

export default {
  name: "Category",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 三级分类表格数据
      categoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryLevel: null,
        parentId: null,
        categoryName: null,
        categoryRank: null,
        isDeleted: null,
        createUser: null,
        updateUser: null,
        img: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        categoryLevel: [
          { required: true, message: "分类级别(1-一级分类 2-二级分类 3-三级分类)不能为空", trigger: "blur" }
        ],
        parentId: [
          { required: true, message: "父分类id不能为空", trigger: "blur" }
        ],
        categoryName: [
          { required: true, message: "分类名称不能为空", trigger: "blur" }
        ],
        categoryRank: [
          { required: true, message: "排序值(字段越大越靠前)不能为空", trigger: "blur" }
        ],
        isDeleted: [
          { required: true, message: "删除标识字段(0-未删除 1-已删除)不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        createUser: [
          { required: true, message: "创建者id不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "修改时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询三级分类列表 */
    getList() {
      this.loading = true;
      listCategory(this.queryParams).then(response => {
        this.categoryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        categoryId: null,
        categoryLevel: null,
        parentId: null,
        categoryName: null,
        categoryRank: null,
        isDeleted: null,
        createTime: null,
        createUser: null,
        updateTime: null,
        updateUser: null,
        img: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.categoryId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加三级分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const categoryId = row.categoryId || this.ids
      getCategory(categoryId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改三级分类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.categoryId != null) {
            updateCategory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCategory(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const categoryIds = row.categoryId || this.ids;
      this.$modal.confirm('是否确认删除三级分类编号为"' + categoryIds + '"的数据项？').then(function() {
        return delCategory(categoryIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('job/category/export', {
        ...this.queryParams
      }, `category_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
