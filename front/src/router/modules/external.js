
/* Layout */
import Layout from '@/layout'


const externalLinkRouter =   {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://github.com/erzhiqianyi',
        meta: { title: 'externalLink', icon: 'link' }
      }
    ]
  }

export default  externalLinkRouter
 