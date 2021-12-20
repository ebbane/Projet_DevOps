pipeline {

    agent any

    environment {
        SSH = 'SSH'
    }

    stages {

        stage('Generate new ssh') {
            steps {
                script {
//                    sh "rm ~/.ssh/id_rsa"
                    sh "ssh-keygen -q -t rsa -N '' -f ~/.ssh/id_rsa"
                }
            }
        }

        stage('Get the public key') {
            steps {
                script {
                    sh "cat ~/.ssh/id_rsa.pub"
                }
            }
        }

    }
}