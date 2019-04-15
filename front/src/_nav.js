export default {
  items: [
    {
      name: '首页',
      url: '/index',
      icon: 'icon-speedometer',
      badge: {
        variant: 'primary',
        text: ''
      }
    },
  
    {
      name: '问卷管理',
      url: '/questionnaire',
      icon: 'icon-puzzle',
      children: [
        {
          name: '我的问卷',
          url: '/questionnaire/list',
          icon: 'icon-list'
        },
        {
          name: '答卷',
          url: '/questionnaire/answer',
          icon: 'icon-check'
        },
        {
          name: '统计分析',
          url: '/questionnaire/analysis',
          icon: 'icon-chart'
        },



      ]
    }
  ]
}
