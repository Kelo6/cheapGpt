// CheapGPT 应用脚本

// 通用工具函数
const App = {
    // 显示通知消息
    showNotification(message, type = 'info') {
        const notification = document.createElement('div');
        notification.className = `alert alert-${type}`;
        notification.textContent = message;
        notification.style.position = 'fixed';
        notification.style.top = '20px';
        notification.style.right = '20px';
        notification.style.zIndex = '9999';
        notification.style.minWidth = '300px';
        
        document.body.appendChild(notification);
        
        setTimeout(() => {
            notification.remove();
        }, 3000);
    },

    // 发送 AJAX 请求
    async request(url, options = {}) {
        try {
            const response = await fetch(url, {
                headers: {
                    'Content-Type': 'application/json',
                    ...options.headers
                },
                ...options
            });
            
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }
            
            return await response.json();
        } catch (error) {
            console.error('请求失败:', error);
            this.showNotification('请求失败: ' + error.message, 'error');
            throw error;
        }
    },

    // 格式化日期
    formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleString('zh-CN');
    },

    // 初始化应用
    init() {
        console.log('CheapGPT 应用已初始化');
        
        // 为所有表单添加加载状态
        document.querySelectorAll('form').forEach(form => {
            form.addEventListener('submit', function() {
                const submitBtn = form.querySelector('button[type="submit"]');
                if (submitBtn) {
                    submitBtn.disabled = true;
                    submitBtn.textContent = '处理中...';
                }
            });
        });
    }
};

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', App.init);

// 全局错误处理
window.addEventListener('error', function(event) {
    console.error('全局错误:', event.error);
});

// 导出到全局
window.App = App;
