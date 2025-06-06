<template>
  <t-dialog v-model:visible="visible" :close-on-overlay-click="false" :destroy-on-close="true"
            :footer="false"
            header="修改用户信息" scroll-to-first-error="smooth" width="750">
    <template #body>
      <t-form :data="formData" :rules="formRules" @submit="save">
        <t-row :gutter="[0, 20]">
          <t-col :span="6">
            <t-form-item label="姓名" name="name">
              <t-input v-model="formData.name" :maxcharacter="20"
                       placeholder="请输入姓名"></t-input>
            </t-form-item>
          </t-col>
          <t-col :span="6"></t-col>
          <t-col :span="6">
            <t-form-item label="用户名" name="userName">
              <t-input v-model="formData.userName" :maxcharacter="20"
                       placeholder="用于登录，不能重复"></t-input>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="手机号" name="mobile">
              <t-input v-model="formData.mobile" :maxcharacter="20" placeholder=""></t-input>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="邮箱" name="email">
              <t-input v-model="formData.email" :maxcharacter="50" placeholder=""></t-input>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="状态" name="status">
              <t-radio-group v-model="formData.status" :options="USER_STATUS"></t-radio-group>
            </t-form-item>
          </t-col>
          <t-col :span="12">
            <t-form-item label="角色" name="roleIds">
              <t-checkbox-group v-model="formData.roleIds" :options="roles"></t-checkbox-group>
            </t-form-item>
          </t-col>
          <t-col :span="12">
            <t-form-item label="备注" name="remark">
              <t-textarea v-model="formData.remark" :maxcharacter="100"
                          placeholder="请输入备注"></t-textarea>
            </t-form-item>
          </t-col>
          <t-col :span="12">
            <t-form-item>
              <t-space>
                <t-button variant="outline" @click="close()">取消</t-button>
                <t-button theme="primary" type="submit">保存</t-button>
              </t-space>
              n
            </t-form-item>
          </t-col>
        </t-row>
      </t-form>
    </template>
  </t-dialog>
</template>

<script setup>
import {reactive, ref} from 'vue'
import {LoadingPlugin, MessagePlugin} from 'tdesign-vue-next'
import {USER_STATUS} from '@/utils/constant'
import request from '@/utils/request'

const visible = ref(false)
const formData = reactive({})
const roles = ref([])
const emit = defineEmits(['refresh'])

const checkUserName = (userName) => {
  return new Promise((resolve) => {
    if (!userName) {
      resolve(true);
    } else {
      request.post('/sys/user/page', {userName}).then(({total, data}) => {
        if (total === 1 && formData.uid !== data[0].uid) {
          resolve({result: false, message: '用户名已存在'});
        } else {
          resolve(true);
        }
      });
    }
  });
}

const formRules = {
  name: [
    {required: true},
    {whitespace: true}
  ],
  userName: [
    {required: true},
    {whitespace: true},
    {min: 5, message: '至少5个字符'},
    {
      validator: checkUserName,
      trigger: 'blur',
    }
  ],
  mobile: [
    {whitespace: true}
  ],
  email: [
    {whitespace: true},
    {email: true, message: '请输入正确邮箱地址'}
  ],
  status: [
    {required: true}
  ]
}

const open = (uid) => {
  visible.value = true
  request.get(`/sys/user/detail/${uid}`).then((data) => {
    Object.assign(formData, data)
  })
}

const save = async ({validateResult}) => {
  if (validateResult !== true) {
    return
  }
  LoadingPlugin(true)
  try {
    await request.post('/sys/user/modify', {
      uid: formData.uid,
      name: formData.name,
      userName: formData.userName,
      mobile: formData.mobile,
      email: formData.email,
      status: formData.status,
      remarks: formData.remarks,
      roleIds: formData.roleIds,
    })
    emit('refresh')
    MessagePlugin.success('修改成功')
    close()
  } finally {
    LoadingPlugin(false)
  }
}

const close = () => {
  visible.value = false
}

// 获取角色列表
request.post('/sys/role/list', {onlyValid: true}).then((data) => {
  roles.value = data.map((item) => ({
    label: item.name,
    value: item.id,
  }))
})

defineExpose({
  open
})
</script>