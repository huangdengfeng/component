<template>
    <t-layout>
        <t-header style="border-bottom: 1px solid var(--td-component-border)">
            <t-head-menu height="120px">
                <template #logo>
                    <a href="/">
                        <img width="136" class="logo" src="https://www.tencent.com/img/index/menu_logo_hover.png"
                            alt="logo" />
                    </a>
                </template>
                <template #operations>
                    <t-space>
                        <t-dropdown trigger="click">
                            <t-space>
                                <t-button variant="text">
                                    <t-icon name="user" size="20" style="margin-right: 8px" />
                                    {{ userStore.userInfo.userName }} {{ userStore.userInfo.name }}
                                    <template #suffix> <t-icon name="chevron-down" size="16" /></template>
                                </t-button>
                            </t-space>
                            <t-dropdown-menu>
                                <t-dropdown-item @click="router.push('/sys/user/center')">个人信息</t-dropdown-item>
                                <t-dropdown-item @click="handleLogout">退出登录</t-dropdown-item>
                            </t-dropdown-menu>
                        </t-dropdown>
                    </t-space>
                </template>
            </t-head-menu>
        </t-header>
        <t-layout>
            <t-aside style="border-right: 1px solid var(--td-component-border);height: calc(100vh - 120px);">
                <t-menu theme="light" value="user-manage">
                    <t-submenu value="system" title="系统管理">
                        <template #icon>
                            <t-icon name="setting" />
                        </template>
                        <t-menu-item to="/sys/user">
                            <template #icon>
                                <t-icon name="user" />
                            </template>
                            用户管理
                        </t-menu-item>
                        <t-menu-item to="/sys/role">
                            <template #icon>
                                <t-icon name="usergroup" />
                            </template>
                            角色管理
                        </t-menu-item>
                        <t-menu-item value="/sys/permission">
                            <template #icon>
                                <t-icon name="lock-on" />
                            </template>
                            权限管理
                        </t-menu-item>
                        <t-menu-item value="/sys/file">
                            <template #icon>
                                <t-icon name="folder" />
                            </template>
                            文件管理
                        </t-menu-item>
                    </t-submenu>
                </t-menu>
            </t-aside>
            <t-layout style="min-height: calc(100vh - 120px);">
                <t-content style="padding: 10px;">
                    <router-view></router-view>
                </t-content>
                <t-footer style="text-align: center;">Copyright @ 2025-{{ new Date().getFullYear() }} Tencent. All Rights Reserved</t-footer>
            </t-layout>
        </t-layout>
    </t-layout>
</template>
<script setup>
import { useUserStore } from '@/store/user'
import { useRouter } from 'vue-router'
import { removeToken } from '@/utils/auth'

const userStore = useUserStore()
const router = useRouter()

const handleLogout = () => {
  removeToken()
  userStore.clearUserInfo()
  router.push('/login')
}
</script>