import Vue from 'vue'
import Router from 'vue-router'

// Containers
const DefaultContainer = () => import('@/containers/DefaultContainer')

// Views
const Dashboard = () => import('@/views/Dashboard')



// Views - Pages
const Page404 = () => import('@/views/pages/Page404')
const Page500 = () => import('@/views/pages/Page500')
const Login = () => import('@/views/pages/Login')
const Register = () => import('@/views/pages/Register')




const Questionnaire = () => import('@/views/questionnaire/QuetsionnaireList')
const Answer = () => import('@/views/questionnaire/AnswerList')
const Analysis = () => import('@/views/questionnaire/AnalysisList')

Vue.use(Router)

export default new Router({
  mode: 'hash', // https://router.vuejs.org/api/#mode
  linkActiveClass: 'open active',
  scrollBehavior: () => ({ y: 0 }),
  routes: [
    {
      path: '/',
      redirect: '/index',
      name: 'Home',
      component: DefaultContainer,
      children: [
        {
          path: 'index',
          name: 'index',
          component: Dashboard
        },
       
        {
          path: '/questionnaire',
          name: '问卷管理',
          component: {
            render(c) { return c('router-view') }
          },
          children: [
            {
              path: 'list',
              name: '我的问卷',
              component: Questionnaire
            },
            {
              path: 'answer',
              name: '答卷',
              component: Answer
            },
            {
              path: 'analysis',
              name: '答卷',
              component: Analysis
            }

          ]
        }
      ]
    },
    {
      path: '/pages',
      redirect: '/pages/404',
      name: 'Pages',
      component: {
        render(c) { return c('router-view') }
      },
      children: [
        {
          path: '404',
          name: 'Page404',
          component: Page404
        },
        {
          path: '500',
          name: 'Page500',
          component: Page500
        },
        {
          path: 'login',
          name: 'Login',
          component: Login
        },
        {
          path: 'register',
          name: 'Register',
          component: Register
        }
      ]
    }
  ]
})
