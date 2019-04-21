/* Layout */
import Layout from '@/layout'


const questionnaireRouter =  {
    path: '/questionnaire',
    component: Layout,
    redirect: '/questionnaire/mine',
    alwaysShow: true, // will always show the root menu
    name: 'questionnaire',
    meta: {
      title: 'questionnaire',
      icon: 'documentation',
    },
    children: [
      {
        path: 'mine',
        component: () => import('@/views/questionnaire/mine'),
        name: 'mineQuestionnaire',
        meta: {
          title: 'mineQuestionnaire',
        }
      },
      {
        path: 'analysis',
        component: () => import('@/views/questionnaire/mine'),
        name: 'analysis',
        meta: {
          title: 'questionnaireAnalysis'
        }
      },
     {
        path: 'create',
        hidden: true,
        component: () => import('@/views/questionnaire/add'),
        name: 'createQuestionnaire',
        meta: {
          title: 'createQuestionnaire'
        }
      }




    ]
}



export default  questionnaireRouter
 