import request from '@/utils/request'

export function fetchMineList(query) {
  return request({
    url: '/questionnaire/list',
    method: 'get',
    params: query
  })
}

