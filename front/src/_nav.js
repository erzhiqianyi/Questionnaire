export default {
  items: [
    {
      name: '首页',
      url: '/dashboard',
      icon: 'icon-speedometer',
      badge: {
        variant: 'primary',
        text: ''
      }
    },
    {
      name: '页面',
      url: '/pages',
      icon: 'icon-star',
      children: [
        {
          name: '登录',
          url: '/pages/login',
          icon: 'icon-star'
        },
        {
          name: '注册',
          url: '/pages/register',
          icon: 'icon-star'
        },
        {
          name: ' 404',
          url: '/pages/404',
          icon: 'icon-star'
        },
        {
          name: ' 500',
          url: '/pages/500',
          icon: 'icon-star'
        }
      ]
    },

  ]
}
