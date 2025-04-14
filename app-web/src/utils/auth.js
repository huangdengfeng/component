export function setToken(token) {
	uni.setStorageSync("token",token)
} 

export function getToken() {
	uni.getStorageSync("token")
} 