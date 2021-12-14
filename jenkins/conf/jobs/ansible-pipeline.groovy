pipeline {

    agent any

    environment {
        ANSIBLE = 'Ansible'
    }

    stages {
        stage('Set host list') {
            steps {

                sh "sed -i 's/AWS_HOST/${params.AWS_HOST}/g' ${params.ANSIBLE_PATH}/hosts "

            }
        }
        stage('Set AWS IP address in api url') {
            steps {
                script {
                    sh "sed -i 's/AWS_HOST/${params.AWS_HOST}/g' ${params.ANSIBLE_PATH}/monitor-playbook.yml "
                }

            }
        }


        stage('Ansible playbook') {
            steps {
                dir("${params.ANSIBLE_PATH}"){

                    sh "ansible-playbook playbook.yml -i hosts --private-key ${params.SSH_KEY_PATH}"

                }
            }
        }

    }
}