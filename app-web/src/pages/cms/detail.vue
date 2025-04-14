<template>
	<view class="container">
		<view class="article-header">
			<text class="title">新闻新闻新闻新闻新闻新闻新闻新闻新闻新闻1</text>
			<view class="article-info">
				<view class="info-item">
					<uni-icons type="calendar" size="14"></uni-icons>
					<text class="info-text">2024-09-09 12:11</text>
				</view>
				<view class="info-item">
					<uni-icons type="eye" size="14"></uni-icons>
					<text class="info-text">11</text>
				</view>
			</view>
		</view>
		
		<view class="article-summary">
			<view class="summary-header">
				<uni-icons type="info" size="16" color="#666"></uni-icons>
				<text class="summary-title">文章摘要</text>
			</view>
			<text class="summary-text">这是一个基础卡片示例，此示例展示了一个标题加标题额外信息的标准卡片。这是一个基础卡片示例，此示例展示了一个标题加标题额外信息的标准卡片。这是一个基础卡片示例，此示例展示了一个标题加标题额外信息的标准卡片。</text>
		</view>
		
		<view class="article-content">
			<rich-text :nodes="content"></rich-text>
		</view>
	</view>
</template>

<script setup>
import { onLoad } from '@dcloudio/uni-app';
import { ref } from 'vue';

const articleId = ref('');
const content = ref(`
	<div style="font-size: 16px; line-height: 1.8; color: #333;">
		<p>这是一个基础卡片示例，此示例展示了一个标题加标题额外信息的标准卡片。</p>
		<p>这是<strong>加粗</strong>的文本，这是<em>斜体</em>的文本。</p>
		<p>这是一个<u>下划线</u>文本。</p>
		<p>这是一个列表：</p>
		<ul>
			<li>列表项 1</li>
			<li>列表项 2</li>
			<li>列表项 3</li>
		</ul>
		<p>这是一个图片：</p>
		<img src="https://picsum.photos/600/300" style="max-width: 100%; height: auto; margin: 10px 0;" />
		<p>这是一个引用：</p>
		<blockquote style="border-left: 4px solid #ccc; margin: 10px 0; padding-left: 10px; color: #666;">
			这是一段引用文本，可以用于突出显示重要的内容。
		</blockquote>
		<p>这是一个代码块：</p>
		<pre style="background-color: #f5f5f5; padding: 10px; border-radius: 4px; overflow-x: auto;">
			<code>const greeting = "Hello World";
console.log(greeting);</code>
		</pre>
	</div>
`);

onLoad((options) => {
	if (options.id) {
		articleId.value = options.id;
		// TODO: 根据id获取文章详情
		// 获取到数据后更新 content.value
	}
});
</script>

<style scoped lang="scss">
.container {
	padding: 20px;
	
	.article-header {
		margin-bottom: 20px;
		
		.title {
			font-size: 20px;
			font-weight: bold;
			margin-bottom: 15px;
			line-height: 1.4;
		}
		
		.article-info {
			display: flex;
			align-items: center;
			color: #666;
			
			.info-item {
				display: flex;
				align-items: center;
				margin-right: 20px;
				
				.info-text {
					margin-left: 4px;
					font-size: 12px;
				}
			}
		}
	}
	
	.article-summary {
		background-color: #f8f8f8;
		padding: 15px;
		border-radius: 8px;
		margin-bottom: 20px;
		
		.summary-header {
			display: flex;
			align-items: center;
			margin-bottom: 10px;
			
			.summary-title {
				margin-left: 6px;
				font-size: 14px;
				color: #666;
				font-weight: 500;
			}
		}
		
		.summary-text {
			font-size: 14px;
			line-height: 1.6;
			color: #666;
		}
	}
	
	.article-content {
		:deep(img) {
			max-width: 100%;
			height: auto;
			margin: 10px 0;
		}
		
		:deep(p) {
			margin: 10px 0;
		}
		
		:deep(blockquote) {
			border-left: 4px solid #ccc;
			margin: 10px 0;
			padding-left: 10px;
			color: #666;
		}
		
		:deep(pre) {
			background-color: #f5f5f5;
			padding: 10px;
			border-radius: 4px;
			overflow-x: auto;
		}
		
		:deep(ul), :deep(ol) {
			padding-left: 20px;
			margin: 10px 0;
		}
		
		:deep(li) {
			margin: 5px 0;
		}
	}
}
</style> 