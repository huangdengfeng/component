<template>
	<view class="container">
		<uni-search-bar placeholder="请输入关键字"></uni-search-bar>
		<view class="type-info">
			<uni-tag class="type-info-item" :inverted="false" type="primary" text="全部"></uni-tag>
			<uni-tag class="type-info-item" :inverted="true" type="primary" v-for="(item,index) in types"
				:text="item.name" :key="index"></uni-tag>
		</view>
		<uni-card title="新闻新闻新闻新闻新闻新闻新闻新闻新闻新闻1" @click="goToDetail(1)">
			<text>这是一个基础卡片示例，此示例展示了一个标题加标题额外信息的标准卡片。</text>
			<view class="card-footer">
				<view class="info-item">
					<uni-icons type="calendar" size="14"></uni-icons>
					<text class="info-text">2024-09-09 12:11</text>
				</view>
				<view class="info-item">
					<uni-icons type="eye" size="14"></uni-icons>
					<text class="info-text">11</text>
				</view>
			</view>
		</uni-card>
		<uni-card title="新闻新闻新闻新闻新闻新闻新闻新闻新闻新闻1">
			<text>这是一个基础卡片示例，此示例展示了一个标题加标题额外信息的标准卡片。</text>
			<view class="card-footer">
				<view class="info-item">
					<uni-icons type="calendar" size="14"></uni-icons>
					<text class="info-text">2024-09-09 12:11</text>
				</view>
				<view class="info-item">
					<uni-icons type="eye" size="14"></uni-icons>
					<text class="info-text">11</text>
				</view>
			</view>
		</uni-card>
		<uni-card title="新闻新闻新闻新闻新闻新闻新闻新闻新闻新闻1">
			<text>这是一个基础卡片示例，此示例展示了一个标题加标题额外信息的标准卡片。</text>
			<view class="card-footer">
				<view class="info-item">
					<uni-icons type="calendar" size="14"></uni-icons>
					<text class="info-text">2024-09-09 12:11</text>
				</view>
				<view class="info-item">
					<uni-icons type="eye" size="14"></uni-icons>
					<text class="info-text">11</text>
				</view>
			</view>
		</uni-card>
		<uni-load-more></uni-load-more>
	</view>
</template>

<script setup>
	import {
		ref
	} from 'vue';
	
	const goToDetail = (id) => {
		uni.navigateTo({
			url: `/pages/cms/detail?id=${id}`
		});
	};
	
	const types = ref([{
			id: 1,
			name: '文章'
		},
		{
			id: 2,
			name: '视频'
		},
		{
			id: 3,
			name: '音频'
		},
		{
			id: 4,
			name: '图片'
		},
		{
			id: 5,
			name: '无效'
		},
		{
			id: 5,
			name: '无效'
		},
		{
			id: 6,
			name: '无效'
		},
		{
			id: 7,
			name: '无效'
		},
		{
			id: 8,
			name: '无效'
		},
		{
			id: 9,
			name: '无效'
		},
		{
			id: 10,
			name: '无效'
		},
	])
</script>

<style scoped lang="scss">
	.type-info {
		display: flex;
		flex-direction: row;
		justify-content: start;
		flex-wrap: wrap;
		border: 1px solid red;
		padding: 5px;

		.type-info-item {
			margin: 5px;
		}
	}

	.card-footer {
		display: flex;
		justify-content: space-between;
		margin-top: 10px;
		padding-top: 10px;
		border-top: 1px solid #eee;

		.info-item {
			display: flex;
			align-items: center;
			margin-right: 20px;
			color: #666;

			.info-text {
				margin-left: 4px;
				font-size: 12px;
			}
		}
	}
</style>