<template>
	<view class="container">
		<view class="search">
			<uni-search-bar placeholder="请输入关键字" @confirm="search" cancelButton="none" v-model="searchValue">
			</uni-search-bar>
		</view>
		
		<view class="banner">
			<swiper
				class="swiper"
				:indicator-dots="true"
				:autoplay="true"
				:interval="3000"
				:duration="500"
				indicator-color="rgba(255, 255, 255, 0.6)"
				indicator-active-color="#ffffff"
				circular>
				<swiper-item class="swiper-item" v-for="(item, index) in banners" :key="index">
					<image :src="item.url" mode="aspectFill" ></image>
				</swiper-item>
			</swiper>
		</view>
		
		<view class="tool">
			<uni-title type="h1" title="实用工具"></uni-title>
			<uni-grid :column="4" :showBorder="false"  :square="false">
			<uni-grid-item class="tool-grid" >
				<view class="tool-grid-item">
					<image src="/static/images/bzpp.png" mode="aspectFill"></image>
					<text class="text">文本</text>
				</view>
			</uni-grid-item>
				<uni-grid-item class="tool-grid" >
					<view class="tool-grid-item">
						<image src="/static/images/bzpp.png" mode="aspectFill"></image>
						<text class="text">文本</text>
					</view>
				</uni-grid-item>
				<uni-grid-item class="tool-grid" >
					<view class="tool-grid-item">
						<image src="/static/images/bzpp.png" mode="aspectFill"></image>
						<text class="text">文本</text>
					</view>
				</uni-grid-item>
				<uni-grid-item class="tool-grid" >
					<view class="tool-grid-item">
						<image src="/static/images/bzpp.png" mode="aspectFill"></image>
						<text class="text">文本</text>
					</view>
				</uni-grid-item>
				<uni-grid-item class="tool-grid" >
					<view class="tool-grid-item">
						<image src="/static/images/bzpp.png" mode="aspectFill"></image>
						<text class="text">文本</text>
					</view>
				</uni-grid-item>
				<uni-grid-item class="tool-grid" >
					<view class="tool-grid-item">
						<image src="/static/images/bzpp.png" mode="aspectFill"></image>
						<text class="text">文本</text>
					</view>
				</uni-grid-item>
				<uni-grid-item class="tool-grid" >
					<view class="tool-grid-item">
						<image src="/static/images/bzpp.png" mode="aspectFill"></image>
						<text class="text">文本</text>
					</view>
				</uni-grid-item>
				<uni-grid-item class="tool-grid" >
					<view class="tool-grid-item">
						<image src="/static/images/bzpp.png" mode="aspectFill"></image>
						<text class="text">文本</text>
					</view>
				</uni-grid-item>
			</uni-grid>
		</view>
		
	</view>
</template>
<script setup>
import { ref, onMounted , onBeforeUnmount} from 'vue'
import { onReady, onShow } from '@dcloudio/uni-app'
import { post } from '@/utils/request'

// 轮播图数据
const banners = ref([])
const searchValue = ref(null)


const search = ()=>{
	
}

onMounted(() => {
	// 加载banner
	post('/public/cms/banners',{ position:''},function(data) {
		banners.value = data
	});
	
})


onReady(() => {
	// 页面渲染完成后的逻辑
})

onShow(() => {
	// 页面显示时的逻辑
})
</script>

<style>
.container {
	min-height: 100vh;
	background-color: #f5f5f5;
}

.swiper {
    /* 16:9 */
	width: 100vw;
	height: calc(100vw * 9 /16);
	border-radius: 10rpx;
	overflow: hidden;
}
.swiper-item image{
	width: 100%;
	height: 100%;
}

.tool-grid-item {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}

.tool-grid-item image {
	width: 100rpx;
	height: 100rpx;
}

.tool-grid-item .text {
	color: #333;
}
</style>
