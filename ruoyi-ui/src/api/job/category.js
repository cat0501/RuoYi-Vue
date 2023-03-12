import request from '@/utils/request'

// 查询三级分类列表
export function listCategory(query) {
  return request({
    url: '/job/category/list',
    method: 'get',
    params: query
  })
}

// 查询三级分类详细
export function getCategory(categoryId) {
  return request({
    url: '/job/category/' + categoryId,
    method: 'get'
  })
}

// 新增三级分类
export function addCategory(data) {
  return request({
    url: '/job/category',
    method: 'post',
    data: data
  })
}

// 修改三级分类
export function updateCategory(data) {
  return request({
    url: '/job/category',
    method: 'put',
    data: data
  })
}

// 删除三级分类
export function delCategory(categoryId) {
  return request({
    url: '/job/category/' + categoryId,
    method: 'delete'
  })
}

function post() {
  var data = new FormData();
  data.append("id", 1000);
  data.append("pn", '华为手机');
  axios.post('http://localhost:8080/api/scanRecord?t=1', data).then(function (res) {
  }).catch(function (error) {
  });
}

