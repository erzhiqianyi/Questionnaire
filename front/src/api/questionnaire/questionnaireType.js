
export default {
    items:
        [{
            name: '调查',
            steps: [
                {
                    name: '选择问卷类型',
                    code: 'setType',
                    description: '设置问卷类型',
                    icon: 'el-icon-menu'
                },
                {
                    code: 'create',
                    name: '创建问卷',
                    description: '问卷基本信息',
                    icon: 'el-icon-info'
                },
                {
                    code: 'question',
                    name: '添加题目',
                    description: '设置题目',
                    icon: 'el-icon-edit'
                },

                {
                    name: '发布',
                    description: '发布问卷',
                    icon: 'el-icon-check'
                }

            ]
        },
        {
            name: '投票',
            steps: [
                {
                    code: 'setType',
                    name: '选择问卷类型',
                    description: '设置问卷类型',
                    icon: 'el-icon-menu'
                },
                {
                    code: 'create',
                    name: '创建投票',
                    description: '投票基本信息',
                    icon: 'el-icon-info'
                },
                {
                    name: '添加候选项',
                    description: '添加候选项',
                    icon: 'el-icon-edit'
                },
                {
                    name: '发起投票',
                    description: '发起投票',
                    icon: 'el-icon-check'
                }


            ]
        },

        {
            name: '测评',
            steps: [
             {
                    code: 'setType',
                    name: '选择问卷类型',
                    description: '设置问卷类型',
                    icon: 'el-icon-menu'
                },
                {
                    code: 'create',
                    name: '创建问卷',
                    description: '问卷基本信息',
                    icon: 'el-icon-info'
                },
                {
                    name: '添加题目',
                    description: '设置题目',
                    icon: 'el-icon-edit'
                },
                {
                    name: '题目维度',
                    description: '设置题目维度',
                    icon: 'el-icon-setting'
                },

                {
                    name: '答案维度',
                    description: '设置答案维度',
                    icon: 'el-icon-setting'
                },

                {
                    name: '发布',
                    description: '发布问卷',
                    icon: 'el-icon-check'
                }

            ]
        }


        ]
}