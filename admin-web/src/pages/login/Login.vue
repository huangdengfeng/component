<template>
    <div class="login-container">
        <div class="login-content">
            <div class="login-header">
                <img height="48" src="/logo.svg" alt="logo" />
                <h1>后台管理系统</h1>
            </div>
            <t-card class="login-card">
                <t-form ref="form" :data="formData" :rules="rules" :colon="true" :label-width="0" @submit="onSubmit">
                    <t-form-item name="username">
                        <t-input v-model="formData.username" clearable placeholder="请输入账户名">
                            <template #prefix-icon>
                                <desktop-icon />
                            </template>
                        </t-input>
                    </t-form-item>

                    <t-form-item name="password">
                        <t-input v-model="formData.password" type="password" clearable placeholder="请输入密码">
                            <template #prefix-icon>
                                <lock-on-icon />
                            </template>
                        </t-input>
                    </t-form-item>

                    <t-form-item>
                        <t-button theme="primary" type="submit" block>登录</t-button>
                    </t-form-item>
                </t-form>
            </t-card>
        </div>
        <footer class="copyright">Copyright © {{ new Date().getFullYear() }} Admin. All Rights Reserved</footer>
    </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { DesktopIcon, LockOnIcon } from 'tdesign-icons-vue-next'
import { MessagePlugin } from 'tdesign-vue-next'
import request from '@/utils/request'
import { setToken } from '@/utils/auth'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const router = useRouter()
const formData = reactive({
    username: 'admin',
    password: '123456'
})

const rules = {
    username: [
        { required: true, message: '请输入账号' },
        { whitespace: true, message: '账号不能为空白字符' }
    ],
    password: [
        { required: true, message: '请输入密码' },
        { whitespace: true, message: '密码不能为空白字符' },
        { min: 6, message: '密码不能少于6位' }
    ]
}

const onSubmit = async ({ validateResult }) => {
    if (validateResult === true) {
        const { accessToken } = await request.post('/sys/login/user_passwd', {
            username: formData.username,
            password: formData.password
        });
        setToken(accessToken);
        await userStore.getUserInfo()
        MessagePlugin.success('登录成功');
        router.push('/');
    }
}
</script>

<style scoped>
.login-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #f5f5f5;
    overflow: hidden;
}

.login-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 0;
}

.login-header {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 40px;
}

.login-header h1 {
    font-size: 24px;
    font-weight: 500;
    color: #0052d9;
    margin: 0;
}

.login-card {
    width: 380px;
    padding: 32px;
    background: #fff;
    border-radius: 6px;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1);
}

.copyright {
    flex-shrink: 0;
    text-align: center;
    padding: 16px;
    color: rgba(0, 0, 0, 0.45);
}
</style>